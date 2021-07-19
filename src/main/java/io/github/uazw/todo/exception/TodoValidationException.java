package io.github.uazw.todo.exception;

import org.springframework.validation.Errors;

public class TodoValidationException extends RuntimeException {

  private Errors bindingResult;

  public TodoValidationException(Errors bindingResult) {
    this.bindingResult = bindingResult;
  }

  public Errors getBindingResult() {
    return bindingResult;
  }
}
