package fh.technikum.carsharing.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import fh.technikum.carsharing.config.RabbitMQConfig;
import fh.technikum.carsharing.persistence.entity.dto.InvoiceDto;
import fh.technikum.carsharing.services.UserService;
import fh.technikum.carsharing.utils.JsonObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;

import static fh.technikum.carsharing.utils.ResponseMessages.*;
import static fh.technikum.carsharing.utils.Utils.removeTokenPrefix;

@RestController
@RequestMapping("api/invoices/")
public class InvoiceController {

    @Autowired
    UserService userService;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @PostMapping("{user-id}")
    public ResponseEntity<String> generateInvoiceForUser(@PathVariable(name="user-id") Long userId,
                                                         @RequestHeader(name = "Authorization") String token) throws JsonProcessingException {

        token = removeTokenPrefix(token);

        if (!userService.isLoggedIn(token) || !userService.isFleetManager(token)) {
           return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(INVALID_AUTHORISATION_INVOICE);
        }

        if(!userService.userExists(userId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(USER_NON_EXISTENT);
        }

        InvoiceDto invoice = new InvoiceDto();
        invoice.setUserId(userId);

        String json = JsonObjectMapper.getInstance().writeValueAsString(invoice);
        rabbitTemplate.convertAndSend(RabbitMQConfig.CREATE_INVOICE_QUEUE_NAME, json.getBytes(StandardCharsets.UTF_8));
        return ResponseEntity.status(HttpStatus.CREATED).body(SUCCESSFULL_INVOICE_CREATION + userId);
    }
}
