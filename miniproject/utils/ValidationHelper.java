package utils;

import tasks.Priority;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.logging.Logger;

/**
 * Utility class for validating task inputs
 * Provides centralized validation logic
 */
public class ValidationHelper {
    private static final Logger logger = Logger.getLogger(ValidationHelper.class.getName());
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    /**
     * Validates task description
     * @throws IllegalArgumentException if description is null or empty
     */
    public static void validateDescription(String description) {
        if (description == null || description.trim().isEmpty()) {
            logger.warning("Attempted to create task with empty description");
            throw new IllegalArgumentException("Task description cannot be empty");
        }
    }

    /**
     * Parses time string to LocalTime
     * @throws IllegalArgumentException if time format is invalid
     */
    public static LocalTime parseTime(String timeStr) {
        try {
            return LocalTime.parse(timeStr, TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            logger.warning("Invalid time format: " + timeStr);
            throw new IllegalArgumentException("Error: Invalid time format. Use HH:mm (e.g., 09:00)");
        }
    }

    /**
     * Validates that end time is after start time
     * @throws IllegalArgumentException if time range is invalid
     */
    public static void validateTimeRange(LocalTime startTime, LocalTime endTime) {
        if (endTime.isBefore(startTime) || endTime.equals(startTime)) {
            logger.warning("End time must be after start time");
            throw new IllegalArgumentException("Error: End time must be after start time");
        }
    }

    /**
     * Parses priority string to Priority enum
     * @throws IllegalArgumentException if priority is invalid
     */
    public static Priority parsePriority(String priorityStr) {
        try {
            return Priority.valueOf(priorityStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            logger.warning("Invalid priority level: " + priorityStr);
            throw new IllegalArgumentException("Error: Invalid priority level. Use LOW, MEDIUM, or HIGH");
        }
    }
}