package org.example.dao.repository;


import org.example.dao.model.Employee;
import org.example.service.dto.EmployeeDto;
import org.example.service.dto.PostDto;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


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

    public Employee createEmployee(Employee employee) {
        employee.setId(employees.size() + 1);
        employees.add(employee);
        return employee;
    }


    public List<Employee> getEmployees() {
        return employees;
    }

    public Optional<Employee> findById(int id) {
        return employees.stream()
                .filter(e -> e.getId() == id)
                .findFirst();
    }


    public Optional<Employee> deleteEmployee(int id) {
        Optional<Employee> employeeToDelete = findById(id);
        employeeToDelete.ifPresent(employees::remove);
        return employeeToDelete;
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    // Вспомогательный метод для получения данных о должности
    public PostDto getPostDto(int postID) {
        return postDataService.findById(postID)
                .map(PostDto::new)
                .orElse(null);
    }

    public List<EmployeeDto> employeeToDto() {
        return employees.stream()
                .map(this::convertToEmployeeDto)
                .collect(Collectors.toList());
    }

    private EmployeeDto convertToEmployeeDto(Employee employee) {
        PostDto postDto = findPostById(employee.getPostID()).orElse(null);

        return new EmployeeDto(
                employee.getId(),
                employee.getCreationDate(),
                employee.getModificationDate(),
                employee.getLastName(),
                employee.getFirstName(),
                employee.getMiddleName(),
                postDto,
                employee.getPostID(),
                employee.getTerminated()
        );
    }

    public Optional<PostDto> findPostById(int postID) {
        return postDataService.findById(postID)
                .map(post -> new PostDto(post.getId(), post.getPostName()));
    }
}