package com.leadauto.api.dto.response;

import java.math.BigDecimal;

public record PecaResponse(
        Long id,
        String nome,
        String descricao,
        BigDecimal preco,
        Integer estoque,
        String fabricante
) {}