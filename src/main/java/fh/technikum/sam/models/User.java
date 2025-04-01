package fh.technikum.sam.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Represents a user in the system, which can also be a driver if they have an associated driver's license.
 * <p>
 * This class contains user-related information such as personal details, login credentials, and status
 * as a fleet manager. It is used to represent customers in the system and is also employed to manage driver
 * details in a fleet management system.
 * </p>
 */
@Data
public class User {

        /**
         * The unique identifier for the user in the system. This is also used as the driver's ID.
         * <p>
         * This ID is used to uniquely identify each user within the system.
         * </p>
         */
        @EqualsAndHashCode.Exclude
        private Long userId;

        /**
         * The username of the user. Used for login purposes.
         * <p>
         * This field is required to authenticate the user and must be unique within the system.
         * </p>
         */
        private String username;

        /**
         * The password of the user. This should never be stored in plaintext.
         * <p>
         * It's recommended to store the password in a hashed form to protect user privacy and security.
         * </p>
         */
        private String password;

        /**
         * The first name of the user.
         * <p>
         * This field holds the user's given name and is used for personalization or addressing the user.
         * </p>
         */
        private String firstName;

        /**
         * The surname (last name) of the user.
         * <p>
         * This field holds the user's family name and is typically used along with the first name.
         * </p>
         */
        private String surname;

        /**
         * The age of the user.
         * <p>
         * This is used to determine the user's age, which may be used for validation (e.g., age restrictions).
         * </p>
         */
        private Integer age;

        /**
         * The unique driving license number associated with the user.
         * <p>
         * This field is used for identifying a user as a valid driver and is important for fleet management systems.
         * </p>
         */
        private Integer drivingLicenseNumber;

        /**
         * The credit card number of the user. This is securely stored and should comply with relevant standards.
         * <p>
         * Sensitive information such as credit card numbers should be securely encrypted and stored in compliance with
         * PCI DSS standards to ensure privacy and security.
         * </p>
         */
        private Integer creditCardNumber;

        /**
         * Indicates if the user is a fleet manager.
         * <p>
         * This boolean field determines whether the user has privileges to manage vehicles and drivers in the fleet.
         * </p>
         */
        @EqualsAndHashCode.Exclude
        private Boolean fleetManager;
}
