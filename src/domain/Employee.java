package domain;

import enums.EmployeeRole;
import interfaces.EmployeeInterface;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@NamedQueries({
        @NamedQuery(name = "Employee.findByEmail",
                query = "select e from Employee e where e.email = :email")
})
public class Employee implements Serializable, EmployeeInterface {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "util.StringUUIDGenerator")
    private String id;

    private String firstName;
    private String lastName;
    private String phone;
    private EmployeeRole role;
    private String password;
    private String email;
    @ManyToOne
    private Company company;


    public Employee(String firstName, String lastName, EmployeeRole role, String password, String email, Company company) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.password = password;
        this.email = email;
        this.company = company;
    }

    public Employee(String firstName, String lastName, String phone, String email, EmployeeRole role, Company company) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.password = password;
        this.email = email;
        this.company = company;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(email, employee.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
