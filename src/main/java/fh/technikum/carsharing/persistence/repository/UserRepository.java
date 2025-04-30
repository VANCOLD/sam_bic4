package fh.technikum.carsharing.persistence.repository;

import fh.technikum.carsharing.persistence.entity.User;
import fh.technikum.carsharing.persistence.entity.dto.LoginDto;
import fh.technikum.carsharing.persistence.entity.dto.UserDto;
import fh.technikum.carsharing.services.DtoTransformerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepository {

    @Autowired
    DtoTransformerService dtoTransformerService;

    @Autowired
    PasswordEncoder encoder;

    // This needs to be static!
    private static Long userIdCounter = 1L;

    // We initialize it here, that way we don't need a constructor
    private Map<Long, User> userMap = new HashMap<>();

    public UserRepository() {}

    /**
     * Retrieves all users in the system.
     *
     * @return A list of all users.
     */
    public List<User> getAll() {
        return this.userMap.values().stream().toList();
    }

    /**
     * Checks if the given user already exists in the system.
     *
     * @param user The user to check.
     * @return true if the user exists in the system, false otherwise.
     */
    public boolean userExists(User user) {
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
    public boolean areCredentialsValid(LoginDto loginDto) {
        // We simply check if the user we are given with its data exists in the map already

        return userMap.values()
                .stream()
                .anyMatch(userToCompare ->
                        userToCompare.getUsername().equals(loginDto.getUsername()) &&
                        encoder.matches(loginDto.getPassword(), userToCompare.getPassword())
                );
    }


    /**
     * Finds a user by their username.
     *
     * @param username The username to search for.
     * @return The {@link User} object if found, null otherwise.
     */
    public User findByUsername(String username) {
        for (User user : userMap.values()) {
            if (user.getUsername().equals(username))
                return user;
        }
        return null;
    }



    public User registerUser(UserDto userDto) {
        userDto.setUserId(userIdCounter);
        userDto.setPassword(encoder.encode(userDto.getPassword()));
        var createdUser = dtoTransformerService.transformToModel(userDto, User.class);
        this.userMap.put(userIdCounter, createdUser);
        userIdCounter++;
        return createdUser;
    }

    public void reset() {
        this.userMap = new HashMap<>();
        userIdCounter = 1L;
    }
}
