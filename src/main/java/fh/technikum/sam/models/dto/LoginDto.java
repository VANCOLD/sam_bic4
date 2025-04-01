package fh.technikum.sam.models.dto;

import lombok.Data;

/**
 * Data Transfer Object (DTO) for user login credentials.
 * <p>
 * This class contains only the necessary fields for user authentication: username and password.
 * It implements {@link DataTransferObject} to be used in communication between different layers of the application.
 * </p>
 */
@Data
public class LoginDto implements DataTransferObject {

    /**
     * The username of the user attempting to log in.
     */
    private String username;

    /**
     * The password of the user attempting to log in.
     */
    private String password;
}
