package dtos;

import domain.Address;
import domain.Box;
import domain.Company;
import domain.Order;

import java.time.LocalDateTime;

public record OrderDto(Box packagingType, LocalDateTime orderDate, Company company, Company customerCompany,
                       Address address) {

    public OrderDto(Order order) {
        this(order.getPackingType(), order.getOrderDate(), order.getCompany(), order.getCustomerCompany(), order.getAddress());

    }

}