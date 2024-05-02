package sg.security.api.exception.global;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.mail.MessagingException;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import sg.security.api.constant.Constants;
import sg.security.api.exception.*;

import javax.security.auth.login.AccountNotFoundException;
import java.io.UnsupportedEncodingException;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.security.SignatureException;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            BadRequestException.class,
            EmailVerificationExpiredException.class,
            ConstraintViolationException.class,
            IllegalArgumentException.class,
            ValidationException.class
    })
    public ResponseEntity<ApiError> handleBadRequestException(final MethodArgumentNotValidException ex) {

        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();

        log.info(Constants.MESSAGE, errors);
        return ApiError.buildResponse(HttpStatus.BAD_REQUEST, errors);
    }

    @ExceptionHandler({
            AuthenticationException.class,
            UserNotAuthorizedException.class
    })
    public ResponseEntity<ApiError> handleAuthenticationException(final RuntimeException ex) {
        log.info(Constants.MESSAGE, ex.getMessage());
        return ApiError.buildResponse(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }

    @ExceptionHandler({
            AccessDeniedException.class,
            BadCredentialsException.class,
            AccountStatusException.class,
            SignatureException.class,
            MalformedJwtException.class,
            ExpiredJwtException.class,
            UserAlreadyExistsException.class
    })
    public ResponseEntity<ApiError> handleSecurityException(final RuntimeException ex) {
        log.info(Constants.MESSAGE, ex.getMessage());
        return ApiError.buildResponse(HttpStatus.FORBIDDEN, ex.getMessage());
    }

    @ExceptionHandler({
            AuthenticationCredentialsNotFoundException.class,
            UserPrincipalNotFoundException.class,
            AccountNotFoundException.class,
            UserNotFoundException.class,
            UsernameNotFoundException.class,
            RoleNotFoundException.class,
            EmailVerificationNotFoundException.class
    })
    public ResponseEntity<ApiError> handleNotFoundException(final RuntimeException ex) {
        log.info(Constants.MESSAGE, ex.getMessage());
        return ApiError.buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler({
            Exception.class,
            MessagingException.class,
            UnsupportedEncodingException.class
    })
    public ResponseEntity<ApiError> handleInternalServerException(final RuntimeException ex) {
        log.info(Constants.MESSAGE, ex.getMessage());
        return ApiError.buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

}