package interfaces;

import java.util.Set;

public interface CompanyInterface extends CustomerInterface {
    String getId();

    String getName();

    String getImage();

    String getPhone();

    Set<AddressInterface> getAddressInterfaces();

    Set<OrderInterface> getOrderInterfaces();

    Set<OrderInterface> getPlacedOrders();

    Set<EmployeeInterface> getEmployeeInterfaces();

    Set<CarrierInterface> getCarrierInterfaces();

    Set<BoxInterface> getBoxInterfaces();
}
