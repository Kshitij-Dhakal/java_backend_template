package np.com.kshitij.template.beans;

import lombok.extern.slf4j.Slf4j;
import np.com.kshitij.commons.ErrorResponse;
import np.com.kshitij.commons.exception.BadRequestException;
import np.com.kshitij.commons.exception.NotFoundException;
import np.com.kshitij.commons.exception.ServerException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> badRequest(BadRequestException badRequestException) {
        return ResponseEntity.badRequest().body(new ErrorResponse(badRequestException.getMessage()));
    }

    @ExceptionHandler(ServerException.class)
    public ResponseEntity<ErrorResponse> internalServerError(ServerException serverException) {
        log.error("Server exception", serverException);
        return ResponseEntity.internalServerError().body(new ErrorResponse(serverException.getMessage()));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> notFound(NotFoundException notFoundException) {
        return new ResponseEntity<>(new ErrorResponse(notFoundException.getMessage()), HttpStatus.NOT_FOUND);
    }
}
