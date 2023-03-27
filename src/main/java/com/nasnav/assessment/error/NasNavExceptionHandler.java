package com.nasnav.assessment.error;

import static com.nasnav.assessment.strings.ExceptionMessages.MAX_UPLOAD_SIZE_EXCEEDED;
import static com.nasnav.assessment.strings.ExceptionMessages.SYSTEM_ERROR;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class NasNavExceptionHandler {

  private static final String CONTENT_TYPE_JSON = "application/json";
  private final MessageSource messageSource;
  private final ObjectMapper mapper;
  private final ErrorResponseGenerator errorResponseGenerator;


  @ExceptionHandler({Exception.class})
  public void handleAllExceptions(Exception e, HttpServletResponse response, Locale locale)
      throws IOException {
    handleExceptionsWithMessageSource(e, response, locale, INTERNAL_SERVER_ERROR,
        SYSTEM_ERROR);
  }

  @ExceptionHandler({ApplicationException.class})
  public void handleAllApplicationException(ApplicationException e, HttpServletResponse response,
      Locale locale)
      throws IOException {
    handleExceptionsWithMessageSource(e, response, locale, INTERNAL_SERVER_ERROR, null);
  }


  @ExceptionHandler({MaxUploadSizeExceededException.class})
  public void handleAllMaxUploadSizeExceededException(MaxUploadSizeExceededException e,
      HttpServletResponse response, Locale locale)
      throws IOException {
    handleExceptionsWithMessageSource(e, response, locale, BAD_REQUEST,
        MAX_UPLOAD_SIZE_EXCEEDED);
  }

  @ExceptionHandler({ImageTypeNotSupportedException.class})
  public void handleAllImageTypeNotSupportedException(ImageTypeNotSupportedException e,
      HttpServletResponse response, Locale locale)
      throws IOException {
    handleExceptionsWithMessageSource(e, response, locale, BAD_REQUEST, null);
  }

  @ExceptionHandler({UnAuthenticatedException.class})
  public void handleUnAuthenticatedException(UnAuthenticatedException e,
      HttpServletResponse response, Locale locale)
      throws IOException {
    handleExceptionsWithMessageSource(e, response, locale, UNAUTHORIZED, null);
  }

  @ExceptionHandler({MethodArgumentNotValidException.class})
  public final void handleMethodArgumentsNotValidException(MethodArgumentNotValidException ex,
      HttpServletResponse response, Locale locale) throws IOException {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach((error) -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });
    handleExceptions(ex, response, BAD_REQUEST, errors.toString(), BAD_REQUEST.name());
  }


  @ExceptionHandler({AccessDeniedException.class})
  private void handleAccessDeniedException(AccessDeniedException e, HttpServletResponse response)
      throws IOException {
    handleExceptions(e, response, UNAUTHORIZED, e.getMessage(), "ACCESS_DENIED");
  }


  private void handleExceptionsWithMessageSource(
      Exception e, HttpServletResponse response, Locale locale, HttpStatus status, String message)
      throws IOException {
    Throwable cause = e.getCause();
    if (cause == null) {
      cause = e;
    }
    String errorMessage;
    String errorCode;
    if (message == null) {
      errorMessage = messageSource.getMessage(cause.getMessage(), null, locale);
      errorCode = cause.getMessage();
    } else {
      errorMessage = messageSource.getMessage(message, null, locale);
      errorCode = message;
    }
    handleExceptions(e, response, status, errorMessage, errorCode);
  }

  private void handleExceptions(Exception e, HttpServletResponse response, HttpStatus status,
      String errorMessage, String errorCode)
      throws IOException {
    ErrorResponse err = errorResponseGenerator.generate(status, errorCode, errorMessage);
    byte[] errResponse = mapper.writeValueAsBytes(err);
    log.error("{}", err, e);
    response.setStatus(err.getStatusCode());
    response.setContentType(CONTENT_TYPE_JSON);
    response.getOutputStream().write(errResponse);
  }

}
