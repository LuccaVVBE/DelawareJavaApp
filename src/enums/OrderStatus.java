package enums;

public enum OrderStatus {
    // geplaatst, verwerkt, verzonden, uit voor levering, geleverd
    RECEIVED("Received"),
    PROCESSED("Processed"),
    SHIPPED("Shipped"),
    IN_TRANSIT("In transit"),
    DELIVERED("Delivered");

    private final String displayName;

    OrderStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
