package tasks;

import utils.ValidationHelper;
import java.time.LocalTime;
import java.util.logging.Logger;

/**
 * Factory class for creating Task objects with validation
 * Implements Factory Design Pattern
 */
public class TaskFactory {
    private static final Logger logger = Logger.getLogger(TaskFactory.class.getName());

    /**
     * Creates a new task with validation
     * @throws IllegalArgumentException if validation fails
     */
    public Task createTask(String description, String startTimeStr, String endTimeStr, String priorityStr)
            throws IllegalArgumentException {

        // Validate description
        ValidationHelper.validateDescription(description);

        // Parse and validate times
        LocalTime startTime = ValidationHelper.parseTime(startTimeStr);
        LocalTime endTime = ValidationHelper.parseTime(endTimeStr);

        // Validate time logic
        ValidationHelper.validateTimeRange(startTime, endTime);

        // Parse priority
        Priority priority = ValidationHelper.parsePriority(priorityStr);

        Task task = new Task(description, startTime, endTime, priority);
        logger.info("Task created: " + description);
        return task;
 