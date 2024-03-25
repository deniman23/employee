package org.example;

import org.example.api.EmployeeAPI;
import org.example.api.PostAPI;
import org.example.entity.Employee;
import org.example.entity.Post;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import static org.example.menu.EmployeeMenu.menuEmployee;
import static org.example.menu.PostMenu.posts;


public class Main extends EmployeeAPI {

    public static final List<Employee> employees = new ArrayList<>();

    public static void main(String[] args) {
        posts.put(1, new Post("dev"));
        posts.put(2, new Post("ceo"));
        posts.put(3, new Post("qa"));

        employees.add(new Employee("Ivanov", "Ivan", "Ivanovich", 1));
        employees.add(new Employee("Alexeev", "Alex", "Alexevich", 2));
        employees.add(new Employee("Vitaliev", "Vitaly", "Vitalievich", 3));

        menuEmployee(employees, posts);
    }


}


