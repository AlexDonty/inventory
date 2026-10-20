package com.exam.inventory.dtos;

import java.sql.Timestamp;

public record ErrorResponseDto(
        Timestamp timestamp,
        int status,
        String message
) {
}
