package br.leetjourney.semijoiasapi.api.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductRequestDTO(
        @NotBlank String name,
        String description,
        @NotNull @Min(0) BigDecimal price,
        @NotNull @Min(0) Integer stockQuantity,
        String imageUrl,
        @NotNull Long categoryId
) {
}
