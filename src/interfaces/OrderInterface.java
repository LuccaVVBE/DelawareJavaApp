package interfaces;

import domain.Notification;

import java.time.LocalDateTime;
import java.util.Set;

public interface OrderInterface {
    String getId();

    BoxInterface getPackingType();

    LocalDateTime getOrderDate();

    AddressInterface getAddressInterface();

    CompanyInterface getCompanyInterface();

    CompanyInterface getCustomerCompanyInterface();

    Set<Notification> getNotifications();

    Set<OrderItemInterface> getOrderItems();

    TrackAndTraceInterface getTrackAndTrace();
}
