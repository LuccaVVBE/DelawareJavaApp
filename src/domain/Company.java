package domain;

import dtos.CompanyDto;
import interfaces.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Company implements Serializable, CompanyInterface {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "util.StringUUIDGenerator")
    private String id;

    private String name;
    private String image;
    private String phone;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Address> addresses;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Order> orders;

    @OneToMany(mappedBy = "customerCompany", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Order> placedOrders;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Notification> notifications;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Employee> employees;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "company_carrier",
            joinColumns = @JoinColumn(name = "company_id"),
            inverseJoinColumns = @JoinColumn(name = "carrier_id"))
    private Set<Carrier> carriers;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<Box> boxes;
    @OneToMany(mappedBy = "customerCompany")
    private Collection<Order> order;

    public Company(String name, String image, String phone, Set<Address> addresses, Set<Order> orders, Set<Notification> notifications, Set<Carrier> carriers) {
        this.name = name;
        this.image = image;
        this.phone = phone;
        this.addresses = addresses;
        this.orders = orders;
        this.notifications = notifications;
        this.carriers = carriers;
    }

    public Company(CompanyDto companyDto) {
        this.name = companyDto.name();
        this.image = companyDto.image();
        this.phone = companyDto.phone();
        this.addresses = companyDto.addresses();
        this.orders = companyDto.orders();
        this.notifications = companyDto.notifications();
    }

    @Override
    public String toString() {
        return "Company{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", addresses=" + addresses +
                ", orders=" + orders +
//                ", notifications=" + notifications +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return Objects.equals(name, company.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public Collection<Order> getOrder() {
        return order;
    }

    public void setOrder(Collection<Order> order) {
        this.order = order;
    }

    public void addBox(Box box) {
        if (this.boxes == null) {
            this.boxes = new HashSet<>();
        }
        this.boxes.add(box);
    }

    @Override
    public Set<AddressInterface> getAddressInterfaces() {
        return new HashSet<>(addresses);
    }

    @Override
    public Set<OrderInterface> getPlacedOrders() {
        return new HashSet<>(this.placedOrders);
    }

    @Override
    public Set<OrderInterface> getOrderInterfaces() {
        return new HashSet<>(this.orders);
    }

    @Override
    public Set<EmployeeInterface> getEmployeeInterfaces() {
        return new HashSet<>(this.employees);
    }

    @Override
    public Set<CarrierInterface> getCarrierInterfaces() {
        return new HashSet<>(this.carriers);
    }

    @Override
    public Set<BoxInterface> getBoxInterfaces() {
        return new HashSet<>(this.boxes);
    }
}
