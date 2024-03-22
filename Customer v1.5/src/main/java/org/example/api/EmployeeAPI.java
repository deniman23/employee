package org.example.api;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.example.entity.Employee;
import org.example.entity.Post;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static org.example.menu.PostMenu.posts;

public class EmployeeAPI {
    private static Map<String, Employee> employees = new HashMap<>();
    public static void createEmployee() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter ID");
        String id = scanner.nextLine();

        System.out.println("Enter lastName");
        String lastName = scanner.nextLine();

        System.out.println("Enter firstName");
        String firstName = scanner.nextLine();

        System.out.println("Enter middleName");
        String middleName = scanner.nextLine();

        System.out.println("Enter postID");
        String postID = scanner.nextLine();

        Post post = posts.get(postID);
        if (post == null) {
            System.out.println("Post not found");
            return;
        }
        Employee newEmployee = new Employee(id, lastName, firstName, middleName, post);
        employees.put(id, newEmployee);
        System.out.println(newEmployee.toJson());
    }

    public void changeEmployee(String id, String lastName, String firstName, String middleName, String positionId) {
        Employee employee = employees.get(id);
        if (employee == null) {
            System.out.println("Сотрудник не найден.");
            return;
        }
        employee.setLastName(lastName);
        employee.setFirstName(firstName);
        employee.setMiddleName(middleName);
        Post newPosition = posts.get(positionId);
        if (newPosition != null) {
            employee.setPosition(newPosition);
        }
        employee.setModificationDate(LocalDate.now());
        System.out.println(employee.toJson());
    }

    public void outputEmployee(String id) {
        Employee employee = employees.get(id);
        if (employee == null) {
            System.out.println("Сотрудник не найден.");
            return;
        }
        System.out.println(employee.toJson());
    }

    public void outputAllEmployeesSortedByLastName() {
        String sortedEmployeesJson = employees.values().stream()
                .sorted((e1, e2) -> e1.getLastName().compareTo(e2.getLastName()))
                .map(Employee::toJson)
                .collect(Collectors.joining(", ", "[", "]"));
        System.out.println(sortedEmployeesJson);
    }

    public void outputEmployeesByFilter(String lastName, String firstName, String middleName, LocalDate fromDate, LocalDate toDate, String positionId) {
        String filteredEmployeesJson = employees.values().stream()
                .filter(e -> lastName == null || e.getLastName().contains(lastName))
                .filter(e -> firstName == null || e.getFirstName().contains(firstName))
                .filter(e -> middleName == null || e.getMiddleName().contains(middleName))
                .filter(e -> fromDate == null || e.getCreationDate().isAfter(fromDate) || e.getCreationDate().isEqual(fromDate))
                .filter(e -> toDate == null || e.getCreationDate().isBefore(toDate) || e.getCreationDate().isEqual(toDate))
                .filter(e -> positionId == null || e.getPosition().getId().equals(positionId))
                .map(Employee::toJson)
                .collect(Collectors.joining(", ", "[", "]"));
        System.out.println(filteredEmployeesJson);
    }

    public void terminateEmployee(String id) {
        Employee employee = employees.get(id);
        if (employee == null) {
            System.out.println("Сотрудник не найден.");
            return;
        }
        employee.setTerminated(true);
        employee.setModificationDate(LocalDate.now());
        System.out.println(employee.toJson());
    }
}