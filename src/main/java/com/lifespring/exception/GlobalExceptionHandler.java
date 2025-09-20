package com.lifespring.exception;


import com.lifespring.message.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    ErrorMessage errorMessage = new ErrorMessage();
//This is the custom exception handler for ResourceNotFoundException
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        errorMessage.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorMessage.setMessage(ex.getMessage());
        errorMessage.setDetails("Resource Not Found");
        errorMessage.setPath(request.getDescription(false));
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    //here writing the global exception handler
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleGlobalException(Exception ex, WebRequest request) {
        errorMessage.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorMessage.setMessage(ex.getMessage());
        errorMessage.setDetails("Internal Server Error");
        errorMessage.setPath(request.getDescription(false));
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    // Here we handling the IllegalArgumentException
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorMessage> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        errorMessage.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorMessage.setMessage(ex.getMessage());
        errorMessage.setDetails("Bad Request");
        errorMessage.setPath(request.getDescription(false));
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    // here we are going to handle user already exists exception

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorMessage> handleUserAlreadyExistsException(UserAlreadyExistsException existsException , WebRequest request){
        errorMessage.setMessage(existsException.getMessage());
        errorMessage.setPath(request.getDescription(false));
        errorMessage.setDetails("User Already Exists");
        errorMessage.setStatusCode(409);

        return new ResponseEntity<>(errorMessage , HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidUserDetailException.class)
    public ResponseEntity<ErrorMessage> handleInvalidUserDetailException(InvalidUserDetailException ex , WebRequest request){
        errorMessage.setMessage(ex.getMessage());
        errorMessage.setStatusCode(404);
        errorMessage.setDetails("User not exists with the given username or password");
        errorMessage.setPath(request.getDescription(false));

        return new ResponseEntity<>(errorMessage,HttpStatus.NOT_FOUND);

    }


    @ExceptionHandler(InvalidEmailDomainException.class)
    public ResponseEntity<ErrorMessage> handleInvalidEmailDomainException(InvalidEmailDomainException ex, WebRequest request){
        errorMessage.setMessage(ex.getMessage());
        errorMessage.setPath(request.getDescription(false));
        errorMessage.setDetails("Invalid email domain provided ");
        errorMessage.setStatusCode(400);
        return new ResponseEntity<>(errorMessage,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorMessage> handleInvalidCredentialsException(InvalidCredentialsException ex, WebRequest request){
        errorMessage.setMessage(ex.getMessage());
        errorMessage.setPath(request.getDescription(false));
        errorMessage.setDetails("Invalid credentials provided ");
        errorMessage.setStatusCode(400);
        return new ResponseEntity<>(errorMessage,HttpStatus.BAD_REQUEST);
    }

    // Handling the exception of token is expired
    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<ErrorMessage> handleTokenExpiredException(TokenExpiredException ex , WebRequest request){
      ErrorMessage message =    ErrorMessage.builder()
                .message(ex.getMessage())
                .details("Invalid Token")
                .path(request.getDescription(false))
                .statusCode(401)
                .build();

      return new ResponseEntity<>(message , HttpStatus.NOT_ACCEPTABLE);
    }
}
