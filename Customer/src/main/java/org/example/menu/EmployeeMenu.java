package org.example.menu;

import org.example.api.EmployeeAPI;
import org.example.entity.Employee;
import org.example.entity.Post;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static org.example.menu.PostMenu.menuPost;

public class EmployeeMenu extends EmployeeAPI {
    PostMenu postMenu = new PostMenu();

    public static void menuEmployee(List<Employee> employees, Map<Integer, Post> posts) {
        boolean isRunning = true;
        while (isRunning) {
            try {
                System.out.println("Choose an action:");
                System.out.println("0. Go to post menu");
                System.out.println("1. Create employee");
                System.out.println("2. Change employee");
                System.out.println("3. Terminate employee");
                System.out.println("4. Output all sorted employees");
                System.out.println("5. Output one employee");
                System.out.println("6. Output filtered employees");
                System.out.println("7. Exit");

                int operator = scanner.nextInt();
                scanner.nextLine();

                switch (operator) {
                    case 0:
                        menuPost(posts, employees);
                        break;
                    case 1:
                        createEmployee(employees);
                        break;
                    case 2:
                        changeEmployee(employees,posts);
                        break;
                    case 3:
                        terminateEmployee(employees);
                        break;
                    case 4:
                        outputAllEmployeesSortedByLastName(employees,posts);
                        break;
                    case 5:
                        outputEmployee(employees,posts);
                        break;
                    case 6:
                        outputEmployeesByFilter(employees);
                        break;
                    case 7:
                        isRunning = false;
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid action");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Очистка сканера
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
                scanner.nextLine(); // Очистка сканера
            }
        }
    }
}