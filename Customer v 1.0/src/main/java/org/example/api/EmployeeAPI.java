package org.example.api;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.example.entity.Employee;
import org.example.entity.Post;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Map;
import java.util.Scanner;

public class EmployeeAPI {
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
    public static void create(Scanner scanner, Gson gson, Map<String, Employee> map, Map<String, Post> postMap) {
        System.out.println("Enter json:");
        String data = scanner.nextLine();
        JsonObject jsonData = parseJson(data, gson);
        if (jsonData != null) {
            processJson(jsonData, false, map, postMap);
        }
    }

    //Вывод всех отсортированных сотрудников
    public static void outputSortedByLastName(Map<String, Employee> map) {
        map.values().stream()
                .sorted(Comparator.comparing(Employee::getLastName))
                .forEach(System.out::println);
    }

    public static void delete(Scanner scanner, Map<String, Employee> map) {
        System.out.println("Enter ID");
        String id = scanner.nextLine();
        if (map.containsKey(id)) {
            map.remove(id);
            System.out.println("Success");
        } else
            System.out.println("Error");
    }

    public static void change(Scanner scanner, Gson gson, Map<String, Employee> map, Map<String, Post> postMap) {
        System.out.println("Enter json with employee data to change:");
        String data = scanner.nextLine();
        JsonObject jsonData = parseJson(data, gson);
        if (jsonData != null) {
            processJson(jsonData, true, map, postMap);
        }
    }

    public static void outputOne(Scanner scanner, Map<String, Employee> map) {
        System.out.println("Enter employee ID:");
        String id = scanner.nextLine();
        Employee employee = map.get(id);
        if (employee != null) {
            System.out.println(employee);
        } else {
            System.out.println("Employee with given ID does not exist.");
        }
    }

    public static void outputFiltered(Scanner scanner, Map<String, Employee> map) {
        System.out.println("Enter search criteria:");
        String criteria = scanner.nextLine().toLowerCase();
        LocalDate dateFrom = LocalDate.parse(scanner.nextLine()); // "yyyy-mm-dd"
        LocalDate dateTo = LocalDate.parse(scanner.nextLine()); // "yyyy-mm-dd"
        String position = scanner.nextLine().toLowerCase();

        map.values().stream()
                .filter(employee -> employee.getLastName().toLowerCase().contains(criteria) ||
                        employee.getFirstName().toLowerCase().contains(criteria) ||
                        employee.getMiddleName().toLowerCase().contains(criteria))
                .filter(employee -> !employee.getCreationDate().isBefore(dateFrom) &&
                        !employee.getCreationDate().isAfter(dateTo))
                .filter(employee -> employee.getPost().getPostName().toLowerCase().equals(position))
                .forEach(System.out::println);
    }
}
