package de.bugtracker.exception.custom;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Some constraints are violated.")
public class ConstraintViolationException extends Exception {

    public ConstraintViolationException(String message) {
        super(message);
    }
}
