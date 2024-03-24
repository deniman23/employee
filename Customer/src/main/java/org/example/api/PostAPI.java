package org.example.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.example.entity.Employee;
import org.example.entity.Post;

import java.util.*;
import java.util.stream.Collectors;

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
        } else if (posts.values().stream().anyMatch(post -> post.getPostName().equalsIgnoreCase(postName))) {
            System.out.println("Invalid name");
            createPost();
        } else {
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
    //Вывод должностей с фамилиями сотрудников
    public static void outputPostsWithEmployees(Map<String, Post> posts, List<Employee> employees) {
        for (Map.Entry<String, Post> postEntry : posts.entrySet()) {
            postObject.addProperty("PostName", postEntry.getValue().getPostName());
            // Фильтруем и сортируем сотрудников по фамилии, принадлежащих к текущей должности
            JsonArray employeesLastNamesArray = new JsonArray();
            employees.stream()
                    .filter(employee -> employee.getPosition().getId().equals(postEntry.getKey()))
                    .sorted(Comparator.comparing(Employee::getLastName))
                    .forEach(employee -> employeesLastNamesArray.add(employee.getLastName()));

            postObject.add("EmployeesLastNames", employeesLastNamesArray);

            // Выводим информацию о каждой должности в отдельной строке
            System.out.println(gson.toJson(postObject));
        }
    }
}
