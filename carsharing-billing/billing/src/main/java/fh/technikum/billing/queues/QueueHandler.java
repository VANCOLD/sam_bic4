package fh.technikum.billing.queues;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import fh.technikum.billing.Utils.JsonObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

import static fh.technikum.billing.config.Config.host;
import static fh.technikum.billing.config.Config.username;
import static fh.technikum.billing.config.Config.password;

public class QueueHandler<T> extends Thread {

    protected static final Logger logger = LoggerFactory.getLogger(QueueHandler.class);

    protected final String queueName;
    protected final Connection connection;
    protected final Channel channel;
    protected final Class<T> conversionClass;

    public QueueHandler(String queueName, Class<T> conversionClass) throws Exception {

        this.queueName = queueName;
        this.conversionClass = conversionClass;

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);
        factory.setUsername(username);
        factory.setPassword(password);

        this.connection = factory.newConnection();
        this.channel = connection.createChannel();

        logger.info("Successfully create the Handler-Object for the queue {}", this.queueName);
    }

    @Override
    public void run() {
        try {
            logger.info(" [*] Waiting for messages from queue '{}'...", queueName);

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String messageBody = new String(delivery.getBody(), StandardCharsets.UTF_8);
                T dto = convertToDto(messageBody);
                logger.info("Received {}: {}", dto.getClass().getSimpleName(), dto);
            };

            channel.basicConsume(queueName, true, deliverCallback, consumerTag -> logger.info("Consumer {} cancelled", consumerTag));

            while (!Thread.currentThread().isInterrupted()) {
                Thread.sleep(1000);
            }
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
            logger.warn("QueueHandler {} was interrupted, stopping...", queueName);
        } catch (Exception e) {
            logger.error("An error occurred while trying to communicate with the queue {}", queueName);
        } finally {
            closeResources();
        }
    }

    protected T convertToDto(String json) throws JsonProcessingException {
        return JsonObjectMapper.getInstance().readValue(json, conversionClass);
    }

    void closeResources() {
        try {
            if (channel != null && channel.isOpen()) channel.close();
            if (connection != null && connection.isOpen()) connection.close();
        } catch (Exception e) {
            logger.error("An error occurred while closing the resources of the QueueHandler {}", queueName);
        }
    }
}
