package org.example.services.service;

import org.example.dao.model.Employee;
import org.example.dao.model.Post;
import org.example.dao.service.EmployeeServiceDao;
import org.example.dao.service.PostServiceDao;
import org.example.error.ResourceNotFoundException;
import org.example.dto.EmployeeDto;
import org.example.filter.EmployeeFilter;
import org.example.request.RequestEmployee;
import org.example.services.mapper.EmployeeMapper;
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

    private Post getPostById(RequestEmployee requestEmployee) {
        return postServiceDao.findById(requestEmployee.getPostID())
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with ID: " + requestEmployee.getPostID()));
    }

    private Employee getEmployeeById(RequestEmployee requestEmployee) {
        return employeeServiceDao.findEmployeeById(requestEmployee.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + requestEmployee.getId()));
    }

    private void setPostById(Employee employee, int postId) {
        employee.post = postServiceDao.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with ID: " + postId));
    }

    public List<EmployeeDto> findAll(EmployeeFilter employeeFilter){
        return employeeServiceDao.findAll(employeeFilter).stream()
                .map(EmployeeMapper::entityToDto)
                .collect(Collectors.toList());
    }

    // Создание должности
    public EmployeeDto createEmployee(RequestEmployee requestEmployee) {
        Post post = getPostById(requestEmployee);
        Employee employee = new Employee(requestEmployee);
        setPostById(employee, post.getId());
        employee = employeeServiceDao.saveEmployee(employee);
        return new EmployeeDto(employee);
    }

    // Изменение должности
    public EmployeeDto changeEmployee(RequestEmployee requestEmployee) {
        Employee employee = getEmployeeById(requestEmployee);
        employee.setModificationDate(LocalDate.now());
        employee.setLastName(requestEmployee.getLastName());
        employee.setFirstName(requestEmployee.getFirstName());
        employee.setMiddleName(requestEmployee.getMiddleName());
        Post post = getPostById(requestEmployee);
        setPostById(employee, post.getId());
        employee = employeeServiceDao.saveEmployee(employee);
        return new EmployeeDto(employee);
    }

    // Увольнение сотрудника
    public void terminateEmployee(int id) {
        employeeServiceDao.terminateEmployee(id);
    }

    // Вывести всех сотрудников отсортированных по Фамилии
    public List<EmployeeDto> outputAllEmployeesSortedByLastName() {
        return employeeServiceDao.findAllEmployeesSortedByLastName().stream()
                .filter(employee -> !employee.getTerminated())
                .map(EmployeeDto::new)
                .collect(Collectors.toList());
    }

    // Вывод одного сотрудника
    public EmployeeDto outputEmployee(int id) {
        Employee employee = employeeServiceDao.findEmployeeByIdOrThrow(id);
        return new EmployeeDto(employee);
    }

    // Вывод уволенных сотрудников
    public List<EmployeeDto> outputTerminatedEmployees() {
        return employeeServiceDao.findAllTerminatedEmployees().stream()
                .map(EmployeeDto::new)
                .collect(Collectors.toList());
    }

    // Поиск сотрудника по должности
    public List<EmployeeDto> searchEmployeesByPost(int postID) {
        return employeeServiceDao.findAllEmployeesByPost(postID).stream()
                .map(EmployeeDto::new)
                .collect(Collectors.toList());
    }

    // Поиск сотрудника по частичному совпадению
    public List<EmployeeDto> searchEmployeesByPartialMatch(String search) {
        return employeeServiceDao.findEmployeesByPartialMatch(search).stream()
                .map(EmployeeDto::new)
                .collect(Collectors.toList());
    }

    // Поиск по дате создания
    public List<EmployeeDto> searchEmployeesByDate(String startDateInput, String endDateInput) {
        LocalDate startDate = LocalDate.parse(startDateInput);
        LocalDate endDate = LocalDate.parse(endDateInput);
        return employeeServiceDao.findEmployeesByDate(startDate, endDate).stream()
                .map(EmployeeDto::new)
                .collect(Collectors.toList());
    }
}
