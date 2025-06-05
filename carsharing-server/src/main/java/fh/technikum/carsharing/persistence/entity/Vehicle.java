package fh.technikum.carsharing.persistence.entity;

import fh.technikum.carsharing.persistence.entity.enums.Priority;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * Represents a vehicle in the system, including its current location, status, and priority.
 * <p>
 * This class contains all relevant information regarding the vehicle, such as its position, status,
 * and any emergency situation it may be involved in. It is used for fleet management systems and
 * tracking the state of vehicles.
 * </p>
 */
@Data
@Entity
@Table(name="vehicles")
public class Vehicle {

        /**
         * The unique identifier for the vehicle.
         * <p>
         * This ID is used to uniquely identify each vehicle within the system.
         * </p>
         */
        @EqualsAndHashCode.Exclude
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long vehicleId;

        /**
         * The longitude of the vehicle's current location.
         * <p>
         * This value represents the vehicle's longitude coordinate in a GPS coordinate system.
         * </p>
         */
        @Column(name="longitude")
        private Double longitude;

        /**
         * The latitude of the vehicle's current location.
         * <p>
         * This value represents the vehicle's latitude coordinate in a GPS coordinate system.
         * </p>
         */
        @Column(name="latitude")
        private Double latitude;

        /**
         * The timestamp of the last update regarding the vehicle's status.
         * <p>
         * This timestamp records the last time the vehicle's data was updated, such as its position, status, or any emergency.
         * </p>
         */
        @Column(name="currentTimestampx") // current_timestamp is a keyword, so we have to change the column name
        private LocalDateTime currentTimestamp;

        /**
         * Indicates if the vehicle is currently occupied by a customer.
         * <p>
         * This field is used to track whether the vehicle is in use by a customer or is available for other assignments.
         * </p>
         */
        @Column(name="isOccupied")
        private Boolean isOccupied;

        /**
         * The unique identifier of the driver or customer occupying the vehicle.
         * <p>
         * This is the ID of the individual currently using the vehicle.
         * </p>
         */
        @Column(name="driverId")
        private Long driverId;

        /**
         * The distance the vehicle has traveled since the last update.
         * <p>
         * This field stores the distance in kilometers or miles the vehicle has moved since the last update.
         * </p>
         */
        @Column(name="distanceSinceLastUpdate")
        private Double distanceSinceLastUpdate;

        /**
         * The time in seconds that has passed since the last update.
         * <p>
         * This field represents the time elapsed since the last update to the vehicle's data.
         * </p>
         */
        @Column(name="timeSinceLastUpdate")
        private Double timeSinceLastUpdate;

        /**
         * The priority level of the vehicle's current status.
         * <p>
         * This field indicates the urgency or importance of the vehicle's current task or condition.
         * </p>
         */
        @Column(name="priority")
        @Enumerated(EnumType.STRING)
        @EqualsAndHashCode.Exclude
        private Priority priority;

        /**
         * A description of any emergency the vehicle is involved in, if applicable.
         * <p>
         * This field may be used to store information about an emergency situation, such as an accident or breakdown.
         * </p>
         */
        @Column(name="emergencyDescription")
        @EqualsAndHashCode.Exclude
        private String emergencyDescription;
}
