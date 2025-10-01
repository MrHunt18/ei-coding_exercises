# 🚀 Astronaut Daily Schedule Organizer

A robust, console-based Java application designed to help astronauts manage their daily schedules efficiently. This application demonstrates professional software engineering practices including design patterns, SOLID principles, and comprehensive error handling.

---

## 📋 Table of Contents

- [Features](#features)
- [Design Patterns](#design-patterns)
- [Project Structure](#project-structure)
- [Requirements](#requirements)
- [Installation & Setup](#installation--setup)
- [Usage Guide](#usage-guide)
- [Architecture Overview](#architecture-overview)
- [Testing Scenarios](#testing-scenarios)
- [Design Decisions](#design-decisions)
- [Contributing](#contributing)

---

## ✨ Features

### Mandatory Features
- ✅ **Add Tasks** - Create tasks with description, start time, end time, and priority
- ✅ **Remove Tasks** - Delete existing tasks from the schedule
- ✅ **View All Tasks** - Display tasks sorted by start time
- ✅ **Conflict Detection** - Automatic validation to prevent overlapping tasks
- ✅ **Error Handling** - Comprehensive error messages for invalid operations

### Optional Features
- 🔄 **Edit Tasks** - Modify existing task details
- ✔️ **Mark Completed** - Track task completion status
- 🎯 **Priority Filtering** - View tasks by priority level (LOW/MEDIUM/HIGH)
- 📝 **Logging** - File-based logging for all operations

---

## 🏗️ Design Patterns

This application implements three core design patterns:

### 1. Singleton Pattern
**Implementation:** `ScheduleManager.java`

Ensures only one instance of the schedule manager exists throughout the application lifecycle.

```java
public static synchronized ScheduleManager getInstance() {
    if (instance == null) {
        instance = new ScheduleManager();
    }
    return instance;
}
```

**Benefits:**
- Centralized task management
- Thread-safe implementation
- Single source of truth for schedule data

### 2. Factory Pattern
**Implementation:** `TaskFactory.java`

Encapsulates task creation logic with built-in validation.

```java
public Task createTask(String description, String startTime, 
                       String endTime, String priority) {
    // Validation and creation logic
}
```

**Benefits:**
- Separation of creation logic from business logic
- Centralized validation
- Easy to extend with new task types

### 3. Observer Pattern
**Implementation:** `IObserver.java`, `ConflictObserver.java`, `LoggerObserver.java`

Provides a publish-subscribe mechanism for schedule events.

```java
public interface IObserver {
    void update(String event, Task task, Task conflictingTask);
}
```

**Benefits:**
- Loose coupling between components
- Easy to add new observers (e.g., email notifications)
- Real-time notifications for conflicts and updates

---

## 📁 Project Structure

```
/AstronautScheduler
  │
  ├── Main.java                          # Application entry point
  │
  ├── manager/
  │     └── ScheduleManager.java         # Singleton - manages all tasks
  │
  ├── tasks/
  │     ├── Task.java                    # Task model with business logic
  │     ├── Priority.java                # Priority enum (LOW/MEDIUM/HIGH)
  │     ├── TaskStatus.java              # Status enum (PENDING/COMPLETED)
  │     └── TaskFactory.java             # Factory for creating tasks
  │
  ├── observers/
  │     ├── IObserver.java               # Observer interface
  │     ├── ConflictObserver.java        # Handles console notifications
  │     └── LoggerObserver.java          # Handles file logging
  │
  └── utils/
        └── ValidationHelper.java         # Centralized validation utilities
```

### Package Responsibilities

| Package | Responsibility |
|---------|---------------|
| `manager` | Schedule management and coordination |
| `tasks` | Task models, enums, and factory |
| `observers` | Event notification system |
| `utils` | Validation and utility functions |

---

## 💻 Requirements

- **Java:** JDK 8 or higher
- **Operating System:** Platform independent (Windows, macOS, Linux)
- **Memory:** Minimal (< 50MB)

---

## 🚀 Installation & Setup

### Step 1: Clone or Download

Download all files maintaining the package structure:

```
/AstronautScheduler
  ├── Main.java
  ├── manager/ScheduleManager.java
  ├── tasks/*.java
  ├── observers/*.java
  └── utils/ValidationHelper.java
```

### Step 2: Compile

Navigate to the project root directory and compile:

```bash
javac Main.java manager/*.java tasks/*.java observers/*.java utils/*.java
```

### Step 3: Run

Execute the application:

```bash
java Main
```

---

## 📖 Usage Guide

### Main Menu

Upon running the application, you'll see:

```
🚀 Astronaut Daily Schedule Organizer 🚀

--- Menu ---
1. Add Task
2. Remove Task
3. View All Tasks
4. Edit Task
5. Mark Task as Completed
6. View Tasks by Priority
7. Exit
Enter your choice:
```

### Operation Examples

#### 1️⃣ Adding a Task

```
Enter your choice: 1
Enter task description: Morning Exercise
Enter start time (HH:mm): 07:00
Enter end time (HH:mm): 08:00
Enter priority (LOW/MEDIUM/HIGH): HIGH

Output: Task added successfully. No conflicts.
```

#### 2️⃣ Viewing All Tasks

```
Enter your choice: 3

Output:
=== Astronaut Daily Schedule ===
[ ] 07:00 - 08:00: Morning Exercise [HIGH]
[ ] 09:00 - 10:00: Team Meeting [MEDIUM]
[ ] 12:00 - 13:00: Lunch Break [LOW]
================================
```

#### 3️⃣ Detecting Conflicts

```
Enter task description: Training Session
Enter start time (HH:mm): 09:30
Enter end time (HH:mm): 10:30

Output: Error: Task conflicts with existing task "Team Meeting".
```

#### 4️⃣ Marking Task as Completed

```
Enter task description to mark as completed: Morning Exercise

Output: ✓ Task marked as completed: Morning Exercise

View Tasks:
[✓] 07:00 - 08:00: Morning Exercise [HIGH]
```

---

## 🏛️ Architecture Overview

### Class Diagram

```
┌─────────────────┐
│      Main       │
└────────┬────────┘
         │
         ▼
┌─────────────────────┐      ┌──────────────┐
│  ScheduleManager    │◄─────│  IObserver   │
│    (Singleton)      │      └──────┬───────┘
└──────────┬──────────┘             │
           │                        │
           │                   ┌────┴─────────────┐
           │                   │                  │
           ▼              ┌────▼──────┐    ┌─────▼────────┐
     ┌──────────┐         │ Conflict  │    │    Logger    │
     │   Task   │         │ Observer  │    │   Observer   │
     └──────────┘         └───────────┘    └──────────────┘
           ▲
           │
     ┌─────┴─────┐
     │TaskFactory│
     └───────────┘
```

### Data Flow

1. **User Input** → Main.java
2. **Main** → ScheduleManager (via Singleton)
3. **ScheduleManager** → TaskFactory (creates task)
4. **TaskFactory** → ValidationHelper (validates input)
5. **ScheduleManager** → Observers (notifies about events)
6. **Observers** → Console/Log File (displays result)

---

## 🧪 Testing Scenarios

### ✅ Positive Test Cases

| Test Case | Input | Expected Output |
|-----------|-------|-----------------|
| Add valid task | "Morning Exercise", "07:00", "08:00", "HIGH" | Task added successfully |
| View empty schedule | View Tasks (no tasks) | "No tasks scheduled for the day" |
| Remove existing task | "Morning Exercise" | Task removed successfully |
| Add non-overlapping task | "Lunch", "12:00", "13:00", "LOW" | Task added successfully |
| Mark task completed | "Morning Exercise" | ✓ Task marked as completed |

### ❌ Negative Test Cases

| Test Case | Input | Expected Output |
|-----------|-------|-----------------|
| Add overlapping task | "Training", "09:30", "10:30" (overlaps 09:00-10:00) | Error: Task conflicts |
| Remove non-existent task | "Non-existent Task" | Error: Task not found |
| Invalid time format | "25:00", "26:00" | Error: Invalid time format |
| End time before start | "10:00", "09:00" | Error: End time must be after start |
| Empty description | "", "09:00", "10:00" | Task description cannot be empty |
| Invalid priority | "URGENT" | Error: Invalid priority level |

---

## 🎯 Design Decisions

### 1. Why Singleton for ScheduleManager?

**Problem:** Multiple instances could lead to inconsistent schedules.

**Solution:** Singleton ensures one centralized schedule manager.

**Trade-offs:**
- ✅ Single source of truth
- ✅ Easy to access globally
- ⚠️ Testing can be challenging (can be solved with dependency injection)

### 2. Why Factory Pattern?

**Problem:** Task creation involves complex validation logic.

**Solution:** Encapsulate creation in a factory.

**Benefits:**
- Separates validation from business logic
- Makes task creation consistent
- Easy to extend with new task types

### 3. Why Observer Pattern?

**Problem:** Need to notify multiple components about schedule changes.

**Solution:** Observer pattern for loose coupling.

**Extensibility:**
- Easy to add email notifications
- Can add SMS alerts
- Can integrate with external calendars

### 4. LocalTime vs String

**Decision:** Use `LocalTime` for time representation.

**Rationale:**
- Type safety
- Built-in comparison methods
- Java standard library support

### 5. Validation Strategy

**Decision:** Centralized validation in `ValidationHelper`.

**Benefits:**
- DRY (Don't Repeat Yourself)
- Consistent validation rules
- Easy to modify validation logic

---

## 🔒 SOLID Principles Implementation

### Single Responsibility Principle (SRP)
- `Task`: Manages task data
- `ScheduleManager`: Manages collection of tasks
- `ValidationHelper`: Handles all validation
- `TaskFactory`: Creates tasks

### Open/Closed Principle (OCP)
- Open for extension: Can add new observers without modifying existing code
- Closed for modification: Core functionality doesn't change when extending

### Liskov Substitution Principle (LSP)
- Any `IObserver` implementation can be used interchangeably

### Interface Segregation Principle (ISP)
- `IObserver` interface is minimal and focused

### Dependency Inversion Principle (DIP)
- `ScheduleManager` depends on `IObserver` interface, not concrete implementations

---

## 📝 Logging

The application generates a log file: `astronaut_schedule.log`

**Log Levels:**
- **INFO**: Task operations (add, remove, update)
- **WARNING**: Conflicts and validation errors
- **SEVERE**: Unexpected errors

**Sample Log Entry:**
```
Jan 15, 2025 10:30:45 AM observers.LoggerObserver update
INFO: Task added: Morning Exercise
```

---

## 🐛 Error Handling

The application handles errors gracefully:

1. **Input Validation Errors**: Clear messages about what went wrong
2. **Conflict Detection**: Identifies which task is conflicting
3. **Exception Handling**: Try-catch blocks prevent crashes
4. **Logging**: All errors are logged for debugging

---

## ⚡ Performance Considerations

- **Time Complexity:**
  - Add task: O(n) for conflict check + O(n log n) for sorting
  - Remove task: O(n)
  - View tasks: O(n)
  
- **Space Complexity:** O(n) where n is number of tasks

- **Optimization Opportunities:**
  - Use TreeSet for automatic sorting
  - Implement interval tree for O(log n) conflict detection
  - Add caching for frequently accessed data

---

## 🔮 Future Enhancements

- [ ] Persist data to database/file
- [ ] Add recurring tasks support
- [ ] Implement task dependencies
- [ ] Add GUI interface
- [ ] Multi-user support with authentication
- [ ] Calendar export (iCal format)
- [ ] Email/SMS notifications
- [ ] Task categories and tags
- [ ] Search functionality
- [ ] Undo/Redo operations

---

## 👥 Contributing

Contributions are welcome! Please follow these guidelines:

1. Fork the repository
2. Create a feature branch
3. Follow existing code style
4. Add unit tests for new features
5. Update documentation
6. Submit a pull request

---

## 📄 License

This project is created for educational purposes.

---

## 📧 Contact

For questions or feedback, please contact me(links are in profile).

---

## 🙏 Acknowledgments

- Design patterns inspired by Gang of Four
- SOLID principles by Robert C. Martin
- Java best practices from Oracle documentation


