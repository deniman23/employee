package org.example.service.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.dao.repository.PostDataService;
import org.example.service.dto.EmployeeDto;
import org.example.service.dto.PostDto;
import org.example.service.dto.PostWithEmployeesDto;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class JsonMapper {
    private final PostDataService postDataService;
    public static Scanner scanner = new Scanner(System.in);
    public static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.registerModule(new JavaTimeModule());
    }

    public JsonMapper(PostDataService postDataService) {
        this.postDataService = postDataService;
    }

    public String convertPostToJson(PostDto postDto) throws JsonProcessingException {
        if (postDto == null) {
            return "\"Not found\"";
        }
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(postDto);
    }

    public String convertPostWithEmployeesToJson(PostWithEmployeesDto postWithEmployeesDto) throws JsonProcessingException {
        if (postWithEmployeesDto == null) {
            return "\"Not found\"";
        }
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(postWithEmployeesDto);
    }

    public String convertEmployeeToJson(EmployeeDto employeeDto) throws IOException {
        if (employeeDto == null) {
            return "\"Not found\"";
        }

        postDataService.getAllPosts().stream()
                .filter(p -> p.getId().equals(employeeDto.getPostID()))
                .findFirst().ifPresent(post -> employeeDto.setPost(new PostDto(post.getId(), post.getPostName())));

        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(employeeDto);
    }
}