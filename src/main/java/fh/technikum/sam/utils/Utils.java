package fh.technikum.sam.utils;

/**
 * A utility class containing helper methods for various common operations.
 */
public class Utils {

    /**
     * Removes the "Bearer " prefix from a token string, if present.
     * This is commonly used in the context of authorization tokens, where the token is passed
     * with the "Bearer " prefix in HTTP request headers.
     *
     * @param token The token string that may contain the "Bearer " prefix.
     * @return The token string without the "Bearer " prefix, or the original token if the prefix is not found.
     */
    public static String removeTokenPrefix(String token) {
        if (token.contains("Bearer ")) {
            token = token.substring("Bearer ".length());
        }

        return token;
    }
}
