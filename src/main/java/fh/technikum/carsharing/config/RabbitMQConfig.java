package fh.technikum.carsharing.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String STATUS_UPDATE_QUEUE_NAME = "Status_Update";
    public static final String EMERGENCY_UPDATE_QUEUE_NAME = "Emergency_Update";
    public static final String CREATE_INVOICE_QUEUE_NAME = "Create_Invoice";

    @Bean
    public Queue statusUpdateQueue() {
        return new Queue(STATUS_UPDATE_QUEUE_NAME, true);
    }

    @Bean
    public Queue emergencyStatusQueue() {
        return new Queue(EMERGENCY_UPDATE_QUEUE_NAME, true);
    }

    @Bean
    public Queue createInvoiceQueue() {
        return new Queue(CREATE_INVOICE_QUEUE_NAME, true);
    }


    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        return connectionFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setDefaultReceiveQueue(CREATE_INVOICE_QUEUE_NAME);
        return rabbitTemplate;
    }

}
