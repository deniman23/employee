package org.example.controllers;

import org.example.filter.EmployeeFilter;
import org.example.services.EmployeeService;
import org.example.dto.EmployeeDto;
import org.example.request.RequestEmployee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<?> createEmployee(@RequestBody RequestEmployee requestEmployee) {
        Optional<EmployeeDto> createdEmployee = Optional.ofNullable(employeeService.createEmployee(requestEmployee));
        return createdEmployee
                .map(employee -> ResponseEntity.ok().body(employee))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> changeEmployee(@PathVariable Integer id, @RequestBody RequestEmployee requestEmployee) {
        requestEmployee.setId(id);
        Optional<EmployeeDto> updatedEmployee = Optional.ofNullable(employeeService.changeEmployee(requestEmployee));
        return updatedEmployee
                .map(employee -> ResponseEntity.ok().body(employee))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> terminateEmployee(@PathVariable int id) {
        employeeService.terminateEmployee(id);
        return ResponseEntity.ok().body("Employee terminated");
    }

    @GetMapping("/{id}")
    public EmployeeDto outputEmployee(@PathVariable int id) {
        return employeeService.outputEmployee(id);
    }

    @GetMapping
    public List<EmployeeDto> filterEmployee(EmployeeFilter employeeFilter) {
        return employeeService.findAll(employeeFilter);
    }
}

