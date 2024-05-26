package test;

import controllers.WarehouseEmployeeController;
import domain.Employee;
import dtos.BoxDto;
import dtos.CarrierDto;
import enums.BoxType;
import exceptions.NoAccessException;
import org.junit.Before;
import org.junit.Test;
import service.CarrierService;
import service.CompanyService;
import service.OrderService;

import static org.mockito.Mockito.mock;

public class WarehouseEmployeeControllerTest {

    private WarehouseEmployeeController warehouseEmployeeController;

    @Before
    public void setUp() {
        CarrierService carrierServiceMock = mock(CarrierService.class);
        OrderService orderServiceMock = mock(OrderService.class);
        CompanyService companyServiceMock = mock(CompanyService.class);
        Employee employee = new Employee();

        warehouseEmployeeController = new WarehouseEmployeeController(carrierServiceMock, orderServiceMock, companyServiceMock, employee);
    }


    @Test(expected = NoAccessException.class)
    public void testAddCarrierThrowsNoAccessException() throws NoAccessException {
        warehouseEmployeeController.addCarrier(new CarrierDto("Email", "Name", "Phone", "img", true, 8, true, "pre"));
    }

    @Test(expected = NoAccessException.class)
    public void testToggleActivationThrowsNoAccessException() throws NoAccessException {
        warehouseEmployeeController.toggleActivation("1");
    }

    @Test(expected = NoAccessException.class)
    public void testRemoveCarrierThrowsNoAccessException() throws NoAccessException {
        warehouseEmployeeController.removeCarrier("1");
    }

    @Test(expected = NoAccessException.class)
    public void testUpdateBoxThrowsNoAccessException() throws NoAccessException {
        warehouseEmployeeController.updateBox("name", new BoxDto("name", 50, 50, 50, BoxType.CUSTOM, true, 50));
    }

    @Test(expected = NoAccessException.class)
    public void testAddBoxThrowsNoAccessException() throws NoAccessException {
        warehouseEmployeeController.addBox(new BoxDto("name", 50, 50, 50, BoxType.CUSTOM, true, 50));
    }
}
