//package org.example.dao.repository;
//
//
//import org.example.dao.model.Employee;
//import org.example.service.dto.EmployeeDto;
//import org.example.service.dto.PostDto;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//
//@Component
//public class EmployeeDataService {
//    private final List<Employee> employees;
//    private final PostDataService postDataService;
//
//    public EmployeeDataService(List<Employee> employees, PostDataService postDataService) {
//        this.employees = employees;
//        this.postDataService = postDataService;
//        createEmployee(new Employee(1, "Ivanov", "Ivan", "Ivanovich", 1));
//        createEmployee(new Employee(2, "Alexeev", "Alex", "Alexevich", 2));
//        createEmployee(new Employee(3, "Vitaliev", "Vitaly", "Vitalievich", 3));
//    }
//
//    public Employee createEmployee(Employee employee) {
//        employee.setId(employees.size() + 1);
//        employees.add(employee);
//        return employee;
//    }
//
//    public List<Employee> getEmployees() {
//        return employees;
//    }
//
//    public Optional<Employee> findById(int id) {
//        return employees.stream()
//                .filter(e -> e.getId() == id)
//                .findFirst();
//    }
//
//    public Optional<Employee> deleteEmployee(int id) {
//        Optional<Employee> employeeToDelete = findById(id);
//        employeeToDelete.ifPresent(employees::remove);
//        return employeeToDelete;
//    }
//
//    public PostDto getPostDto(int postID) {
//        return postDataService.findById(postID)
//                .map(PostDto::new)
//                .orElse(null);
//    }
//
//    public List<EmployeeDto> employeeToDto() {
//        return employees.stream()
//                .map(employee -> {
//                    PostDto postDto = postDataService.findById(employee.getPostID())
//                            .map(PostDto::new)
//                            .orElseThrow(() -> new IllegalStateException("Post not found for ID: " + employee.getPostID()));
//                    return convertToEmployeeDto(employee, postDto);
//                })
//                .collect(Collectors.toList());
//    }
//
//    private EmployeeDto convertToEmployeeDto(Employee employee, PostDto postDto) {
//        return new EmployeeDto(
//                employee.getId(),
//                employee.getCreationDate(),
//                employee.getModificationDate(),
//                employee.getLastName(),
//                employee.getFirstName(),
//                employee.getMiddleName(),
//                postDto,
//                employee.getPostID(),
//                employee.getTerminated()
//        );
//    }
//}