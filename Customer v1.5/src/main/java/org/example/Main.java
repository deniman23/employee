package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.api.PostAPI;
import org.example.entity.Employee;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.example.menu.EmployeeMenu.menuEmployee;
import static org.example.menu.PostMenu.posts;


public class Main extends PostAPI {
    public static final Map<String, Employee> employees = new HashMap<>();

    //public static Gson gson = new Gson();

    public static void main(String[] args) {
        menuEmployee(employees, posts);
    }


}


