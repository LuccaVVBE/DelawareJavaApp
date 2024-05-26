package domain;

import enums.OrderStatus;
import interfaces.TrackAndTraceInterface;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import util.CuidGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class TrackAndTrace implements Serializable, TrackAndTraceInterface {
    private String number;
    private String verificationCode;

    private OrderStatus status;

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "util.StringUUIDGenerator")
    private String id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "orderId", referencedColumnName = "orderId")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "carrierId", referencedColumnName = "id")
    private Carrier carrier;

    public TrackAndTrace(Order order) {
        this.order = order;
        this.status = OrderStatus.RECEIVED;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrackAndTrace that = (TrackAndTrace) o;
        return Objects.equals(number, that.number) && Objects.equals(verificationCode, that.verificationCode) && status == that.status && Objects.equals(order, that.order);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, verificationCode, status, order);
    }

    public void setCarrier(Carrier carrier) {
        this.carrier = carrier;
        var num = carrier.getPrefix();
        var len = carrier.getAmountOfCharacters();
        if (carrier.isNumOnly()) {
            num += CuidGenerator.getRandomNumber(len - num.length());
        } else {
            num += CuidGenerator.getRandomString(len - num.length());
        }
        this.number = num;
        this.verificationCode = CuidGenerator.getRandomString(6);
    }

    @Override
    public String toString() {
        return "TrackAndTrace{" +
                "number='" + number + '\'' +
                ", verificationCode='" + verificationCode + '\'' +
                ", status='" + status + '\'' +
                ", id='" + id + '\'' +
                ", order=" + order +
                '}';
    }
}