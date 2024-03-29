package org.example.service.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.dao.model.Employee;
import org.example.dao.model.Post;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class JsonMapper {
    public static Scanner scanner = new Scanner(System.in);
    public static final ObjectMapper objectMapper = new ObjectMapper();


    static {
        objectMapper.registerModule(new JavaTimeModule());
    }

    public String convertPostToJson(Post post) throws JsonProcessingException {
        if (post == null) {
            return "\"Not found\"";
        }

        ObjectNode postJson = objectMapper.createObjectNode();
        postJson.put("id", post.getId());
        postJson.put("postName", post.getPostName());
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(postJson);
    }

    public String convertEmployeeToJson(Employee employee, List<Post> posts) throws IOException {
        ObjectNode employeeJson = objectMapper.createObjectNode();
        employeeJson.put("id", employee.getId());
        employeeJson.put("lastName", employee.getLastName());
        employeeJson.put("firstName", employee.getFirstName());
        employeeJson.put("middleName", employee.getMiddleName());
        employeeJson.put("creationDate", employee.getCreationDate().toString());
        employeeJson.put("modificationDate", employee.getModificationDate().toString());
        employeeJson.put("terminated", employee.getTerminated());

        Post post = posts.get(employee.getPositionId());
        String postJson = convertPostToJson(post);
        if (post != null) {
            JsonNode postJsonNode = objectMapper.readTree(postJson);
            employeeJson.set("post", postJsonNode);
        } else {
            employeeJson.put("post", "Not found");
        }

        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(employeeJson);
    }
}