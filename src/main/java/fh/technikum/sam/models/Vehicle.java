package fh.technikum.sam.models;

import fh.technikum.sam.models.enums.Priority;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Vehicle{

        /* Unique identifier for the vehicle. */
        Long vehicleId;

        /* Longitude value of the vehicle's current location. */
        Double longitude;

        /* Latitude value of the vehicle's current location. */
        Double latitude;

        /* Timestamp of the last update regarding the vehicle's status. */
        LocalDateTime currentTimestamp;

        /* Indicates if the vehicle is currently occupied by a customer. */
        Boolean isOccupied;

        /* Unique identifier of the driver or customer occupying the vehicle. */
        Integer driverId;

        /* The distance the vehicle has traveled since the last update. */
        Double distanceSinceLastUpdate;

        /* Time in seconds that has passed since the last update. */
        Double timeSinceLastUpdate;

        /* The priority level of the vehicle's current status. */
        Priority priority;

        /* Description of any emergency, if applicable. */
        String emergencyDescription;
}
