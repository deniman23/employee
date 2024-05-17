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


//            return Specification.where(attributeContains("", employeeFilter.getSearch())
//                    .or(attributeContains("", employeeFilter.getSearch())
//                            .or(attributeContains("", employeeFilter.getSearch())).toPredicate(root, query, cb);
//
//
//            Specification<Employee> bySearchStr = request.getSearch().map(e ->
//                    Specification.where(nameContains(e).or(shortNameContains(e)).or(userFirstNameContains(e))
//                            .or(userLastNameContains(e)).or(userMiddleNameContains(e)))).orElse(null);
//
//            Specification<ControlType> byActive = request.getActive().map(e ->
//                    Specification.where(byActive(e))).orElse(null);
//
//            return Specification.where(bySearchStr).and(byActive).toPredicate(root, query, cb);
        };

    }

    protected Specification<Employee> position(String postName) {

        if (postName == null) {
            return null;
        }

        return (root, query, cb) ->
                cb.equal(root.get("postName"), postName);

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
                return cb.greaterThanOrEqualTo(root.get("modificationDate"/*creationDate*/), startDate);
            }

            if (startDate == null) {
                return cb.lessThanOrEqualTo(root.get("modificationDate"/*creationDate*/), endDate);
            }

            return cb.between(root.get("modificationDate"/*creationDate*/), startDate, endDate);
        };
    }

}
