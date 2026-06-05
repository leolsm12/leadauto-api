package com.leadauto.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

public record ServicoRequest(
        @NotNull(message = "Cliente é obrigatório")
        Long clienteId,

        List<Long> pecaIds,

        @NotBlank(message = "Descrição é obrigatória")
        String descricao,

        BigDecimal valorMaoObra
) {}