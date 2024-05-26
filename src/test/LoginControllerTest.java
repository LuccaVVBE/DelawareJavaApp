package test;

import controllers.AdminController;
import controllers.LoginController;
import controllers.WarehouseEmployeeController;
import domain.Employee;
import enums.EmployeeRole;
import exceptions.NoAccessException;
import org.junit.Before;
import org.junit.Test;
import service.UserService;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class LoginControllerTest {

    private UserService userServiceMock;
    private LoginController loginController;

    @Before
    public void setUp() {
        userServiceMock = mock(UserService.class);
        loginController = new LoginController();
        loginController.userService = userServiceMock;
    }

    @Test
    public void handleLoginAdminTest() throws NoAccessException {
        Employee admin = new Employee();
        admin.setRole(EmployeeRole.ADMIN);
        admin.setEmail("admin@test.com");
        admin.setPassword("admin");

        when(userServiceMock.findByEmail("admin@test.com")).thenReturn(admin);
        doNothing().when(userServiceMock).checkCredentials(admin, "admin");

        assertTrue(loginController.handleLogin("admin@test.com", "admin") instanceof AdminController);
    }

    @Test
    public void handleLoginWarehouseEmployeeTest() throws NoAccessException {
        Employee warehouseEmployee = new Employee();
        warehouseEmployee.setRole(EmployeeRole.WAREHOUSE_EMPLOYEE);
        warehouseEmployee.setEmail("employee@test.com");
        warehouseEmployee.setPassword("employee");

        when(userServiceMock.findByEmail("employee@test.com")).thenReturn(warehouseEmployee);
        doNothing().when(userServiceMock).checkCredentials(warehouseEmployee, "employee");

        assertTrue(loginController.handleLogin("employee@test.com", "employee") instanceof WarehouseEmployeeController);
    }

    @Test(expected = NoAccessException.class)
    public void handleLoginFailedTest() throws NoAccessException {
        when(userServiceMock.findByEmail(anyString())).thenThrow(new RuntimeException());

        loginController.handleLogin("fail@test.com", "fail");
    }
}
