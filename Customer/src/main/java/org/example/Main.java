package org.example;

import org.example.dao.model.Employee;
import org.example.dao.model.Post;
import org.example.dao.repository.EmployeeDataService;
import org.example.dao.repository.PostDataService;
import org.example.service.console.EmployeeMenu;
import org.example.service.console.PostMenu;
import org.example.service.api.EmployeeAPI;
import org.example.service.api.PostAPI;
import org.example.service.mapper.JsonMapper;


import java.util.*;


public class Main {
    private EmployeeMenu employeeMenu;

    public Main(EmployeeMenu employeeMenu) {
        this.employeeMenu = employeeMenu;
    }

    public static void main(String[] args) {
        List<Post> posts = new ArrayList<>();
        List<Employee> employees = new ArrayList<>();
        JsonMapper jsonMapper = new JsonMapper();

        PostDataService postDataService = new PostDataService(posts);
        EmployeeDataService employeeDataService = new EmployeeDataService(employees);

        EmployeeAPI employeeAPI = new EmployeeAPI(postDataService, employeeDataService, jsonMapper);
        PostAPI postAPI = new PostAPI(postDataService, employeeDataService, jsonMapper);

        EmployeeMenu employeeMenu = new EmployeeMenu(employeeAPI, null);
        PostMenu postMenu = new PostMenu(postAPI, employeeMenu);

        employeeMenu.setPostMenu(postMenu);

        postDataService.addPost(new Post(1, "dev"));
        postDataService.addPost(new Post(2, "ceo"));
        postDataService.addPost(new Post(3, "qa"));

        employeeDataService.addEmployee(new Employee(1, "Ivanov", "Ivan", "Ivanovich", 1));
        employeeDataService.addEmployee(new Employee(2, "Alexeev", "Alex", "Alexevich", 2));
        employeeDataService.addEmployee(new Employee(3, "Vitaliev", "Vitaly", "Vitalievich", 3));

        employeeMenu.menuEmployee();
    }
}