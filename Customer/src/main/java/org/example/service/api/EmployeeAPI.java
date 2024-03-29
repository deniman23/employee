package org.example.service.api;

import org.example.dao.model.Employee;
import org.example.dao.repository.EmployeeDataService;
import org.example.dao.repository.PostDataService;
import org.example.menu.EmployeeMenu;
import org.example.service.mapper.JsonMapper;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

import static org.example.service.mapper.JsonMapper.scanner;


public class EmployeeAPI {

    private final PostDataService postDataService;
    private final EmployeeDataService employeeDataService;
    private final JsonMapper jsonMapper;
    private EmployeeMenu employeeMenu;

    public EmployeeAPI(PostDataService postDataService, EmployeeDataService employeeDataService, JsonMapper jsonMapper) {
        this.postDataService = postDataService;
        this.employeeDataService = employeeDataService;
        this.jsonMapper = jsonMapper;
    }
    public void setEmployeeMenu(EmployeeMenu employeeMenu) {
        this.employeeMenu = employeeMenu;
    }

    //Создание сотрудника
    public void createEmployee() {
        System.out.println("Enter lastName");
        String lastName = scanner.nextLine();

        System.out.println("Enter firstName");
        String firstName = scanner.nextLine();

        System.out.println("Enter middleName");
        String middleName = scanner.nextLine();

        System.out.println("Enter postID");
        int postID = scanner.nextInt();
        scanner.nextLine();

        Optional<Employee> createdEmployee = employeeDataService.createEmployee(employeeDataService.getEmployees().size() + 1, lastName, firstName, middleName, postID);
        if (createdEmployee.isPresent()) {
            System.out.println("Success, id: " + createdEmployee.get().getId());
        } else {
            System.out.println("Invalid name or ID already exists");
        }
    }

    //Изменение сотрудника
    public void changeEmployee() {
        System.out.println("Enter ID");
        int id = scanner.nextInt();
        scanner.nextLine();

        Employee employee = employeeDataService.getEmployees().stream()
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

        if (employeeDataService.getEmployees().get(id).getPosition() != null) {
            employee.setPositionId(postID);
        } else {
            System.out.println("Post not found");
        }

        Optional<Employee> updatedEmployee = employeeDataService.updateEmployee(id, lastName, firstName, middleName, postID);
        if (updatedEmployee.isPresent()) {
            System.out.println("Employee updated successfully");
        } else {
            System.out.println("Employee not found");
        }
    }


    // Увольнение сотрудника
    public void terminateEmployee() {
        System.out.println("Enter ID");
        int id = scanner.nextInt();
        scanner.nextLine();


        if (employeeDataService.getEmployees().get(id).getTerminated()) {
            System.out.println("Employee already terminated");
            return;
        }

        Optional<Employee> employeeToDelete = employeeDataService.deleteEmployee(id);
        if (employeeToDelete.isPresent()) {
            System.out.println("Employee terminated successfully");
        } else {
            System.out.println("Employee with the given ID was not found.");
        }
    }

    //Вывести всех сотрудников отсортированных по Фамилии
    public void outputAllEmployeesSortedByLastName() {
        if (employeeDataService.getEmployees().isEmpty()) {
            System.out.println("The list of employees is empty.");
        } else {
            employeeDataService.getEmployees().stream()
                    .filter(employee -> !employee.getTerminated())
                    .sorted(Comparator.comparing(Employee::getLastName))
                    .forEach(employee -> {
                        try {
                            String employeeJsonString = jsonMapper.convertEmployeeToJson(employee, postDataService.getPosts());
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

        Employee employee = employeeDataService.getEmployees().stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .orElse(null);

        if (employee == null) {
            System.out.println("Employee with the given ID was not found.");
        } else {
            try {
                String employeeJsonString = jsonMapper.convertEmployeeToJson(employee, postDataService.getPosts());
                System.out.println(employeeJsonString);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //Вывести уволенных сотрудников
    public void outputTerminatedEmployees() {
        if (employeeDataService.getEmployees().isEmpty()) {
            System.out.println("The list of employees is empty.");
        } else if (employeeDataService.getEmployees().stream().noneMatch(Employee::getTerminated)) {
            System.out.println("There are no terminated employees.");
        } else {
            employeeDataService.getEmployees().stream()
                    .filter(Employee::getTerminated)
                    .forEach(employee -> {
                        try {
                            String employeeJsonString = jsonMapper.convertEmployeeToJson(employee, postDataService.getPosts());
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
        scanner.nextLine(); // Очистка буфера сканера
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

    public void searchEmployeesByPost() {
        System.out.println("Enter position ID:");
        int positionId = scanner.nextInt();
        scanner.nextLine(); // Очистка буфера сканера

        List<Employee> employeesWithPosition = employeeDataService.getEmployees().stream()
                .filter(e -> e.getPositionId() == positionId)
                .collect(Collectors.toList());

        if (employeesWithPosition.isEmpty()) {
            System.out.println("No employees found for the given position ID.");
        } else {
            employeesWithPosition.forEach(employee -> {
                try {
                    String employeeJsonString = jsonMapper.convertEmployeeToJson(employee, postDataService.getPosts());
                    System.out.println(employeeJsonString);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
        }
    }

    public void searchEmployeesByPartialMatch() {
        System.out.println("Enter the first name, last name or middle name by which you want to find:");
        String search = scanner.nextLine();

        employeeDataService.getEmployees().stream()
                .filter(e -> e.getLastName().toLowerCase().contains(search.toLowerCase()) ||
                        e.getFirstName().toLowerCase().contains(search.toLowerCase()) ||
                        e.getMiddleName().toLowerCase().contains(search.toLowerCase()))
                .forEach(employee -> {
                    try {
                        String employeeJsonString = jsonMapper.convertEmployeeToJson(employee, postDataService.getPosts());
                        System.out.println(employeeJsonString);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });
    }

    public void searchEmployeesByDate() {
        try {
            System.out.println("Enter the start date (yyyy-mm-dd):");
            String startDateInput = scanner.nextLine();
            LocalDate startDate = LocalDate.parse(startDateInput);

            System.out.println("Enter the end date (yyyy-mm-dd):");
            String endDateInput = scanner.nextLine();
            LocalDate endDate = LocalDate.parse(endDateInput);

            employeeDataService.getEmployees().stream()
                    .filter(e -> (e.getCreationDate().isEqual(startDate) || e.getCreationDate().isAfter(startDate)) &&
                            (e.getCreationDate().isEqual(endDate) || e.getCreationDate().isBefore(endDate)))
                    .forEach(employee -> {
                        try {
                            String employeeJsonString = jsonMapper.convertEmployeeToJson(employee, postDataService.getPosts());
                            System.out.println(employeeJsonString);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    });
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please use the yyyy-mm-dd format.");
        }
    }
}