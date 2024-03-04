package ru.clevertec.handling.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ApplicationException extends RuntimeException {

    private final String code;
    private final String message;

}