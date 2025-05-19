package fh.technikum.billing.persistence.enums;

import lombok.Getter;

/**
 * Enum representing the priority levels.
 * <p>
 * This enum defines four priority levels for tasks or vehicles: LOW, MEDIUM, HIGH, and CRITICAL.
 * It is used to categorize the urgency or importance of a task or vehicle in the system.
 * </p>
 */
@Getter
public enum Priority {

    /**
     * Represents a low priority level.
     */
    LOW("LOW"),

    /**
     * Represents a medium priority level.
     */
    MEDIUM("MEDIUM"),

    /**
     * Represents a high priority level.
     */
    HIGH("HIGH"),

    /**
     * Represents a critical priority level.
     */
    CRITICAL("CRITICAL");

    /**
     * The string name representing the priority level.
     */
    final String name;

    /**
     * Constructor for the enum, setting the name of the priority level.
     *
     * @param name the name of the priority level
     */
    Priority(String name) {
        this.name = name;
    }

}
