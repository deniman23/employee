package org.example.service.api;

import org.example.EmployeeRepository;
import org.example.dao.model.Employee;
import org.example.dao.model.Post;

import org.example.PostRepository;
import org.example.error.ResourceNotFoundException;
import org.example.service.dto.EmployeeDto;
import org.example.service.responce.RequestEmployee;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmployeeAPI {
    private final PostRepository postRepository;
    private final EmployeeRepository employeeRepository;


    public EmployeeAPI(EmployeeRepository employeeRepository, PostRepository postRepository) {
        this.employeeRepository = employeeRepository;
        this.postRepository = postRepository;
    }

    // Создание сотрудника
    public EmployeeDto createEmployee(RequestEmployee requestEmployee) {
        Post post = postRepository.findById(requestEmployee.getPostID())
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with ID: " + requestEmployee.getPostID()));
        Employee employee = new Employee(requestEmployee);
        employee.setPost(post);
        employee = employeeRepository.save(employee);
        return new EmployeeDto(employee);
    }

    // Изменение сотрудника
    public EmployeeDto changeEmployee(RequestEmployee requestEmployee) {
        Employee employee = employeeRepository.findById(requestEmployee.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + requestEmployee.getId()));
        employee.setModificationDate(LocalDate.now());
        employee.setLastName(requestEmployee.getLastName());
        employee.setFirstName(requestEmployee.getFirstName());
        employee.setMiddleName(requestEmployee.getMiddleName());
        Post post = postRepository.findById(requestEmployee.getPostID())
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with ID: " + requestEmployee.getPostID()));
        employee.setPost(post);
        employee = employeeRepository.save(employee);
        return new EmployeeDto(employee);
    }

    // Уволить сотрудника
    public void terminateEmployee(int id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + id));
        employee.setTerminated(true);
        employeeRepository.save(employee); // Обновите статус сотрудника в БД
    }

    // Вывести всех сотрудников отсортированных по Фамилии
    public List<EmployeeDto> outputAllEmployeesSortedByLastName() {
        return employeeRepository.findAllByIsTerminatedFalseOrderByLastNameAsc().stream()
                .map(EmployeeDto::new)
                .collect(Collectors.toList());
    }

    // Вывести одного сотрудника по ID
    public EmployeeDto outputEmployee(int id) {
        return findById(id);
    }

    private EmployeeDto findById(int id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + id));
        return new EmployeeDto(employee);
    }

    // Вывести уволенных сотрудников
    public List<EmployeeDto> outputTerminatedEmployees() {
        return employeeRepository.findAllByIsTerminated().stream()
                .map(EmployeeDto::new)
                .collect(Collectors.toList());
    }

    // Вывод сотрудника по фильтру
    public List<EmployeeDto> searchEmployeesByPost(int postID) {
        Post post = postRepository.findById(postID)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with ID: " + postID));
        return employeeRepository.findAllByPostId(postID).stream()
                .map(EmployeeDto::new)
                .collect(Collectors.toList());
    }

    // Вывод по совпадению
    public List<EmployeeDto> searchEmployeesByPartialMatch(String search) {
        return employeeRepository.findByPartialMatch(search).stream()
                .map(EmployeeDto::new)
                .collect(Collectors.toList());
    }

    // Поиск по промежутку времени
    public List<EmployeeDto> searchEmployeesByDate(String startDateInput, String endDateInput) {
        LocalDate startDate = LocalDate.parse(startDateInput);
        LocalDate endDate = LocalDate.parse(endDateInput);
        return employeeRepository.findAllByDate(startDate, endDate).stream()
                .map(EmployeeDto::new)
                .collect(Collectors.toList());
    }
}