package org.example.console;


import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

import static org.example.dto.mapper.JsonMapper.scanner;

@Component
public class PostMenu {
    private final PostConsoleService postConsoleService;
    private EmployeeMenu employeeMenu;

    public PostMenu(PostConsoleService postConsoleService, @Lazy EmployeeMenu employeeMenu) {
        this.postConsoleService = postConsoleService;
        this.employeeMenu = employeeMenu;
    }


    public void menuPost() {
        boolean isRunning = true;
        while (isRunning) {
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/resources/postMenu.txt"));
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
                        employeeMenu.menuEmployee();
                        break;
                    case 1:
                        postConsoleService.create();
                        break;
                    case 2:
                        postConsoleService.change();
                        break;
                    case 3:
                        postConsoleService.delete();
                        break;
                    case 4:
                        postConsoleService.outputAll();
                        break;
                    case 5:
                        postConsoleService.output();
                        break;
                    case 6:
                        postConsoleService.outputPostsWithEmployees();
                        break;
                    case 7:
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

    public void setEmployeeMenu(EmployeeMenu employeeMenu) {
        this.employeeMenu = employeeMenu;
    }
}