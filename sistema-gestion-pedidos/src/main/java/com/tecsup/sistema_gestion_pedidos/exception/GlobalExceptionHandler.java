package com.tecsup.sistema_gestion_pedidos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

  // Recurso no encontrado (404)
  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<Map<String, Object>> handleResourceNotFound(ResourceNotFoundException ex) {
    Map<String, Object> error = new HashMap<>();
    error.put("mensaje", ex.getMessage());
    error.put("timestamp", LocalDateTime.now().toString());
    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
  }

  // Stock insuficiente (400)
  @ExceptionHandler(StockInsuficienteException.class)
  public ResponseEntity<Map<String, Object>> handleStockInsuficiente(StockInsuficienteException ex) {
    Map<String, Object> error = new HashMap<>();
    error.put("mensaje", ex.getMessage());
    error.put("timestamp", LocalDateTime.now().toString());
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  // Errores de validación (400)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
    Map<String, Object> errors = new HashMap<>();
    Map<String, String> fieldErrors = new HashMap<>();

    ex.getBindingResult().getAllErrors().forEach((error) -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      fieldErrors.put(fieldName, errorMessage);
    });

    errors.put("errores", fieldErrors);
    errors.put("mensaje", "Error de validación");
    errors.put("timestamp", LocalDateTime.now().toString());
    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
  }

  // RuntimeException general (400)
  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<Map<String, Object>> handleRuntimeException(RuntimeException ex) {
    Map<String, Object> error = new HashMap<>();
    error.put("mensaje", ex.getMessage());
    error.put("timestamp", LocalDateTime.now().toString());
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  // Excepciones generales (500)
  @ExceptionHandler(Exception.class)
  public ResponseEntity<Map<String, Object>> handleGeneralException(Exception ex) {
    Map<String, Object> error = new HashMap<>();
    error.put("mensaje", "Error interno del servidor: " + ex.getMessage());
    error.put("timestamp", LocalDateTime.now().toString());
    return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}