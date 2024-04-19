package org.example.service.console;

import org.example.service.api.EmployeeAPI;
import org.example.service.dto.EmployeeDto;
import org.example.service.mapper.JsonMapper;
import org.example.service.responce.RequestEmployee;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.example.service.mapper.JsonMapper.scanner;

public class EmployeeConsoleService {
    private final EmployeeAPI employeeAPI;
    private final JsonMapper jsonMapper;
    private final EmployeeMenu employeeMenu;

    public EmployeeConsoleService(EmployeeAPI employeeAPI, JsonMapper jsonMapper, EmployeeMenu employeeMenu) {
        this.employeeAPI = employeeAPI;
        this.jsonMapper = jsonMapper;
        this.employeeMenu = employeeMenu;
    }

    public void createEmployeeConsole() {
        System.out.println("Enter lastName");
        String lastName = scanner.nextLine();

        System.out.println("Enter firstName");
        String firstName = scanner.nextLine();

        System.out.println("Enter middleName");
        String middleName = scanner.nextLine();

        System.out.println("Enter postID");
        int postID = scanner.nextInt();
        scanner.nextLine();
        RequestEmployee requestEmployee = new RequestEmployee(0, lastName, firstName, middleName, postID);
        Optional<EmployeeDto> createdEmployee = Optional.ofNullable(employeeAPI.createEmployee(requestEmployee));
        if (createdEmployee.isPresent()) {
            System.out.println("Success, id: " + createdEmployee.get().getId());
        } else {
            System.out.println("Invalid name or ID already exists");
        }
    }

    public void changeEmployeeConsole() {
        System.out.println("Enter ID:");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter lastName:");
        String lastName = scanner.nextLine();

        System.out.println("Enter firstName:");
        String firstName = scanner.nextLine();

        System.out.println("Enter middleName:");
        String middleName = scanner.nextLine();

        System.out.println("Enter postID:");
        int postID = scanner.nextInt() + 1;
        scanner.nextLine();

        RequestEmployee requestEmployee = new RequestEmployee(id, lastName, firstName, middleName, postID);
        Optional<EmployeeDto> employee = Optional.ofNullable(employeeAPI.changeEmployee(requestEmployee));
        if (employee.isPresent()) {
            System.out.println("Employee updated successfully");
        } else {
            System.out.println("Employee not found");
        }
    }

    public void terminateEmployeeConsole() {
        System.out.println("Enter ID:");
        int id = scanner.nextInt();
        scanner.nextLine();
        employeeAPI.terminateEmployee(id);
    }

    public void outputAllEmployeesSortedByLastNameConsole() {
        List<EmployeeDto> employeeDtos = employeeAPI.outputAllEmployeesSortedByLastName();
        if (employeeDtos.isEmpty()) {
            System.out.println("The list of employees is empty.");
        } else {
            employeeDtos.forEach(employeeDto -> {
                try {
                    System.out.println(jsonMapper.convertEmployeeToJson(employeeDto));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
        }
    }

    public void outputEmployeeConsole() {
        System.out.println("Enter ID:");
        int id = scanner.nextInt();
        scanner.nextLine();

        try {
            EmployeeDto employeeDto = employeeAPI.outputEmployee(id);
            String employeeJson = jsonMapper.convertEmployeeToJson(employeeDto);
            System.out.println(employeeJson);
        } catch (IOException e) {
            System.err.println("Error processing JSON: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    public void outputTerminatedEmployeesConsole() {
        List<EmployeeDto> terminatedEmployeeDtos = employeeAPI.outputTerminatedEmployees();
        printEmployeeDtos(terminatedEmployeeDtos);
    }


    private void printEmployeeDtos(List<EmployeeDto> employees) {
        if (employees.isEmpty()) {
            System.out.println("The list of employees is empty.");
        } else {
            employees.forEach(this::outputEmployeeDto);
        }
    }

    private void outputEmployeeDto(EmployeeDto employeeDto) {
        try {
            String employeeJsonString = jsonMapper.convertEmployeeToJson(employeeDto);
            System.out.println(employeeJsonString);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void outputEmployeesByFilterConsole() {
        System.out.println("Choose filter: ");
        System.out.println("1. Search by Last Name, First Name, Middle Name (by partial coincidence)");
        System.out.println("2. Search by creation date");
        System.out.println("3. Search by position");
        System.out.println("4. Exit");

        int operator = scanner.nextInt();
        scanner.nextLine();
        switch (operator) {
            case 1:
                searchEmployeesByPartialMatchConsole();
                break;
            case 2:
                searchEmployeesByDateConsole();
                break;
            case 3:
                searchEmployeesByPostConsole();
                break;
            case 4:
                employeeMenu.menuEmployee();
                break;
            default:
                System.out.println("Invalid action");
        }
    }

    public void searchEmployeesByPostConsole() {
        System.out.println("Enter position ID:");
        int postID = scanner.nextInt();
        scanner.nextLine();
        List<EmployeeDto> employeeDtos = employeeAPI.searchEmployeesByPost(postID);
        printEmployeeDtos(employeeDtos);
    }

    public void searchEmployeesByDateConsole() {
        System.out.println("Enter the start date (yyyy-mm-dd):");
        String startDateInput = scanner.nextLine();
        System.out.println("Enter the end date (yyyy-mm-dd):");
        String endDateInput = scanner.nextLine();
        List<EmployeeDto> employeeDtos = employeeAPI.searchEmployeesByDate(startDateInput, endDateInput);
        printEmployeeDtos(employeeDtos);
    }

    public void searchEmployeesByPartialMatchConsole() {
        System.out.println("Enter the first name, last name or middle name by which you want to find:");
        String search = scanner.nextLine();
        List<EmployeeDto> employeeDtos = employeeAPI.searchEmployeesByPartialMatch(search);
        printEmployeeDtos(employeeDtos);
    }

}
