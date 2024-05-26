package interfaces;

import enums.OrderStatus;

public interface TrackAndTraceInterface {

    String getNumber();

    String getVerificationCode();

    OrderStatus getStatus();

    String getId();

    OrderInterface getOrder();

    CarrierInterface getCarrier();
}
