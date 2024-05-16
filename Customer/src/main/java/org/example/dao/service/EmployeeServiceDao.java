package org.example.dao.service;

import org.example.dao.repository.EmployeeRepository;
import org.example.dao.model.Employee;

import org.example.dao.specification.EmployeeSpecification;
import org.example.error.ResourceNotFoundException;
import org.example.filter.EmployeeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class EmployeeServiceDao {
    private final EmployeeRepository employeeRepository;
    private final EmployeeSpecification spec;

    @Autowired
    public EmployeeServiceDao(EmployeeRepository employeeRepository, EmployeeSpecification spec) {
        this.employeeRepository = employeeRepository;
        this.spec = spec;
    }

    // Сохранение сотрудника
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    // Удаление сотрудника по ID
    public void terminateEmployee(int id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + id));
        employee.setTerminated(true);
        employee.setModificationDate(LocalDate.now());
        employeeRepository.save(employee);
    }

    public List<Employee> findAll(EmployeeFilter employeeFilter){
        return employeeRepository.findAll(spec.searchFilter(employeeFilter));
    }

    // Поиск сотрудника по ID
    public Optional<Employee> findEmployeeById(int id) {
        return employeeRepository.findById(id);
    }

    // Вывод всех сотрудников, отсортированных по фамилии
    public List<Employee> findAllEmployeesSortedByLastName() {
        return employeeRepository.findAllByOrderByLastNameAsc();
    }

    // Вывод сотрудника по ID
    public Employee findEmployeeByIdOrThrow(int id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + id));
    }

    // Вывод уволенных сотрудников
    public List<Employee> findAllTerminatedEmployees() {
        return employeeRepository.findAllByTerminatedTrue();
    }

    // Поиск сотрудников по должности
    public List<Employee> findAllEmployeesByPost(int postID) {
        return employeeRepository.findAllByPostId(postID);
    }

    // Поиск сотрудников по частичному совпадению
    public List<Employee> findEmployeesByPartialMatch(String search) {
        return employeeRepository.findByLastNameContainingIgnoreCaseOrFirstNameContainingIgnoreCaseOrMiddleNameContainingIgnoreCase(search, search, search);
    }

    // Поиск сотрудников по промежутку времени
    public List<Employee> findEmployeesByDate(LocalDate startDate, LocalDate endDate) {
        return employeeRepository.findAllByModificationDateBetween(startDate, endDate);
    }
}