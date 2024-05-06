package org.example.dao.repository;

import org.example.dao.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    List<Employee> findAllByPostId(int id);

    List<Employee> findAllByOrderByLastNameAsc();

    List<Employee> findAllByTerminatedTrue();

    List<Employee> findByLastNameContainingIgnoreCaseOrFirstNameContainingIgnoreCaseOrMiddleNameContainingIgnoreCase(String lastName, String firstName, String middleName);

    List<Employee> findAllByModificationDateBetween(LocalDate startDate, LocalDate endDate);
}
