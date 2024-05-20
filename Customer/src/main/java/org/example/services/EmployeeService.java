package org.example.services;

import org.example.dao.model.Employee;
import org.example.dao.model.Post;
import org.example.dao.service.EmployeeServiceDao;
import org.example.dao.service.PostServiceDao;

import org.example.dto.mapper.PostMapper;
import org.example.error.ResourceNotFoundException;
import org.example.dto.EmployeeDto;
import org.example.filter.EmployeeFilter;
import org.example.request.RequestEmployee;
import org.example.dto.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeServiceDao employeeServiceDao;
    private final PostServiceDao postServiceDao;

    @Autowired
    public EmployeeService(EmployeeServiceDao employeeServiceDao, PostServiceDao postServiceDao) {
        this.employeeServiceDao = employeeServiceDao;
        this.postServiceDao = postServiceDao;
    }

    @Transactional(readOnly = true)
    public List<EmployeeDto> findAll(EmployeeFilter employeeFilter) {
        return employeeServiceDao.findAll(employeeFilter).stream()
                .map(EmployeeMapper::entityToDto)
                .collect(Collectors.toList());
    }


    // Создание должности
    @Transactional(rollbackFor = Exception.class)
    public EmployeeDto createEmployee(RequestEmployee requestEmployee) {
        Post post = postServiceDao.findById(requestEmployee.getPostId())
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with ID: " + requestEmployee.getPostId()));
        Employee employee = EmployeeMapper.requestToEntity(requestEmployee);
        employee.setPost(post);
        employee = employeeServiceDao.save(employee);
        return EmployeeMapper.entityToDto(employee);
    }

    // Изменение должности
    @Transactional(rollbackFor = Exception.class)
    public EmployeeDto changeEmployee(RequestEmployee requestEmployee) {
        Employee employee = employeeServiceDao.findEmployeeById(requestEmployee.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + requestEmployee.getId()));
        employee.setModificationDate(LocalDate.now());
        employee.setLastName(requestEmployee.getLastName());
        employee.setFirstName(requestEmployee.getFirstName());
        employee.setMiddleName(requestEmployee.getMiddleName());

        Integer postId = requestEmployee.getPostId();
        if (postId != null) {
            Post post = postServiceDao.findById(postId)
                    .orElseThrow(() -> new ResourceNotFoundException("Post not found with ID: " + postId));
            employee.setPost(post);
        }
        employee = employeeServiceDao.save(employee);
        return EmployeeMapper.entityToDto(employee);
    }

    // Увольнение сотрудника
    public void terminateEmployee(Integer id) {
        //TODO: changeEmployee. В dao УБРАТЬ
        Employee employee = employeeServiceDao.findEmployeeById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + id));
        employee.setTerminated(true);
        employee.setModificationDate(LocalDate.now());
        employeeServiceDao.save(employee);
    }


    // Вывод одного сотрудника
    @Transactional(readOnly = true)
    public EmployeeDto outputEmployee(Integer id) {
        Optional<Employee> employee = employeeServiceDao.findEmployeeById(id);
        Post post = postServiceDao.findById(employee.get().getPostId())
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with ID: " + employee.get().getPostId()));
        EmployeeDto employeeDto = EmployeeMapper.entityToDto(employee.orElseThrow());
        employeeDto.setPost(PostMapper.entityToDto(post));
        return employeeDto;
    }
}
