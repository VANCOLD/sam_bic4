package fh.technikum.carsharing.controllers;

import fh.technikum.carsharing.persistence.entity.dto.EmergencyStatusDto;
import fh.technikum.carsharing.persistence.entity.dto.VehicleStatusDto;
import fh.technikum.carsharing.services.UserService;
import fh.technikum.carsharing.services.VehicleService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static fh.technikum.carsharing.utils.ResponseMessages.*;


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
                                                 @RequestBody VehicleStatusDto vehicleStatusDto) {

        // Vehicle is found if we do not get a null value! Therefore, if not logged in or null as vehicle is bad!
        if (!userService.isLoggedIn(token) || vehicleService.getById(vehicleId) == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(INVALID_AUTHORISATION_STATUS);
        }

        rabbitTemplate.convertAndSend("Status_Update", vehicleStatusDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(SUCCESSFULL_STATUS_UPDATE + vehicleStatusDto.getClass().getCanonicalName());
    }

    @PostMapping("{vehicle-id}/alarm")
    public ResponseEntity<String> vehicleEmergencyStatusUpdate(@PathVariable(name="vehicle-id") Long vehicleId,
                                                           @RequestHeader(name = "Authorization") String token,
                                                           @RequestBody EmergencyStatusDto emergencyStatusDto) {
        if (!userService.isLoggedIn(token) || vehicleService.getById(vehicleId) == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(INVALID_AUTHORISATION_STATUS);
        }

        rabbitTemplate.convertAndSend("Emergency_Update", emergencyStatusDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(SUCCESSFULL_STATUS_UPDATE + emergencyStatusDto.getClass().getCanonicalName());
    }
}
