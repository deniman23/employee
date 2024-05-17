package org.example.dto.mapper;

import org.example.dao.model.Employee;
import org.example.dto.EmployeeDto;
import org.example.request.RequestEmployee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeMapper {

    public static EmployeeDto entityToDto(Employee employee) {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstName(employee.getFirstName());
        employeeDto.setLastName(employee.getLastName());
        employeeDto.setMiddleName(employee.getMiddleName());
        employeeDto.setModificationDate(employee.getModificationDate());
        employeeDto.setCreationDate(employee.getCreationDate());
        employeeDto.setTerminated(employee.getTerminated());
        employeeDto.setId(employee.getId());
        employeeDto.setPost(PostMapper.entityToDto(employee.getPost()));
        return employeeDto;
    }



    public static List<EmployeeDto> entityToDto(List<Employee> employees) {
        List<EmployeeDto> list = new ArrayList<>();
        for (Employee employee : employees) {
            EmployeeDto employeeDto = entityToDto(employee);
            list.add(employeeDto);
        }
        return list;
    }

    public static RequestEmployee entityToRequest(Employee employee) {
        RequestEmployee requestEmployee = new RequestEmployee();
        requestEmployee.setId(employee.getId());
        requestEmployee.setMiddleName(employee.getMiddleName());
        requestEmployee.setFirstName(employee.getFirstName());
        requestEmployee.setLastName(employee.getLastName());
        requestEmployee.setPostId(employee.getId());
        return requestEmployee;
    }

    public static Employee requestToEntity(RequestEmployee requestEmployee) {
        Employee employee = new Employee();
        employee.setId(requestEmployee.getId());
        employee.setMiddleName(requestEmployee.getMiddleName());
        employee.setFirstName(requestEmployee.getFirstName());
        employee.setLastName(requestEmployee.getLastName());
        employee.setPostId(requestEmployee.getPostId());
        return employee;
    }
}
