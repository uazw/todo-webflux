package io.github.uazw.todo.handler;

import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class ErrorResponse {
  private final String code;
  private final String message;
  private final String target;
  private final List<ErrorResponseDetail> details;

  public ErrorResponse(Errors errors) {
    this("Bad Request", "validation error", errors.getObjectName(),
        errors.getFieldErrors()
            .stream().map(ErrorResponse.ErrorResponseDetail::new)
            .collect(toList()));
  }

  private ErrorResponse(String code, String message, String target, List<ErrorResponseDetail> details) {
    this.code = code;
    this.message = message;
    this.target = target;
    this.details = details;
  }

  public String getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }

  public String getTarget() {
    return target;
  }

  public List<ErrorResponseDetail> getDetails() {
    return details;
  }

  public static class ErrorResponseDetail {
    private String code;
    private String target;
    private String message;

    public ErrorResponseDetail(FieldError fieldError) {
      this.code = fieldError.getCode();
      this.target = fieldError.getField();
      this.message = fieldError.getDefaultMessage();
    }

    public ErrorResponseDetail(String code, String target, String message) {
      this.code = code;
      this.target = target;
      this.message = message;
    }

    public String getCode() {
      return code;
    }

    public String getTarget() {
      return target;
    }

    public String getMessage() {
      return message;
    }
  }
}
