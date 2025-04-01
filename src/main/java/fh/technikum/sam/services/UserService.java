package fh.technikum.sam.services;

import fh.technikum.sam.models.User;
import fh.technikum.sam.models.dto.LoginDto;
import fh.technikum.sam.models.dto.UserDto;
import fh.technikum.sam.utils.ResponseMessages;
import fh.technikum.sam.utils.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service class responsible for managing user-related operations such as registration, login, and logout.
 * <p>
 * This service handles user registration, login and logout processes. It maintains an in-memory storage
 * for users and tracks logged-in users with a simple token-based mechanism. It also allows the verification
 * of user roles, such as checking if a user is a fleet manager.
 * </p>
 */
@Service
public class UserService {

    @Autowired
    DtoTransformerService dtoTransformerService;

    // This needs to be static!
    private static Long userIdCounter = 1L;

    // We initialize it here, that way we don't need a constructor
    private final Map<Long, User> userMap = new HashMap<>();

    // String = token
    private final Map<String, User> loggedInMap = new HashMap<>();

    /**
     * Checks if the user associated with the given token is a fleet manager.
     *
     * @param token The authentication token of the user.
     * @return true if the user is a fleet manager, false otherwise.
     */
    public boolean isFleetManager(String token) {
        // This list keeps track of which users are logged in, otherwise, we won't check for the fleet manager flag
        if (this.loggedInMap.containsKey(token)) {
            return getLoggedInByToken(token).getFleetManager();
        }
        return false;
    }

    /**
     * Retrieves all users in the system.
     *
     * @return A list of all users.
     */
    public List<User> getAll() {
        return this.userMap.values().stream().toList();
    }

    /**
     * Checks if the user is logged in by verifying the presence of their token in the logged-in map.
     *
     * @param token The authentication token of the user.
     * @return true if the user is logged in, false otherwise.
     */
    public boolean isLoggedIn(String token) {
        var result = getLoggedInByToken(token);
        return result != null; // if we find an entry, the user must be logged in!
    }

    /**
     * Registers a new user based on the provided {@link UserDto}.
     * If a user with the same username already exists, registration fails.
     *
     * @param userDto The DTO containing the user data for registration.
     * @return The created {@link User} object, or null if the username already exists.
     */
    public User register(UserDto userDto) {
        User existingUser = this.findByUsername(userDto.getUsername());

        if (!this.userExists(existingUser)) {
            // if we don't find the same user, it is ok to save!
            userDto.setUserId(userIdCounter);
            existingUser = dtoTransformerService.transformToModel(userDto, User.class);
            this.userMap.put(userIdCounter, existingUser);
            userIdCounter++;
            return existingUser;
        }

        return null;
    }

    /**
     * Attempts to log a user in by validating the username and password.
     * If successful, a token is generated and the user is added to the logged-in map.
     *
     * @param loginDto The DTO containing the login credentials (username and password).
     * @return A tuple containing the HTTP status and a message, including the token on success.
     */
    public Tuple<HttpStatus, String> loginUser(LoginDto loginDto) {
        User userToLogin = this.findByUsername(loginDto.getUsername());

        if (userToLogin == null) {
            return new Tuple<>(HttpStatus.NOT_FOUND, ResponseMessages.USER_NON_EXISTENT);
        }

        if (this.isLoggedIn(userToLogin)) {
            return new Tuple<>(HttpStatus.BAD_REQUEST, ResponseMessages.USER_ALREADY_LOGGED_IN);
        }

        if (this.areCredentialsValid(loginDto)) {
            String token = userToLogin.getUserId() + "";
            loggedInMap.put(token, userToLogin);
            return new Tuple<>(HttpStatus.OK, token);
        } else {
            return new Tuple<>(HttpStatus.BAD_REQUEST, ResponseMessages.INVALID_CREDENTIALS);
        }
    }

    /**
     * Logs out the user associated with the given token.
     *
     * @param token The authentication token of the user.
     * @return A tuple containing the HTTP status and a message.
     */
    public Tuple<HttpStatus, String> logoutUser(String token) {
        User userToLogout = this.getLoggedInByToken(token);

        if (userToLogout == null) {
            return new Tuple<>(HttpStatus.NOT_FOUND, ResponseMessages.USER_NOT_LOGGED_IN);
        } else {
            loggedInMap.remove(token);
            return new Tuple<>(HttpStatus.OK, ResponseMessages.USER_LOGGED_OUT);
        }
    }

    /**
     * Retrieves the user associated with the given authentication token.
     *
     * @param token The authentication token of the user.
     * @return The {@link User} associated with the token, or null if not found.
     */
    private User getLoggedInByToken(String token) {
        return loggedInMap.get(token);
    }

    /**
     * Checks if the given user already exists in the system.
     *
     * @param user The user to check.
     * @return true if the user exists in the system, false otherwise.
     */
    private boolean userExists(User user) {
        // We simply check if the user we are given with its data exists in the map already
        for (User userToCompare : userMap.values()) {
            if (userToCompare.equals(user)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Validates the credentials (username and password) against the stored user data.
     *
     * @param loginDto The login credentials to validate.
     * @return true if the credentials match an existing user, false otherwise.
     */
    private boolean areCredentialsValid(LoginDto loginDto) {
        // We simply check if the user we are given with its data exists in the map already
        for (User userToCompare : userMap.values()) {
            if (userToCompare.getUsername().equals(loginDto.getUsername()) &&
                    userToCompare.getPassword().equals(loginDto.getPassword())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Finds a user by their username.
     *
     * @param username The username to search for.
     * @return The {@link User} object if found, null otherwise.
     */
    private User findByUsername(String username) {
        for (User user : userMap.values()) {
            if (user.getUsername().equals(username))
                return user;
        }
        return null;
    }

    /**
     * Checks if a user is logged in by comparing their details to the logged-in users.
     *
     * @param user The user to check.
     * @return true if the user is logged in, false otherwise.
     */
    private boolean isLoggedIn(User user) {
        for (User userToFind : loggedInMap.values()) {
            if (userToFind.equals(user))
                return true;
        }
        return false;
    }
}
