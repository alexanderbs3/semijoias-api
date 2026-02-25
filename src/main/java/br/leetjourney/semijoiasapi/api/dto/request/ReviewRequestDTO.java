package br.leetjourney.semijoiasapi.api.dto.request;

import jakarta.validation.constraints.*;

public record ReviewRequestDTO(
        @NotNull Long productId,
        @NotNull @Min(1) @Max(5) Integer rating,
        @NotBlank @Size(max = 500) String comment
) {
}
