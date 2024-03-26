package org.example;

import org.example.api.EmployeeAPI;
import org.example.api.PostAPI;
import org.example.entity.Employee;
import org.example.entity.Post;
import org.example.menu.EmployeeMenu;
import org.example.menu.PostMenu;


import java.util.*;


public class Main {
    private EmployeeMenu employeeMenu;
    private EmployeeAPI employeeAPI;
    private PostMenu postMenu;
    private PostAPI postAPI;
    private List<Employee> employees;
    private Map<Integer, Post> posts;

    public Main() {
        posts = new HashMap<>();
        employees = new ArrayList<>();
        postAPI = new PostAPI(posts);
        employeeAPI = new EmployeeAPI(posts, employees);
        employeeMenu = new EmployeeMenu(employeeAPI, null);
        postMenu = new PostMenu(postAPI, employeeAPI, employeeMenu);
        employeeMenu.setPostMenu(postMenu);


        posts.put(1, new Post(1, "dev"));
        posts.put(2, new Post(2, "ceo"));
        posts.put(3, new Post(3, "qa"));

        employees.add(new Employee(1,"Ivanov", "Ivan", "Ivanovich", 1));
        employees.add(new Employee(2,"Alexeev", "Alex", "Alexevich", 2));
        employees.add(new Employee(3,"Vitaliev", "Vitaly", "Vitalievich", 3));

        employeeMenu.menuEmployee(employees, posts);
    }

    public static void main(String[] args) {
        new Main();
    }
}