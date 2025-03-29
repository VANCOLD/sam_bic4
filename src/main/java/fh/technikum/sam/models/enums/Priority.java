package fh.technikum.sam.models.enums;

import lombok.Getter;

/**
 * Enum representing different levels of priority.
 * <p>
 * This enum provides four levels of priorities: LOW, MEDIUM, HIGH, and EXCEPTION.
 * It also includes a method to map a string to its corresponding priority level.
 * </p>
 */
@Getter
public enum Priority
{
    LOW("LOW"),
    MEDIUM("MEDIUM"),
    HIGH("HIGH"),
    CRITICAL("CRITICAL");

    final String name;

    Priority(String name) {
        this.name = name;
    }

    /**
     * <p>
     * Takes in {@link String} and maps it onto the {@link Priority} Enum.
     * If no match is found, this method will throw an {@link IllegalArgumentException}
     * </p>
     * @param name the name of a priority we want to compare to
     * @return the enum representation of that priority, if the name doesn't map to anything -> exception
     */
    Priority getPriorityFromName(String name) {
        return switch(name) {
            case "LOW" -> LOW;
            case "MEDIUM" -> MEDIUM;
            case "HIGH" -> HIGH;
            case "CRITICAL" -> CRITICAL;
            default -> throw new IllegalArgumentException("Unknown Priority: " + name);
        };
    }
}
