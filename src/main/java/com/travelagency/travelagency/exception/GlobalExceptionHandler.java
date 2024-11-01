package com.travelagency.travelagency.exception;

import com.travelagency.travelagency.exception.auth.AuthenticationFailedException;
import com.travelagency.travelagency.exception.error.ApiError;
import com.travelagency.travelagency.exception.role.RoleAlreadyExistsException;
import com.travelagency.travelagency.exception.role.RoleNotFoundException;
import com.travelagency.travelagency.exception.tour.*;
import com.travelagency.travelagency.exception.user.*;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    // Handle UserNotFoundException
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException exception, HttpServletRequest request) {
        ApiError apiError = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND)
                .message(exception.getMessage())
                .debugMessage(ExceptionUtils.getRootCauseMessage(exception))
                .path(request.getRequestURI())
                .build();

        return buildResponseEntity(apiError);
    }

    // Handle UserAlreadyExistsException
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Object> handleUserAlreadyExistsException(UserAlreadyExistsException exception, HttpServletRequest request) {
        ApiError apiError = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CONFLICT)
                .message(exception.getMessage())
                .debugMessage(ExceptionUtils.getRootCauseMessage(exception))
                .path(request.getRequestURI())
                .build();

        return buildResponseEntity(apiError);
    }

    // Handle EmailAlreadyExistsException
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<Object> handleEmailAlreadyExistsException(EmailAlreadyExistsException exception, HttpServletRequest request) {
        ApiError apiError = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CONFLICT)
                .message(exception.getMessage())
                .debugMessage(ExceptionUtils.getRootCauseMessage(exception))
                .path(request.getRequestURI())
                .build();

        return buildResponseEntity(apiError);
    }

    // Handle RoleNotFoundException
    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<Object> handleRoleNotFoundException(RoleNotFoundException exception, HttpServletRequest request) {
        ApiError apiError = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND)
                .message(exception.getMessage())
                .debugMessage(ExceptionUtils.getRootCauseMessage(exception))
                .path(request.getRequestURI())
                .build();

        return buildResponseEntity(apiError);
    }

    // Handle RoleAlreadyExistsException
    @ExceptionHandler(RoleAlreadyExistsException.class)
    public ResponseEntity<Object> handleRoleAlreadyExistsException(RoleAlreadyExistsException exception, HttpServletRequest request) {
        ApiError apiError = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CONFLICT)
                .message(exception.getMessage())
                .debugMessage(ExceptionUtils.getRootCauseMessage(exception))
                .path(request.getRequestURI())
                .build();

        return buildResponseEntity(apiError);
    }

    // Handle TourNotFoundException
    @ExceptionHandler(TourNotFoundException.class)
    public ResponseEntity<Object> handleTourNotFoundException(TourNotFoundException exception, HttpServletRequest request) {
        ApiError apiError = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND)
                .message(exception.getMessage())
                .debugMessage(ExceptionUtils.getRootCauseMessage(exception))
                .path(request.getRequestURI())
                .build();

        return buildResponseEntity(apiError);
    }

    // Handle TourAlreadyExistsException
    @ExceptionHandler(TourAlreadyExistsException.class)
    public ResponseEntity<Object> handleTourAlreadyExistsException(TourAlreadyExistsException exception, HttpServletRequest request) {
        ApiError apiError = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CONFLICT)
                .message(exception.getMessage())
                .debugMessage(ExceptionUtils.getRootCauseMessage(exception))
                .path(request.getRequestURI())
                .build();

        return buildResponseEntity(apiError);
    }

    // Handle TourAlreadyBookedException
    @ExceptionHandler(TourAlreadyBookedException.class)
    public ResponseEntity<Object> handleTourAlreadyBookedException(TourAlreadyBookedException exception, HttpServletRequest request) {
        ApiError apiError = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CONFLICT)
                .message(exception.getMessage())
                .debugMessage(ExceptionUtils.getRootCauseMessage(exception))
                .path(request.getRequestURI())
                .build();

        return buildResponseEntity(apiError);
    }

    // Handle AirportNotFoundException
    @ExceptionHandler(AirportNotFoundException.class)
    public ResponseEntity<Object> handleAirportNotFoundException(AirportNotFoundException exception, HttpServletRequest request) {
        ApiError apiError = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND)
                .message(exception.getMessage())
                .debugMessage(ExceptionUtils.getRootCauseMessage(exception))
                .path(request.getRequestURI())
                .build();

        return buildResponseEntity(apiError);
    }

    // Handle CityNotFoundException
    @ExceptionHandler(CityNotFoundException.class)
    public ResponseEntity<Object> handleCityNotFoundException(CityNotFoundException exception, HttpServletRequest request) {
        ApiError apiError = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND)
                .message(exception.getMessage())
                .debugMessage(ExceptionUtils.getRootCauseMessage(exception))
                .path(request.getRequestURI())
                .build();

        return buildResponseEntity(apiError);
    }

    // Handle CountryNotFoundException
    @ExceptionHandler(CountryNotFoundException.class)
    public ResponseEntity<Object> handleCountryNotFoundException(CountryNotFoundException exception, HttpServletRequest request) {
        ApiError apiError = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND)
                .message(exception.getMessage())
                .debugMessage(ExceptionUtils.getRootCauseMessage(exception))
                .path(request.getRequestURI())
                .build();

        return buildResponseEntity(apiError);
    }

    // Handle HotelNotFoundException
    @ExceptionHandler(HotelNotFoundException.class)
    public ResponseEntity<Object> handleHotelNotFoundException(HotelNotFoundException exception, HttpServletRequest request) {
        ApiError apiError = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND)
                .message(exception.getMessage())
                .debugMessage(ExceptionUtils.getRootCauseMessage(exception))
                .path(request.getRequestURI())
                .build();

        return buildResponseEntity(apiError);
    }

    // Handle AuthenticationFailedException
    @ExceptionHandler(AuthenticationFailedException.class)
    public ResponseEntity<Object> handleAuthenticationFailedException(AuthenticationFailedException exception, HttpServletRequest request) {
        ApiError apiError = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.UNAUTHORIZED)
                .message(exception.getMessage())
                .debugMessage(ExceptionUtils.getRootCauseMessage(exception))
                .path(request.getRequestURI())
                .build();

        return buildResponseEntity(apiError);
    }

    // Handle AirportAlreadyExistsException
    @ExceptionHandler(AirportAlreadyExistsException.class)
    public ResponseEntity<Object> handleAirportAlreadyExistsException(AirportAlreadyExistsException exception, HttpServletRequest request) {
        ApiError apiError = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CONFLICT)
                .message(exception.getMessage())
                .debugMessage(ExceptionUtils.getRootCauseMessage(exception))
                .path(request.getRequestURI())
                .build();

        return buildResponseEntity(apiError);
    }

    // Handle CityAlreadyExistsException
    @ExceptionHandler(CityAlreadyExistsException.class)
    public ResponseEntity<Object> handleCityAlreadyExistsException(CityAlreadyExistsException exception, HttpServletRequest request) {
        ApiError apiError = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CONFLICT)
                .message(exception.getMessage())
                .debugMessage(ExceptionUtils.getRootCauseMessage(exception))
                .path(request.getRequestURI())
                .build();

        return buildResponseEntity(apiError);
    }

    // Handle HotelAlreadyExistsException
    @ExceptionHandler(HotelAlreadyExistsException.class)
    public ResponseEntity<Object> handleHotelAlreadyExistsException(HotelAlreadyExistsException exception, HttpServletRequest request) {
        ApiError apiError = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CONFLICT)
                .message(exception.getMessage())
                .debugMessage(ExceptionUtils.getRootCauseMessage(exception))
                .path(request.getRequestURI())
                .build();

        return buildResponseEntity(apiError);
    }

    // Handle CountryAlreadyExistsException
    @ExceptionHandler(CountryAlreadyExistsException.class)
    public ResponseEntity<Object> handleCountryAlreadyExistsException(CountryAlreadyExistsException exception, HttpServletRequest request) {
        ApiError apiError = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CONFLICT)
                .message(exception.getMessage())
                .debugMessage(ExceptionUtils.getRootCauseMessage(exception))
                .path(request.getRequestURI())
                .build();

        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(UserNotAuthenticatedException.class)
    public ResponseEntity<Object> handleUserNotAuthenticatedException(UserNotAuthenticatedException exception, HttpServletRequest request) {
        ApiError apiError = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.UNAUTHORIZED) // 401 Unauthorized status
                .message(exception.getMessage())
                .debugMessage(ExceptionUtils.getRootCauseMessage(exception))
                .path(request.getRequestURI())
                .build();

        return buildResponseEntity(apiError);
    }
}
