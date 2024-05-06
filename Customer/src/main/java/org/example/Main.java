//package org.example;
//
//import org.example.dao.model.Employee;
//import org.example.dao.model.Post;
//import org.example.service.console.EmployeeConsoleService;
//import org.example.service.console.EmployeeMenu;
//import org.example.service.console.PostConsoleService;
//import org.example.service.console.PostMenu;
//import org.example.service.api.EmployeeAPI;
//import org.example.service.api.PostAPI;
//import org.example.service.mapper.JsonMapper;
//
//
//import java.util.*;
//
//
//public class Main {
//    public static void main(String[] args) {
//        List<Post> posts = new ArrayList<>();
//        List<Employee> employees = new ArrayList<>();
//
//
//
//        PostDataService postDataService = new PostDataService(posts);
//        EmployeeDataService employeeDataService = new EmployeeDataService(employees, postDataService);
//        JsonMapper jsonMapper = new JsonMapper(new PostDataService(posts));
//
//        PostAPI postAPI = new PostAPI(postDataService, employeeDataService);
//        EmployeeMenu employeeMenu = new EmployeeMenu(null, null);
//        PostMenu postMenu = new PostMenu(new PostConsoleService(postAPI, jsonMapper), employeeMenu);
//
//        employeeMenu.setPostMenu(postMenu);
//        employeeMenu.setEmployeeAPI(new EmployeeConsoleService(new EmployeeAPI(postDataService, employeeDataService), jsonMapper, employeeMenu));
//
//        employeeMenu.menuEmployee();
//    }
//}