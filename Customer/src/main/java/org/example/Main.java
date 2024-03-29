package org.example;

import org.example.dao.model.Employee;
import org.example.dao.model.Post;
import org.example.dao.repository.EmployeeDataService;
import org.example.dao.repository.PostDataService;
import org.example.menu.EmployeeMenu;
import org.example.menu.PostMenu;
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
        // Инициализация данных
        List<Post> posts = new ArrayList<>();
        List<Employee> employees = new ArrayList<>();
        JsonMapper jsonMapper = new JsonMapper();

        // Создание API и меню
        EmployeeAPI employeeAPI = new EmployeeAPI(new PostDataService(posts),new EmployeeDataService(employees),jsonMapper);
        PostAPI postAPI = new PostAPI(new PostDataService(posts),new EmployeeDataService(employees),jsonMapper);

        // Создание меню
        EmployeeMenu employeeMenu = new EmployeeMenu(employeeAPI, new PostMenu(postAPI, null));
        PostMenu postMenu = new PostMenu(postAPI, new EmployeeMenu(employeeAPI, null));
        employeeAPI.setEmployeeMenu(employeeMenu);
        postAPI.setPostMenu(postMenu);

        // Запуск приложения
        employeeMenu.menuEmployee();
    }
}