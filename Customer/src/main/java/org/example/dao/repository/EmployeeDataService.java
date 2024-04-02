package org.example.dao.repository;


import org.example.dao.model.Employee;
import org.example.service.dto.EmployeeDto;
import org.example.service.dto.PostDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EmployeeDataService {
    private final List<Employee> employees;

    public EmployeeDataService(List<Employee> employees) {
        this.employees = employees;
    }

    public Optional<Employee> createEmployee(int id, String lastName, String firstName, String middleName, int positionId) {
        if (findById(id).isPresent()) {
            return Optional.empty();
        }
        Employee newEmployee = new Employee(id, lastName, firstName, middleName, positionId);
        addEmployee(newEmployee);
        return Optional.of(newEmployee);
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public Optional<Employee> findById(int id){
        return employees.stream()
                .filter(e -> e.getId() == id)
                .findFirst();
    }

    public Optional<Employee> updateEmployee(int id, String lastName, String firstName, String middleName, int positionId) {
        Optional<Employee> employee = findById(id);
        if (!employee.isPresent()) {
            return Optional.empty();
        }
        employee.get().setLastName(lastName);
        employee.get().setFirstName(firstName);
        employee.get().setMiddleName(middleName);
        employee.get().setPostID(positionId);
        employee.get().setModificationDate(LocalDate.now());
        return employee;
    }

    public Optional<Employee> deleteEmployee(int id) {
        Optional<Employee> employeeToDelete = findById(id);
        employeeToDelete.ifPresent(employees::remove);
        return employeeToDelete;
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public List<Employee> findAll() {
        return new ArrayList<>(employees);
    }

    public List<EmployeeDto> employeeToDto() {
        return this.employees.stream()
                .map(EmployeeDto::new)
                .collect(Collectors.toList());
    }

}