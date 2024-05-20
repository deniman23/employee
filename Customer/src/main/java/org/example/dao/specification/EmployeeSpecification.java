package org.example.dao.specification;


import org.example.dao.model.Employee;
import org.example.filter.EmployeeFilter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class EmployeeSpecification {
    public Specification<Employee> searchFilter(EmployeeFilter employeeFilter) {
        return (root, query, cb) -> {
            return Specification.where(attributeContains("lastName", employeeFilter.getSearch()))
                    .or(attributeContains("firstName", employeeFilter.getSearch()))
                    .or(attributeContains("middleName", employeeFilter.getSearch()))
                    .and(equals("terminated", employeeFilter.getTerminated()))
                    .and(date(employeeFilter.getStartDate(), employeeFilter.getEndDate()))
                    .and(position(employeeFilter.getPostName()))
                    .toPredicate(root, query, cb);

        };

    }

    protected Specification<Employee> position(String postName) {
        return (root, query, cb) -> {
            if (postName == null) {
                return null;
            }

            return cb.equal(root.join("post").get("postName"), postName);
        };
    }

    protected Specification<Employee> attributeContains(String attribute, String value) {
        return (root, query, cb) -> {

            if (value == null) {
                return null;
            }

            return cb.like(
                    cb.lower(root.get(attribute)),
                    "%" + value.toLowerCase() + "%"
            );
        };
    }

    protected Specification<Employee> equals(String attribute, Boolean value) {
        return (root, query, cb) -> {

            if (value == null) {
                return null;
            }

            return cb.equal(root.get(attribute), value);
        };
    }

    protected Specification<Employee> date(LocalDate startDate, LocalDate endDate) {
        return (root, query, cb) -> {
            if (startDate == null && endDate == null) {
                return null;
            }

            if (startDate != null && endDate == null) {
                return cb.greaterThanOrEqualTo(root.get("modificationDate"), startDate);
            }

            if (startDate == null) {
                return cb.lessThanOrEqualTo(root.get("modificationDate"), endDate);
            }

            return cb.between(root.get("modificationDate"), startDate, endDate);
        };
    }

}
