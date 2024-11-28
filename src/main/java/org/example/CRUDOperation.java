package org.example;


import java.util.List;
import java.util.UUID;

import static java.lang.System.out;

public class CRUDOperation {

    private static final CassandraUtil CASSANDRA_UTIL = new CassandraUtil();
    private static final ConsoleReader CONSOLE_READER = ConsoleReader.getConsoleReaderInstance();
    
    private CRUDOperation() {
    }
    
    public static void deleteTaskById() {
        UUID taskUUIDToDelete = getTaskUUIDFromConsole();
        UUID userId = getUserUUIDFromConsole();
        CASSANDRA_UTIL.deleteUserTask(taskUUIDToDelete, userId);
        out.println("Task with id: " + taskUUIDToDelete + " deleted.");
    }

    private static UUID getTaskUUIDFromConsole() {
        out.println("Enter task id:");
        return CONSOLE_READER.readUUID();
    }

    private static UUID getUserUUIDFromConsole() {
        out.println("Enter user id:");
        return CONSOLE_READER.readUUID();
    }

    public static void updateTaskStatus() {
        UUID userId = getUserUUIDFromConsole();
        UUID taskUUIDToUpdate = getTaskUUIDFromConsole();
        out.println("Enter new status: " +
                "\n1. PENDING" +
                "\n2. IN_PROGRESS" +
                "\n3. COMPLETE");
        int status = CONSOLE_READER.readInt();
        Status newStatus = getStatus(status);
        if (newStatus == Status.ERROR) {
            out.println("Invalid status. Please enter a valid status.");
            return;
        }
        CASSANDRA_UTIL.updateTaskStatus(taskUUIDToUpdate, userId, newStatus.getTaskStatus());
        out.println("Task with id " + taskUUIDToUpdate + " updated with new status: " + newStatus);
    }

    private static Status getStatus(int status) {
        switch (status){
            case 1:
                return Status.PENDING;
            case 2:
                return Status.IN_PROGRESS;
            case 3:
                return Status.COMPLETED;
            default:
                return Status.ERROR;
        }
    }

    public static void getTaskById() {
        UUID  taskUUID = getTaskUUIDFromConsole();
        CASSANDRA_UTIL.getTaskById(taskUUID)
                .ifPresent(task -> out.println("Task with id " + task.getTaskId() + ":\n" + task));
    }

    public static void getAllTaskByUserId() {
        UUID userId = getUserUUIDFromConsole();
        out.println("Tasks for user with id " + userId + ":\n");

        List<Task> allTasksForAUser = CASSANDRA_UTIL.getAllTasksForAUser(userId);
        if (allTasksForAUser.isEmpty()) {
            out.println("No tasks found for user with id " + userId);
            return;
        }
        allTasksForAUser.forEach(task -> out.println(task.toString()));
    }

    public static void createNewUser() {
        out.println("Enter username:");
        String username = CONSOLE_READER.readLine();
        out.println("Enter email:");
        String email = CONSOLE_READER.readLine();
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setEmail(email);
        out.println("New user created with id: " + CASSANDRA_UTIL.createNewUser(newUser));
    }

    public static void createNewTask() {
        UUID userId = getUserUUIDFromConsole();
        out.println("Enter title:");
        String title = CONSOLE_READER.readLine();
        out.println("Enter description:");
        String description = CONSOLE_READER.readLine();
        Task newTask = Task.builder()
                .userId(userId)
                .title(title)
                .description(description)
                .build();
        out.println("New task created with id: " + CASSANDRA_UTIL.createNewTask(newTask));
    }
}
