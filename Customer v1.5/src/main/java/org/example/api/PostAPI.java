package org.example.api;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.example.entity.Employee;
import org.example.entity.Post;

import java.util.*;

import static org.example.menu.PostMenu.posts;

/*Многое повторяется, но над Post почти все действия реализованы, осталось:
	*Вывести должности с вложенными сотрудниками учитывая сортировку по должностям по “Наименование должности”
	 и вложенным сотрудникам по “Фамилии”
	 * При создании/изменении учесть, что должность с таким именем уже может существовать. (Выводить информирующее сообщение) */
public class PostAPI extends JSON {
    static Scanner scanner = new Scanner(System.in);


    //Создание должности
    public static void createPost() {

        System.out.println("Enter ID");
        String postID = scanner.nextLine();

        System.out.println("Enter Name");
        String postName = scanner.nextLine();


        if (posts.containsKey(postID)) {
            System.out.println("Invalid id");
            createPost();
        }
        else if (posts.values().stream().anyMatch(post -> post.getPostName().equalsIgnoreCase(postName))) {
            System.out.println("Invalid name");
            createPost();
        }
        else {
            posts.put(postID, new Post(postID, postName.toLowerCase()));
            System.out.println("Success");
        }


    }

    //Изменение должности вводить в формате json
    public static void changePost(Map<String, Post> map) {
        System.out.println("Enter json for existing post:");
        String data = scanner.nextLine();
        JsonObject jsonData = parseJson(data, gson);
        if (jsonData != null) {
            processJson(jsonData, true, map);
        }
    }

    //Удаление должности по ID
    public static void deletePost(Map<String, Post> map) {
        outputAllPosts(posts);
        System.out.println("Enter ID");
        String id = scanner.nextLine();
        if (map.containsKey(id)) {
            map.remove(id);
            System.out.println("Success");
        } else
            System.out.println("Error");
    }

    //Вывод всех должностей с индексом
    public static void outputAllPosts(Map<String, Post> posts) {
        posts.values().stream().forEach(post -> System.out.println("{\"id\": " + post.getId() +
                ", \"postName\": " + post.getPostName() + "}"));

    }

    //Вывод одной должности
    public static void outputPost(Map<String, Post> map) {
        System.out.println("Enter ID");
        String id = scanner.nextLine();
        map.values().stream()
                .filter(post -> post.getId().equals(id))
                .forEach(post -> System.out.println("{\"id\": " + post.getId() +
                        ", \"postName\": " + post.getPostName() + "}"));
    }

    public static void outputPostsWithEmployees(Map<String, Post> posts, Map<String, Employee> employees) {
        // Сначала сортируем должности по наименованию
        posts.values().stream()
                .sorted(Comparator.comparing(Post::getPostName))
                .forEach(post -> {
                    // Для каждой должности выводим её название и список сотрудников
                    System.out.println("{\"PostName\": \"" + post.getPostName() + "\", \"Employees\": [");
                    // Фильтруем сотрудников, которые принадлежат к текущей должности и сортируем их по фамилии
                    employees.values().stream()
                            .filter(employee -> employee.getPosition().equals(post))
                            .sorted(Comparator.comparing(Employee::getLastName))
                            .forEach(employee -> {
                                // Выводим информацию о сотруднике в формате JSON
                                System.out.println(employee.toJson() + ",");
                            });
                    System.out.println("]}");
                });
    }


}
