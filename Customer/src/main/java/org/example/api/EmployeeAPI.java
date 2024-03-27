package org.example.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.example.entity.Employee;
import org.example.entity.Post;
import org.example.menu.EmployeeMenu;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

import static org.example.api.JSON.*;

public class EmployeeAPI {

    private Map<Integer, Post> posts;
    private List<Employee> employees;
    private EmployeeMenu employeeMenu;

    public EmployeeAPI(Map<Integer, Post> posts, List<Employee> employees) {
        this.posts = posts;
        this.employees = employees;
    }

    public EmployeeAPI(EmployeeMenu employeeMenu) {
        this.employeeMenu = employeeMenu;
    }

    // Добавляет в JsonObject все свойства и возвращает их со значениями
    private String convertEmployeeToJson(Employee employee) {
        JsonObject employeeJson = new JsonObject();
        employeeJson.addProperty("id", employee.getId());
        employeeJson.addProperty("lastName", employee.getLastName());
        employeeJson.addProperty("firstName", employee.getFirstName());
        employeeJson.addProperty("middleName", employee.getMiddleName());
        employeeJson.addProperty("creationDate", employee.getCreationDate().toString());
        employeeJson.addProperty("modificationDate", employee.getModificationDate().toString());
        employeeJson.addProperty("isTerminated", employee.getTerminated());
        return gson.toJson(employeeJson);
    }


    //Создание сотрудника
    public void createEmployee(List<Employee> employees, Map<Integer, Post> posts) {
        int employeeID = employees.size() + 1;
        System.out.println("Enter lastName");
        String lastName = scanner.nextLine();

        System.out.println("Enter firstName");
        String firstName = scanner.nextLine();

        System.out.println("Enter middleName");
        String middleName = scanner.nextLine();

        System.out.println("Enter postID");
        int postID = scanner.nextInt();
        scanner.nextLine();

        Post post = posts.get(postID);
        if (post == null) {
            System.out.println("Position not found");
        } else {
            Employee newEmployee = new Employee(employeeID, lastName, firstName, middleName, postID);
            employees.add(newEmployee);
            System.out.println("Success, id: " + newEmployee.getId());
        }
    }

    //Изменение сотрудника
    public void changeEmployee(List<Employee> employees, Map<Integer, Post> posts) {
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
        scanner.nextLine();

        Post newPosition = posts.get(postID);
        if (newPosition != null) {
            employee.setPosition(newPosition);
            employee.setPositionId(postID);
        } else {
            System.out.println("Post not found");
        }

        employee.setLastName(lastName);
        employee.setFirstName(firstName);
        employee.setMiddleName(middleName);
        employee.setModificationDate(LocalDate.now());

        System.out.println("Employee updated successfully");
    }


    // Увольнение сотрудника
    public void terminateEmployee(List<Employee> employees) {
        System.out.println("Enter ID");
        int id = scanner.nextInt();
        scanner.nextLine();

        Employee employee = employees.stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .orElse(null);

        if (employee == null) {
            System.out.println("Employee with the given ID was not found.");
            return;
        }

        if (employee.getTerminated()) {
            System.out.println("Employee already terminated");
            return;
        }

        employee.setTerminated(true);
        employee.setModificationDate(LocalDate.now());
        System.out.println("Employee terminated successfully");
    }

    //Вывести всех сотрудников отсортированных по Фамилии
    public void outputAllEmployeesSortedByLastName(List<Employee> employees, Map<Integer, Post> posts) {
        if (employees.isEmpty()) {
            System.out.println("Empty");
        } else {
            employees.sort(Comparator.comparing(Employee::getLastName));
            JsonArray employeesJsonArray = new JsonArray();
            for (Employee employee : employees) {
                String employeeJsonString = convertEmployeeToJson(employee);
                JsonObject employeeJson = JsonParser.parseString(employeeJsonString).getAsJsonObject();

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
            String json = gson.toJson(employeesJsonArray);
            System.out.println(json);
        }
    }


    public void outputEmployee(List<Employee> employees, Map<Integer, Post> posts) {
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
            String employeeJson = convertEmployeeToJson(employee);

            Post post = posts.get(employee.getPositionId());
            JsonObject postJson = new JsonObject();
            if (post != null) {
                postJson.addProperty("id", post.getId());
                postJson.addProperty("postName", post.getPostName());
            } else {
                postJson.addProperty("id", "Not found");
                postJson.addProperty("postName", "Not found");
            }

            JsonObject employeeData = JsonParser.parseString(employeeJson).getAsJsonObject();
            employeeData.add("post", postJson);

            System.out.println(gson.toJson(employeeData));
        }
    }

    //Вывод сотрудника по фильтру
    public void outputEmployeesByFilter(List<Employee> employees) {
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
                searchEmployeesByPost(employees, posts);
                break;
            case 4:
                employeeMenu.menuEmployee(employees, posts);
                break;
            default:
                System.out.println("Invalid action");
        }
    }


    //Поиск по занимаемой должности
    private void searchEmployeesByPost(List<Employee> employees, Map<Integer, Post> posts) {
        Set<String> positions = posts.values().stream()
                .map(Post::getPostName)
                .collect(Collectors.toSet());
        System.out.println("Enter position name:");
        System.out.println("Examples: " + String.join(", ", positions));
        scanner.nextLine();
        String postToFind = scanner.nextLine();

        boolean positionExists = employees.stream()
                .filter(e -> e.getPositionId() != 0 && posts.containsKey(e.getPositionId()))
                .anyMatch(e -> posts.get(e.getPositionId()).getPostName().equals(postToFind));

        if (!positionExists) {
            System.out.println("Position not found. Please enter a valid position name.");
        }

        employees.stream()
                .filter(e -> e.getPositionId() != 0 && posts.containsKey(e.getPositionId()))
                .filter(e -> posts.get(e.getPositionId()).getPostName().equals(postToFind))
                .map(this::convertEmployeeToJson)
                .forEach(System.out::println);
    }

    //Поиск по фамилии, имени и отчеству, или по частичному совпадению
    private void searchEmployeesByPartialMatch(List<Employee> employees) {
        System.out.println("Enter the first name, last name or middle name by which you want to find");
        String search = scanner.next();

        employees.stream()
                .filter(e -> e.getLastName().toLowerCase().contains(search.toLowerCase()) ||
                        e.getFirstName().toLowerCase().contains(search.toLowerCase()) ||
                        e.getMiddleName().toLowerCase().contains(search.toLowerCase()))
                .map(this::convertEmployeeToJson)
                .forEach(System.out::println);
    }

    //Поиск по промежутку времени
    private void searchEmployeesByDate(List<Employee> employees) {
        try {
            System.out.println("Enter the start date (yyyy-mm-dd):");
            String startDateInput = scanner.next();
            LocalDate startDate = LocalDate.parse(startDateInput);
            System.out.println("Enter the end date (yyyy-mm-dd):");
            String endDateInput = scanner.next();
            LocalDate endDate = LocalDate.parse(endDateInput);
            employees.stream()
                    .filter(e -> (e.getCreationDate().isEqual(startDate) || e.getCreationDate().isAfter(startDate)) &&
                            (e.getCreationDate().isEqual(endDate) || e.getCreationDate().isBefore(endDate)))
                    .map(this::convertEmployeeToJson)
                    .forEach(System.out::println);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid end date format. Please use the yyyy-mm-dd format.");
        }
    }


}