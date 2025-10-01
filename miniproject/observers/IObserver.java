package observers;

import tasks.Task;

/**
 * Observer interface for the Observer Design Pattern
 * Allows multiple observers to be notified of schedule changes
 */
public interface IObserver {
    /**
     * Called when a schedule event occurs
     * @param event The type of event (ADDED, REMOVED, UPDATED, CONFLICT, COMPLETED)
     * @param task The task involved in the event
     * @param conflictingTask The conflicting task (only for CONFLICT events)
     */
    void update(String event, Task task, Task conflictingTask);
}