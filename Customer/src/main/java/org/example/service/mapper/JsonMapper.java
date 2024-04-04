package org.example.service.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.service.dto.EmployeeDto;
import org.example.service.dto.PostDto;
import org.example.service.dto.PostWithEmployeesDto;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class JsonMapper {
    public static Scanner scanner = new Scanner(System.in);
    public static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.registerModule(new JavaTimeModule());
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

    public String convertEmployeeToJson(EmployeeDto employeeDto, List<PostDto> postDtos) throws IOException {
        if (employeeDto == null) {
            return "\"Not found\"";
        }

        postDtos.stream()
                .filter(p -> p.getId() == employeeDto.getPostID())
                .findFirst().ifPresent(employeeDto::setPost);

        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(employeeDto);
    }
}