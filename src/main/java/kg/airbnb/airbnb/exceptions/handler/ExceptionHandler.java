package kg.airbnb.airbnb.exceptions.handler;

import kg.airbnb.airbnb.exceptions.BadRequestException;
import kg.airbnb.airbnb.exceptions.ExceptionResponse;
import kg.airbnb.airbnb.exceptions.ForbiddenException;
import kg.airbnb.airbnb.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandler {

    //404
    @org.springframework.web.bind.annotation.ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse handlerNotFoundException(NotFoundException e) {
        return new ExceptionResponse(
                HttpStatus.NOT_FOUND,
                e.getClass().getSimpleName(),
                e.getMessage()
        );

    }

    //400
    @org.springframework.web.bind.annotation.ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handlerBadRequestException(BadRequestException e) {
        return new ExceptionResponse(
                HttpStatus.BAD_REQUEST,
                e.getClass().getSimpleName(),
                e.getMessage()
        );
    }

    //403
    @org.springframework.web.bind.annotation.ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionResponse handlerForbiddenException(ForbiddenException e) {
        return new ExceptionResponse(
                HttpStatus.NOT_FOUND,
                e.getClass().getSimpleName(),
                e.getMessage()
        );
    }

}

