package org.example.menu;

import com.google.gson.Gson;
import org.example.entity.Post;

import java.util.Map;
import java.util.Scanner;

import static org.example.api.PostAPI.*;
import static org.example.menu.EmployeeMenu.menu;

public class PostMenu {
    public static void postMenu(Map<String, Post> map, Gson gson) {
        try (Scanner scanner = new Scanner(System.in)) {
            boolean isRunning = true;
            while (isRunning) {
                System.out.println("Choose an action");
                System.out.println("0. Return to Employee");
                System.out.println("1. Create post");
                System.out.println("2. Change post");
                System.out.println("3. Delete post");
                System.out.println("4. Output all posts");
                System.out.println("5. Output one post");
                System.out.println("6. Exit");

                int operator = scanner.nextInt();
                scanner.nextLine(); // Считывание символа новой строки

                switch (operator) {
                    case 0:
                        menu();
                        break;
                    case 1:
                        createPost(scanner, gson, map);
                        break;
                    case 2:
                        changePost(scanner, gson, map);
                        break;
                    case 3:
                        deletePost(scanner, map);
                        break;
                    case 4:
                        outputAllPosts(map);
                        break;
                    case 5:
                        outputPost(scanner, map);
                        break;
                    case 6:
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
