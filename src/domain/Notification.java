package domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Setter
@Getter
@NoArgsConstructor
//@Table(name = "notification")
public class Notification implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "util.StringUUIDGenerator")
    private String id;
    private String title;
    private String description;
    @Column(name = "is_read")
    private Boolean isRead;
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "orderId")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "companyId")
    private Company company;

    public Notification(String title, String description, LocalDateTime createdAt, Order order, Company company) {
        this.title = title;
        this.description = description;
        this.isRead = false;
        this.createdAt = createdAt;
        this.order = order;
        this.company = company;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", isRead=" + isRead +
                ", createdAt=" + createdAt +
                ", order=" + order.getId() +
                ", company=" + company.getId() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notification that = (Notification) o;
        return Objects.equals(createdAt, that.createdAt) && Objects.equals(order, that.order) && Objects.equals(company, that.company);
    }

    @Override
    public int hashCode() {
        return Objects.hash(createdAt, order, company);
    }
}
