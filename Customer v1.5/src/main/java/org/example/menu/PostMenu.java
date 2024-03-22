package org.example.menu;

import org.example.entity.Employee;
import org.example.entity.Post;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static org.example.api.PostAPI.*;

public class PostMenu {
    public static Map<String, Post> posts = new HashMap<>();

    static {
        // Инициализация должностей
        posts.put("1", new Post("1", "dev"));
        posts.put("2", new Post("2", "ceo"));
        posts.put("3", new Post("3", "qa"));
    }

    public static void menuPost(Map<String, Post> posts, Map<String, Employee> employees) {
        try (Scanner scanner = new Scanner(System.in)) {
            boolean isRunning = true;
            while (isRunning) {
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
                        EmployeeMenu.menuEmployee(employees, posts);
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
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}