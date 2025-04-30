package fh.technikum.carsharing.utils;

/**
 * A utility class to store predefined response messages used throughout the application.
 * <p>
 * This class centralizes all messages that are returned to the client for different
 * operations like login, user registration, and vehicle management. Using constants
 * helps avoid hardcoding messages in multiple places, making the code easier to maintain.
 * </p>
 */
public class ResponseMessages {

    /**
     * Message returned when a user tries to access a resource without being logged in.
     */
    public static final String USER_NOT_LOGGED_IN = "The user is not logged in!";

    /**
     * Message returned when a user successfully logs out.
     */
    public static final String USER_LOGGED_OUT = "The user has been logged out successfully!";

    /**
     * Message returned when a user tries to log in while already being logged in.
     */
    public static final String USER_ALREADY_LOGGED_IN = "The user is already logged in!";

    /**
     * Message returned when a user attempts to log in with a non-existent account.
     */
    public static final String USER_NON_EXISTENT = "The given user doesn't exist!";

    /**
     * Message returned when the user provides invalid login credentials.
     */
    public static final String INVALID_CREDENTIALS = "The given user data isn't correct!";

    /**
     * Message returned when a user tries to register with data that already exists in the database.
     */
    public static final String USER_ALREADY_EXISTS = "The given user data is already existent in the database";

    /**
     * Message returned when no vehicle is found with the provided ID.
     */
    public static final String NO_VEHICLE_FOUND = "No vehicle found with the given id: ";

}
