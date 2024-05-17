package org.example.console;

import org.example.filter.EmployeeFilter;
import org.example.services.EmployeeService;
import org.example.dto.EmployeeDto;
import org.example.dto.mapper.JsonMapper;
import org.example.request.RequestEmployee;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.example.dto.mapper.JsonMapper.scanner;

@Service
public class EmployeeConsoleService {
    private final EmployeeService employeeService;
    private final JsonMapper jsonMapper;
    private final EmployeeFilter employeeFilter;

    public EmployeeConsoleService(EmployeeService employeeService, JsonMapper jsonMapper, EmployeeFilter employeeFilter) {
        this.employeeService = employeeService;
        this.jsonMapper = jsonMapper;
        this.employeeFilter = employeeFilter;
    }

    public void create() {
        System.out.println("Enter lastName");
        String lastName = scanner.nextLine();

        System.out.println("Enter firstName");
        String firstName = scanner.nextLine();

        System.out.println("Enter middleName");
        String middleName = scanner.nextLine();

        System.out.println("Enter postID");
        int postID = scanner.nextInt();
        scanner.nextLine();
        RequestEmployee requestEmployee = new RequestEmployee();
        requestEmployee.setId(0);
        requestEmployee.setLastName(lastName);
        requestEmployee.setFirstName(firstName);
        requestEmployee.setMiddleName(middleName);
        requestEmployee.setPostId(postID);

        Optional<EmployeeDto> createdEmployee = Optional.ofNullable(employeeService.createEmployee(requestEmployee));
        if (createdEmployee.isPresent()) {
            System.out.println("Success, id: " + createdEmployee.get().getId());
        } else {
            System.out.println("Invalid name or ID already exists");
        }
    }

    public void change() {
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

        RequestEmployee requestEmployee = new RequestEmployee();
        requestEmployee.setId(id);
        requestEmployee.setLastName(lastName);
        requestEmployee.setFirstName(firstName);
        requestEmployee.setMiddleName(middleName);
        requestEmployee.setPostId(postID);
        Optional<EmployeeDto> employee = Optional.ofNullable(employeeService.changeEmployee(requestEmployee));
        if (employee.isPresent()) {
            System.out.println("Employee updated successfully");
        } else {
            System.out.println("Employee not found");
        }
    }

    public void terminate() {
        System.out.println("Enter ID:");
        int id = scanner.nextInt();
        scanner.nextLine();
        employeeService.terminateEmployee(id);
    }

    public void outputAll() {
        List<EmployeeDto> employeeDtos = employeeService.findAll(employeeFilter);
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

    public void output() {
        System.out.println("Enter ID:");
        int id = scanner.nextInt();
        scanner.nextLine();

        try {
            EmployeeDto employeeDto = employeeService.outputEmployee(id);
            String employeeJson = jsonMapper.convertEmployeeToJson(employeeDto);
            System.out.println(employeeJson);
        } catch (IOException e) {
            System.err.println("Error processing JSON: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
        }
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

    public void searchEmployeesConsole() {

        System.out.println("Enter true to output terminated employees or press Enter to skip:");
        String terminatedInput = scanner.nextLine();
        if (!terminatedInput.isEmpty()) {
            employeeFilter.setTerminated(Boolean.parseBoolean(terminatedInput));
        }

        System.out.println("Enter position Name or press Enter to skip:");
        String postName = scanner.nextLine();
        if (!postName.isEmpty()) {
            employeeFilter.setSearch(postName);
        }

        System.out.println("Enter the start date (yyyy-mm-dd) or press Enter to skip:");
        String startDateInput = scanner.nextLine();
        if (!startDateInput.isEmpty()) {
            LocalDate startDate = LocalDate.parse(startDateInput);
            employeeFilter.setStartDate(startDate);
        }

        System.out.println("Enter the end date (yyyy-mm-dd) or press Enter to skip:");
        String endDateInput = scanner.nextLine();
        if (!endDateInput.isEmpty()) {
            LocalDate endDate = LocalDate.parse(endDateInput);
            employeeFilter.setEndDate(endDate);
        }

        System.out.println("Enter the first name, last name or middle name by which you want to find or press Enter to skip:");
        String search = scanner.nextLine();
        if (!search.isEmpty()) {
            employeeFilter.setSearch(search);
        }

        List<EmployeeDto> employeesDto = employeeService.findAll(employeeFilter);
        printEmployeeDtos(employeesDto);
    }

//    public void outputEmployeesByFilterConsole() {
//        System.out.println("Choose filter: ");
//        System.out.println("1. Search by Last Name, First Name, Middle Name (by partial coincidence)");
//        System.out.println("2. Search by creation date");
//        System.out.println("3. Search by position");
//        System.out.println("4. Exit");
//
//        int operator = scanner.nextInt();
//        scanner.nextLine();
//        switch (operator) {
//            case 1:
//                searchEmployeesByPartialMatchConsole();
//                break;
//            case 2:
//                searchEmployeesByDateConsole();
//                break;
//            case 3:
//                searchEmployeesByPostConsole();
//                break;
//            case 4:
//                employeeMenu.menuEmployee();
//                break;
//            default:
//                System.out.println("Invalid action");
//        }
//    }


//    public void searchEmployeesByPostConsole() {
//        System.out.println("Enter position ID:");
//        int postID = scanner.nextInt();
//        scanner.nextLine();
//        List<EmployeeDto> employeeDtos = employeeService.searchEmployeesByPost(postID);
//        printEmployeeDtos(employeeDtos);
//    }
//
//    public void searchEmployeesByDateConsole() {
//        System.out.println("Enter the start date (yyyy-mm-dd):");
//        String startDateInput = scanner.nextLine();
//        System.out.println("Enter the end date (yyyy-mm-dd):");
//        String endDateInput = scanner.nextLine();
//        List<EmployeeDto> employeeDtos = employeeService.searchEmployeesByDate(startDateInput, endDateInput);
//        printEmployeeDtos(employeeDtos);
//    }
//
//    public void searchEmployeesByPartialMatchConsole() {
//        System.out.println("Enter the first name, last name or middle name by which you want to find:");
//        String search = scanner.nextLine();
//        List<EmployeeDto> employeeDtos = employeeService.searchEmployeesByPartialMatch(search);
//        printEmployeeDtos(employeeDtos);
//    }
}
