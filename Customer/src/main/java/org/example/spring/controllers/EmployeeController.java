package org.example.spring.controllers;

import org.example.dao.model.Employee;
import org.example.service.api.EmployeeAPI;
import org.example.service.dto.EmployeeDto;
import org.example.service.responce.ResponseEmployee;
import org.example.service.responce.SearchResult;
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
    public ResponseEntity<Object> createEmployee(@RequestBody ResponseEmployee responseEmployee) {
        Optional<Employee> createdEmployee = employeeAPI.createEmployee(responseEmployee);
        return createdEmployee
                .map(employee -> ResponseEntity.ok().body((Object) employee))
                .orElseGet(() -> ResponseEntity.badRequest().body("Unable to create employee"));
    }

    @PutMapping("/change/{id}")
    public ResponseEntity<?> changeEmployee(@PathVariable int id, @RequestBody ResponseEmployee responseEmployee) {
        responseEmployee.setId(id);
        Optional<Employee> updatedEmployee = employeeAPI.changeEmployee(responseEmployee);
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
    public SearchResult outputEmployee(@PathVariable int id) {
        return employeeAPI.outputEmployee(id);
    }

    @GetMapping("/terminated")
    public SearchResult outputTerminatedEmployees() {
        return employeeAPI.outputTerminatedEmployees();
    }

    @GetMapping("/search/post/{postID}")
    public SearchResult searchEmployeesByPost(@PathVariable int postID) {
        return employeeAPI.searchEmployeesByPost(postID);
    }

    @GetMapping("/search/match/{search}")
    public SearchResult searchEmployeesByPartialMatch(@PathVariable String search) {
        return employeeAPI.searchEmployeesByPartialMatch(search);
    }

    @GetMapping("/search/date")
    public SearchResult searchEmployeesByDate(@RequestParam String startDate, @RequestParam String endDate) {
        return employeeAPI.searchEmployeesByDate(startDate, endDate);
    }
}

