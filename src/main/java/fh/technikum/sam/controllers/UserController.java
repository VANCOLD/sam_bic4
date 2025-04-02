package fh.technikum.sam.controllers;

import fh.technikum.sam.models.User;
import fh.technikum.sam.models.dto.LoginDto;
import fh.technikum.sam.models.dto.UserDto;
import fh.technikum.sam.services.DtoTransformerService;
import fh.technikum.sam.services.UserService;
import fh.technikum.sam.utils.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static fh.technikum.sam.utils.ResponseMessages.USER_ALREADY_EXISTS;
import static fh.technikum.sam.utils.Utils.removeTokenPrefix;

/**
 * Controller for handling user-related operations such as registration, login, and fetching user details.
 */
@Controller
@RequestMapping("api/users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    DtoTransformerService dtoTransformerService;

    /**
     * Registers a new user.
     *
     * @param user the user details provided for registration
     * @return ResponseEntity containing the created user object or an error message if the user already exists
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDto user) {
        User userToRegister = userService.register(user);

        if (userToRegister == null) {
            return ResponseEntity.badRequest().body(USER_ALREADY_EXISTS);
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(userToRegister);
        }
    }

    /**
     * Authenticates a user and returns a token upon successful login.
     *
     * @param loginDto the login credentials
     * @return ResponseEntity containing the authentication token or an error message
     */
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {
        Tuple<HttpStatus, String> result = userService.loginUser(loginDto);
        return ResponseEntity.status(result.getKey()).body(result.getValue());
    }

    /**
     * Logs out a user by invalidating their token.
     *
     * @param token the authentication token from the request header
     * @return ResponseEntity with the logout status
     */
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader(name = "Authorization") String token) {
        token = removeTokenPrefix(token);
        Tuple<HttpStatus, String> result = userService.logoutUser(token);
        return ResponseEntity.status(result.getKey()).body(result.getValue());
    }

    /**
     * Retrieves all users if the requester is logged in and has fleet manager privileges.
     *
     * @param token the authentication token from the request header
     * @return ResponseEntity containing a list of users or an appropriate HTTP status
     */
    @GetMapping
    public ResponseEntity<List<UserDto>> getAll(@RequestHeader(name = "Authorization") String token) {
        token = removeTokenPrefix(token);

        if (userService.isFleetManager(token) && userService.isLoggedIn(token)) {
            List<User> userList = userService.getAll();

            if (userList.isEmpty()) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.ok(userList.stream()
                        .map(user -> dtoTransformerService.transformToDto(user, UserDto.class))
                        .toList());
            }
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}
