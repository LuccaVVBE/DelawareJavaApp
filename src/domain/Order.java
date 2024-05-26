package domain;

import dtos.OrderDto;
import interfaces.AddressInterface;
import interfaces.CompanyInterface;
import interfaces.OrderInterface;
import interfaces.OrderItemInterface;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order implements Serializable, OrderInterface {

    @Id
    @Column(name = "orderId")
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "util.StringUUIDGenerator")
    private String id;

    @ManyToOne(cascade=CascadeType.PERSIST)
    private Box packingType;

    private LocalDateTime orderDate;

    @ManyToOne
    @JoinColumn(name = "addressId")
    private Address address;

    @ManyToOne
//    @JoinColumn(name = "companyId")
    private Company company;

    @ManyToOne
//    @JoinColumn(name = "customerCompanyId")
    private Company customerCompany;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<Notification> notifications;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<OrderItem> orderItems;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private TrackAndTrace trackAndTrace;

    public Order(Box packagingType, LocalDateTime orderDate, Company company, Company customerCompany, Address address) {
        this.packingType = packagingType;
        this.orderDate = orderDate;
        this.company = company;
        this.customerCompany = customerCompany;
        this.address = address;
    }

    public Order(OrderDto orderDto) {
        this.packingType = orderDto.packagingType();
        this.orderDate = orderDto.orderDate();
        this.company = orderDto.company();
        this.customerCompany = orderDto.customerCompany();
        this.address = orderDto.address();
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", packingType='" + packingType + '\'' +
                ", orderDate=" + orderDate +
                ", address=" + address.getName() +
                ", company=" + company.getName() +
                ", customerCompany=" + customerCompany.getName() +
                ", notifications=" + notifications +
                ", orderItems=" + orderItems +
                ", trackAndTrace=" + trackAndTrace.getStatus() != null ? trackAndTrace.getStatus().toString() : "No Track&Trace" +
                '}';
    }

    @Override
    public AddressInterface getAddressInterface() {
        return this.address;
    }

    @Override
    public CompanyInterface getCompanyInterface() {
        return this.company;
    }


    @Override
    public CompanyInterface getCustomerCompanyInterface() {
        return this.customerCompany;
    }

    @Override
    public Set<OrderItemInterface> getOrderItems() {
        return new HashSet<>(this.orderItems);
    }
}

