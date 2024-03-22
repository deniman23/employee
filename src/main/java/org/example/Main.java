package org.example;

import com.google.gson.JsonObject;
import com.google.gson.Gson;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
/*В файле data.json лежат примеры для ввода в консоль*/
public class Main extends PostAPI{
    private static Map<String, Employee> employees = new HashMap<>();
    //Специальный класс из библ google.gson
    static Gson gson = new Gson();

    public static void main(String[] args) {
        menu();
    }
    //потом отделится в отдельный класс Api, пока всё тут
    //Есть много повторов, позже всё объединится
    public static void menu(){
        try (Scanner scanner = new Scanner(System.in)) {
            boolean isRunning = true;
            while (isRunning == true) {
                System.out.println("Choose an action");
                System.out.println("0. Go to post menu");
                System.out.println("1. Create employer");
                System.out.println("2. Change employer");
                System.out.println("3. Delete employer");
                System.out.println("4. Output all sorted employees");
                System.out.println("5. Output one employer");
                System.out.println("6. Output filtered employees");
                System.out.println("7. Exit");


                int operator = scanner.nextInt();
                scanner.nextLine(); // Считывание символа новой строки

                switch (operator) {
                    case 0:
                        postMenu(posts,gson);
                        break;
                    case 1:
                        create(scanner, gson, employees,posts);
                        break;
                    case 2:

                        break;
                    case 3:

                        break;
                    case 4:
                        outputAll(employees);
                        break;
                    case 5:

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
    //Действия над json
    private static void processJson(JsonObject jsonData, boolean isChange, Map<String, Employee> employeeMap, Map<String, Post> postMap) {
        String id = jsonData.get("id").getAsString();
        String lastName = jsonData.get("lastName").getAsString();
        String firstName = jsonData.get("firstName").getAsString();
        String middleName = jsonData.get("middleName").getAsString();
        String postId = jsonData.get("postId").getAsString();
        boolean isTerminated = jsonData.get("isTerminated").getAsBoolean();
        //Обновление сотрудника
        if (isChange) {
            if (employeeMap.containsKey(id)) {
                Employee employee = employeeMap.get(id);
                employee.setLastName(lastName);
                employee.setFirstName(firstName);
                employee.setMiddleName(middleName);
                if (postMap.containsKey(postId)) {
                    employee.setPost(postMap.get(postId));
                }
                employee.setTerminated(isTerminated);
                employee.setModificationDate(LocalDate.now());
                System.out.println("Employee updated successfully.");
            } else {
                System.out.println("Employee with given ID does not exist.");
            }
            //Создание сотрудника при isChange = false
        } else {
            if (postMap.containsKey(postId)) {
                Employee employee = new Employee(id, firstName, lastName, middleName, postMap.get(postId));
                employee.setTerminated(isTerminated);
                employeeMap.put(id, employee);
                System.out.println("Employee created successfully.");
            } else {
                System.out.println("Post with given ID does not exist. Cannot create employee.");
            }
        }
    }
    //Метод на создание Сотрудника
    public static void create(Scanner scanner, Gson gson, Map<String, Employee> map,Map<String, Post> postMap) {
        System.out.println("Enter json:");
        String data = scanner.nextLine();
        JsonObject jsonData = parseJson(data, gson);
        if (jsonData != null) {
            processJson(jsonData, false, map,postMap);
        }
    }
    //Вывод всех отсортированных сотрудников
    public static void outputAll(Map<String, Employee> map) {
        map.values().stream().sorted().forEach(post -> System.out.println(post.getId() + ", " + post.getFirstName() + ", " + post.getLastName() + ", " + post.getMiddleName() + ", " + post.getPost().getPostName()+", "+post.getPost().getId()));
    }
}