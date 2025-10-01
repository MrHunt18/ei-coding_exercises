package manager;

import observers.IObserver;
import tasks.Task;
import tasks.TaskFactory;
import tasks.Priority;

import java.util.*;
import java.util.logging.Logger;

/**
 * Singleton class that manages all tasks in the schedule.
 * Implements thread-safe singleton pattern.
 */
public class ScheduleManager {
    private static ScheduleManager instance;
    private final List<Task> tasks;
    private final List<IObserver> observers;
    private final TaskFactory taskFactory;
    private static final Logger logger = Logger.getLogger(ScheduleManager.class.getName());

    private ScheduleManager() {
        tasks = new ArrayList<>();
        observers = new ArrayList<>();
        taskFactory = new TaskFactory();
        logger.info("ScheduleManager initialized");
    }

    /**
     * Thread-safe singleton instance retrieval
     */
    public static synchronized ScheduleManager getInstance() {
        if (instance == null) {
            instance = new ScheduleManager();
        }
        return instance;
    }

    // Observer management
    public void addObserver(IObserver observer) {
        observers.add(observer);
        logger.info("Observer added: " + observer.getClass().getSimpleName());
    }

    public void removeObserver(IObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers(String event, Task task, Task conflictingTask) {
        for (IObserver observer : observers) {
            observer.update(event, task, conflictingTask);
        }
    }

    /**
     * Adds a new task to the schedule
     * @return true if task added successfully, false if conflict or validation error
     */
    public boolean addTask(String description, String startTime, String endTime, String priority) {
        try {
            Task newTask = taskFactory.createTask(description, startTime, endTime, priority);

            // Check for conflicts
            Task conflictingTask = findConflictingTask(newTask);
            if (conflictingTask != null) {
                notifyObservers("CONFLICT", newTask, conflictingTask);
                return false;
            }

            tasks.add(newTask);
            sortTasksByStartTime();
            notifyObservers("ADDED", newTask, null);
            return true;

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            logger.warning("Failed to add task: " + e.getMessage());
            return false;
        }
    }

    /**
     * Finds if the new task conflicts with any existing task
     */
    private Task findConflictingTask(Task newTask) {
        for (Task existingTask : tasks) {
            if (newTask.overlapsWith(existingTask)) {
                return existingTask;
            }
        }
        return null;
    }

    /**
     * Removes a task by description
     */
    public boolean removeTask(String description) {
        Optional<Task> taskToRemove = tasks.stream()
            .filter(t -> t.getDescription().equalsIgnoreCase(description))
            .findFirst();

        if (taskToRemove.isPresent()) {
            tasks.remove(taskToRemove.get());
            notifyObservers("REMOVED", taskToRemove.get(), null);
            return true;
        } else {
            System.out.println("Error: Task not found.");
            logger.warning("Attempted to remove non-existent task: " + description);
            return false;
        }
    }

    /**
     * Edits an existing task
     */
    public boolean editTask(String description, String newDescription, String newStartTime,
                           String newEndTime, String newPriority) {
        Optional<Task> taskToEdit = tasks.stream()
            .filter(t -> t.getDescription().equalsIgnoreCase(description))
            .findFirst();

        if (!taskToEdit.isPresent()) {
            System.out.println("Error: Task not found.");
            return false;
        }

        try {
            Task tempTask = taskFactory.createTask(newDescription, newStartTime, newEndTime, newPriority);
            Task currentTask = taskToEdit.get();

            // Check for conflicts with other tasks (excluding current task)
            for (Task existingTask : tasks) {
                if (!existingTask.getId().equals(currentTask.getId()) && tempTask.overlapsWith(existingTask)) {
                    notifyObservers("CONFLICT", tempTask, existingTask);
                    return false;
                }
            }

            // Update task
            currentTask.setDescription(newDescription);
            currentTask.setStartTime(tempTask.getStartTime());
            currentTask.setEndTime(tempTask.getEndTime());
            currentTask.setPriority(tempTask.getPriority());

            sortTasksByStartTime();
            notifyObservers("UPDATED", currentTask, null);
            return true;

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Marks a task as completed
     */
    public boolean markTaskCompleted(String description) {
        Optional<Task> task = tasks.stream()
            .filter(t -> t.getDescription().equalsIgnoreCase(description))
            .findFirst();

        if (task.isPresent()) {
            task.get().markCompleted();
            notifyObservers("COMPLETED", task.get(), null);
            return true;
        } else {
            System.out.println("Error: Task not found.");
            return false;
        }
    }

    /**
     * Displays all tasks sorted by start time
     */
    public void viewAllTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks scheduled for the day.");
            return;
        }

        System.out.println("\n=== Astronaut Daily Schedule ===");
        for (Task task : tasks) {
            System.out.println(task);
        }
        System.out.println("================================\n");
    }

    /**
     * Views tasks filtered by priority level
     */
    public void viewTasksByPriority(Priority priority) {
        List<Task> filteredTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getPriority() == priority) {
                filteredTasks.add(task);
            }
        }

        if (filteredTasks.isEmpty()) {
            System.out.println("No tasks with priority: " + priority);
            return;
        }

        System.out.println("\n=== Tasks with Priority: " + priority + " ===");
        for (Task task : filteredTasks) {
            System.out.println(task);
        }
        System.out.println("====================================\n");
    }

    /**
     * Sorts tasks by start time for optimal viewing
     */
    private void sortTasksByStartTime() {
        tasks.sort(Comparator.comparing(Task::getStartTime));
    }

    public List<Task> getTasks() {
        return new ArrayList<>(tasks);
    }
}
