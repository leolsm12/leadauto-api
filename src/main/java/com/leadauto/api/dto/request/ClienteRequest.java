package com.leadauto.api.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ClienteRequest(
        @NotBlank(message = "Nome é obrigatório")
        String nome,

        @NotBlank(message = "Telefone é obrigatório")
        String telefone,

        String email,
        String cpf
) {}