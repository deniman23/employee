package org.example.service.console;


import org.example.service.api.PostAPI;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

import static org.example.service.mapper.JsonMapper.scanner;


public class PostMenu {
    private final PostAPI postAPI;
    private final EmployeeMenu employeeMenu;

    public PostMenu(PostAPI postAPI, EmployeeMenu employeeMenu) {
        this.postAPI = postAPI;
        this.employeeMenu = employeeMenu;
    }


    public void menuPost() {
        boolean isRunning = true;
        while (isRunning) {
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/java/org/example/service/console/postMenu.txt"));
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
                        postAPI.createPost();
                        break;
                    case 2:
                        postAPI.changePost();
                        break;
                    case 3:
                        postAPI.deletePost();
                        break;
                    case 4:
                        postAPI.outputAllPosts();
                        break;
                    case 5:
                        postAPI.outputPost();
                        break;
                    case 6:
                        postAPI.outputPostsWithEmployees();
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