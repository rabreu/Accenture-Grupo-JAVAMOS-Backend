package com.accenture.javamos.controller.exception;

import java.util.HashMap;
import java.util.Map;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.WebUtils;

// TODO: create internationalization resources
// TODO: Logger
// TODO: handle disabled user

@ControllerAdvice
public class ApiExceptionHandler {

  @ExceptionHandler(Exception.class)
  public final ResponseEntity<ApiException> handleException(
    Exception e,
    WebRequest request
  ) {
    HttpHeaders headers = new HttpHeaders();
    HttpStatus status;
    ApiException apiException = new ApiException();
    Map<String, Object> errors = new HashMap<>();
    String message = null;

    if (e instanceof MethodArgumentNotValidException) {
      /** Method Argument Not Valid: @Valid fails */
      MethodArgumentNotValidException manve = (MethodArgumentNotValidException) e;
      status = HttpStatus.BAD_REQUEST;

      manve
        .getBindingResult()
        .getFieldErrors()
        .stream()
        .forEach(f -> errors.put(f.getField(), f.getDefaultMessage()));

      message = "Dado(s) inválido(s).";
    } else if (e instanceof EmailAlreadyTakenException) {
      /** E-mail already exists */
      status = HttpStatus.CONFLICT;
      errors.put("email", "E-mail já cadastrado.");
      message = "Escolha outro e-mail para registrar uma conta.";
    } else if (e instanceof DataIntegrityViolationException) {
      /** Data Integrity Violation */
      DataIntegrityViolationException dive = (DataIntegrityViolationException) e;
      status = HttpStatus.BAD_REQUEST;
      errors.put("violation", dive.getCause().getCause().getMessage());
      message = "O(s) dado(s) inserido(s) viola(m) a integridade da aplicação.";
    } else if (
      e instanceof DisabledException ||
      e instanceof BadCredentialsException ||
      e instanceof UnauthorizedException
    ) {
      /** Unauthorized Access */
      status = HttpStatus.UNAUTHORIZED;
      errors.put("credentials", "Credenciais inválidas.");
      message = "Faça o login.";
    } else {
      /** Internal Server Error */
      status = HttpStatus.INTERNAL_SERVER_ERROR;
      errors.put(
        "server",
        "O servidor não foi capaz de processar a requisição"
      );
      message =
        "Tente novamente mais tarde. Caso o erro persista, entre em contato com o admin";
    }

    apiException.setMessage(message);
    apiException.setErrors(errors);

    return handleExceptionInternal(e, apiException, headers, status, request);
  }

  protected ResponseEntity<ApiException> handleExceptionInternal(
    Exception e,
    ApiException body,
    HttpHeaders headers,
    HttpStatus status,
    WebRequest request
  ) {
    if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
      request.setAttribute(
        WebUtils.ERROR_EXCEPTION_ATTRIBUTE,
        e,
        WebRequest.SCOPE_REQUEST
      );
    }

    return new ResponseEntity<>(body, headers, status);
  }
}
