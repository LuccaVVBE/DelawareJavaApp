package controllers;

import domain.Box;
import domain.Carrier;
import domain.Company;
import domain.Employee;
import dtos.BoxDto;
import dtos.CarrierDto;
import dtos.EmployeeDto;
import enums.EmployeeRole;
import exceptions.NoAccessException;
import interfaces.EmployeeInterface;
import service.BoxService;
import service.CarrierService;
import service.CompanyService;
import service.OrderService;

import java.util.Set;

public class AdminController extends EmployeeController {
    public AdminController(Employee employee) {
        super(employee);
    }

    public AdminController(CarrierService carrierService, OrderService orderService, BoxService boxService, CompanyService companyService, Employee employee) {
        super(employee);
        this.carrierService = carrierService;
        this.orderService = orderService;
        this.companyService = companyService;
        this.boxService = boxService;
    }

    @Override
    public void addCarrier(CarrierDto carrierDto) {
        Carrier carrier = new Carrier(carrierDto);
        Company company = getEmployeeCompany();
        carrier.setCompany(company);
        company.getCarriers().add(carrier);
        getCompanyService().update(company);
    }

    @Override
    public void toggleActivation(String id) {
        Carrier carrier;
        try {
            carrier = getEmployeeCompany().getCarriers().stream().filter(c -> c.getId().equals(id)).findFirst().orElseThrow();
            carrier.setActive(!carrier.isActive());
            getCompanyService().update(getEmployeeCompany());
        } catch (Exception e) {
            throw new IllegalArgumentException("Carrier not found");
        }
    }

    @Override
    public void removeCarrier(String id) {
        Carrier carrier;
        try {
            var carriers = getEmployeeCompany().getCarriers();
            carrier = carriers.stream().filter(c -> c.getId().equals(id)).findFirst().orElseThrow();
            getEmployeeCompany().getCarriers().remove(carrier);
            getCompanyService().update(getEmployeeCompany());
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Carrier not found");
        }
    }

    @Override
    public void updateCarrier(String id, CarrierDto carrierDto) {
        Carrier carrier;
        Company company = getEmployeeCompany();
        try {
            var carriers = company.getCarriers();
            carrier = carriers.stream().filter(c -> c.getId().equals(id)).findFirst().orElseThrow();
            carrier.setName(carrierDto.name());
            carrier.setEmail(carrierDto.email());
            carrier.setPhone(carrierDto.phone());
            carrier.setActive(carrierDto.active());
            carrier.setAmountOfCharacters(carrierDto.amountOfCharacters());
            carrier.setNumOnly(carrierDto.numOnly());
            carrier.setPrefix(carrierDto.prefix());
            carrier.setImage(carrierDto.image());
            getCompanyService().update(company);
        } catch (Exception e) {
            throw new IllegalArgumentException("Carrier not found");
        }
    }

    @Override
    public void updateEmployee(String id, EmployeeDto employeeDto) throws NoAccessException, IllegalArgumentException {
        Employee employee;
        Company company = getEmployeeCompany();
        try {
            var employees = company.getEmployees();
            employee = employees.stream().filter(c -> c.getId().equals(id)).findFirst().orElseThrow();
            employee.setFirstName(employeeDto.firstname());
            employee.setLastName(employeeDto.lastname());
            employee.setEmail(employeeDto.email());
            company.setPhone(employeeDto.phone());
            employee.setRole(employeeDto.role());
            getCompanyService().update(company);
        } catch (Exception e) {
            throw new IllegalArgumentException("Employee not found");
        }
    }

    public void updateBox(String name, BoxDto boxDto) {
        Box box;
        Company company = getEmployeeCompany();

        try {
            var boxes = company.getBoxes();
            box = boxes.stream().filter(b -> b.getName().equals(name)).findFirst().orElseThrow();
            box.setName(boxDto.name());
            box.setWidth(boxDto.width());
            box.setHeight(boxDto.height());
            box.setDepth(boxDto.depth());
            box.setBoxType(boxDto.boxType().toString());
            box.setActive(boxDto.active());
            box.setPrice(boxDto.price());
            box.setCompany(getEmployee().getCompany());

            getCompanyService().update(company);
            getBoxService().update(box);
        } catch (Exception e) {
            throw new IllegalArgumentException("Box not found!");
        }

    }

    @Override
    public void addBox(BoxDto boxDto) {
        Box box = new Box(boxDto);
        Company company = getEmployeeCompany();
        box.setCompany(company);
        company.addBox(box);
        getCompanyService().update(company);
    }

    @Override
    public void removeBox(String boxID) {
        Company company = getEmployeeCompany();
        Box box;
        try {
            box = company.getBoxes().stream().filter(b -> b.getName().equals(boxID)).findFirst().orElseThrow();
            company.getBoxes().remove(box);
            getCompanyService().update(company);
        } catch (Exception e) {
            throw new IllegalArgumentException("Box not found!");
        }
    }


    @Override
    public Set<EmployeeInterface> getCompanyEmployees() throws NoAccessException {
        return getEmployeeCompany().getEmployeeInterfaces();
    }

    @Override
    public void makeAdmin(String id) throws NoAccessException, IllegalArgumentException {
        Employee employee;
        try {
            employee = getEmployeeCompany().getEmployees().stream().filter(e -> e.getId().equals(id)).findFirst().orElseThrow();
            employee.setRole(EmployeeRole.ADMIN);
            getCompanyService().update(getEmployeeCompany());
        } catch (Exception e) {
            throw new IllegalArgumentException("Employee not found or already admin");
        }
    }

    @Override
    public void createEmployee(EmployeeDto employeeDto) throws NoAccessException {
        Employee employee = new Employee(employeeDto.firstname(), employeeDto.lastname(), employeeDto.phone(), employeeDto.email(), employeeDto.role(), getEmployeeCompany());
        Company company = getEmployeeCompany();
        employee.setCompany(company);
        company.getEmployees().add(employee);
        getCompanyService().update(company);
    }
}
