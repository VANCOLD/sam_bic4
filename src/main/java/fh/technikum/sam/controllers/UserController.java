package fh.technikum.sam.controllers;

import fh.technikum.sam.models.User;
import fh.technikum.sam.models.dto.LoginDto;
import fh.technikum.sam.models.dto.UserDto;
import fh.technikum.sam.models.dto.UserPublicDto;
import fh.technikum.sam.services.DtoTransformerService;
import fh.technikum.sam.services.UserService;
import fh.technikum.sam.utils.Tuple;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static fh.technikum.sam.utils.ResponseMessages.*;
import static fh.technikum.sam.utils.Utils.removeTokenPrefix;

@Controller
@RequestMapping("api/users")
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private DtoTransformerService dtoTransformerService;

    @PostMapping("/register")
    @Operation(
            summary = "Register a new user",
            description = "Creates a new user account in the carsharing system"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "User registration details",
            required = true,
            content = @Content(
                    schema = @Schema(implementation = UserDto.class),
                    examples = @ExampleObject(
                            value = """
                {
                  "username": "newuser",
                  "password": "SecurePass123!",
                  "firstName": "John",
                  "surname": "Doe",
                  "age": 25,
                  "drivingLicenseNumber": 123456789,
                  "creditCardNumber": 4111111111111111,
                  "fleetManager": false
                }"""
                    )
            )
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "User registered successfully",
                    content = @Content(
                            schema = @Schema(implementation = UserPublicDto.class),
                            examples = @ExampleObject(
                                    value = """
                    {
                      "userId": 1,
                      "username": "newuser",
                      "firstName": "John",
                      "surname": "Doe",
                      "age": 25,
                      "fleetManager": false
                    }"""
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "User already exists",
                    content = @Content(
                            examples = @ExampleObject(value = USER_ALREADY_EXISTS)
                    )
            )
    })
    public ResponseEntity<?> register(@RequestBody UserDto user) {
        User userToRegister = userService.register(user);

        if (userToRegister == null) {
            return ResponseEntity.badRequest().body(USER_ALREADY_EXISTS);
        } else {
            UserPublicDto publicUser = dtoTransformerService.transformToDto(userToRegister, UserPublicDto.class);
            return ResponseEntity.status(HttpStatus.CREATED).body(publicUser);
        }
    }

    @PostMapping("/login")
    @Operation(
            summary = "Authenticate user",
            description = "Logs in a user and returns an authentication token"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "User credentials",
            required = true,
            content = @Content(
                    schema = @Schema(implementation = LoginDto.class),
                    examples = @ExampleObject(
                            value = """
                {
                  "username": "existinguser",
                  "password": "UserPass123!"
                }"""
                    )
            )
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Login successful",
                    content = @Content(
                            examples = @ExampleObject(value = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "User already logged in",
                    content = @Content(
                            examples = @ExampleObject(value = USER_ALREADY_LOGGED_IN)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Invalid credentials",
                    content = @Content(
                            examples = @ExampleObject(value = INVALID_CREDENTIALS)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found",
                    content = @Content(
                            examples = @ExampleObject(value = USER_NON_EXISTENT)
                    )
            )
    })
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {
        Tuple<HttpStatus, String> result = userService.loginUser(loginDto);
        return ResponseEntity.status(result.getKey()).body(result.getValue());
    }

    @PostMapping("/logout")
    @Operation(
            summary = "Logout user",
            description = "Invalidates the user's authentication token",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Logout successful",
                    content = @Content(
                            examples = @ExampleObject(value = USER_LOGGED_OUT)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found",
                    content = @Content(
                            examples = @ExampleObject(value = USER_NOT_LOGGED_IN)
                    )
            )
    })
    public ResponseEntity<String> logout(
            @RequestHeader(name = "Authorization")
            @Parameter(
                    description = "Bearer token",
                    required = true,
                    example = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
            )
            String token
    ) {
        token = removeTokenPrefix(token);
        Tuple<HttpStatus, String> result = userService.logoutUser(token);
        return ResponseEntity.status(result.getKey()).body(result.getValue());
    }

    @GetMapping
    @Operation(
            summary = "Get all users",
            description = "Retrieves all registered users (Fleet Manager only)",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Users retrieved successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = UserPublicDto.class)),
                            examples = @ExampleObject(
                                    value = """
                    [
                      {
                        "userId": 1,
                        "username": "user1",
                        "firstName": "John",
                        "surname": "Doe",
                        "age": 25,
                        "fleetManager": false
                      },
                      {
                        "userId": 2,
                        "username": "user2",
                        "firstName": "Jane",
                        "surname": "Smith",
                        "age": 30,
                        "fleetManager": true
                      }
                    ]"""
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "No users found",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden - Insufficient privileges",
                    content = @Content
            )
    })
    public ResponseEntity<List<UserPublicDto>> getAll(
            @RequestHeader(name = "Authorization")
            @Parameter(
                    description = "Bearer token",
                    required = true,
                    example = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
            )
            String token
    ) {
        token = removeTokenPrefix(token);

        if (userService.isFleetManager(token) && userService.isLoggedIn(token)) {
            List<User> userList = userService.getAll();

            if (userList.isEmpty()) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.ok(userList.stream()
                        .map(user -> dtoTransformerService.transformToDto(user, UserPublicDto.class))
                        .toList());
            }
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}