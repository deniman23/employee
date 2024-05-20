package org.example.console;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.InputMismatchException;

import static org.example.dto.mapper.JsonMapper.scanner;

@Component
public class EmployeeMenu {
    private EmployeeConsoleService employeeConsoleService;
    private final PostMenu postMenu;

    @Autowired
    public EmployeeMenu(PostMenu postMenu) {
        this.postMenu = postMenu;
    }

    @Autowired
    public void setEmployeeConsoleService(EmployeeConsoleService employeeConsoleService) {
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
                        employeeConsoleService.create();
                        break;
                    case 2:
                        employeeConsoleService.change();
                        break;
                    case 3:
                        employeeConsoleService.terminate();
                        break;
                    case 4:
                        employeeConsoleService.outputAll();
                        break;
                    case 5:
                        employeeConsoleService.output();
                        break;
                    case 6:
                        employeeConsoleService.searchEmployees();
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