package org.example.api;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.entity.Employee;
import org.example.entity.Post;
import org.example.menu.EmployeeMenu;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

import static org.example.api.JSON.*;


public class EmployeeAPI {

    private final Map<Integer, Post> posts;
    private final List<Employee> employees;
    private final EmployeeMenu employeeMenu;
    private final JSON json;

    public EmployeeAPI(Map<Integer, Post> posts, List<Employee> employees, EmployeeMenu employeeMenu, JSON json) {
        this.posts = posts;
        this.employees = employees;
        this.employeeMenu = employeeMenu;
        this.json = json;
    }

    //Создание сотрудника
    public void createEmployee() {
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
    public void changeEmployee() {
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
    public void terminateEmployee() {
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
    public void outputAllEmployeesSortedByLastName() {
        if (employees.isEmpty()) {
            System.out.println("Empty");
        } else {
            ArrayNode employeesJsonArray = objectMapper.createArrayNode();
            objectMapper.registerModule(new JavaTimeModule());
            employees.stream()
                    .sorted(Comparator.comparing(Employee::getLastName))
                    .forEach(employee -> {
                        ObjectNode employeeJson = objectMapper.valueToTree(employee);
                        ObjectNode postJson = (ObjectNode) Optional.ofNullable(posts.get(employee.getPositionId()))
                                .map(objectMapper::valueToTree)
                                .orElseGet(() -> {
                                    ObjectNode notFoundPostJson = objectMapper.createObjectNode();
                                    notFoundPostJson.put("id", "Not found");
                                    notFoundPostJson.put("postName", "Not found");
                                    return notFoundPostJson;
                                });
                        employeeJson.set("post", postJson);
                        employeesJsonArray.add(employeeJson);
                    });

            try {
                System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(employeesJsonArray));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void outputEmployee() {
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
            ObjectNode employeeJson = objectMapper.valueToTree(employee);

            ObjectNode postJson = (ObjectNode) Optional.ofNullable(posts.get(employee.getPositionId()))
                    .map(objectMapper::valueToTree)
                    .orElseGet(() -> {
                        ObjectNode notFoundPostJson = objectMapper.createObjectNode();
                        notFoundPostJson.put("id", "Not found");
                        notFoundPostJson.put("postName", "Not found");
                        return notFoundPostJson;
                    });
            employeeJson.set("post", postJson);

            try {
                System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(employeeJson));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //Вывод сотрудника по фильтру
    public void outputEmployeesByFilter() {
        System.out.println("Choose filter: ");
        System.out.println("1. Search by Last Name, First Name, Middle Name (by partial coincidence)");
        System.out.println("2. Search by creation date");
        System.out.println("3. Search by position");
        System.out.println("4. Exit");


        int operator = scanner.nextInt();
        switch (operator) {
            case 1:
                searchEmployeesByPartialMatch();
                break;
            case 2:
                searchEmployeesByDate();
                break;
            case 3:
                searchEmployeesByPost();
                break;
            case 4:
                employeeMenu.menuEmployee();
                break;
            default:
                System.out.println("Invalid action");
        }
    }

    //Поиск по занимаемой должности
    public void searchEmployeesByPost() {
        Set<String> positions = posts.values().stream()
                .map(Post::getPostName)
                .collect(Collectors.toSet());
        System.out.println("Enter position name:");
        System.out.println("Examples: " + String.join(", ", positions));
        scanner.nextLine();
        String postToFind = scanner.nextLine();

        List<Employee> employeesWithPosition = employees.stream()
                .filter(e -> e.getPositionId() != 0 && posts.containsKey(e.getPositionId()))
                .filter(e -> posts.get(e.getPositionId()).getPostName().equals(postToFind))
                .collect(Collectors.toList());

        if (employeesWithPosition.isEmpty()) {
            System.out.println("Position not found. Please enter a valid position name.");
        } else {
            employeesWithPosition.stream()
                    .map(e -> json.convertEmployeeToJson(e, this.posts))
                    .forEach(System.out::println);
        }
    }

    //Поиск по фамилии, имени и отчеству, или по частичному совпадению
    public void searchEmployeesByPartialMatch() {
        System.out.println("Enter the first name, last name or middle name by which you want to find:");
        scanner.nextLine(); // Очистка буфера сканера
        String search = scanner.nextLine();

        employees.stream()
                .filter(e -> e.getLastName().toLowerCase().contains(search.toLowerCase()) ||
                        e.getFirstName().toLowerCase().contains(search.toLowerCase()) ||
                        e.getMiddleName().toLowerCase().contains(search.toLowerCase()))
                .map(e -> json.convertEmployeeToJson(e, this.posts))
                .forEach(System.out::println);
    }

    //Поиск по промежутку времени
    public void searchEmployeesByDate() {
        try {
            System.out.println("Enter the start date (yyyy-mm-dd):");
            String startDateInput = scanner.nextLine();
            LocalDate startDate = LocalDate.parse(startDateInput);

            System.out.println("Enter the end date (yyyy-mm-dd):");
            String endDateInput = scanner.nextLine();
            LocalDate endDate = LocalDate.parse(endDateInput);

            employees.stream()
                    .filter(e -> (e.getCreationDate().isEqual(startDate) || e.getCreationDate().isAfter(startDate)) &&
                            (e.getCreationDate().isEqual(endDate) || e.getCreationDate().isBefore(endDate)))
                    .map(e -> json.convertEmployeeToJson(e, this.posts))
                    .forEach(System.out::println);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please use the yyyy-mm-dd format.");
        }
    }
}