package test;

import controllers.WarehouseEmployeeController;
import domain.*;
import enums.OrderStatus;
import interfaces.OrderInterface;
import interfaces.OrderItemInterface;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import service.CarrierService;
import service.CompanyService;
import service.OrderService;

import java.util.*;

import static org.mockito.Mockito.*;

public class EmployeeControllerTest {

    private CarrierService carrierServiceMock;
    private OrderService orderServiceMock;
    private CompanyService companyServiceMock;
    private WarehouseEmployeeController warehouseEmployeeController;

    @Before
    public void setUp() {
        carrierServiceMock = mock(CarrierService.class);
        orderServiceMock = mock(OrderService.class);
        companyServiceMock = mock(CompanyService.class);
        Employee employee = new Employee();
        employee.setCompany(new Company());

        warehouseEmployeeController = new WarehouseEmployeeController(carrierServiceMock, orderServiceMock, companyServiceMock, employee);
    }


    @Test
    public void testGetCarriers() {
        List<Carrier> carriers = new ArrayList<>();
        when(carrierServiceMock.findAll()).thenReturn(carriers);
        Company company = new Company();
        company.setId("SOEP");
        company.setCarriers(new HashSet<>(carriers));
        when(warehouseEmployeeController.getEmployeeCompany()).thenReturn(company);
        Assert.assertEquals(new HashSet<>(carriers), warehouseEmployeeController.getCarriers());
    }


    @Test
    public void testGetCompanies() {
        List<Company> companies = new ArrayList<>();
        when(companyServiceMock.findAll()).thenReturn(companies);
        Assert.assertEquals(new HashSet<>(companies), warehouseEmployeeController.getCompanies());
    }

    @Test
    public void testSetCurrentWorkingOrder() {
        Order order = mock(Order.class);
        warehouseEmployeeController.setCurrentWorkingOrder(order);
        Assert.assertEquals(order, warehouseEmployeeController.getCurrentWorkingOrder());
    }

    @Test
    public void testSaveOrder() {
        Order order = mock(Order.class);
        Carrier carrier = mock(Carrier.class);
        Company company = mock(Company.class);
        company.setNotifications(new HashSet<>());
        TrackAndTrace trackAndTrace = mock(TrackAndTrace.class);

        when(order.getCustomerCompany()).thenReturn(company);
        when(order.getTrackAndTrace()).thenReturn(trackAndTrace);

        warehouseEmployeeController.saveOrder(order, carrier, OrderStatus.PROCESSED);
        verify(orderServiceMock).update(order);
    }


    @Test
    public void testProcessOrder() {
        Order order = mock(Order.class);
        Carrier carrier = mock(Carrier.class);
        Company company = mock(Company.class);
        company.setNotifications(new HashSet<>());
        TrackAndTrace trackAndTrace = mock(TrackAndTrace.class);

        when(order.getCustomerCompany()).thenReturn(company);
        when(order.getTrackAndTrace()).thenReturn(trackAndTrace);

        warehouseEmployeeController.processOrder(order, carrier);

        verify(orderServiceMock).update(order);
        verify(trackAndTrace).setStatus(any(OrderStatus.class));
    }


    @Test
    public void testUpdateOrder() {
        Order order = mock(Order.class);
        warehouseEmployeeController.updateOrder(order);
        verify(orderServiceMock).update(order);
    }

    @Test
    public void testCalculateTotalOrderPrice() {
        // Create mock objects
        OrderInterface order = mock(Order.class);
        OrderItemInterface orderItem1 = mock(OrderItem.class);
        OrderItemInterface orderItem2 = mock(OrderItem.class);
        Product product1 = mock(Product.class);
        Product product2 = mock(Product.class);
        ProductPrice productPrice1 = mock(ProductPrice.class);
        ProductPrice productPrice2 = mock(ProductPrice.class);

        Set<OrderItemInterface> orderItems = new HashSet<>(Arrays.asList(orderItem1, orderItem2));
        Set<ProductPrice> productPrices = new HashSet<>(Collections.singletonList(productPrice1));
        Set<ProductPrice> productPrices2 = new HashSet<>(Collections.singletonList(productPrice2));

        // Set up the mock objects
        when(order.getOrderItems()).thenReturn(orderItems);
        when(orderItem1.getProduct()).thenReturn(product1);
        when(orderItem1.getQuantity()).thenReturn(2);
        when(orderItem2.getProduct()).thenReturn(product2);
        when(orderItem2.getQuantity()).thenReturn(3);
        when(product1.getProductPrices()).thenReturn((productPrices));
        when(product2.getProductPrices()).thenReturn((productPrices2));
        when(productPrice1.getPrice()).thenReturn(10.0F);
        when(productPrice2.getPrice()).thenReturn(20.0F);

        // Call the method under test
        String result = warehouseEmployeeController.calculateTotalOrderPrice(order);

        // Verify the result
        Assert.assertEquals("€80,00", result);
    }


}
