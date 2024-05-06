package org.example.services.console;


import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

import static org.example.services.mapper.JsonMapper.scanner;


public class PostMenu {
    private final PostConsoleService postConsoleService;
    private final EmployeeMenu employeeMenu;

    public PostMenu(PostConsoleService postConsoleService, EmployeeMenu employeeMenu) {
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
                        postConsoleService.createPostConsole();
                        break;
                    case 2:
                        postConsoleService.changePostConsole();
                        break;
                    case 3:
                        postConsoleService.deletePostConsole();
                        break;
                    case 4:
                        postConsoleService.outputAllPostsConsole();
                        break;
                    case 5:
                        postConsoleService.outputPostConsole();
                        break;
                    case 6:
                        postConsoleService.outputPostsWithEmployeesConsole();
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
}