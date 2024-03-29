package org.example.service;

import org.example.entity.Employee;
import org.example.entity.Post;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataService {
    Map<Integer, Post> posts = new HashMap<>();
    List<Employee> employees = new ArrayList<>();

    public DataService(Map<Integer, Post> posts, List<Employee> employees) {
        this.posts = posts;
        this.employees = employees;
    }

    public Map<Integer, Post> getPosts() {
        return posts;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public boolean removeEmployee(Employee employee) {
        return employees.remove(employee);
    }

    public void addPost(Integer id, Post post) {
        posts.put(id, post);
    }

    public Post removePost(Integer id) {
        return posts.remove(id);
    }

    public void initializePosts() {
        posts.put(1, new Post(1, "dev"));
        posts.put(2, new Post(2, "ceo"));
        posts.put(3, new Post(3, "qa"));
    }

    public void initializeEmployees() {
        employees.add(new Employee(1, "Ivanov", "Ivan", "Ivanovich", 1));
        employees.add(new Employee(2, "Alexeev", "Alex", "Alexevich", 2));
        employees.add(new Employee(3, "Vitaliev", "Vitaly", "Vitalievich", 3));

    }
}
