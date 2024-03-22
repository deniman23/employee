package org.example.menu;

import org.example.api.EmployeeAPI;

import java.util.Scanner;

import static org.example.Main.employees;
import static org.example.Main.gson;
import static org.example.api.PostAPI.posts;
import static org.example.menu.PostMenu.postMenu;

public class EmployeeMenu extends EmployeeAPI {
    public static void menu() {
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;
        try {
            while (isRunning) {
                System.out.println("Choose an action");
                System.out.println("0. Go to post menu");
                System.out.println("1. Create employer");
                System.out.println("2. Change employer");
                System.out.println("3. Delete employer");
                System.out.println("4. Output all sorted employees");
                System.out.println("5. Output one employer");
                System.out.println("6. Output filtered employees");
                System.out.println("7. Exit");


                int operator = scanner.nextInt();
                scanner.nextLine(); // Считывание символа новой строки

                switch (operator) {
                    case 0:
                        postMenu(posts, gson);
                        break;
                    case 1:
                        create(scanner, gson, employees, posts);
                        break;
                    case 2:
                        change(scanner, gson, employees, posts);
                        break;
                    case 3:
                        delete(scanner, employees);
                        break;
                    case 4:
                        outputSortedByLastName(employees);
                        break;
                    case 5:
                        outputOne(scanner, employees);
                        break;
                    case 6:
                        outputFiltered(scanner, employees);
                        break;
                    case 7:
                        isRunning = false;
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid action");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }

}
