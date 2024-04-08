package org.example.service.api;

import org.example.dao.model.Employee;
import org.example.dao.model.Post;
import org.example.dao.repository.EmployeeDataService;
import org.example.dao.repository.PostDataService;
import org.example.error.ResourceNotFoundException;
import org.example.service.dto.EmployeeDto;
import org.example.service.dto.PostDto;
import org.example.service.responce.RequestEmployee;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmployeeAPI {

    private final PostDataService postDataService;
    private final EmployeeDataService employeeDataService;

    public EmployeeAPI(PostDataService postDataService, EmployeeDataService employeeDataService) {
        this.postDataService = postDataService;
        this.employeeDataService = employeeDataService;
    }

    // Создание сотрудника
    public EmployeeDto createEmployee(RequestEmployee requestEmployee) {
        if (requestEmployee.getPostID() > 0) {
            employeeDataService.findById(requestEmployee.getPostID()).orElseThrow(
                    ()->new ResourceNotFoundException("Post didn't found ID: "+requestEmployee.getPostID()));
        }
        Employee employee = employeeDataService.createEmployee(new Employee(requestEmployee));
        return new EmployeeDto(employee);
    }

    // Изменение сотрудника
    public EmployeeDto changeEmployee(RequestEmployee requestEmployee) {
        Employee employee = employeeDataService.findById(requestEmployee.getId()).orElseThrow(() -> new ResourceNotFoundException("Employee didn't found ID: " + requestEmployee.getId()));
        employee.setModificationDate(LocalDate.now());
        employee.setLastName(requestEmployee.getLastName());
        employee.setFirstName(requestEmployee.getFirstName());
        employee.setMiddleName(requestEmployee.getMiddleName());
        employee.setPostID(requestEmployee.getPostID() - 1);
        return new EmployeeDto(employee);
    }

    // Уволить сотрудника
    public void terminateEmployee(int id) {
        employeeDataService.findById(id)
                .ifPresent(employee -> employee.setTerminated(true));
    }

    // Вывести всех сотрудников отсортированных по Фамилии
    public List<EmployeeDto> outputAllEmployeesSortedByLastName() {
        List<EmployeeDto> employeeDtos = employeeDataService.employeeToDto();

        return employeeDtos.stream()
                .filter(employeeDto -> !employeeDto.isTerminated()).sorted(Comparator.comparing(EmployeeDto::getLastName)).collect(Collectors.toList());
    }

    // Вывести одного сотрудника по ID
    public EmployeeDto outputEmployee(int id) {
        return findById(id);
    }

    private EmployeeDto findById(int id) {
        Optional<Employee> employee = employeeDataService.findById(id);
        if (employee.isEmpty()){
            throw new ResourceNotFoundException("Employee didn't found ID: "+id);
        }
        EmployeeDto employeeDto = new EmployeeDto(employee.get());
        Optional<Post> post = postDataService.findById(employee.get().getPostID());
        post.ifPresent(p -> employeeDto.setPost(new PostDto(p)));
        return employeeDto;
    }

    // Вывести уволенных сотрудников
    public List<EmployeeDto> outputTerminatedEmployees() {
        return employeeDataService.employeeToDto().stream()
                .filter(EmployeeDto::isTerminated)
                .collect(Collectors.toList());
    }

    // Вывод сотрудника по фильтру
    public List<EmployeeDto> searchEmployeesByPost(int postID) {
        return employeeDataService.getEmployees().stream()
                .filter(e -> e.getPostID() == postID)
                .map(EmployeeDto::new)
                .collect(Collectors.toList());
    }

    // Вывод по совпадению
    public List<EmployeeDto> searchEmployeesByPartialMatch(String search) {
        return employeeDataService.getEmployees().stream()
                .filter(e -> e.getLastName().toLowerCase().contains(search.toLowerCase()) ||
                        e.getFirstName().toLowerCase().contains(search.toLowerCase()) ||
                        e.getMiddleName().toLowerCase().contains(search.toLowerCase()))
                .map(EmployeeDto::new)
                .collect(Collectors.toList());
    }

    // Поиск по промежутку времени
    public List<EmployeeDto> searchEmployeesByDate(String startDateInput, String endDateInput) {
        LocalDate startDate = LocalDate.parse(startDateInput);
        LocalDate endDate = LocalDate.parse(endDateInput);
        return employeeDataService.getEmployees().stream()
                .filter(e -> (e.getCreationDate().isEqual(startDate) || e.getCreationDate().isAfter(startDate)) &&
                        (e.getCreationDate().isEqual(endDate) || e.getCreationDate().isBefore(endDate)))
                .map(EmployeeDto::new)
                .collect(Collectors.toList());
    }
}