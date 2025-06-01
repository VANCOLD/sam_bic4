package fh.technikum.carsharing.persistence.repository;

import fh.technikum.carsharing.persistence.entity.Vehicle;
import fh.technikum.carsharing.persistence.entity.dto.VehicleDto;
import fh.technikum.carsharing.services.DtoTransformerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class VehicleRepository {

    @Autowired
    private DtoTransformerService dtoTransformerService;

    /**
     * Counter used to generate unique vehicle IDs.
     */
    private static Long vehicleIdCounter = 1L;

    /**
     * In-memory map storing the vehicles by their unique vehicle ID.
     */
    private final Map<Long, Vehicle> vehicleMap = new HashMap<>();

    /**
     * Creates a new vehicle from the provided VehicleDto.
     * The method assigns a unique ID to the new vehicle and stores it in the system.
     *
     * @param vehicleDto The data transfer object containing the vehicle's information.
     * @return The created Vehicle object, or null if a vehicle with the same ID already exists.
     */
    public Vehicle create(VehicleDto vehicleDto) {
        vehicleDto.setVehicleId(vehicleIdCounter);
        Vehicle vehicleToSave = dtoTransformerService.transformToModel(vehicleDto, Vehicle.class);
        this.vehicleMap.put(vehicleIdCounter, vehicleToSave);
        vehicleIdCounter++;
        return vehicleToSave;
    }

    /**
     * Retrieves all vehicles stored in the system (pass through from service to controller).
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
     * Checks if a given id is contained in the vehicle-map
     *
     * @param vehicleId the id of the vehicle we like to look for
     * @return true if the vehicle is stored inside the map, false if not
     */
    public boolean exists(Long vehicleId) {
        return vehicleMap.containsKey(vehicleId);
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
        this.vehicleMap.clear();
        vehicleIdCounter = 1L;
    }
}
