package util;

import enums.OrderStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class OrderStatusConverter implements AttributeConverter<OrderStatus, String> {

    @Override
    public String convertToDatabaseColumn(OrderStatus category) {
        if (category == null) {
            return null;
        }
        return category.getDisplayName();
    }

    @Override
    public OrderStatus convertToEntityAttribute(String name) {
        if (name == null) {
            return null;
        }

        return Stream.of(OrderStatus.values()).filter(c -> c.getDisplayName().equals(name)).findFirst().orElseThrow(() -> new IllegalArgumentException(name + " is not a valid status. Valid status are " + OrderStatus.values() + "."));
    }
}