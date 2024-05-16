package org.example.services.mapper;

import org.example.dao.model.Employee;
import org.example.dao.model.Post;
import org.example.dto.EmployeeDto;
import org.example.dto.PostDto;

import java.util.ArrayList;
import java.util.List;

public class PostMapper {

    public static PostDto entityToDto(Post post){
        PostDto dto = new PostDto();
        dto.setPostName(post.getPostName());
        dto.setId(post.getId());
        return dto;
    }

    public static List<EmployeeDto> entityToDto(List<Employee> employees){
//        List<EmployeeDto> list = new ArrayList<>();
//        for (Employee employee : employees) {
//            EmployeeDto employeeDto = new EmployeeDto();
//            employeeDto.setId(employee.getId());
//            employeeDto.setPost(employee.getPost());
//        }
//
//        employees.forEach(e->list.add());
        return null;
    }
}
