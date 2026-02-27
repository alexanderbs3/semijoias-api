package br.leetjourney.semijoiasapi.core.enums;

public enum UserRole {
    USER("Usuário"),
    ADMIN("Administrador");

    private final String description;

    UserRole(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
