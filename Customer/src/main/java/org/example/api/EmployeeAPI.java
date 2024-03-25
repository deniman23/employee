package org.example.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.example.entity.Employee;
import org.example.entity.Post;
import org.example.menu.EmployeeMenu;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

import static org.example.Main.posts;

public class EmployeeAPI extends PostAPI {

static EmployeeMenu employeeMenu = new EmployeeMenu();
    //Создание сотрудника
    public static void createEmployee(List<Employee> employees) {

        System.out.println("Enter lastName");
        String lastName = scanner.nextLine();

        System.out.println("Enter firstName");
        String firstName = scanner.nextLine();

        System.out.println("Enter middleName");
        String middleName = scanner.nextLine();

        System.out.println("Enter postID");
        int postID = scanner.nextInt();
        scanner.nextLine(); // Считываем оставшийся символ новой строки

        Post post = posts.get(postID);
        if (post == null) {
            System.out.println("Post not found");
        } else {
            Employee newEmployee = new Employee(lastName, firstName, middleName, postID);
            employees.add(newEmployee);
            System.out.println("Success, id: " + newEmployee.getId());
        }
    }

    //Изменение сотрудника
    public static void changeEmployee(List<Employee> employees, Map<Integer, Post> posts) {
        System.out.println("Enter ID");
        int id = scanner.nextInt();
        scanner.nextLine();

        Employee employee = employees.stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .orElse(null);

        if (employee == null) {
            System.out.println("Employee didn't found");
            return;
        }

        System.out.println("Enter lastName");
        String lastName = scanner.nextLine();

        System.out.println("Enter firstName");
        String firstName = scanner.nextLine();

        System.out.println("Enter middleName");
        String middleName = scanner.nextLine();

        System.out.println("Enter postID");
        int postID = scanner.nextInt();
        scanner.nextLine(); // Считываем оставшийся символ новой строки

        Post newPosition = posts.get(postID);
        if (newPosition != null) {
            employee.setPosition(newPosition);
            employee.setPositionId(postID);
        } else {
            System.out.println("Post not found");
            return;
        }

        employee.setLastName(lastName);
        employee.setFirstName(firstName);
        employee.setMiddleName(middleName);
        employee.setModificationDate(LocalDate.now());

        System.out.println("Employee updated successfully");
    }


    // Увольнение сотрудника
    public static void terminateEmployee(List<Employee> employees) {
        System.out.println("Enter ID");
        int id = scanner.nextInt();
        try {
            Employee employee = employees.get(id);
            employee.setTerminated(true);
            employee.setModificationDate(LocalDate.now());
            String json = JSON.gson.toJson(employee);
            System.out.println(json);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Employee with the given ID was not found.");
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid ID number.");
        }
    }

    //Вывести всех сотрудников отсортированных по Фамилии
    public static void outputAllEmployeesSortedByLastName(List<Employee> employees, Map<Integer, Post> posts) {
        if (employees.isEmpty()) {
            System.out.println("Empty");
        } else {
            employees.sort(Comparator.comparing(Employee::getLastName));

            JsonArray employeesJsonArray = new JsonArray();
            for (Employee employee : employees) {
                JsonObject employeeJson = new JsonObject();
                employeeJson.addProperty("id", employee.getId());
                employeeJson.addProperty("lastName", employee.getLastName());
                employeeJson.addProperty("firstName", employee.getFirstName());
                employeeJson.addProperty("middleName", employee.getMiddleName());
                employeeJson.addProperty("creationDate", employee.getCreationDate().toString());
                employeeJson.addProperty("modificationDate", employee.getModificationDate().toString());
                employeeJson.addProperty("isTerminated", employee.getTerminated());

// Получение объекта Post по positionId и добавление информации о должности
                Post post = posts.get(employee.getPositionId());
                JsonObject postJson = new JsonObject();
                if (post != null) {
                    postJson.addProperty("id", post.getId());
                    postJson.addProperty("postName", post.getPostName());
                } else {
                    postJson.addProperty("id", "Not found");
                    postJson.addProperty("postName", "Not found");
                }
                employeeJson.add("post", postJson);

                employeesJsonArray.add(employeeJson);
            }

            String json = JSON.gson.toJson(employeesJsonArray);
            System.out.println(json);
        }
    }


