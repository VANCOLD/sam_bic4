package fh.technikum.sam.models.dto;

import fh.technikum.sam.models.User;
import lombok.Data;

/**
 * Data Transfer Object (DTO) for {@link User} containing detailed user information.
 * <p>
 * This class is used to transfer user-related data between different layers of the application,
 * such as the service and controller layers. It contains various user details like username,
 * first name, surname, age, etc.
 * </p>
 */
@Data
public class UserDto implements DataTransferObject {

    /**
     * The unique identifier for the user.
     */
    private Long userId;

    /**
     * The username of the user.
     */
    private String username;

    /**
     * The password of the user (should be hashed when stored in the database).
     */
    private String password;

    /**
     * The first name of the user.
     */
    private String firstName;

    /**
     * The surname (last name) of the user.
     */
    private String surname;

    /**
     * The age of the user.
     */
    private Integer age;

    /**
     * The driving license number of the user.
     */
    private Integer drivingLicenseNumber;

    /**
     * The credit card number associated with the user.
     * <p>
     * Note: Be mindful of privacy concerns when handling sensitive information such as credit card numbers.
     * </p>
     */
    private Integer creditCardNumber;

    /**
     * Indicates whether the user is a fleet manager.
     */
    private Boolean fleetManager;
}
