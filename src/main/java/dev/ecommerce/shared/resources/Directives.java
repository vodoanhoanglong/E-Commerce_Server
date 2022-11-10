package dev.ecommerce.shared.resources;

public enum Directives {
    HasRoles("hasRoles", "roles");

    private final String name;
    private final String firstArg;

    Directives(String name, String firstArg) {
        this.name = name;
        this.firstArg = firstArg;
    }

    public String getName() {
        return name;
    }

    public String getFirstArg() {
        return firstArg;
    }
}
