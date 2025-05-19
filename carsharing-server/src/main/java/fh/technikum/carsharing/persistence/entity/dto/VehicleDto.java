package fh.technikum.carsharing.persistence.entity.dto;

import fh.technikum.carsharing.persistence.entity.Vehicle;
import fh.technikum.carsharing.persistence.entity.enums.Priority;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) for {@link Vehicle}, providing detailed information about a vehicle's current state.
 * <p>
 * This class is used to transfer vehicle-related data between different layers of the application, such as the
 * service and controller layers. It includes the vehicle's location, status, and other relevant information.
 * </p>
 */
@Data
public class VehicleDto implements DataTransferObject {

    /**
     * The unique identifier of the vehicle.
     */
    private Long vehicleId;

    /**
     * The longitude coordinate of the vehicle's current position.
     */
    private Double longitude;

    /**
     * The latitude coordinate of the vehicle's current position.
     */
    private Double latitude;

    /**
     * The timestamp of when the vehicle's data was last updated.
     */
    private LocalDateTime currentTimestamp;

    /**
     * Indicates whether the vehicle is currently occupied.
     */
    private Boolean isOccupied;

    /**
     * The ID of the driver currently operating the vehicle.
     */
    private Long driverId;

    /**
     * The distance traveled by the vehicle since the last update.
     */
    private Double distanceSinceLastUpdate;

    /**
     * The time elapsed since the vehicle's data was last updated, in minutes.
     */
    private Double timeSinceLastUpdate;

    /**
     * The priority level of the vehicle.
     * <p>
     * This is typically used to indicate the urgency or importance of the vehicle's task.
     * </p>
     */
    private Priority priority;

    /**
     * A description of any emergency situation the vehicle may be involved in.
     * <p>
     * This field may be used for vehicles handling emergency situations, such as accidents or breakdowns.
     * </p>
     */
    private String emergencyDescription;
}
