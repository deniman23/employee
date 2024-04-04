package org.example.service.responce;

import org.example.dao.repository.EmployeeDataService;
import org.example.service.dto.EmployeeDto;
import org.example.service.dto.PostDto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SearchResult {
    private final List<EmployeeDto> employeeDtos;
    private final List<PostDto> postDtos;

    public SearchResult(List<EmployeeDto> employeeDtos, List<PostDto> postDtos, EmployeeDataService employeeDataService) {
        this.employeeDtos = employeeDtos.stream()
                .peek(dto -> {
                    Optional<PostDto> postDtoOptional = employeeDataService.findPostById(dto.getPostID());
                    dto.setPost(postDtoOptional.orElse(null));
                })
                .collect(Collectors.toList());
        this.postDtos = postDtos;
    }

    public List<EmployeeDto> getEmployeeDtos() {
        return employeeDtos;
    }

    public List<PostDto> getPostDtos() {
        return postDtos;
    }
}