package service;

import domain.Employee;
import repository.EmployeeDao;
import repository.EmployeeRepo;
import util.PasswordUtil;

import java.util.List;

public class UserService implements EmployeeDao {
    private final EmployeeRepo employeeRepo = new EmployeeRepo();

    @Override
    public List<Employee> findAll() {
        return employeeRepo.findAll();
    }

    @Override
    public <U> Employee get(U id) {
        return employeeRepo.get(id);
    }

    @Override
    public Employee update(Employee employee) {
        return employeeRepo.update(employee);
    }

    @Override
    public void delete(Employee employee) {
        employeeRepo.delete(employee);
    }

    @Override
    public void insert(Employee employee) {
        employeeRepo.insert(employee);
    }

    @Override
    public <U> boolean exists(U id) {
        return employeeRepo.exists(id);
    }

    @Override
    public Employee findByEmail(String email) {
        return employeeRepo.findByEmail(email);
    }

    public void checkCredentials(Employee user, String password) throws IllegalArgumentException {
        if (!PasswordUtil.checkPassword(password, user.getPassword())) {
            throw new IllegalArgumentException("Password is incorrect");
        }
    }
}
