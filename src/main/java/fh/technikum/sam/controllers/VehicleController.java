package fh.technikum.sam.controllers;

import fh.technikum.sam.models.Vehicle;
import fh.technikum.sam.models.dto.VehicleDto;
import fh.technikum.sam.services.DtoTransformerService;
import fh.technikum.sam.services.UserService;
import fh.technikum.sam.services.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static fh.technikum.sam.utils.ResponseMessages.NO_VEHICLE_FOUND;
import static fh.technikum.sam.utils.Utils.removeTokenPrefix;

/**
 * Controller for handling vehicle-related operations such as retrieval, creation, and deletion of vehicles.
 */
@Controller
@RequestMapping("api/vehicles")
public class VehicleController {

    @Autowired
    VehicleService vehicleService;

    @Autowired
    UserService userService;

    @Autowired
    DtoTransformerService dtoTransformerService;

    /**
     * Retrieves all vehicles if the requester is logged in and has fleet manager privileges.
     *
     * @param token the authentication token from the request header
     * @return ResponseEntity containing a list of vehicles or an appropriate HTTP status
     */
    @GetMapping
    public ResponseEntity<List<VehicleDto>> getAll(@RequestHeader(name = "Authorization") String token) {
        token = removeTokenPrefix(token);

        if (!userService.isLoggedIn(token) || !userService.isFleetManager(token)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        List<Vehicle> vehicleList = vehicleService.getAll();

        if (vehicleList.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(vehicleList.stream()
                    .map(vehicle -> dtoTransformerService.transformToDto(vehicle, VehicleDto.class))
                    .toList());
        }
    }

    /**
     * Retrieves a vehicle by its ID if the requester has appropriate access.
     *
     * @param vehicleId the ID of the vehicle to retrieve
     * @param token     the authentication token from the request header
     * @return ResponseEntity containing the vehicle details or an error message if not found
     */
    @GetMapping("/{vehicleId}")
    public ResponseEntity<?> getById(@PathVariable(name = "vehicleId") Long vehicleId,
                                     @RequestHeader(name = "Authorization") String token) {
        token = removeTokenPrefix(token);

        if (!userService.isLoggedIn(token) || !userService.isFleetManager(token)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Vehicle vehicle = vehicleService.getById(vehicleId);

        if (vehicle == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(NO_VEHICLE_FOUND + vehicleId);
        } else {
            return ResponseEntity.ok(vehicle);
        }
    }

    /**
     * Creates a new vehicle.
     *
     * @param vehicleDto the vehicle data to be created
     * @param token      the authentication token from the request header
     * @return ResponseEntity containing the created vehicle or an error status
     */
    @PostMapping
    public ResponseEntity<VehicleDto> create(@RequestBody VehicleDto vehicleDto,
                                             @RequestHeader(name = "Authorization") String token) {
        token = removeTokenPrefix(token);

        if (!userService.isLoggedIn(token) || !userService.isFleetManager(token)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Vehicle vehicleToSave = vehicleService.create(vehicleDto);
        if (vehicleToSave == null) {
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.ok(dtoTransformerService.transformToDto(vehicleToSave, VehicleDto.class));
        }
    }

    /**
     * Deletes a vehicle by its ID if the requester has appropriate access.
     *
     * @param vehicleId the ID of the vehicle to delete
     * @param token     the authentication token from the request header
     * @return ResponseEntity containing the deleted vehicle details or an error status
     */
    @DeleteMapping("/{vehicleId}")
    public ResponseEntity<VehicleDto> delete(@PathVariable(name = "vehicleId") Long vehicleId,
                                             @RequestHeader(name = "Authorization") String token) {
        token = removeTokenPrefix(token);

        if (!userService.isLoggedIn(token) || !userService.isFleetManager(token)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Vehicle vehicleToDelete = vehicleService.deleteById(vehicleId);
        if (vehicleToDelete == null) {
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.ok(dtoTransformerService.transformToDto(vehicleToDelete, VehicleDto.class));
        }
    }
}
