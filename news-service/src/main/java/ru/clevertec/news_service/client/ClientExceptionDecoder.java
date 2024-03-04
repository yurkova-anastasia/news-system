package ru.clevertec.news_service.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import ru.clevertec.handling.exception.ApplicationException;
import ru.clevertec.handling.handler.ExceptionMessage;

import java.io.InputStream;

/**
 * A Feign {@link ErrorDecoder} that decodes exceptions from the server.
 *
 * @author Yurkova Anastasia
 */
@Component
@RequiredArgsConstructor
public class ClientExceptionDecoder implements ErrorDecoder {

    private final ObjectMapper mapper;

    /**
     * Decodes the exception from the Feign response.
     *
     * @param methodKey the method key
     * @param response  the Feign response
     * @return the exception
     */
    @Override
    @SneakyThrows
    public Exception decode(String methodKey, Response response) {
        try (InputStream inputStream = response.body().asInputStream();) {
            ExceptionMessage exceptionMessage = mapper.readValue(inputStream, ExceptionMessage.class);
            return new ApplicationException(String.valueOf(exceptionMessage.getCode()),
                    exceptionMessage.getMessage());
        }
    }
}
