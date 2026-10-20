package com.exam.inventory.dtos;

public record FileDto(
        int status,
        String message,
        String fileName,
        String fileBase64
) {
}
