package com.leadauto.api.dto.response;

import com.leadauto.api.model.entity.StatusServico;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record ServicoResponse(
        Long id,
        ClienteResponse cliente,
        List<PecaResponse> pecas,
        String descricao,
        BigDecimal valorMaoObra,
        StatusServico status,
        LocalDateTime criadoEm,
        LocalDateTime atualizadoEm
) {}