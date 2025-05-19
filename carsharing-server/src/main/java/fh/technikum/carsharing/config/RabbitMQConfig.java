package fh.technikum.carsharing.config;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.host}")
    private String host;

    @Value("${rabbitmq.username}")
    private String username;

    @Value("${rabbitmq.password}")
    private String password;

    @Value("${rabbitmq.default-queue}")
    private String defaultQueue;

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
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        return connectionFactory;
    }

    @Bean
    public AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setDefaultReceiveQueue(defaultQueue);
        return rabbitTemplate;
    }
}

