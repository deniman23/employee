package org.example.spring.controllers;

import org.example.service.api.EmployeeAPI;
import org.example.service.dto.EmployeeDto;
import org.example.service.responce.RequestEmployee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeAPI employeeAPI;

    @Autowired
    public EmployeeController(EmployeeAPI employeeAPI) {
        this.employeeAPI = employeeAPI;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createEmployee(@RequestBody RequestEmployee requestEmployee) {
        Optional<EmployeeDto> createdEmployee = Optional.ofNullable(employeeAPI.createEmployee(requestEmployee));
        return createdEmployee
                .map(employee -> ResponseEntity.ok().body(employee))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PutMapping("/change/{id}")
    public ResponseEntity<?> changeEmployee(@PathVariable int id, @RequestBody RequestEmployee requestEmployee) {
        requestEmployee.setId(id);
        Optional<EmployeeDto> updatedEmployee = Optional.ofNullable(employeeAPI.changeEmployee(requestEmployee));
        return updatedEmployee
                .map(employee -> ResponseEntity.ok().body(employee))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/terminate/{id}")
    public ResponseEntity<?> terminateEmployee(@PathVariable int id) {
        employeeAPI.terminateEmployee(id);
        return ResponseEntity.ok().body("Employee terminated");
    }

    @GetMapping("/sorted")
    public List<EmployeeDto> outputAllEmployeesSortedByLastName() {
        return employeeAPI.outputAllEmployeesSortedByLastName();
    }

    @GetMapping("/{id}")
    public EmployeeDto outputEmployee(@PathVariable int id) {
        return employeeAPI.outputEmployee(id);
    }

    @GetMapping("/terminated")
    public List<EmployeeDto> outputTerminatedEmployees() {
        return employeeAPI.outputTerminatedEmployees();
    }

    @GetMapping("/search/post/{postID}")
    public List<EmployeeDto> searchEmployeesByPost(@PathVariable int postID) {
        return employeeAPI.searchEmployeesByPost(postID);
    }

    @GetMapping("/search/match/{search}")
    public List<EmployeeDto> searchEmployeesByPartialMatch(@PathVariable String search) {
        return employeeAPI.searchEmployeesByPartialMatch(search);
    }

    @GetMapping("/search/date")
    public List<EmployeeDto> searchEmployeesByDate(@RequestParam String startDate, @RequestParam String endDate) {
        return employeeAPI.searchEmployeesByDate(startDate, endDate);
    }
}

