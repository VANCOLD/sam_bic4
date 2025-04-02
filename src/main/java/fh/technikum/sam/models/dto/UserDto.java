package fh.technikum.sam.models.dto;

import fh.technikum.sam.models.User;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(
        name = "User",
        description = "Represents a user in the carsharing system",
        example = """
        {
          "userId": 12345,
          "username": "johndoe",
          "firstName": "John",
          "surname": "Doe",
          "age": 30,
          "fleetManager": false
        }"""
)
public class UserDto implements DataTransferObject {

    @Schema(
            description = "The unique identifier for the user",
            example = "12345",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private Long userId;

    @Schema(
            description = "Unique username for authentication",
            example = "johndoe",
            requiredMode = Schema.RequiredMode.REQUIRED,
            minLength = 4,
            maxLength = 20
    )
    private String username;

    @Schema(
            description = "User's password (hashed when stored)",
            accessMode = Schema.AccessMode.WRITE_ONLY,
            requiredMode = Schema.RequiredMode.REQUIRED,
            minLength = 8
    )
    private String password;

    @Schema(
            description = "User's first name",
            example = "John",
            requiredMode = Schema.RequiredMode.REQUIRED,
            minLength = 2
    )
    private String firstName;

    @Schema(
            description = "User's last name",
            example = "Doe",
            requiredMode = Schema.RequiredMode.REQUIRED,
            minLength = 2
    )
    private String surname;

    @Schema(
            description = "User's age in years",
            example = "30",
            minimum = "18",
            maximum = "120"
    )
    private Integer age;

    @Schema(
            description = "Driver's license number",
            accessMode = Schema.AccessMode.WRITE_ONLY,
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Integer drivingLicenseNumber;

    @Schema(
            description = "Credit card number (PCI compliant handling required)",
            accessMode = Schema.AccessMode.WRITE_ONLY,
            hidden = true
    )
    private Integer creditCardNumber;

    @Schema(
            description = "Indicates if user has fleet manager privileges",
            example = "false",
            defaultValue = "false"
    )
    private Boolean fleetManager;
}