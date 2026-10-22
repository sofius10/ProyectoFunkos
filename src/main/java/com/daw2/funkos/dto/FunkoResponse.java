package com.daw2.funkos.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record FunkoResponse(
        Long id,
        String nombre,
        BigDecimal precio,
        Integer cantidad,
        String categoria,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}