package test;

import controllers.AdminController;
import domain.Box;
import domain.Carrier;
import domain.Company;
import domain.Employee;
import dtos.BoxDto;
import dtos.CarrierDto;
import enums.BoxType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import service.BoxService;
import service.CarrierService;
import service.CompanyService;
import service.OrderService;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.*;

public class AdminControllerTest {

    private CarrierService carrierServiceMock;
    private OrderService orderServiceMock;
    private BoxService boxServiceMock;
    private CompanyService companyServiceMock;
    private AdminController adminController;

    @Before
    public void setUp() {
        carrierServiceMock = mock(CarrierService.class);
        orderServiceMock = mock(OrderService.class);
        boxServiceMock = mock(BoxService.class);
        companyServiceMock = mock(CompanyService.class);
        Employee employee = new Employee();

        adminController = new AdminController(carrierServiceMock, orderServiceMock, boxServiceMock, companyServiceMock, employee);
    }


    @Test
    public void testAddCarrier() {
        Company company = new Company();
        company.setId("1");
        company.setCarriers(new HashSet<>());
        CompanyService companyServiceMock = Mockito.mock(CompanyService.class);

        AdminController adminControllerSpy = Mockito.spy(adminController);
        doReturn(company).when(adminControllerSpy).getEmployeeCompany();
        doReturn(companyServiceMock).when(adminControllerSpy).getCompanyService();

        Company mockCompany = new Company();
        when(companyServiceMock.update(any(Company.class))).thenReturn(mockCompany);

        when(companyServiceMock.get(anyString())).thenReturn(company);
        CarrierDto carrierDto = new CarrierDto("name", "email", "phone", "image", true, 1, true, "11");
        adminControllerSpy.addCarrier(carrierDto);

        verify(adminControllerSpy, times(1)).addCarrier(carrierDto);
        verify(companyServiceMock, times(1)).update(any(Company.class));
    }

    // test updating a carrier
    @Test
    public void testUpdateCarrier() {
        String oldCarrierName = "oldName";
        String id = "1";
        Carrier carrier = new Carrier();
        carrier.setName(oldCarrierName);
        carrier.setId(id);
        Company company = new Company();
        company.setId(id);
        company.setCarriers(new HashSet<>(Collections.singletonList(carrier)));
        CompanyService companyServiceMock = Mockito.mock(CompanyService.class);

        AdminController adminControllerSpy = Mockito.spy(adminController);
        doReturn(company).when(adminControllerSpy).getEmployeeCompany();
        doReturn(companyServiceMock).when(adminControllerSpy).getCompanyService();

        when(companyServiceMock.update(any(Company.class))).thenReturn(company);
        when(companyServiceMock.get(anyString())).thenReturn(company);
        CarrierDto updatedCarrierDto = new CarrierDto("newName", "email", "phone", "image", true, 1, true, "11");

        adminControllerSpy.updateCarrier(id, updatedCarrierDto);

        verify(adminControllerSpy, times(1)).updateCarrier("1", updatedCarrierDto);
        verify(companyServiceMock, times(1)).update(any(Company.class));
    }

    @Test
    public void testToggleActivation() {
        String carrierId = "1";
        Carrier carrier = new Carrier();
        carrier.setId(carrierId);
        carrier.setActive(true);
        Company company = new Company();
        company.setId("1");
        company.setCarriers(new HashSet<>(Collections.singletonList(carrier)));
        CompanyService companyServiceMock = Mockito.mock(CompanyService.class);

        AdminController adminControllerSpy = Mockito.spy(adminController);
        doReturn(company).when(adminControllerSpy).getEmployeeCompany();
        doReturn(companyServiceMock).when(adminControllerSpy).getCompanyService();

        when(companyServiceMock.update(any(Company.class))).thenReturn(company);
        when(companyServiceMock.get(anyString())).thenReturn(company);

        adminControllerSpy.toggleActivation(carrierId);

        Assert.assertFalse("Carrier should be deactivated", carrier.isActive());
        verify(adminControllerSpy, times(1)).toggleActivation(carrierId);
        verify(companyServiceMock, times(1)).update(any(Company.class));
    }


    @Test
    public void testUpdateBox() {
        Company company = new Company();
        company.setId("1");
        Box box = new Box("name", 50, 50, 50, BoxType.CUSTOM, true, 50, company);
        company.setBoxes(Set.of(box));

        Employee employee = new Employee();
        company.setEmployees(Set.of(employee));
        employee.setCompany(company);

        CompanyService companyServiceMock = Mockito.mock(CompanyService.class);
        BoxService boxServiceMock = Mockito.mock(BoxService.class);

        AdminController adminControllerSpy = Mockito.spy(adminController);
        doReturn(company).when(adminControllerSpy).getEmployeeCompany();
        doReturn(companyServiceMock).when(adminControllerSpy).getCompanyService();
        doReturn(boxServiceMock).when(adminControllerSpy).getBoxService();
        doReturn(employee).when(adminControllerSpy).getEmployee();

        Company mockCompany = new Company();
        mockCompany.setEmployees(Set.of(employee));
        when(companyServiceMock.update(any(Company.class))).thenReturn(mockCompany);

        // When
        BoxDto boxDto = new BoxDto("name", 60, 60, 60, BoxType.CUSTOM, false, 60);
        adminControllerSpy.updateBox("name", boxDto);

        // Then
        verify(adminControllerSpy, times(1)).updateBox("name", boxDto);
        verify(companyServiceMock, times(1)).update(any(Company.class));
        verify(boxServiceMock, times(1)).update(any(Box.class));

    }


    @Test
    public void testAddBox() {
        // Given
        Company company = new Company();
        company.setId("1");
        Employee employee = new Employee();
        employee.setCompany(company);

        CompanyService companyServiceMock = Mockito.mock(CompanyService.class);

        AdminController adminControllerSpy = Mockito.spy(adminController);
        doReturn(company).when(adminControllerSpy).getEmployeeCompany();
        doReturn(companyServiceMock).when(adminControllerSpy).getCompanyService();

        Company mockCompany = new Company();
        when(companyServiceMock.update(any(Company.class))).thenReturn(mockCompany);

        when(companyServiceMock.get(anyString())).thenReturn(company);

        BoxDto boxDto = new BoxDto("name", 50, 50, 50, BoxType.CUSTOM, true, 50);
        // When
        adminControllerSpy.addBox(boxDto);

        // Then
        verify(adminControllerSpy, times(1)).addBox(boxDto);
        verify(companyServiceMock, times(1)).update(any(Company.class));
    }


    @Test
    public void testRemoveBox() {
        // Given
        Company company = new Company();
        company.setId("1");
        Set<Box> boxes = new HashSet<>();
        Box boxToRemove = new Box("name", 50, 50, 50, BoxType.CUSTOM, true, 50, company);
        boxes.add(boxToRemove);
        company.setBoxes(boxes);

        CompanyService companyServiceMock = Mockito.mock(CompanyService.class);

        AdminController adminControllerSpy = Mockito.spy(adminController);
        doReturn(company).when(adminControllerSpy).getEmployeeCompany();
        doReturn(companyServiceMock).when(adminControllerSpy).getCompanyService();

        // When
        adminControllerSpy.removeBox("name");

        // Then
        verify(adminControllerSpy, times(1)).removeBox("name");
        verify(companyServiceMock, times(1)).update(any(Company.class));
    }


}
