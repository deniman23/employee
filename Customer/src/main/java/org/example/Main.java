package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.api.PostAPI;
import org.example.entity.Employee;
import org.example.entity.Post;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.example.menu.EmployeeMenu.menuEmployee;
import static org.example.menu.PostMenu.posts;


public class Main extends PostAPI {
    public static final List<Employee>employees = new ArrayList<>();

    public static void main(String[] args) {
        employees.add(new Employee("1", "Ivanov","Ivan", "Ivanovich", new Post("1", "dev")));
        employees.add(new Employee("2", "Alexeev","Alex", "Alexevich", new Post("2", "ceo")));
        employees.add(new Employee("3", "Vitaliev","Vitaly", "Vitalievich", new Post("3", "qa")));

        menuEmployee(employees, posts);
    }


}


