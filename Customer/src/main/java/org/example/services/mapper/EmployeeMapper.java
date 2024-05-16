package org.example.services.mapper;

import org.example.dao.model.Employee;
import org.example.dto.EmployeeDto;

import java.util.ArrayList;
import java.util.List;

public class EmployeeMapper {

    public static EmployeeDto entityToDto(Employee employee){
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

    public static List<EmployeeDto> entityToDto(List<Employee> employees){
        List<EmployeeDto> list = new ArrayList<>();
        for (Employee employee : employees) {
            EmployeeDto employeeDto = entityToDto(employee);
            list.add(employeeDto);
        }
        return list;
    }
}
