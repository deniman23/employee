package org.example.menu;

import org.example.api.PostAPI;
import org.example.entity.Employee;
import org.example.entity.Post;

import java.util.*;

import static org.example.api.PostAPI.*;
import static org.example.menu.EmployeeMenu.menuEmployee;

public class PostMenu extends PostAPI{


    public static void menuPost(Map<Integer, Post> posts, List<Employee> employees) {

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
                        menuEmployee(employees, posts);
                        break;
                    case 1:
                        createPost();
                        break;
                    case 2:
                        changePost(posts);
                        break;
                    case 3:
                        deletePost(posts);
                        break;
                    case 4:
                        outputAllPosts(posts);
                        break;
                    case 5:
                        outputPost(posts);
                        break;
                    case 6:
                        outputPostsWithEmployees(posts, employees);
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
                scanner.nextLine(); // Очистка сканера
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
                scanner.nextLine(); // Очистка сканера
            }
        }
    }
}