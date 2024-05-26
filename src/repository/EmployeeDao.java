package repository;

import domain.Employee;

public interface EmployeeDao extends GenericDao<Employee> {
    Employee findByEmail(String email);

}

