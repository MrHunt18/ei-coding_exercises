package observers;

import tasks.Task;

/**
 * Observer that handles conflict notifications
 */
public class ConflictObserver implements IObserver {

    @Override
    public void update(String event, Task task, Task conflictingTask) {
        switch (event) {
            case "CONFLICT":
                System.out.println(String.format("Error: Task conflicts with existing task \"%s\".",
                    conflictingTask.getDescription()));
                break;
            case "ADDED":
                System.out.println("Task added successfully. No conflicts.");
                break;
            case "REMOVED":
                System.out.println("Task removed successfully.");
                break;
            case "UPDATED":
                System.out.println("Task updated successfully.");
                break;
            case "COMPLETED":
                System.out.println("✓ Task marked as completed: " + task.getDescription());
                break;
        }
    }
}