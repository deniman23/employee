package org.example.menu;

import org.example.Main;
import org.example.api.EmployeeAPI;
import org.example.api.PostAPI;
import org.example.entity.Employee;
import org.example.entity.Post;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;

import static org.example.api.EmployeeAPI.*;
import static org.example.api.JSON.scanner;


public class EmployeeMenu{
    private EmployeeAPI employeeAPI;
    private PostMenu postMenu;

    public EmployeeMenu(EmployeeAPI employeeAPI, PostMenu postMenu) {
        this.employeeAPI = employeeAPI;
        this.postMenu = postMenu;
    }

    public void setPostMenu(PostMenu postMenu) {
        this.postMenu = postMenu;
    }

    public void menuEmployee(List<Employee> employees, Map<Integer, Post> posts) {
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
                        postMenu.menuPost(posts, employees);
                        break;
                    case 1:
                        employeeAPI.createEmployee(employees);
                        break;
                    case 2:
                        employeeAPI.changeEmployee(employees, posts);
                        break;
                    case 3:
                        employeeAPI.terminateEmployee(employees);
                        break;
                    case 4:
                        employeeAPI.outputAllEmployeesSortedByLastName(employees, posts);
                        break;
                    case 5:
                        employeeAPI.outputEmployee(employees, posts);
                        break;
                    case 6:
                        employeeAPI.outputEmployeesByFilter(employees);
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
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
                scanner.nextLine();
            }
        }
    }
}