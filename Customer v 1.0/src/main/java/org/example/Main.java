package org.example;

import com.google.gson.Gson;
import org.example.api.PostAPI;
import org.example.entity.Employee;

import java.util.HashMap;
import java.util.Map;

import static org.example.menu.EmployeeMenu.menu;


public class Main extends PostAPI {
    public static final Map<String, Employee> employees = new HashMap<>();

    public static Gson gson = new Gson();

    public static void main(String[] args) {
        menu();
    }


}