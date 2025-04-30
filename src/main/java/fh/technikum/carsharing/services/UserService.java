package fh.technikum.carsharing.services;

import fh.technikum.carsharing.persistence.entity.User;
import fh.technikum.carsharing.persistence.entity.dto.LoginDto;
import fh.technikum.carsharing.persistence.entity.dto.UserDto;
import fh.technikum.carsharing.persistence.repository.UserRepository;
import fh.technikum.carsharing.utils.ResponseMessages;
import fh.technikum.carsharing.utils.Tuple;
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
    UserRepository userRepository;

    // We store the logged in users in the service, since we don't want to save the logged in users in the repo
    // we just keep track in here as long as the session exists
    private Map<String, User> loggedInMap = new HashMap<>();

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
        User existingUser = userRepository.findByUsername(userDto.getUsername());

        if (!userRepository.userExists(existingUser)) {
            // if we don't find the same user, it is ok to save!
            return userRepository.registerUser(userDto);
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
        User userToLogin = userRepository.findByUsername(loginDto.getUsername());

        if (userToLogin == null) {
            return new Tuple<>(HttpStatus.NOT_FOUND, ResponseMessages.USER_NON_EXISTENT);
        }

        if (this.isLoggedIn(userToLogin)) {
            return new Tuple<>(HttpStatus.BAD_REQUEST, ResponseMessages.USER_ALREADY_LOGGED_IN);
        }

        if (userRepository.areCredentialsValid(loginDto)) {
            String token = userToLogin.getUserId() + "";
            loggedInMap.put(token, userToLogin);
            return new Tuple<>(HttpStatus.OK, token);
        } else {
            return new Tuple<>(HttpStatus.UNAUTHORIZED, ResponseMessages.INVALID_CREDENTIALS);
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
    public User getLoggedInByToken(String token) {
        return loggedInMap.get(token);
    }

    /**
     * Checks if a user is logged in by comparing their details to the logged-in users.
     *
     * @param user The user to check.
     * @return true if the user is logged in, false otherwise.
     */
    public boolean isLoggedIn(User user) {
        for (User userToFind : loggedInMap.values()) {
            if (userToFind.equals(user))
                return true;
        }
        return false;
    }

    /**
     * Retrieves all users in the system (this uses the repos getAll method).
     *
     * @return A list of all users.
     */
    public List<User> getAll() {
        return this.userRepository.getAll();
    }

    /**
     * This method is only for testing purposes!
     * Don't use it in any other case
     */
    public void reset() {
        this.loggedInMap = new HashMap<>();
        this.userRepository.reset();
    }
}
