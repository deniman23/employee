package org.example.dao.repository;


import org.example.dao.model.Employee;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class EmployeeDataService {
    private final List<Employee> employees;

    public EmployeeDataService(List<Employee> employees) {
        this.employees = employees;
    }


    public Optional<Employee> createEmployee(int id, String lastName, String firstName, String middleName, int positionId) {
        if (employees.stream().anyMatch(e -> e.getId() == id)) {
            return Optional.empty();
        }
        Employee newEmployee = new Employee(id, lastName, firstName, middleName, positionId);
        addEmployee(newEmployee);
        return Optional.of(newEmployee);
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public Optional<Employee> updateEmployee(int id, String lastName, String firstName, String middleName, int positionId) {
        Employee employee = employees.stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .orElse(null);
        if (employee == null) {
            return Optional.empty();
        }
        employee.setLastName(lastName);
        employee.setFirstName(firstName);
        employee.setMiddleName(middleName);
        employee.setPostID(positionId);
        employee.setModificationDate(LocalDate.now());
        return Optional.of(employee);
    }

    public Optional<Employee> deleteEmployee(int id) {
        Predicate<Employee> byId = e -> e.getId() == id;
        Optional<Employee> employeeToDelete = employees.stream()
                .filter(byId)
                .findFirst();
        employeeToDelete.ifPresent(employees::remove);
        return employeeToDelete;
    }

    public boolean addEmployee(Employee employee) {
        employees.add(employee);
        return true;
    }
}