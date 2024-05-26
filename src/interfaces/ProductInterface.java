package interfaces;

import domain.ProductPrice;

import java.util.Set;

public interface ProductInterface {
    String getId();

    int getStock();

    int getEta();

    String getLinkToImg();

    Set<ProductPrice> getProductPrices();

    double getPrice(String currencyId);

    String getName(String languageId);


}
