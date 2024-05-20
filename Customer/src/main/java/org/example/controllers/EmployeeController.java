package org.example.controllers;

import org.example.filter.EmployeeFilter;
import org.example.services.EmployeeService;
import org.example.dto.EmployeeDto;
import org.example.request.RequestEmployee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<EmployeeDto> create(@RequestBody RequestEmployee requestEmployee) {
        EmployeeDto createdEmployee = employeeService.createEmployee(requestEmployee);
        return ResponseEntity.ok().body(createdEmployee);
    }

    @PutMapping
    public ResponseEntity<EmployeeDto> change(@RequestBody RequestEmployee requestEmployee) {
        EmployeeDto updatedEmployee = employeeService.changeEmployee(requestEmployee);
        return ResponseEntity.ok().body(updatedEmployee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> terminate(@PathVariable int id) {
        employeeService.terminateEmployee(id);
        return ResponseEntity.ok().body("Employee terminated");
    }

    @GetMapping("/{id}")
    public EmployeeDto output(@PathVariable int id) {
        return employeeService.outputEmployee(id);
    }

    @GetMapping
    public List<EmployeeDto> filter(EmployeeFilter employeeFilter) {
        return employeeService.findAll(employeeFilter);
    }
}

