package fh.technikum.sam.services;

import fh.technikum.sam.models.Vehicle;
import fh.technikum.sam.models.dto.VehicleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service class responsible for handling operations related to vehicles,
 * such as retrieving, creating, and deleting vehicles.
 */
@Service
public class VehicleService {

    @Autowired
    private DtoTransformerService dtoTransformerService;

    /**
     * Counter used to generate unique vehicle IDs.
     */
    private static Long vehicleIdCounter = 1L;

    /**
     * In-memory map storing the vehicles by their unique vehicle ID.
     */
    private Map<Long, Vehicle>  vehicleMap = new HashMap<>();

    /**
     * Retrieves all vehicles stored in the system.
     *
     * @return A list of all vehicles.
     */
    public List<Vehicle> getAll() {
        return vehicleMap.values().stream().toList();
    }

    /**
     * Retrieves a vehicle by its unique ID.
     *
     * @param vehicleId The ID of the vehicle to retrieve.
     * @return The vehicle corresponding to the given ID, or null if not found.
     */
    public Vehicle getById(Long vehicleId) {
        return vehicleMap.get(vehicleId);
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
        if (!vehicleMap.containsKey(vehicleDto.getVehicleId())) {
            vehicleDto.setVehicleId(vehicleIdCounter);
            Vehicle vehicleToSave = dtoTransformerService.transformToModel(vehicleDto, Vehicle.class);
            this.vehicleMap.put(vehicleIdCounter, vehicleToSave);
            vehicleIdCounter++;
            return vehicleToSave;
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
        return this.vehicleMap.remove(vehicleId);
    }


    /**
     * This method is only for testing purposes!
     * Don't use it in any other case
     */
    public void reset() {
        this.vehicleMap = new HashMap<>();
        vehicleIdCounter = 1L;
    }
}
