package fh.technikum.carsharing.persistence.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Data Transfer Object (DTO) for user login credentials.
 * <p>
 * This class contains only the necessary fields for user authentication: username and password.
 * It implements {@link DataTransferObject} to be used in communication between different layers of the application.
 * </p>
 */
@Data
@Schema(
        name = "LoginCredentials",
        description = "Contains authentication credentials for user login",
        example = """
        {
          "username": "john doe",
          "password": "securePassword123!"
        }"""
)
public class LoginDto implements DataTransferObject {

    @Schema(
            description = "Unique username for authentication",
            example = "john doe",
            requiredMode = Schema.RequiredMode.REQUIRED,
            minLength = 4,
            maxLength = 20,
            pattern = "^[a-zA-Z0-9_]+$"  // Alphanumeric + underscore
    )
    private String username;

    @Schema(
            description = "User's password for authentication",
            example = "securePassword123!",
            requiredMode = Schema.RequiredMode.REQUIRED,
            minLength = 8,
            maxLength = 64,
            format = "password",
            accessMode = Schema.AccessMode.WRITE_ONLY,
            pattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$"
    )
    private String password;
}