package domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductCategory implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "util.StringUUIDGenerator")
    private String categoryId;
    private String categoryName;

    @ManyToMany(mappedBy = "productCategories", fetch = FetchType.EAGER)
    private Set<Product> products;

    public ProductCategory(String categoryName, Set<Product> products) {
        this.categoryName = categoryName;
        this.products = products;
    }

    @Override
    public String toString() {
        return "ProductCategory{" +
                "categoryId='" + categoryId + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", products=" + products +
                '}';
    }
}
