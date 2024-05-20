package org.example.dao.service;

import org.example.dao.repository.EmployeeRepository;
import org.example.dao.model.Employee;

import org.example.dao.specification.EmployeeSpecification;
import org.example.filter.EmployeeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    public List<Employee> findAll(EmployeeFilter employeeFilter) {
        return employeeRepository.findAll(spec.searchFilter(employeeFilter));
    }

    // Поиск сотрудника по ID
    public Optional<Employee> findEmployeeById(Integer id) {
        return employeeRepository.findById(id);
    }

    // Поиск сотрудников по должности
    public List<Employee> findAllEmployeesByPost(Integer postID) { //TODO: убрать
        return employeeRepository.findAllByPostId(postID);
    }

}