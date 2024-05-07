package org.example.spring.controllers;

import org.example.services.service.EmployeeService;
import org.example.services.dto.EmployeeDto;
import org.example.services.response.ResponseEmployee;
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

    @PostMapping("/create")
    public ResponseEntity<?> createEmployee(@RequestBody ResponseEmployee responseEmployee) {
        Optional<EmployeeDto> createdEmployee = Optional.ofNullable(employeeService.createEmployee(responseEmployee));
        return createdEmployee
                .map(employee -> ResponseEntity.ok().body(employee))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PutMapping("/change/{id}")
    public ResponseEntity<?> changeEmployee(@PathVariable int id, @RequestBody ResponseEmployee responseEmployee) {
        responseEmployee.setId(id);
        Optional<EmployeeDto> updatedEmployee = Optional.ofNullable(employeeService.changeEmployee(responseEmployee));
        return updatedEmployee
                .map(employee -> ResponseEntity.ok().body(employee))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/terminate/{id}")
    public ResponseEntity<?> terminateEmployee(@PathVariable int id) {
        employeeService.terminateEmployee(id);
        return ResponseEntity.ok().body("Employee terminated");
    }

    @GetMapping("/sorted")
    public List<EmployeeDto> outputAllEmployeesSortedByLastName() {
        return employeeService.outputAllEmployeesSortedByLastName();
    }

    @GetMapping("/{id}")
    public EmployeeDto outputEmployee(@PathVariable int id) {
        return employeeService.outputEmployee(id);
    }

    @GetMapping("/terminated")
    public List<EmployeeDto> outputTerminatedEmployees() {
        return employeeService.outputTerminatedEmployees();
    }

    @GetMapping("/search/post/{postID}")
    public List<EmployeeDto> searchEmployeesByPost(@PathVariable int postID) {
        return employeeService.searchEmployeesByPost(postID);
    }

    @GetMapping("/search/match/{search}")
    public List<EmployeeDto> searchEmployeesByPartialMatch(@PathVariable String search) {
        return employeeService.searchEmployeesByPartialMatch(search);
    }

    @GetMapping("/search/date")
    public List<EmployeeDto> searchEmployeesByDate(@RequestParam String startDate, @RequestParam String endDate) {
        return employeeService.searchEmployeesByDate(startDate, endDate);
    }
}

