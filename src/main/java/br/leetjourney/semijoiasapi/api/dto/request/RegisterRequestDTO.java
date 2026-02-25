package br.leetjourney.semijoiasapi.api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequestDTO(
        @NotBlank String name,
        @NotBlank @Email String email,
        @NotBlank @Size(min = 6) String password,
        @NotBlank String address,
        @NotBlank String city,
        @NotBlank String state,
        @NotBlank String zipCode
) {
}
