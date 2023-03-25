package com.nasnav.assessment.error;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ErrorResponseGenerator {

    public ErrorResponse generate(HttpStatus status, String errorCode, String errorMessage) {
        return generate(status, errorCode, errorMessage, null);
    }

    public ErrorResponse generate(HttpStatus status, String errorCode, String errorMessage,
        List<ValidationDetail> validationDetails) {
        var err = new ErrorResponse(status.value(), errorCode, errorMessage, validationDetails);
        return err;
    }
}
