package dtos;

import domain.Address;
import domain.Notification;
import domain.Order;

import java.util.Set;

public record CompanyDto(String name, String image, String phone, Set<Address> addresses, Set<Order> orders,
                         Set<Notification> notifications) {
}
