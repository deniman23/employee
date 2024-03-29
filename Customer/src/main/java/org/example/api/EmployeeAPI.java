package org.example.api;

import org.example.entity.Employee;
import org.example.entity.Post;
import org.example.menu.EmployeeMenu;
import org.example.service.DataService;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

import static org.example.api.JSON.*;


public class EmployeeAPI {

    private final DataService dataService;
    private final EmployeeMenu employeeMenu;
    private final JSON json;

    public EmployeeAPI(DataService dataService, EmployeeMenu employeeMenu, JSON json) {
        this.dataService = dataService;
        this.employeeMenu = employeeMenu;
        this.json = json;
    }

    //Создание сотрудника
    public void createEmployee() {
        int employeeID = dataService.getEmployees().size() + 1;
        System.out.println("Enter lastName");
        String lastName = scanner.nextLine();

        System.out.println("Enter firstName");
        String firstName = scanner.nextLine();

        System.out.println("Enter middleName");
        String middleName = scanner.nextLine();

        System.out.println("Enter postID");
        int postID = scanner.nextInt();
        scanner.nextLine();

        Post post = dataService.getPosts().get(postID);
        if (post == null) {
            System.out.println("Position not found");
        } else {
            Employee newEmployee = new Employee(employeeID, lastName, firstName, middleName, postID);
            dataService.getEmployees().add(newEmployee);
            System.out.println("Success, id: " + newEmployee.getId());
        }
    }

    //Изменение сотрудника
    public void changeEmployee() {
        System.out.println("Enter ID");
        int id = scanner.nextInt();
        scanner.nextLine();

        Employee employee = dataService.getEmployees().stream()
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

        if (dataService.getPosts().get(postID) != null) {
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

        Employee employee = dataService.getEmployees().stream()
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
        if (dataService.getEmployees().isEmpty()) {
            System.out.println("The list of employees is empty.");
        } else {
            dataService.getEmployees().stream()
                    .filter(employee -> !employee.getTerminated())
                    .sorted(Comparator.comparing(Employee::getLastName))
                    .forEach(employee -> {
                        try {
                            String employeeJsonString = json.convertEmployeeToJson(employee, dataService.getPosts());
                            System.out.println(employeeJsonString);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        }
    }

    //Вывести одного сотрудника по ID
    public void outputEmployee() {
        System.out.println("Enter ID");
        int id = scanner.nextInt();
        scanner.nextLine();

        Employee employee = dataService.getEmployees().stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .orElse(null);

        if (employee == null) {
            System.out.println("Employee with the given ID was not found.");
        } else {
            try {
                String employeeJsonString = json.convertEmployeeToJson(employee, dataService.getPosts());
                System.out.println(employeeJsonString);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //Вывести уволенных сотрудников
    public void outputTerminatedEmployees() {
        if (dataService.getEmployees().isEmpty()) {
            System.out.println("The list of employees is empty.");
        } else if (dataService.getEmployees().stream().noneMatch(Employee::getTerminated)) {
            System.out.println("There are no terminated employees.");
        } else {
            dataService.getEmployees().stream()
                    .filter(Employee::getTerminated)
                    .forEach(employee -> {
                        try {
                            String employeeJsonString = json.convertEmployeeToJson(employee, dataService.getPosts());
                            System.out.println(employeeJsonString);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
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
        Set<String> positions = dataService.getPosts().values().stream()
                .map(Post::getPostName)
                .collect(Collectors.toSet());
        System.out.println("Enter position name:");
        System.out.println("Examples: " + String.join(", ", positions));
        scanner.nextLine();
        String postToFind = scanner.nextLine();

        List<Employee> employeesWithPosition = dataService.getEmployees().stream()
                .filter(e -> e.getPositionId() != 0 && dataService.getPosts().containsKey(e.getPositionId()))
                .filter(e -> dataService.getPosts().get(e.getPositionId()).getPostName().equals(postToFind))
                .collect(Collectors.toList());

        if (employeesWithPosition.isEmpty()) {
            System.out.println("Position not found. Please enter a valid position name.");
        } else {
            employeesWithPosition.stream()
                    .map(e -> {
                        try {
                            return json.convertEmployeeToJson(e, this.dataService.getPosts());
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    })
                    .forEach(System.out::println);
        }
    }

    //Поиск по фамилии, имени и отчеству, или по частичному совпадению
    public void searchEmployeesByPartialMatch() {
        System.out.println("Enter the first name, last name or middle name by which you want to find:");
        scanner.nextLine(); // Очистка буфера сканера
        String search = scanner.nextLine();

        dataService.getEmployees().stream()
                .filter(e -> e.getLastName().toLowerCase().contains(search.toLowerCase()) ||
                        e.getFirstName().toLowerCase().contains(search.toLowerCase()) ||
                        e.getMiddleName().toLowerCase().contains(search.toLowerCase()))
                .map(e -> {
                    try {
                        return json.convertEmployeeToJson(e, this.dataService.getPosts());
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                })
                .forEach(System.out::println);
    }

    //Поиск по промежутку времени
    public void searchEmployeesByDate() {
        try {
            System.out.println("Enter the start date (yyyy-mm-dd):");
            scanner.nextLine();
            String startDateInput = scanner.nextLine();
            LocalDate startDate = LocalDate.parse(startDateInput);

            System.out.println("Enter the end date (yyyy-mm-dd):");
            String endDateInput = scanner.nextLine();
            LocalDate endDate = LocalDate.parse(endDateInput);

            dataService.getEmployees().stream()
                    .filter(e -> (e.getCreationDate().isEqual(startDate) || e.getCreationDate().isAfter(startDate)) &&
                            (e.getCreationDate().isEqual(endDate) || e.getCreationDate().isBefore(endDate)))
                    .map(e -> {
                        try {
                            return json.convertEmployeeToJson(e, this.dataService.getPosts());
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    })
                    .forEach(System.out::println);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please use the yyyy-mm-dd format.");
        }
    }
}