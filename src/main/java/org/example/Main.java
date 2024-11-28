package org.example;

import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

import static java.lang.System.*;
import static org.example.CRUDOperation.*;

@Slf4j
public class Main {

    public static void main(String[] args) {
        out.println("Welcome!");
        int option = 99;
        while (option != 0) {
            out.println("\nMenu options: " +
                    "\n1. Create a new user" +
                    "\n2. Create a new task" +
                    "\n3. Get all tasks for a specific user by their user ID" +
                    "\n4. Get task by id" +
                    "\n5. Update task status" +
                    "\n6. Delete task" +
                    "\n0. Exit");
            try {
                option = ConsoleReader.getConsoleReaderInstance().readInt();
            } catch (Exception e) {
                option = 99;
            }

            switch (option) {
                case 1:
                    createNewUser();
                    break;
                case 2:
                    createNewTask();
                    break;
                case 3:
                    getAllTaskByUserId();
                    break;
                case 4:
                    getTaskById();
                    break;
                case 5:
                    updateTaskStatus();
                    break;
                case 6:
                    deleteTaskById();
                    break;
                case 0:
                    CassandraConnection.getInstance().close();
                    ConsoleReader.getConsoleReaderInstance().close();
                    out.println("Goodbye!");
                    break;
                default:
                    out.println("Invalid option. Please try again.");
                break;
            }
        }
    }


}