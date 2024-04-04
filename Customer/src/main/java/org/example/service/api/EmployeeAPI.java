package org.example.service.api;

import org.example.dao.model.Employee;
import org.example.dao.repository.EmployeeDataService;
import org.example.dao.repository.PostDataService;
import org.example.service.dto.EmployeeDto;
import org.example.service.dto.PostDto;
import org.example.service.responce.ResponseEmployee;
import org.example.service.responce.SearchResult;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmployeeAPI {

    private final PostDataService postDataService;
    private final EmployeeDataService employeeDataService;

    public EmployeeAPI(PostDataService postDataService, EmployeeDataService employeeDataService) {
        this.postDataService = postDataService;
        this.employeeDataService = employeeDataService;
    }

    //Создание сотрудника
    public Optional<Employee> createEmployee(ResponseEmployee responseEmployee) {
        return employeeDataService.createEmployee(
                employeeDataService.getEmployees().size() + 1, responseEmployee.getLastName(), responseEmployee.getFirstName(),
                responseEmployee.getMiddleName(), responseEmployee.getPostID()
        );
    }

    //Изменение сотрудника
    public Optional<Employee> changeEmployee(ResponseEmployee responseEmployee) {
        int adjustedPostID = responseEmployee.getPostID() - 1;
        return employeeDataService.updateEmployee(
                responseEmployee.getId(), responseEmployee.getLastName(), responseEmployee.getFirstName(),
                responseEmployee.getMiddleName(), adjustedPostID
        );
    }


    public void terminateEmployee(int id) {
        employeeDataService.findById(id).get().setTerminated(true);
    }


    //Вывести всех сотрудников отсортированных по Фамилии
    public List<EmployeeDto> outputAllEmployeesSortedByLastName() {
        List<EmployeeDto> employeeDtos = employeeDataService.employeeToDto();
        employeeDtos.sort(Comparator.comparing(EmployeeDto::getLastName));
        return employeeDtos;
    }

    //Вывести одного сотрудника по ID
    public SearchResult outputEmployee(int id) {
        Optional<EmployeeDto> employeeDtoOptional = employeeDataService.getEmployees().stream()
                .filter(e -> e.getId() == id)
                .map(EmployeeDto::new)
                .findFirst();
        List<PostDto> postDtos = postDataService.positionToDto();
        return new SearchResult(employeeDtoOptional.map(Collections::singletonList).orElse(Collections.emptyList()), postDtos, employeeDataService);
    }

    //Вывести уволенных сотрудников
    public SearchResult outputTerminatedEmployees() {
        List<EmployeeDto> employeeDtos = employeeDataService.employeeToDto();
        List<PostDto> postDtos = postDataService.positionToDto();
        return new SearchResult(employeeDtos, postDtos, employeeDataService);
    }

    //Вывод сотрудника по фильтру
    public SearchResult searchEmployeesByPost(int postID) {
        List<PostDto> postDtos = postDataService.positionToDto();
        List<EmployeeDto> employeeDtos = employeeDataService.getEmployees().stream()
                .filter(e -> e.getPostID() == postID)
                .map(EmployeeDto::new)
                .collect(Collectors.toList());
        return new SearchResult(employeeDtos, postDtos, employeeDataService);
    }

    // Вывод по совпадению
    public SearchResult searchEmployeesByPartialMatch(String search) {
        List<PostDto> postDtos = postDataService.positionToDto();
        List<EmployeeDto> employeeDtos = employeeDataService.getEmployees().stream()
                .filter(e -> e.getLastName().toLowerCase().contains(search.toLowerCase()) ||
                        e.getFirstName().toLowerCase().contains(search.toLowerCase()) ||
                        e.getMiddleName().toLowerCase().contains(search.toLowerCase()))
                .map(EmployeeDto::new)
                .collect(Collectors.toList());
        return new SearchResult(employeeDtos, postDtos, employeeDataService);
    }

    // Поиск по промежутку времени
    public SearchResult searchEmployeesByDate(String startDateInput, String endDateInput) {
        List<PostDto> postDtos = postDataService.positionToDto();
        LocalDate startDate = LocalDate.parse(startDateInput);
        LocalDate endDate = LocalDate.parse(endDateInput);
        List<EmployeeDto> employeeDtos = employeeDataService.getEmployees().stream()
                .filter(e -> (e.getCreationDate().isEqual(startDate) || e.getCreationDate().isAfter(startDate)) &&
                        (e.getCreationDate().isEqual(endDate) || e.getCreationDate().isBefore(endDate)))
                .map(EmployeeDto::new)
                .collect(Collectors.toList());
        return new SearchResult(employeeDtos, postDtos, employeeDataService);
    }
}