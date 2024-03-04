package ru.clevertec.logging.util;

import ru.clevertec.logging.annotation.Logging;

public interface ExampleService {

    Long exampleMethod(Long id);

    class ExampleServiceImpl implements ExampleService {

        @Override
        @Logging
        public Long exampleMethod(Long id) {
            return id;
        }
    }

}
