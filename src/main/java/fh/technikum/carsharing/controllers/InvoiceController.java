package fh.technikum.carsharing.controllers;

import fh.technikum.carsharing.services.UserService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static fh.technikum.carsharing.utils.ResponseMessages.*;

@RestController
@RequestMapping("api/invoices/")
public class InvoiceController {

    @Autowired
    UserService userService;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @PostMapping("{user-id}")
    public ResponseEntity<String> generateInvoiceForuser(@PathVariable(name="user-id") Long userId,
                                                         @RequestHeader(name = "Authorization") String token) {

        if (!userService.isLoggedIn(token) || !userService.isFleetManager(token)) {
           return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(INVALID_AUTHORISATION_INVOICE);
        }

        if(!userService.userExists(userId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(USER_NON_EXISTENT);
        }

        rabbitTemplate.convertAndSend("Create_Invoice","" + userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(SUCCESSFULL_INVOICE_CREATION + userId);
    }
}
