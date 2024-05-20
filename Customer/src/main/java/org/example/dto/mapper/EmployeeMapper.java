package org.example.dto.mapper;

import org.example.dao.model.Employee;
import org.example.dto.EmployeeDto;
import org.example.request.RequestEmployee;


public class EmployeeMapper {

    public static EmployeeDto entityToDto(Employee employee) {
        if (employee==null){
            return null;
        }
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
