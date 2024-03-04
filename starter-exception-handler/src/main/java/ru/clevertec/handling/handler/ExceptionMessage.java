package ru.clevertec.handling.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class ExceptionMessage {

    private LocalDateTime timestamp;
    private int code;
    private String status;
    private String message;
    private String path;

    public ExceptionMessage(LocalDateTime timestamp, int code, String message, String path) {
        this.timestamp = timestamp;
        this.code = code;
        this.message = message;
        this.path = path;
    }

    public ExceptionMessage(LocalDateTime timestamp, int code, String status,  String message, String path) {
        this.timestamp = timestamp;
        this.code = code;
        this.status = status;
        this.message = message;
        this.path = path;
    }

}
