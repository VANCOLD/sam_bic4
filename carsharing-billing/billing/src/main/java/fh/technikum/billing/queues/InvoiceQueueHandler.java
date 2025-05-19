package fh.technikum.billing.queues;

import com.rabbitmq.client.DeliverCallback;
import fh.technikum.billing.persistence.InvoiceDto;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static fh.technikum.billing.Utils.Constants.invoiceLocation;

public class InvoiceQueueHandler extends QueueHandler<InvoiceDto>{

    public InvoiceQueueHandler(String queueName) throws Exception {
        super(queueName, InvoiceDto.class);
    }

    @Override
    public void run() {
        logger.info(" [*] Waiting for messages from queue '{}'...", queueName);

        try {
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                try {
                    String messageBody = new String(delivery.getBody(), StandardCharsets.UTF_8);
                    InvoiceDto dto = convertToDto(messageBody);
                    logger.info("Received {}: {}",dto.getClass().getSimpleName(),dto);


                    String userHome = System.getProperty("user.home");
                    Path dirPath = Paths.get(userHome, "Documents", invoiceLocation);
                    Files.createDirectories(dirPath);

                    String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
                    String fileName = String.format("billing_%d_%s.txt", dto.getUserId(), timestamp);
                    Path filePath = dirPath.resolve(fileName);


                    Files.writeString(filePath, "User ID: " + dto.getUserId());
                    logger.info("Invoice written to {}", filePath);

                } catch (Exception e) {
                    logger.error("Failed to process or write invoice from queue {}", queueName, e);
                }
            };

            channel.basicConsume(queueName, true, deliverCallback, consumerTag ->
                    logger.info("Consumer {} cancelled", consumerTag)
            );

            while (!Thread.currentThread().isInterrupted()) {
                Thread.sleep(1000);
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.warn("QueueHandler '{}' was interrupted, stopping...", queueName);
        } catch (Exception e) {
            logger.error("Error in QueueHandler '{}'", queueName, e);
        } finally {
            closeResources();
        }
    }
}