    public static void outputEmployee(List<Employee> employees, Map<Integer, Post> posts) {
        System.out.println("Enter ID");
        int id = scanner.nextInt();
        scanner.nextLine();

        Employee employee = employees.stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .orElse(null);

        if (employee == null) {
            System.out.println("Employee with the given ID was not found.");
        } else {
            JsonObject employeeJson = new JsonObject();
            employeeJson.addProperty("id", employee.getId());
            employeeJson.addProperty("lastName", employee.getLastName());
            employeeJson.addProperty("firstName", employee.getFirstName());
            employeeJson.addProperty("middleName", employee.getMiddleName());
            employeeJson.addProperty("creationDate", employee.getCreationDate().toString());
            employeeJson.addProperty("modificationDate", employee.getModificationDate().toString());
            employeeJson.addProperty("isTerminated", employee.getTerminated());

// Получение объекта Post по positionId и добавление информации о должности
            Post post = posts.get(employee.getPositionId());
            JsonObject postJson = new JsonObject();
            if (post != null) {
                postJson.addProperty("id", post.getId());
                postJson.addProperty("postName", post.getPostName());
            } else {
                postJson.addProperty("id", "Not found");
                postJson.addProperty("postName", "Not found");
            }
            employeeJson.add("post", postJson);

            String json = JSON.gson.toJson(employeeJson);
            System.out.println(json);
        }
    }

    //Вывод сотрудника по фильтру
    public static void outputEmployeesByFilter(List<Employee> employees) {
        System.out.println("Choose filter: ");
        System.out.println("1. Search by Last Name, First Name, Middle Name (by partial coincidence)");
        System.out.println("2. Search by creation date");
        System.out.println("3. Search by position");
        System.out.println("4. Exit");

        int operator = scanner.nextInt();
        switch (operator) {
            case 1:
                searchEmployeesByPartialMatch(employees);
                break;
            case 2:
                searchEmployeesByDate(employees);
                break;
            case 3:
                searchEmployeesByPost(employees,posts);
                break;
            case 4:
                employeeMenu.menuEmployee(employees, posts);
                break;
            default:
                System.out.println("Invalid action");
        }
    }
    private static void searchEmployeesByPost(List<Employee> employees, Map<Integer, Post> posts) {
// Получаем список всех уникальных должностей
        Set<String> positions = posts.values().stream()
                .map(Post::getPostName)
                .collect(Collectors.toSet());
        System.out.println("Enter position name");
        System.out.println("Examples: " + String.join(", ", positions));
        String postToFind = scanner.next();
        List<Employee> foundEmployees = employees.stream()
                .filter(employee -> {
                    Post position = employee.getPosition();
                    return position != null && position.getPostName().equalsIgnoreCase(postToFind);
                })
                .collect(Collectors.toList());
        String json = gson.toJson(foundEmployees);
        System.out.println(json);
    }

    //Поиск по фамилии, имени или отчеству
    private static void searchEmployeesByPartialMatch(List<Employee> employees) {
        System.out.println("Enter the first name, last name or middle name by which you want to find");
        String search = scanner.next();
        List<Employee> foundEmployees = employees.stream()
                .filter(e -> e.getLastName().toLowerCase().contains(search.toLowerCase()) ||
                        e.getFirstName().toLowerCase().contains(search.toLowerCase()) ||
                        e.getMiddleName().toLowerCase().contains(search.toLowerCase()))
                .collect(Collectors.toList());

        String json = gson.toJson(foundEmployees);
        System.out.println(json);
    }

    //Поиск по промежутку времени
    private static void searchEmployeesByDate(List<Employee> employees) {
        try {
            System.out.println("Enter the start date (yyyy-mm-dd):");
            String startDateInput = scanner.next();
            LocalDate startDate = LocalDate.parse(startDateInput);
            System.out.println("Enter the end date (yyyy-mm-dd):");
            String endDateInput = scanner.next();
            LocalDate endDate = LocalDate.parse(endDateInput);

            List<Employee> filteredEmployees = employees.stream()
                    .filter(e -> (e.getCreationDate().isEqual(startDate) || e.getCreationDate().isAfter(startDate)) &&
                            (e.getCreationDate().isEqual(endDate) || e.getCreationDate().isBefore(endDate)))
                    .collect(Collectors.toList());

            String json = gson.toJson(filteredEmployees);
            System.out.println(json);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid end date format. Please use the yyyy-mm-dd format.");
        }
    }


}