package domain;

import dtos.BoxDto;
import enums.BoxType;
import interfaces.BoxInterface;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"name", "width", "height", "depth", "boxType", "isActive", "price", "company"})
public class Box implements Serializable, BoxInterface {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "util.StringUUIDGenerator")
    private String id;

    private String name;

    private double width;
    private double height;
    private double depth;
    private String boxType;
    private boolean isActive;
    private double price;

    @ManyToOne
    private Company company;

    public Box(String name, double width, double height, double depth, BoxType boxType, boolean active, double price, Company company) {
        this.name = name;
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.boxType = boxType.toString();
        this.isActive = active;
        setPrice(price);
        this.company = company;

    }

    public Box(BoxDto boxDto) {
        this.name = boxDto.name();
        this.width = boxDto.width();
        this.height = boxDto.height();
        this.depth = boxDto.depth();
        this.boxType = boxDto.boxType().toString();
        this.isActive = boxDto.active();
        setPrice(boxDto.price());
    }

    @Override
    public String toString() {
        return "Box{" +
                " name='" + name + '\'' +
                ", width='" + width + '\'' +
                ", height='" + height + '\'' +
                ", depth=" + depth +
                ", price='" + price + '\'' +
                ", active='" + isActive + '\'' +
                ", type='" + boxType +
                "'}";
    }

    public void setPrice(double price) {
        this.price = new BigDecimal(price).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }


}