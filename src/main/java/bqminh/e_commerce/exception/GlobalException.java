package bqminh.e_commerce.exception;

import bqminh.e_commerce.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;


@ControllerAdvice
public class GlobalException {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<List<String>>>handleValidationErrors(MethodArgumentNotValidException e){
        List<String>errors=e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();
        ApiResponse<List<String>>response=new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "Validation failed",errors);
        return ResponseEntity.badRequest().body(response);
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<String>>handleRuntimeException(RuntimeException e){
        ApiResponse<String>response=new ApiResponse<>(HttpStatus.BAD_REQUEST.value(),e.getMessage(),null);
        return ResponseEntity.badRequest().body(response);
    }
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<String>>handleAccessDeniedException(AccessDeniedException e){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponse<>(HttpStatus.FORBIDDEN.value(), "FORBIDDEN",null));
    }
}
