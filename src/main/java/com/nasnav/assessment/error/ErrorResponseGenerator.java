package com.nasnav.assessment.error;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ErrorResponseGenerator {

    public ErrorResponse generate(HttpStatus status, String errorCode, String errorMessage) {
        var err = new ErrorResponse(status.value(), errorCode, errorMessage);
        return err;
    }
}
