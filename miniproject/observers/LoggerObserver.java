package observers;

import tasks.Task;
import java.util.logging.*;
import java.io.IOException;

/**
 * Observer that logs all schedule events to a file
 */
public class LoggerObserver implements IObserver {
    private static final Logger logger = Logger.getLogger(LoggerObserver.class.getName());

    static {
        setupLogger();
    }

    private static void setupLogger() {
        try {
            LogManager.getLogManager().reset();
            logger.setLevel(Level.ALL);

            // Console handler for warnings and errors
            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setLevel(Level.WARNING);
            consoleHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(consoleHandler);

            // File handler for all logs
            FileHandler fileHandler = new FileHandler("astronaut_schedule.log", true);
            fileHandler.setLevel(Level.ALL);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);

        } catch (IOException e) {
            System.err.println("Failed to setup logger: " + e.getMessage());
        }
    }

    @Override
    public void update(String event, Task task, Task conflictingTask) {
        switch (event) {
            case "CONFLICT":
                logger.warning(String.format("Task conflict: '%s' conflicts with '%s'",
                    task.getDescription(), conflictingTask.getDescription()));
                break;
            case "ADDED":
                logger.info("Task added: " + task.getDescription());
                break;
            case "REMOVED":
                logger.info("Task removed: " + task.getDescription());
                break;
            case "UPDATED":
                logger.info("Task updated: " + task.getDescription());
                break;
            case "COMPLETED":
                logger.info("Task completed: " + task.getDescription());
                break;
        }
    }
}