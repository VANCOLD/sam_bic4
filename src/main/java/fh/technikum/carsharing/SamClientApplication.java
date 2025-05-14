package fh.technikum.carsharing;

import com.rabbitmq.client.DeliverCallback;
import fh.technikum.carsharing.config.RabbitMQConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class SamClientApplication {


    protected static final Logger logger = LoggerFactory.getLogger(SamClientApplication.class);
    protected static RabbitMQConfig config = new RabbitMQConfig();

    public static void main(String[] args) throws IOException {
        updateStatusMethodCallback();
        emergencyStatusMethodCallback();
        invoiceMethodCallback();
    }

    public static void updateStatusMethodCallback() throws IOException {
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println("\nYou received: " + message);
        };

        config.connectionFactory()
                .createConnection()
                .createChannel(false)
                .basicConsume(RabbitMQConfig.STATUS_UPDATE_QUEUE_NAME, true, deliverCallback, consumerTag -> {});
    }

    public static void emergencyStatusMethodCallback() throws IOException {
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println("\nYou received: " + message);
        };

        config.connectionFactory()
                .createConnection()
                .createChannel(false)
                .basicConsume(RabbitMQConfig.EMERGENCY_UPDATE_QUEUE_NAME, true, deliverCallback, consumerTag -> {});
    }

    public static void invoiceMethodCallback() throws IOException {
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println("\nYou received: " + message);
        };

        config.connectionFactory()
                .createConnection()
                .createChannel(false)
                .basicConsume(RabbitMQConfig.CREATE_INVOICE_QUEUE_NAME, true, deliverCallback, consumerTag -> {});
    }
}
