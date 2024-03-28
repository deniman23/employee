package org.example;

import org.example.api.EmployeeAPI;
import org.example.api.JSON;
import org.example.api.PostAPI;
import org.example.entity.Employee;
import org.example.entity.Post;
import org.example.menu.EmployeeMenu;
import org.example.menu.PostMenu;


import java.util.*;


public class Main {
    private List<Employee> employees;
    public Map<Integer, Post> posts;
    private EmployeeMenu employeeMenu;
    private EmployeeAPI employeeAPI;
    private PostMenu postMenu;
    private PostAPI postAPI;
    private JSON json;

    public Main() {
        posts = new HashMap<>();
        employees = new ArrayList<>();
        json = new JSON();
        postAPI = new PostAPI(posts, employees, json);
        employeeAPI = new EmployeeAPI(posts, employees, employeeMenu, json);
        employeeMenu = new EmployeeMenu(employeeAPI, null);
        postMenu = new PostMenu(postAPI, employeeMenu);
        employeeMenu.setPostMenu(postMenu);

        postInitializer();
        employeeInitializer();

        employeeMenu.menuEmployee();
    }

    public static void main(String[] args) {

        new Main();
    }

    private void postInitializer() {
        posts.put(1, new Post(1, "dev"));
        posts.put(2, new Post(2, "ceo"));
        posts.put(3, new Post(3, "qa"));
    }

    private void employeeInitializer() {
        employees.add(new Employee(1, "Ivanov", "Ivan", "Ivanovich", 1));
        employees.add(new Employee(2, "Alexeev", "Alex", "Alexevich", 2));
        employees.add(new Employee(3, "Vitaliev", "Vitaly", "Vitalievich", 3));
    }
}