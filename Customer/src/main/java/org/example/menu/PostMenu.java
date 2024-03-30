package org.example.menu;


import org.example.service.api.PostAPI;

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
                System.out.println("Choose an action:");
                System.out.println("0. Return to Employee menu");
                System.out.println("1. Create post");
                System.out.println("2. Change post");
                System.out.println("3. Delete post");
                System.out.println("4. Output all posts");
                System.out.println("5. Output one post");
                System.out.println("6. Output posts with employees");
                System.out.println("7. Exit");

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