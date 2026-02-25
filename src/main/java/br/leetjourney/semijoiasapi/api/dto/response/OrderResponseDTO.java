package br.leetjourney.semijoiasapi.api.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponseDTO(
        Long id,
        String status,
        BigDecimal subtotal,
        BigDecimal shippingCost,
        BigDecimal total,
        LocalDateTime createdAt,
        List<OrderItemResponseDTO> items
) {
}
