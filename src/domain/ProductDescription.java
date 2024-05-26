package domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Getter
@Setter
@IdClass(ProductDescription.ProductDescriptionId.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductDescription implements Serializable {

    @Id
    private String languageId;
    @Id
    private String productName;
    private String productShortDescription;
    private String productLongDescription;

    private String productId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "productId", referencedColumnName = "id", insertable = false, updatable = false)
    private Product product;

    public ProductDescription(String languageId, String productName, String productShortDescription, String productLongDescription, Product product) {
        this.languageId = languageId;
        this.productName = productName;
        this.productShortDescription = productShortDescription;
        this.productLongDescription = productLongDescription;
        this.product = product;
//        if (product != null && product.getId() != null) {
//            this.productId = product.getId();
//        }

        this.productId = product.getId();

    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductDescription that = (ProductDescription) o;
        return Objects.equals(languageId, that.languageId) && Objects.equals(productName, that.productName) && Objects.equals(productShortDescription, that.productShortDescription) && Objects.equals(productLongDescription, that.productLongDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(languageId, productName, productShortDescription, productLongDescription);
    }

    @Override
    public String toString() {
        return "ProductDescription{" +
                "languageId='" + languageId + '\'' +
                ", productName='" + productName + '\'' +
                ", productShortDescription='" + productShortDescription + '\'' +
                ", productLongDescription='" + productLongDescription + '\'' +
                ", productId='" + productId + '\'' +
                '}';
    }

    public static class ProductDescriptionId implements Serializable {

        private String productId;
        private String languageId;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            ProductDescriptionId that = (ProductDescriptionId) o;

            if (!Objects.equals(productId, that.productId)) return false;
            return Objects.equals(languageId, that.languageId);
        }

        @Override
        public int hashCode() {
            int result = productId != null ? productId.hashCode() : 0;
            result = 31 * result + (languageId != null ? languageId.hashCode() : 0);
            return result;
        }
    }

}
