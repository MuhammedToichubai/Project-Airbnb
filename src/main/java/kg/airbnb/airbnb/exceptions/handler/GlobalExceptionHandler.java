package kg.airbnb.airbnb.exceptions.handler;

import kg.airbnb.airbnb.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse notFoundException(NotFoundException e) {
        return new ExceptionResponse(HttpStatus.NOT_FOUND, e.getClass().getSimpleName(), e.getMessage());
    }

    @ExceptionHandler(AlreadyExistException.class)
    @ResponseStatus(HttpStatus.ALREADY_REPORTED)
    public ExceptionResponse AlreadyExistException(AlreadyExistException e) {
        return new ExceptionResponse(HttpStatus.ALREADY_REPORTED, e.getClass().getSimpleName(), e.getMessage());
    }

    @ExceptionHandler(WrongPasswordException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ExceptionResponse WrongPasswordException(WrongPasswordException e) {
        return new ExceptionResponse(HttpStatus.NOT_ACCEPTABLE, e.getClass().getSimpleName(), e.getMessage());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionResponse ForbiddenException(ForbiddenException e) {
        return new ExceptionResponse(HttpStatus.NOT_FOUND, e.getClass().getSimpleName(), e.getMessage());
    }

}
