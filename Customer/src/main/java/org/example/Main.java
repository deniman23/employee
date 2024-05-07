package org.example;

import org.example.services.console.EmployeeConsoleService;
import org.example.services.console.EmployeeMenu;
import org.example.services.console.PostConsoleService;
import org.example.services.console.PostMenu;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Main.class, args);


        PostConsoleService postConsoleService = context.getBean(PostConsoleService.class);
        EmployeeConsoleService employeeConsoleService = context.getBean(EmployeeConsoleService.class);

        EmployeeMenu employeeMenu = context.getBean(EmployeeMenu.class);
        employeeMenu.setEmployeeConsoleService(employeeConsoleService);

        PostMenu postMenu = context.getBean(PostMenu.class);
        postMenu.setEmployeeMenu(employeeMenu);

        employeeMenu.menuEmployee();
    }
}
