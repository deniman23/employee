package org.example.service.console;

import org.example.service.api.EmployeeAPI;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.InputMismatchException;

import static org.example.service.mapper.JsonMapper.scanner;


public class EmployeeMenu {
    private EmployeeAPI employeeAPI;
    private PostMenu postMenu;

    public EmployeeMenu(EmployeeAPI employeeAPI, PostMenu postMenu) {
        this.employeeAPI = employeeAPI;
        this.postMenu = postMenu;
    }

    public void setPostMenu(PostMenu postMenu) {
        this.postMenu = postMenu;
    }

    public void setEmployeeAPI(EmployeeAPI employeeAPI) {
        this.employeeAPI = employeeAPI;
    }

    public void menuEmployee() {
        boolean isRunning = true;
        while (isRunning) {
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/java/org/example/service/console/employeeMenu.txt"));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    System.out.println(line);
                }
                bufferedReader.close();

                System.out.print("Select option: ");
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
                        System.out.println("Invalid action. Please try again.");
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