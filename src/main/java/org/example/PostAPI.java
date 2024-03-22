package org.example;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static org.example.Main.menu;
/*Многое повторяется, но над Post почти все действия реализованы, осталось:
	*Вывести должности с вложенными сотрудниками учитывая сортировку по должностям по “Наименование должности”
	 и вложенным сотрудникам по “Фамилии”
	 * При создании/изменении учесть, что должность с таким именем уже может существовать. (Выводить информирующее сообщение) */
public class PostAPI {
    static Map<String, Post> posts = new HashMap<>();
    public static void postMenu(Map<String, Post> map,Gson gson) {
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
    //Метод приводит входящий String в json
    private static JsonObject parseJson(String data, Gson gson) {
        try {
            return gson.fromJson(data, JsonObject.class);
        } catch (Exception e) {
            System.out.println("Error parsing JSON: " + e.getMessage());
            return null;
        }
    }
    private static void processJson(JsonObject jsonData, boolean isChange,Map<String, Post> map) {
        String id = jsonData.get("id").getAsString();
        String postName = jsonData.get("postName").getAsString();
        if (isChange) {
            if (map.containsKey(id)) {
                map.get(id).setPostName(postName);
                System.out.println("Post updated successfully.");
            } else {
                System.out.println("Post with given ID does not exist.");
            }
        } else {
            map.put(id, new Post(id, postName));
            System.out.println("Post created successfully.");
        }
    }
    //Вывод одной должности
    public static void outputPost(Scanner scanner, Map<String, Post> map) {
        System.out.println("Enter Name");
        String name = scanner.nextLine();
        map.values().stream()
                .filter(post -> post.getPostName().equals(name))
                .forEach(post -> System.out.println(post.getId() + ", " + post.getPostName()));
    }
    //Создание должности
    public static void createPost(Scanner scanner, Gson gson, Map<String, Post> map) {
        System.out.println("Enter json for new post:");
        String data = scanner.nextLine();
        JsonObject jsonData = parseJson(data, gson);
        if (jsonData != null) {
            processJson(jsonData, false, map);
        }
    }
    //Изменение json
    public static void changePost(Scanner scanner, Gson gson, Map<String, Post> map) {
        System.out.println("Enter json for existing post:");
        String data = scanner.nextLine();
        JsonObject jsonData = parseJson(data, gson);
        if (jsonData != null) {
            processJson(jsonData, true, map);
        }
    }
    //Удаление должности по ID
    public static void deletePost(Scanner scanner, Map<String, Post> map) {
        System.out.println("Enter ID");
        String id = scanner.nextLine();
        if(map.containsKey(id)) {
            map.remove(id);
            System.out.println("Success");
        }
        else
            System.out.println("Error");
    }
    //Вывод всех должностей с индексом
    public static void outputAllPosts(Map<String, Post> map) {
        map.values().forEach(post -> System.out.println(post.getId() + ", " + post.getPostName()));
    }
}
