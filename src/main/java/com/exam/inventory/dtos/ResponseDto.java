package com.exam.inventory.dtos;

public record ResponseDto(
        int status,
        String message
){
    public ResponseDto(String message) {
        this(200, message);
    }
}

