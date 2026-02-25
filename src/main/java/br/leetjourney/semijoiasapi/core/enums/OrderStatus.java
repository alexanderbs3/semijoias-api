package br.leetjourney.semijoiasapi.core.enums;

public enum OrderStatus {
    PENDING("Pendente"),
    PROCESSING("Em Processamento"),
    SHIPPED("Enviado"),
    DELIVERED("Entregue"),
    CANCELLED("Cancelado");

    private final String description;

    OrderStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
