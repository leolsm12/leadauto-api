package com.leadauto.api.dto.response;

import java.time.LocalDateTime;

public record ClienteResponse(
        Long id,
        String nome,
        String telefone,
        String email,
        String cpf,
        LocalDateTime criadoEm
) {}