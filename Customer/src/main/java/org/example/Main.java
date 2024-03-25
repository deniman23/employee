package org.example;

import org.example.api.EmployeeAPI;
import org.example.api.PostAPI;
import org.example.entity.Employee;
import org.example.entity.Post;
import org.example.menu.EmployeeMenu;


import java.util.*;



public class Main extends EmployeeMenu {

    public static final List<Employee> employees = new ArrayList<>();
    public static Map<Integer, Post> posts = new HashMap<>();
    public static void main(String[] args) {
        posts.put(1, new Post(1,"dev"));
        posts.put(2, new Post(2,"ceo"));
        posts.put(3, new Post(3, "qa"));

        employees.add(new Employee("Ivanov", "Ivan", "Ivanovich", 1));
        employees.add(new Employee("Alexeev", "Alex", "Alexevich", 2));
        employees.add(new Employee("Vitaliev", "Vitaly", "Vitalievich", 3));

        EmployeeMenu employeeMenu = new EmployeeMenu();
        employeeMenu.menuEmployee(employees,posts);
    }


}


