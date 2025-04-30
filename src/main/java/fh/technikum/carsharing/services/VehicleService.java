package fh.technikum.carsharing.services;

import fh.technikum.carsharing.persistence.entity.Vehicle;
import fh.technikum.carsharing.persistence.entity.dto.VehicleDto;
import fh.technikum.carsharing.persistence.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class responsible for handling operations related to vehicles,
 * such as retrieving, creating, and deleting vehicles.
 */
@Service
public class VehicleService {

    @Autowired
    VehicleRepository vehicleRepository;

    /**
     * Retrieves all vehicles stored in the system (passthrough from service to controller).
     *
     * @return A list of all vehicles.
     */
    public List<Vehicle> getAll() {
        return vehicleRepository.getAll();
    }

    /**
     * Retrieves a vehicle by its unique ID (passthrough).
     *
     * @param vehicleId The ID of the vehicle to retrieve.
     * @return The vehicle corresponding to the given ID, or null if not found.
     */
    public Vehicle getById(Long vehicleId) {
        return vehicleRepository.getById(vehicleId);
    }

    /**
     * Creates a new vehicle from the provided VehicleDto.
     * The method assigns a unique ID to the new vehicle and stores it in the system.
     *
     * @param vehicleDto The data transfer object containing the vehicle's information.
     * @return The created Vehicle object, or null if a vehicle with the same ID already exists.
     */
    public Vehicle create(VehicleDto vehicleDto) {
        // No need to check if the vehicle exists; every vehicle registration is unique.
        if (!vehicleRepository.exists(vehicleDto.getVehicleId())) {
            return vehicleRepository.create(vehicleDto);
        }
        return null;
    }

    /**
     * Deletes a vehicle from the system by its unique ID.
     *
     * @param vehicleId The ID of the vehicle to delete.
     * @return The deleted Vehicle object, or null if no vehicle with the given ID was found.
     */
    public Vehicle deleteById(Long vehicleId) {
        return vehicleRepository.deleteById(vehicleId);
    }


    /**
     * This method is only for testing purposes! (passthrough)
     * Don't use it in any other case
     */
    public void reset() {
        vehicleRepository.reset();
    }
}
