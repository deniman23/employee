package org.example;

import org.example.api.EmployeeAPI;
import org.example.api.JSON;
import org.example.api.PostAPI;
import org.example.entity.Employee;
import org.example.entity.Post;
import org.example.menu.EmployeeMenu;
import org.example.menu.PostMenu;
import org.example.service.DataService;


import java.util.*;


public class Main {
    private final EmployeeMenu employeeMenu;

    public Main(EmployeeMenu employeeMenu) {
        this.employeeMenu = employeeMenu;
    }

    public static void main(String[] args) {
        Map<Integer, Post> posts = new HashMap<>();
        List<Employee> employees = new ArrayList<>();
        JSON json = new JSON();
        DataService dataService = new DataService(posts, employees);

        EmployeeMenu employeeMenu = new EmployeeMenu(null, null);
        PostAPI postAPI = new PostAPI(dataService, json);
        EmployeeAPI employeeAPI = new EmployeeAPI(dataService, employeeMenu, json);

        employeeMenu.setEmployeeAPI(employeeAPI);
        PostMenu postMenu = new PostMenu(postAPI, employeeMenu);
        employeeMenu.setPostMenu(postMenu);

        DataInitializer initializer = new DataInitializer(dataService);
        initializer.initialize();

        new Main(employeeMenu).startApplication();
    }

    private void startApplication() {
        employeeMenu.menuEmployee();
    }
}