package com.global.mazaad.common.exception.handler;

import java.util.stream.Stream;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

@Service
public class ErrorDebugMessageCreator {

    private static final String DESCRIPTION_TEMPLATE
            = "Operation was failed in method: %s that belongs to the class: %s. Problematic code line: %d";

    public String buildErrorDebugMessage(Exception exception) {
        StackTraceElement[] stackTrace = exception.getStackTrace();
        return Stream.of(stackTrace)
                .findFirst()
                .map(element -> DESCRIPTION_TEMPLATE.formatted(element.getMethodName(), element.getClassName(), element.getLineNumber()))
                .orElse(Strings.EMPTY);
    }
}
