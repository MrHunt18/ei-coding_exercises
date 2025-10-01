import manager.ScheduleManager;
import observers.ConflictObserver;
import observers.LoggerObserver;
import tasks.Task;

import java.util.Scanner;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        logger.info("Application started");

        // Initialize Singleton ScheduleManager
        ScheduleManager manager = ScheduleManager.getInstance();

        // Register observers
        manager.addObserver(new ConflictObserver());
        manager.addObserver(new LoggerObserver());

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("🚀 Astronaut Daily Schedule Organizer 🚀");

        while (running) {
            displayMenu();

            try {
                String choice = scanner.nextLine().trim();

                switch (choice) {
                    case "1":
                        addTaskMenu(scanner, manager);
                        break;
                    case "2":
                        removeTaskMenu(scanner, manager);
                        break;
                    case "3":
                        manager.viewAllTasks();
                        break;
                    case "4":
                        editTaskMenu(scanner, manager);
                        break;
                    case "5":
                        markCompletedMenu(scanner, manager);
                        break;
                    case "6":
                        viewByPriorityMenu(scanner, manager);
                        break;
                    case "7":
                        running = false;
                        System.out.println("Exiting... Safe travels, astronaut! 🚀");
                        logger.info("Application terminated by user");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }

            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
                logger.severe("Unexpected error: " + e.getMessage());
            }
        }

        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("\n--- Menu ---");
        System.out.println("1. Add Task");
        System.out.println("2. Remove Task");
        System.out.println("3. View All Tasks");
        System.out.println("4. Edit Task");
        System.out.println("5. Mark Task as Completed");
        System.out.println("6. View Tasks by Priority");
        System.out.println("7. Exit");
        System.out.println("please choose the number(1-7)");
        System.out.print("Enter your choice: ");
    }

    private static void addTaskMenu(Scanner scanner, ScheduleManager manager) {
        System.out.print("Enter task description: ");
        String description = scanner.nextLine().trim();

        System.out.print("Enter start time (HH:mm): ");
        String startTime = scanner.nextLine().trim();

        System.out.print("Enter end time (HH:mm): ");
        String endTime = scanner.nextLine().trim();

        System.out.print("Enter priority (LOW/MEDIUM/HIGH): ");
        String priority = scanner.nextLine().trim();

        manager.addTask(description, startTime, endTime, priority);
    }

    private static void removeTaskMenu(Scanner scanner, ScheduleManager manager) {
        System.out.print("Enter task description to remove: ");
        String description = scanner.nextLine().trim();
        manager.removeTask(description);
    }

    private static void editTaskMenu(Scanner scanner, ScheduleManager manager) {
        System.out.print("Enter task description to edit: ");
        String description = scanner.nextLine().trim();

        System.out.print("Enter new description: ");
        String newDescription = scanner.nextLine().trim();

        System.out.print("Enter new start time (HH:mm): ");
        String startTime = scanner.nextLine().trim();

        System.out.print("Enter new end time (HH:mm): ");
        String endTime = scanner.nextLine().trim();

        System.out.print("Enter new priority (LOW/MEDIUM/HIGH): ");
        String priority = scanner.nextLine().trim();

        manager.editTask(description, newDescription, startTime, endTime, priority);
    }

    private static void markCompletedMenu(Scanner scanner, ScheduleManager manager) {
        System.out.print("Enter task description to mark as completed: ");
        String description = scanner.nextLine().trim();
        manager.markTaskCompleted(description);
    }

    private static void viewByPriorityMenu(Scanner scanner, ScheduleManager manager) {
        System.out.print("Enter priority level (LOW/MEDIUM/HIGH): ");
        String priorityStr = scanner.nextLine().trim();

        try {
            tasks.Priority priority = tasks.Priority.valueOf(priorityStr.toUpperCase());
            manager.viewTasksByPriority(priority);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: Invalid priority level.");
        }
    }
}