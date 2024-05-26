package domain;

import interfaces.AddressInterface;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;


@Entity
@Getter
@Setter
@NamedQueries({
        @NamedQuery(name = "Address.findByCompanyId",
                query = "select a from Address a where a.company.id = :companyId")
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address implements Serializable, AddressInterface {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String street;
    private String city;
    private String zipCode;
    private String country;
    private String number;

    @ManyToOne
    @JoinColumn(name = "companyId")
    private Company company;
    //
    @OneToMany(mappedBy = "address", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<Order> orders;

    public Address(String name, String street, String number, String city, String zipCode, String country) {
        this.name = name;
        this.street = street;
        this.city = city;
        this.zipCode = zipCode;
        this.country = country;
        this.number = number;
    }

    //
    @Override
    public String toString() {
        String companyName = (company == null || company.getName() == null) ? null : company.getName();

        return "Address{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", country='" + country + '\'' +
                ", number='" + number + '\'' +
                ", company=" + companyName +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(name, address.name) && Objects.equals(street, address.street) && Objects.equals(city, address.city) && Objects.equals(zipCode, address.zipCode) && Objects.equals(country, address.country) && Objects.equals(number, address.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, street, city, zipCode, country, number);
    }


}
