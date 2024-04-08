package org.example.service.console;

import org.example.service.api.EmployeeAPI;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.InputMismatchException;

import static org.example.service.mapper.JsonMapper.scanner;


public class EmployeeMenu {
    private EmployeeConsoleService employeeConsoleService;
    private PostMenu postMenu;

    public EmployeeMenu(EmployeeConsoleService employeeConsoleService, PostMenu postMenu) {
        this.employeeConsoleService = employeeConsoleService;
        this.postMenu = postMenu;
    }

    public void setPostMenu(PostMenu postMenu) {
        this.postMenu = postMenu;
    }

    public void setEmployeeAPI(EmployeeConsoleService employeeConsoleService) {
        this.employeeConsoleService = employeeConsoleService;
    }

    public void menuEmployee() {
        boolean isRunning = true;
        while (isRunning) {
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/resources/employeeMenu.txt"));
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
                        employeeConsoleService.createEmployeeConsole();
                        break;
                    case 2:
                        employeeConsoleService.changeEmployeeConsole();
                        break;
                    case 3:
                        employeeConsoleService.terminateEmployeeConsole();
                        break;
                    case 4:
                        employeeConsoleService.outputAllEmployeesSortedByLastNameConsole();
                        break;
                    case 5:
                        employeeConsoleService.outputEmployeeConsole();
                        break;
                    case 6:
                        employeeConsoleService.outputTerminatedEmployeesConsole();
                        break;
                    case 7:
                        employeeConsoleService.outputEmployeesByFilterConsole();
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