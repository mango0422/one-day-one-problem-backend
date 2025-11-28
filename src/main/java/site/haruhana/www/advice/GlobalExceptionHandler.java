package site.haruhana.www.advice;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import site.haruhana.www.dto.BaseResponse;
import site.haruhana.www.exception.InvalidAnswerFormatException;
import site.haruhana.www.exception.ProblemNotFoundException;
import java.util.stream.Collectors;

import static site.haruhana.www.common.ErrorCode.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse<Void>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String errorDetails = e.getBindingResult().getAllErrors().stream()
                .map(error -> {
                    String fieldName = ((FieldError) error).getField();
                    String errorMsg = error.getDefaultMessage();
                    return fieldName + "(" + errorMsg + ")";
                })
                .collect(Collectors.joining(", "));

        String message = INVALID_INPUT_VALUE.getDefaultMessage() + ": " + errorDetails;

        return INVALID_INPUT_VALUE.toResponseEntity(message);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<BaseResponse<Void>> handleConstraintViolationException(ConstraintViolationException e) {
        String errorDetails = e.getConstraintViolations().stream()
                .map(violation -> {
                    String propertyPath = violation.getPropertyPath().toString();
                    String message = violation.getMessage();
                    return propertyPath + "(" + message + ")";
                })
                .collect(Collectors.joining(", "));

        String message = INVALID_INPUT_VALUE.getDefaultMessage() + ": " + errorDetails;

        return INVALID_INPUT_VALUE.toResponseEntity(message);
    }

    @ExceptionHandler(ProblemNotFoundException.class)
    public ResponseEntity<BaseResponse<Void>> handleProblemNotFoundException(ProblemNotFoundException e) {
        return PROBLEM_NOT_FOUND.toResponseEntity(e.getMessage());
    }

    @ExceptionHandler(InvalidAnswerFormatException.class)
    public ResponseEntity<BaseResponse<Void>> handleInvalidAnswerFormatException(InvalidAnswerFormatException e) {
        return INVALID_ANSWER_FORMAT.toResponseEntity(e.getMessage());
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<BaseResponse<Void>> handleExpiredJwtException(ExpiredJwtException e) {
        return TOKEN_EXPIRED.toResponseEntity(e.getMessage());
    }

    @ExceptionHandler(UnsupportedJwtException.class)
    public ResponseEntity<BaseResponse<Void>> handleUnsupportedJwtException(UnsupportedJwtException e) {
        return TOKEN_UNSUPPORTED.toResponseEntity(e.getMessage());
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<BaseResponse<Void>> handleMalformedJwtException(MalformedJwtException e) {
        return TOKEN_MALFORMED.toResponseEntity(e.getMessage());
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<BaseResponse<Void>> handleJwtException(JwtException e) {
        return TOKEN_ERROR.toResponseEntity(e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<BaseResponse<Void>> handleIllegalArgumentException(IllegalArgumentException e) {
        return ILLEGAL_ARGUMENT.toResponseEntity(e.getMessage());
    }
}
