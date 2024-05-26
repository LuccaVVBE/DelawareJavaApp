package interfaces;

public interface OrderItemInterface {
    public String getId();

    public String getBoxId();

    public int getQuantity();

    public OrderInterface getOrder();

    public ProductInterface getProduct();

    public String getProductId();

}
