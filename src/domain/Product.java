package domain;

import interfaces.ProductInterface;
import lombok.Getter;
import lombok.Setter;
import util.CuidGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
@NamedQueries(
        {
                @NamedQuery(name = "Product.findProductPrices",
                        query = "select p.productPrices from Product p where p.id = :productId"),
                @NamedQuery(name = "Product.findProductDescriptions",
                        query = "select p.productDescriptions from Product p where p.id = :productId"),
                @NamedQuery(name = "Product.findProductCategories",
                        query = "select p.productCategories from Product p where p.id = :productId"),
        }
)
public class Product implements Serializable, ProductInterface {

    int stock;
    int eta;
    String linkToImg;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    Set<ProductPrice> productPrices;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    Set<ProductDescription> productDescriptions;
    @Id
    private String id;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "producttocategory",
            joinColumns = @JoinColumn(name = "productId"),
            inverseJoinColumns = @JoinColumn(name = "categoryId"))
    private Set<ProductCategory> productCategories;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<OrderItem> orderItems;

    public Product(int stock, int eta, Set<ProductPrice> productPrices, Set<ProductDescription> productDescriptions, Set<ProductCategory> productCategories) {
        this.stock = stock;
        this.eta = eta;
        this.productPrices = productPrices;
        this.productDescriptions = productDescriptions;
        this.productCategories = productCategories;
        this.id = CuidGenerator.createCuid();
    }

    public Product() {
        this.id = CuidGenerator.createCuid();
    }


    public String getName(String languageId) {
        return productDescriptions.stream().filter(productDescription -> productDescription.getLanguageId().equals(languageId)).findFirst().get().getProductName();
    }

    public String getDescription(String languageId) {
        return productDescriptions.stream().filter(productDescription -> productDescription.getLanguageId().equals(languageId)).findFirst().get().getProductShortDescription();
    }

    // get price
    @Override
    public double getPrice(String currencyId) {
        return this.productPrices.stream().filter(productPrice -> productPrice.getCurrencyId().equals(currencyId)).findFirst().get().getPrice();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(productDescriptions, product.productDescriptions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productDescriptions, productPrices);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", stock=" + stock +
                ", eta=" + eta +
                ", linkToImg='" + linkToImg + '\'' +
                ", productPrices=" + productPrices +
                ", productDescriptions=" + productDescriptions +
//                ", productCategories=" + productCategories.stream().map(ProductCategory::getCategoryName).collect(Collectors.joining()) +
                '}';
    }
}
