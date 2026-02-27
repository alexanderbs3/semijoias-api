    package br.leetjourney.semijoiasapi.api.dto.response;

    public record LoginResponseDTO(
            String token,
            String name,
            String role
    ) {
    }
