package util;

import enums.EmployeeRole;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class EmployeeRoleConverter implements AttributeConverter<EmployeeRole, String> {


    @Override
    public String convertToDatabaseColumn(EmployeeRole employeeRole) {
        if (employeeRole == null) {
            return null;
        }
        return employeeRole.getDisplayName();
    }

    @Override
    public EmployeeRole convertToEntityAttribute(String s) {
        if (s == null) {
            return null;
        }

        return Stream.of(EmployeeRole.values()).filter(er -> er.getDisplayName().equals(s)).findFirst().orElseThrow(() -> new IllegalArgumentException(s + " is not a valid role. Valid employee roles are " + EmployeeRole.values() + "."));
    }
}
