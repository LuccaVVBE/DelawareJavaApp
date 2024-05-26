package controllers;


import domain.Employee;
import enums.EmployeeRole;
import exceptions.NoAccessException;
import service.UserService;


public class LoginController {

    public UserService userService = new UserService();


    public EmployeeController handleLogin(String email, String password) throws NoAccessException {
        try {
            Employee employee = userService.findByEmail(email);
            // if the password doesn't match, an exception is thrown
            userService.checkCredentials(employee, password);
            if (employee.getRole() == EmployeeRole.ADMIN) {
                return new AdminController(employee);
            } else {
                return new WarehouseEmployeeController(employee);
            }
        } catch (Exception e) {
            throw new NoAccessException("Login failed");
        }
    }

}
