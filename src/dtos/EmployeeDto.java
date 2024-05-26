package dtos;

import enums.EmployeeRole;

public record EmployeeDto(String firstname, String lastname, String phone, String email, EmployeeRole role
) {
}
