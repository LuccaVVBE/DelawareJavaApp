package controllers;

import domain.Employee;
import service.CarrierService;
import service.CompanyService;
import service.OrderService;

public class WarehouseEmployeeController extends EmployeeController {
    public WarehouseEmployeeController(Employee employee) {
        super(employee);
    }

    public WarehouseEmployeeController(CarrierService carrierService, OrderService orderService, CompanyService companyService, Employee employee) {
        super(employee);
        this.carrierService = carrierService;
        this.orderService = orderService;
        this.companyService = companyService;
    }

}
