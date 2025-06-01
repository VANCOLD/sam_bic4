package fh.technikum.carsharing.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import fh.technikum.carsharing.config.RabbitMQConfig;
import fh.technikum.carsharing.persistence.entity.dto.EmergencyStatusDto;
import fh.technikum.carsharing.persistence.entity.dto.VehicleStatusDto;
import fh.technikum.carsharing.services.UserService;
import fh.technikum.carsharing.services.VehicleService;
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
@RequestMapping("api/devices/")
public class VehicleStatusUpdateController {

    @Autowired
    UserService userService;

    @Autowired
    VehicleService vehicleService;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @PostMapping("{vehicle-id}/status")
    public ResponseEntity<String> vehicleStatusUpdate(@PathVariable(name="vehicle-id") Long vehicleId,
                                                 @RequestHeader(name = "Authorization") String token,
                                                 @RequestBody VehicleStatusDto vehicleStatusDto)
                                                    throws JsonProcessingException {

        token = removeTokenPrefix(token);

        // Vehicle is found if we do not get a null value! Therefore, if not logged in or null as vehicle is bad!
        if (!userService.isLoggedIn(token) || vehicleService.getById(vehicleId) == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(INVALID_AUTHORISATION_STATUS);
        }

        // Since we have 2 services, we send as json, and use the object mapper to map it to the dto in the other service.
        // This isn't the best solution but for this simple exercise it will suffice
        String json = JsonObjectMapper.getInstance().writeValueAsString(vehicleStatusDto);
        rabbitTemplate.convertAndSend(RabbitMQConfig.STATUS_UPDATE_QUEUE_NAME, json.getBytes(StandardCharsets.UTF_8));
        return ResponseEntity.status(HttpStatus.CREATED).body(SUCCESSFUL_STATUS_UPDATE + vehicleStatusDto.getClass().getSimpleName());
    }

    @PostMapping("{vehicle-id}/alarm")
    public ResponseEntity<String> vehicleEmergencyStatusUpdate(@PathVariable(name="vehicle-id") Long vehicleId,
                                                           @RequestHeader(name = "Authorization") String token,
                                                           @RequestBody EmergencyStatusDto emergencyStatusDto)
                                                            throws JsonProcessingException {

        token = removeTokenPrefix(token);

        if (!userService.isLoggedIn(token) || vehicleService.getById(vehicleId) == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(INVALID_AUTHORISATION_STATUS);
        }

        // Since we have 2 services, we send as json, and use the object mapper to map it to the dto in the other service.
        // This isn't the best solution but for this simple exercise it will suffice
        String json = JsonObjectMapper.getInstance().writeValueAsString(emergencyStatusDto);
        rabbitTemplate.convertAndSend(RabbitMQConfig.EMERGENCY_UPDATE_QUEUE_NAME, json.getBytes(StandardCharsets.UTF_8));
        return ResponseEntity.status(HttpStatus.CREATED).body(SUCCESSFUL_STATUS_UPDATE + emergencyStatusDto.getClass().getSimpleName());
    }
}
