package com.pragma.powerup.smallsquaremicroservice.config;

import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.exceptions.CategoryNotFoundException;
import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.exceptions.NitAlreadyExistsException;
import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.exceptions.PhoneAlreadyExistsException;
import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.exceptions.RestaurantNotFoundException;
import com.pragma.powerup.smallsquaremicroservice.domain.exceptions.InvalidNameException;
import com.pragma.powerup.smallsquaremicroservice.domain.exceptions.InvalidNitException;
import com.pragma.powerup.smallsquaremicroservice.domain.exceptions.InvalidPhoneException;
import com.pragma.powerup.smallsquaremicroservice.domain.exceptions.InvalidUserException;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.Map;

import static com.pragma.powerup.smallsquaremicroservice.config.Constants.*;

@ControllerAdvice
public class ControllerAdvisor {
    @ExceptionHandler(InvalidNameException.class)
    public ResponseEntity<Map<String, String>> handlerInvalidNameException() {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, INVALID_NAME));
    }

    @ExceptionHandler(InvalidPhoneException.class)
    public ResponseEntity<Map<String, String>> handlerInvalidPhoneException() {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, INVALID_PHONE));
    }

    @ExceptionHandler(InvalidNitException.class)
    public ResponseEntity<Map<String, String>> handlerInvalidNitException() {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, INVALID_NIT));
    }

    @ExceptionHandler(InvalidUserException.class)
    public ResponseEntity<Map<String, String>> handlerInvalidUserException() {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, INVALID_USER));
    }

    @ExceptionHandler(PhoneAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handlePhoneAlreadyExistsException() {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, PHONE_ALREADY_EXISTS_MESSAGE));
    }

    @ExceptionHandler(NitAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleNitAlreadyExistsException() {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, NIT_ALREADY_EXISTS_MESSAGE));
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<Map<String, String>> handleUnauthorizedException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, WRONG_CREDENTIALS_MESSAGE));
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Map<String, String>> handleNullPointerException(NullPointerException nullPointerException) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, nullPointerException.getMessage()));
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Map<String, String>> handleIllegalStateException(IllegalStateException IllegalStateException) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, IllegalStateException.getMessage()));
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleCategoryNotFoundException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, CATEGORY_NOT_FOUND));
    }

    @ExceptionHandler(RestaurantNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleRestaurantNotFoundException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, RESTAURANT_NOT_FOUND));
    }

}
