package org.example.menu;

import org.example.api.EmployeeAPI;

import java.util.InputMismatchException;

import static org.example.api.JSON.scanner;


public class EmployeeMenu {
    private final EmployeeAPI employeeAPI;
    private PostMenu postMenu;

    public EmployeeMenu(EmployeeAPI employeeAPI, PostMenu postMenu) {
        this.employeeAPI = employeeAPI;
        this.postMenu = postMenu;
    }

    public void setPostMenu(PostMenu postMenu) {
        this.postMenu = postMenu;
    }

    public void menuEmployee() {
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
                System.out.println("6. Output terminated employees");
                System.out.println("7. Output filtered employees");
                System.out.println("8. Exit");

                int operator = scanner.nextInt();
                scanner.nextLine();

                switch (operator) {
                    case 0:
                        postMenu.menuPost();
                        break;
                    case 1:
                        employeeAPI.createEmployee();
                        break;
                    case 2:
                        employeeAPI.changeEmployee();
                        break;
                    case 3:
                        employeeAPI.terminateEmployee();
                        break;
                    case 4:
                        employeeAPI.outputAllEmployeesSortedByLastName();
                        break;
                    case 5:
                        employeeAPI.outputEmployee();
                        break;
                    case 6:
                        employeeAPI.outputTerminatedEmployees();
                        break;
                    case 7:
                        employeeAPI.outputEmployeesByFilter();
                        break;
                    case 8:
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