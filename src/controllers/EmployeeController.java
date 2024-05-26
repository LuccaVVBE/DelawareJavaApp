package controllers;

import domain.*;
import dtos.BoxDto;
import dtos.CarrierDto;
import dtos.EmployeeDto;
import enums.OrderStatus;
import exceptions.NoAccessException;
import interfaces.*;
import lombok.Getter;
import lombok.Setter;
import service.BoxService;
import service.CarrierService;
import service.CompanyService;
import service.OrderService;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDateTime;
import java.util.*;

public abstract class EmployeeController {
    @Getter
    CarrierService carrierService = new CarrierService();
    @Getter
    OrderService orderService = new OrderService();
    @Getter
    CompanyService companyService = new CompanyService();
    @Getter
    BoxService boxService = new BoxService();
    @Setter
    private Employee employee;
    @Getter
    private Order currentWorkingOrder;


    public EmployeeController(Employee employee) {
        this.employee = employee;
    }

//    GETTERS

    public Set<CarrierInterface> getCarriers() {
        Set<Carrier> carriers = getEmployeeCompany().getCarriers();
        return new HashSet<>(carriers);
    }

    public Set<CompanyInterface> getCompanies() {
        Set<Company> companies = new HashSet<>(companyService.findAll().stream().filter(company -> !company.equals(getEmployeeCompany())).toList());
        return new HashSet<>(companies);
    }

    // returns all customers of a company
    public Set<CustomerInterface> getCustomers(String id) {
        Company company = companyService.get(id);
        Set<Order> orders = new HashSet<>(company.getOrders());
        Set<CompanyInterface> customers = new HashSet<>();
        for (Order order : orders) {
            Company customer = order.getCustomerCompany();
            customers.add(customer);
        }
        return new HashSet<>((Collection) customers);
    }

    public Set<OrderInterface> getOrders() {
        Set<OrderInterface> orderInterfaces = new HashSet<>();
        Set<Order> orders = new HashSet<>(getEmployeeCompany().getOrders());
        for (Order order : orders) {
            if (order.getTrackAndTrace() == null) {
                var tnt = new TrackAndTrace(order);
                order.setTrackAndTrace(tnt);
                updateOrder(order);
            }
            orderInterfaces.add(order);
        }
        return orderInterfaces;
    }

    public void addTnt(Company company) {
        Set<Order> orders = new HashSet<>(company.getOrders());
        for (Order order : orders) {
            if (order.getTrackAndTrace() == null) {
                var tnt = new TrackAndTrace(order);
                order.setTrackAndTrace(tnt);
                updateOrder(order);
            }
        }
    }
//    SETTERS

    public void setCurrentWorkingOrder(OrderInterface order) {
        currentWorkingOrder = (Order) order;
    }

    //    METHODS

    public void saveOrder(OrderInterface order, CarrierInterface carrier, OrderStatus orderStatus) {
        Order localOrder = (Order) order;
        localOrder.getTrackAndTrace().setCarrier((Carrier) carrier);
        localOrder.getTrackAndTrace().setStatus(orderStatus);
        updateOrder(localOrder);
    }


    public boolean orderIsReceived(OrderInterface order) {
        return order.getTrackAndTrace().getStatus().equals(OrderStatus.RECEIVED);
    }

    public void processOrder(OrderInterface order, CarrierInterface carrier) {
        Order localOrder = (Order) order;
        localOrder.getTrackAndTrace().setStatus(OrderStatus.PROCESSED);
        localOrder.getTrackAndTrace().setCarrier((Carrier) carrier);
        sendNotification(localOrder);
        updateOrder(localOrder);
    }

    public void updateOrder(OrderInterface order) {
        orderService.update((Order) order);
    }


    public void addCarrier(CarrierDto carrierDto) throws NoAccessException {
        throw new NoAccessException();
    }

    public void toggleActivation(String id) throws NoAccessException {
        throw new NoAccessException();
    }

    public void removeCarrier(String id) throws NoAccessException {
        throw new NoAccessException();
    }

    public void updateCarrier(String id, CarrierDto carrierDto) throws NoAccessException, IllegalArgumentException {
        throw new NoAccessException();
    }


    private void sendNotification(Order order) {
        Company company = order.getCustomerCompany();
        Set<Notification> notifications = company.getNotifications();
        notifications.add(new Notification("Status Update", "Your order with order id " + order.getId() + " has been processed", LocalDateTime.now(), order, company));
        company.setNotifications(notifications);
    }

    public String calculateTotalOrderPrice(OrderInterface order) {
        double total = order.getOrderItems().stream().mapToDouble(item -> {
            var prices = new ArrayList<>(item.getProduct().getProductPrices());
            return prices.get(0).getPrice() * item.getQuantity();
        }).sum();

        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator('.');

        DecimalFormat df = new DecimalFormat("#.00", symbols);
        return "€" + df.format(total);
    }

    public Employee getEmployee() {
        return getEmployeeCompany().getEmployees().stream().filter(employee1 -> employee1.getId().equals(employee.getId())).findFirst().orElse(null);
    }

    public Company getEmployeeCompany() {
        return companyService.get(employee.getCompany().getId());
    }

    public Set<EmployeeInterface> getCompanyEmployees() throws NoAccessException {
        throw new NoAccessException();
    }

    public void makeAdmin(String id) throws NoAccessException, IllegalArgumentException {
        throw new NoAccessException();
    }

    public void updateEmployee(String id, EmployeeDto employeeDto) throws NoAccessException, IllegalArgumentException {
        throw new NoAccessException();
    }

    public void createEmployee(EmployeeDto employeeDto) throws NoAccessException {
        throw new NoAccessException();
    }

    public Set<BoxInterface> getBoxes() {
        return new HashSet<>(companyService.get(employee.getCompany().getId()).getBoxes());
    }

    public void updateBox(String name, BoxDto editedBox) throws NoAccessException {
        throw new NoAccessException();
    }

    public void addBox(BoxDto newBox) throws NoAccessException {
        throw new NoAccessException();
    }

    public void removeBox(String boxID) throws NoAccessException {
        throw new NoAccessException();
    }
}
