package com.exam.inventory.config;

import com.exam.inventory.common.exception.ConflictException;
import com.exam.inventory.common.exception.NotFoundException;
import com.exam.inventory.dtos.ErrorResponseDto;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.Timestamp;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidDataAccessResourceUsageException.class)
    public ResponseEntity<ErrorResponseDto> handleInvalidDataAccess(
            InvalidDataAccessResourceUsageException ex) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponseDto(
                        new Timestamp(System.currentTimeMillis()),
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        ex.getMessage()
                ));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> badResquestMethodValidator (MethodArgumentNotValidException e){
        List<String> messages = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .toList();

        return new ResponseEntity<>(new ErrorResponseDto(
                new Timestamp(System.currentTimeMillis()),
                HttpStatus.BAD_REQUEST.value(),
                String.join(", ", messages)
        ), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponseDto> notFoundException (NotFoundException e){
        return new ResponseEntity<>(new ErrorResponseDto(
                new Timestamp(System.currentTimeMillis()),
                HttpStatus.NOT_FOUND.value(),
                e.getMessage()
        ), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponseDto> conflictException (ConflictException e){
        return new ResponseEntity<>(new ErrorResponseDto(
                new Timestamp(System.currentTimeMillis()),
                HttpStatus.CONFLICT.value(),
                e.getMessage()
        ), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorResponseDto> dataAccessException (DataAccessException e){
        return new ResponseEntity<>(new ErrorResponseDto(
                new Timestamp(System.currentTimeMillis()),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.getRootCause().getMessage()
        ), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
