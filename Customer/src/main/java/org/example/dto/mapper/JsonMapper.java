package org.example.dto.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.example.dto.EmployeeDto;
import org.example.dto.PostDto;
import org.example.dto.PostWithEmployeesDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class JsonMapper {
    public static Scanner scanner = new Scanner(System.in);
    private final ObjectMapper objectMapper;

    @Autowired
    public JsonMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
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

    public String convertEmployeeToJson(EmployeeDto employeeDto) throws JsonProcessingException {
        if (employeeDto == null) {
            return "\"Not found\"";
        }

        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(employeeDto);
    }
}