package repository;


import domain.Employee;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;

public class EmployeeRepo extends GenericDaoJpa<Employee> implements EmployeeDao {

    public EmployeeRepo() {
        super(Employee.class);
    }

    @Override
    public Employee findByEmail(String email) {
        try {
            return em.createNamedQuery("Employee.findByEmail", Employee.class).setParameter("email", email).getSingleResult();
        } catch (NoResultException e) {
            throw new EntityNotFoundException();
        }
    }

}
