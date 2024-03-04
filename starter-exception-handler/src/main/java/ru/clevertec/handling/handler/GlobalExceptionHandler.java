package ru.clevertec.handling.handler;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.clevertec.handling.exception.ApplicationException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ApplicationException.class})
    public ResponseEntity<ExceptionMessage> handleApplicationException(HttpServletRequest request,
                                                                       ApplicationException ex) {
        int code = Integer.parseInt(ex.getCode().substring(0, 3));
        HttpStatus httpStatus = HttpStatus.valueOf(code);
        ExceptionMessage exceptionMessage = new ExceptionMessage(LocalDateTime.now(),
                code,
                httpStatus.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI());
        return new ResponseEntity<>(exceptionMessage, httpStatus);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionMessage> handleValidationException(
            HttpServletRequest request,
            Exception ex
    ) {
        ExceptionMessage response = new ExceptionMessage(
                LocalDateTime.now(),
                400,
                "Bad Request",
                "Arguments are not valid",
                request.getRequestURI()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionMessage> handleOtherExceptions(
            HttpServletRequest request,
            Exception ex
    ) {
        ExceptionMessage response = new ExceptionMessage(
                LocalDateTime.now(),
                400,
                "Bad Request",
                ex.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
