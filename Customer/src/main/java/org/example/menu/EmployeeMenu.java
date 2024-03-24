package org.example.menu;

import org.example.api.EmployeeAPI;
import org.example.entity.Employee;
import org.example.entity.Post;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class EmployeeMenu extends EmployeeAPI {

    public static void menuEmployee(List<Employee> employees, Map<String, Post> posts) {
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;
        try {
            while (isRunning) {
                System.out.println("Choose an action");
                System.out.println("0. Go to post menu");
                System.out.println("1. Create employee");
                System.out.println("2. Change employee");
                System.out.println("3. Terminate employee");
                System.out.println("4. Output all sorted employees");
                System.out.println("5. Output one employee");
                System.out.println("6. Output filtered employees");
                System.out.println("7. Exit");

                int operator = scanner.nextInt();
                scanner.nextLine(); // Считывание символа новой строки

                switch (operator) {
                    case 0:
                        PostMenu.menuPost(posts, employees); // Assuming PostMenu has a similar static method 'menu'
                        break;
                    case 1:
                        createEmployee();
                        break;
                    case 2:
                        changeEmployee();
                        break;
                    case 3:
                        terminateEmployee(employees);
                        break;
                    case 4:
                        outputAllEmployeesSortedByLastName(employees);
                        break;
                    case 5:
                        outputEmployee(employees);
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
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}