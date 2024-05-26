package interfaces;

import enums.EmployeeRole;

public interface EmployeeInterface {
    String getId();

    String getFirstName();

    String getLastName();

    EmployeeRole getRole();

    String getEmail();

    CompanyInterface getCompany();
}
