package org.example.menu;

import org.example.api.EmployeeAPI;
import org.example.api.PostAPI;
import org.example.entity.Employee;
import org.example.entity.Post;

import java.util.*;

import static org.example.api.JSON.scanner;


public class PostMenu {
    private PostAPI postAPI;
    private EmployeeAPI employeeAPI;
    private EmployeeMenu employeeMenu;

    public PostMenu(PostAPI postAPI, EmployeeAPI employeeAPI,EmployeeMenu employeeMenu) {
        this.postAPI = postAPI;
        this.employeeAPI = employeeAPI;
        this.employeeMenu = employeeMenu;
    }



    public void menuPost(Map<Integer, Post> posts, List<Employee> employees) {

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
                scanner.nextLine(); // Считывание символа новой строки

                switch (operator) {
                    case 0:
                        employeeMenu.menuEmployee(employees, posts);
                        break;
                    case 1:
                        postAPI.createPost();
                        break;
                    case 2:
                        postAPI.changePost(posts);
                        break;
                    case 3:
                        postAPI.deletePost(posts);
                        break;
                    case 4:
                        postAPI.outputAllPosts(posts);
                        break;
                    case 5:
                        postAPI.outputPost(posts);
                        break;
                    case 6:
                        postAPI.outputPostsWithEmployees(posts, employees);
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