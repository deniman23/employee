package org.example;

import org.example.dao.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    List<Employee> findAllByPostId(int id);

    List<Employee> findAllByIsTerminated();

    List<Employee> findAllByIsTerminatedFalseOrderByLastNameAsc();

    List<Employee> findByPartialMatch(String search);

    List<Employee> findAllByDate(LocalDate startDate, LocalDate endDate);
}
