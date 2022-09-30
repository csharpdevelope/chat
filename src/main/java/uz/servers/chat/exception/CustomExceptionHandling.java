package uz.servers.chat.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import uz.servers.chat.payload.response.ErrorResponse;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class CustomExceptionHandling {

    @ExceptionHandler(NotFoundException.class)
    public ErrorResponse notfound(NotFoundException ex, WebRequest request) {
        ErrorResponse response = new ErrorResponse();
        response.setMessage(ex.getMessage());
        response.setCode(404);
        return response;
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> badrequest(BadRequestException ex, WebRequest request) {
        ErrorResponse response = new ErrorResponse();
        response.setMessage(ex.getMessage());
        response.setCode(400);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<?> error(Exception e, HttpServletRequest request) {
        ErrorResponse response = new ErrorResponse();
        response.setCode(500);
        response.setMessage(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
