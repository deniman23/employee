package org.example.services.service;

import org.example.dao.model.Employee;
import org.example.dao.model.Post;
import org.example.error.ResourceNotFoundException;
import org.example.services.dto.EmployeeDto;
import org.example.services.response.ResponseEmployee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeServiceDao employeeServiceDao;
    private final PostServiceDao postServiceDao;

    @Autowired
    public EmployeeService(EmployeeServiceDao employeeServiceDao, PostServiceDao postServiceDao) {
        this.employeeServiceDao = employeeServiceDao;
        this.postServiceDao = postServiceDao;
    }


    public EmployeeDto createEmployee(ResponseEmployee responseEmployee) {
        Post post = postServiceDao.findById(responseEmployee.getPostID())
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with ID: " + responseEmployee.getPostID()));
        Employee employee = new Employee(responseEmployee);
        employee.setPost(post);
        employee = employeeServiceDao.saveEmployee(employee);
        return new EmployeeDto(employee);
    }

    public EmployeeDto changeEmployee(ResponseEmployee responseEmployee) {
        Employee employee = employeeServiceDao.findEmployeeById(responseEmployee.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + responseEmployee.getId()));
        employee.setModificationDate(LocalDate.now());
        employee.setLastName(responseEmployee.getLastName());
        employee.setFirstName(responseEmployee.getFirstName());
        employee.setMiddleName(responseEmployee.getMiddleName());
        Post post = postServiceDao.findById(responseEmployee.getPostID())
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with ID: " + responseEmployee.getPostID()));
        employee.setPost(post);
        employee = employeeServiceDao.saveEmployee(employee);
        return new EmployeeDto(employee);
    }


    public void terminateEmployee(int id) {
        employeeServiceDao.terminateEmployee(id);
    }

    public List<EmployeeDto> outputAllEmployeesSortedByLastName() {
        return employeeServiceDao.findAllEmployeesSortedByLastName().stream()
                .map(EmployeeDto::new)
                .collect(Collectors.toList());
    }

    public EmployeeDto outputEmployee(int id) {
        Employee employee = employeeServiceDao.findEmployeeByIdOrThrow(id);
        return new EmployeeDto(employee);
    }

    public List<EmployeeDto> outputTerminatedEmployees() {
        return employeeServiceDao.findAllTerminatedEmployees().stream()
                .map(EmployeeDto::new)
                .collect(Collectors.toList());
    }

    public List<EmployeeDto> searchEmployeesByPost(int postID) {
        return employeeServiceDao.findAllEmployeesByPost(postID).stream()
                .map(EmployeeDto::new)
                .collect(Collectors.toList());
    }

    public List<EmployeeDto> searchEmployeesByPartialMatch(String search) {
        return employeeServiceDao.findEmployeesByPartialMatch(search).stream()
                .map(EmployeeDto::new)
                .collect(Collectors.toList());
    }

    public List<EmployeeDto> searchEmployeesByDate(String startDateInput, String endDateInput) {
        LocalDate startDate = LocalDate.parse(startDateInput);
        LocalDate endDate = LocalDate.parse(endDateInput);
        return employeeServiceDao.findEmployeesByDate(startDate, endDate).stream()
                .map(EmployeeDto::new)
                .collect(Collectors.toList());
    }
}
