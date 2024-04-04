package org.example.service.responce;

import org.example.service.dto.EmployeeDto;
import org.example.service.dto.PostDto;

import java.util.List;

public class SearchResult {
    private final List<EmployeeDto> employeeDtos;
    private final List<PostDto> postDtos;

    public SearchResult(List<EmployeeDto> employeeDtos, List<PostDto> postDtos) {
        this.employeeDtos = employeeDtos;
        this.postDtos = postDtos;
    }

    public List<EmployeeDto> getEmployeeDtos() {
        return employeeDtos;
    }

    public List<PostDto> getPostDtos() {
        return postDtos;
    }
}