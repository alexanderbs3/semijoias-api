package br.leetjourney.semijoiasapi.api.dto.response;

import java.math.BigDecimal;

public record ProductResponseDTO(
        Long id,
        String name,
        String description,
        BigDecimal price,
        Integer stockQuantity,
        String imageUrl,
        Double averageRating,
        Integer reviewCount,
        Long categoryId,
        String categoryName
) {
}
