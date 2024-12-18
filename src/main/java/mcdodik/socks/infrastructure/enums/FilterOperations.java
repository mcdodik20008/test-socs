package mcdodik.socks.infrastructure.enums;

public enum FilterOperations {

    EQUAL("eq"),
    LESS_THEN("less"),
    MORE_THEN("more");

    private final String name;


    FilterOperations(String name) {
        this.name = name;
    }

    public static FilterOperations getByName(String name) {
        for (FilterOperations operation : values()) {
            if (operation.name.equalsIgnoreCase(name)) {
                return operation;
            }
        }
        throw new UnsupportedOperationException("Не поддерживаемая операция сравнения: " + name);
    }
}
