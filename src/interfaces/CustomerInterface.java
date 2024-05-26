package interfaces;

import java.util.Set;

public interface CustomerInterface {

    public String getId();

    public String getName();

    public String getImage();

    public String getPhone();

    public Set<AddressInterface> getAddressInterfaces();

    public Set<OrderInterface> getPlacedOrders();

    public Set<EmployeeInterface> getEmployeeInterfaces();

    public Set<CarrierInterface> getCarrierInterfaces();
}
