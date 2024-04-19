package org.example.service.dto;


import java.util.List;

public class PostWithEmployeesDto {
    private PostDto post;
    private List<String> employeeLastNames;

    public PostWithEmployeesDto(PostDto post, List<String> employeeLastNames) {
        this.post = post;
        this.employeeLastNames = employeeLastNames;
    }

    public PostDto getPost() {
        return post;
    }

    public void setPost(PostDto post) {
        this.post = post;
    }

    public void setEmployeeLastNames(List<String> employeeLastNames) {
        this.employeeLastNames = employeeLastNames;
    }

    public List<String> getEmployeeLastNames() {
        return employeeLastNames;
    }
}
