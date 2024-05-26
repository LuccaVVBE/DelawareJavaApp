package domain;

import interfaces.OrderItemInterface;
import lombok.AccessLevel;
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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem implements Serializable, OrderItemInterface {

    @Id
    @Column(name = "orderItemId")
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "util.StringUUIDGenerator")
    private String id;

    private String boxId;
    private int quantity;

    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "orderId")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;

    @Transient
    private String productId;

    public OrderItem(int quantity, Order order, Product product) {
        this.quantity = quantity;
        this.order = order;
        this.product = product;
        if (product != null)
            this.productId = product.getId();

    }

    public void setProduct(Product product) {
        this.product = product;
        this.productId = product.getId();
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "quantity=" + quantity +
                ", id='" + id + '\'' +
                ", boxId='" + boxId + '\'' +
                ", order=" + order.getId() +
                ", product=" + product.getName("en") +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return quantity == orderItem.quantity && Objects.equals(order, orderItem.order) && Objects.equals(productId, orderItem.getProductId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(quantity, order, productId);
    }
}