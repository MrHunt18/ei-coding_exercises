package tasks;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * Represents a single task in the astronaut's schedule
 */
public class Task {
    private String description;
    private LocalTime startTime;
    private LocalTime endTime;
    private Priority priority;
    private TaskStatus status;
    private final String id;

    public Task(String description, LocalTime startTime, LocalTime endTime, Priority priority) {
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.priority = priority;
        this.status = TaskStatus.PENDING;
        this.id = UUID.randomUUID().toString();
    }

    // Getters and Setters
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalTime getStartTime() { return startTime; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }

    public LocalTime getEndTime() { return endTime; }
    public void setEndTime(LocalTime endTime) { this.endTime = endTime; }

    public Priority getPriority() { return priority; }
    public void setPriority(Priority priority) { this.priority = priority; }

    public TaskStatus getStatus() { return status; }
    public String getId() { return id; }

    public void markCompleted() {
        this.status = TaskStatus.COMPLETED;
    }

    /**
     * Checks if this task overlaps with another task
     */
    public boolean overlapsWith(Task other) {
        return !(this.endTime.isBefore(other.startTime) || this.endTime.equals(other.startTime) ||
                 this.startTime.isAfter(other.endTime) || this.startTime.equals(other.endTime));
    }

    @Override
    public String toString() {
        String statusIcon = status == TaskStatus.COMPLETED ? "✓" : " ";
        return String.format("[%s] %s - %s: %s [%s]",
            statusIcon,
            startTime.format(DateTimeFormatter.ofPattern("HH:mm")),
            endTime.format(DateTimeFormatter.ofPattern("HH:mm")),
            description,
            priority);
    }
}
