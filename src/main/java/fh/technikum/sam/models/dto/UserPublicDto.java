package fh.technikum.sam.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Public-facing user representation without sensitive data
 */
@Data
@Schema(
        name = "UserPublic",
        description = "Public user profile information",
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
public class UserPublicDto implements DataTransferObject {

    @Schema(
            description = "The unique identifier for the user",
            example = "12345",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private Long userId;

    @Schema(
            description = "Unique username",
            example = "johndoe"
    )
    private String username;

    @Schema(
            description = "User's first name",
            example = "John"
    )
    private String firstName;

    @Schema(
            description = "User's last name",
            example = "Doe"
    )
    private String surname;

    @Schema(
            description = "User's age in years",
            example = "30"
    )
    private Integer age;

    @Schema(
            description = "Indicates if user has fleet manager privileges",
            example = "false"
    )
    private Boolean fleetManager;
}