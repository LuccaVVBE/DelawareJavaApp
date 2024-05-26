package domain;

import interfaces.ProductPriceInterface;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@IdClass(ProductPrice.ProductPriceId.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductPrice implements Serializable, ProductPriceInterface {
    @Id
    private String currencyId;
    @Id
    private String unitOfMeasure;
    private Float price;
    private LocalDateTime syncDateTime;
    @Id
    private String productId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "productId", referencedColumnName = "id", insertable = false, updatable = false)
    private Product product;

    public ProductPrice(String currencyId, String unitOfMeasure, Float price, Product product) {
        this.currencyId = currencyId;
        this.unitOfMeasure = unitOfMeasure;
        this.price = price;
        this.product = product;
        this.productId = product.getId();

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductPrice that = (ProductPrice) o;
        return Objects.equals(currencyId, that.currencyId) && Objects.equals(price, that.price) && Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currencyId, price, product.getId());
    }

    @Override
    public String toString() {
        return "ProductPrice{" +
                "currencyId='" + currencyId + '\'' +
                ", unitOfMeasure='" + unitOfMeasure + '\'' +
                ", price=" + price +
                ", syncDateTime=" + syncDateTime +
                ", productId='" + productId + '\'' +
                ", product=" + product.getId() +
                '}';
    }

    public static class ProductPriceId implements Serializable {

        String productId;
        private String currencyId;
        private String unitOfMeasure;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ProductPriceId that = (ProductPriceId) o;
            return Objects.equals(productId, that.productId) && Objects.equals(currencyId, that.currencyId) && Objects.equals(unitOfMeasure, that.unitOfMeasure);
        }

        @Override
        public int hashCode() {
            return Objects.hash(productId, currencyId, unitOfMeasure);
        }
    }


}

