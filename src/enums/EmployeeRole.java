package enums;

public enum EmployeeRole {

    ADMIN("Admin"),
    WAREHOUSE_EMPLOYEE("Warehouse Employee");
    private final String displayName;

    EmployeeRole(String name) {
        this.displayName = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
