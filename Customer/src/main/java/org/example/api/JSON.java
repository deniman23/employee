package org.example.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.example.entity.Employee;
import org.example.entity.Post;

import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

public class JSON {
    public static Scanner scanner = new Scanner(System.in);
    public static final ObjectMapper objectMapper = new ObjectMapper();

    // Добавляет в JsonObject все свойства и возвращает их со значениями
    public String convertPostToJson(Post post) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode postJson = mapper.createObjectNode();
        if (post != null) {
            postJson.put("id", post.getId());
            postJson.put("postName", post.getPostName());
        } else {
            postJson.put("id", "Not found");
            postJson.put("postName", "Not found");
        }
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(postJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
    public String convertEmployeeToJson(Employee employee, Map<Integer, Post> posts) {
        ObjectNode employeeJson = objectMapper.createObjectNode();
        employeeJson.put("id", employee.getId());
        employeeJson.put("lastName", employee.getLastName());
        employeeJson.put("firstName", employee.getFirstName());
        employeeJson.put("middleName", employee.getMiddleName());

        ArrayNode creationDateArray = objectMapper.createArrayNode();
        creationDateArray.add(employee.getCreationDate().getYear());
        creationDateArray.add(employee.getCreationDate().getMonthValue());
        creationDateArray.add(employee.getCreationDate().getDayOfMonth());
        employeeJson.set("creationDate", creationDateArray);

        ArrayNode modificationDateArray = objectMapper.createArrayNode();
        modificationDateArray.add(employee.getModificationDate().getYear());
        modificationDateArray.add(employee.getModificationDate().getMonthValue());
        modificationDateArray.add(employee.getModificationDate().getDayOfMonth());
        employeeJson.set("modificationDate", modificationDateArray);

        employeeJson.put("terminated", employee.getTerminated());

// Используем convertPostToJson для добавления информации о должности
        Post post = posts.get(employee.getPositionId());
        if (post != null) {
            try {
                JsonNode postJsonNode = objectMapper.readTree(convertPostToJson(post));
                employeeJson.set("post", postJsonNode);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            ObjectNode postJson = objectMapper.createObjectNode();
            postJson.put("id", "Not found");
            postJson.put("postName", "Not found");
            employeeJson.set("post", postJson);
        }

        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(employeeJson);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
