package io.github.franzli347.exception;


import io.github.franzli347.result.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
@Slf4j
@Order()
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> exceptionHandler(Exception e) {
        log.error("exception", e);
        return ResponseEntity.internalServerError().body(e.getMessage());
    }

    @ExceptionHandler({PermissionException.class,LoginStatusException.class})
    public ResponseEntity<Object> permissionExceptionHandler(BusinessException e) {
        log.error("exception", e);
        return ResponseEntity.status(403).body(ResponseResult.error(e.getMessage()));
    }
}
