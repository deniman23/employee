package org.example.dao.repository;


import org.example.dao.model.Employee;
import org.example.dao.model.Post;
import org.example.service.dto.EmployeeDto;
import org.example.service.dto.PostDto;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class EmployeeDataService {
    private final List<Employee> employees;
    private final PostDataService postDataService;

    public EmployeeDataService(List<Employee> employees, PostDataService postDataService) {
        this.employees = employees;
        this.postDataService = postDataService;
        addEmployee(new Employee(1, "Ivanov", "Ivan", "Ivanovich", 1));
        addEmployee(new Employee(2, "Alexeev", "Alex", "Alexevich", 2));
        addEmployee(new Employee(3, "Vitaliev", "Vitaly", "Vitalievich", 3));
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
        if (employee.isEmpty()) {
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
        List<EmployeeDto> employeeDtos = new ArrayList<>();
        for (Employee employee : this.getEmployees()) {
            EmployeeDto dto = new EmployeeDto(employee);
            PostDto postDto = findPostById(employee.getPostID());
            dto.setPost(postDto);
            employeeDtos.add(dto);
        }
        return employeeDtos;
    }

    private PostDto findPostById(int postID) {
        return postDataService.findById(postID)
                .map(post -> new PostDto(post.getId(), post.getPostName()))
                .orElse(null);
    }


}