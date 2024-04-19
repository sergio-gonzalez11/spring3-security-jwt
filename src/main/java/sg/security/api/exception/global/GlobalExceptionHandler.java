package sg.security.api.exception.global;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import sg.security.api.exception.EmailVerificationNotFoundException;
import sg.security.api.exception.RoleNotFoundException;
import sg.security.api.exception.UserAlreadyExistsException;
import sg.security.api.exception.UserNotFoundException;

import javax.security.auth.login.AccountNotFoundException;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.security.SignatureException;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler /*extends ResponseEntityExceptionHandler*/ {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private static final String MESSAGE = "Error: ";

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
        LOGGER.error(MESSAGE, ex);
        return ApiError.buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiError> handleAuthenticationException(final RuntimeException ex) {
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
        LOGGER.error(MESSAGE, ex);
        return ApiError.buildResponse(HttpStatus.FORBIDDEN, ex.getMessage());
    }

    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            ConstraintViolationException.class,
            ValidationException.class
    })
    public ResponseEntity<ApiError> handleBadRequestException(final MethodArgumentNotValidException ex) {

        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();

        LOGGER.error(MESSAGE, ex);
        return ApiError.buildResponse(HttpStatus.BAD_REQUEST, errors);
    }


   /* @ExceptionHandler({
            AuthenticationCredentialsNotFoundException.class,
            UserPrincipalNotFoundException.class,
            AccountNotFoundException.class,
            UserNotFoundException.class,
            UsernameNotFoundException.class,
            RoleNotFoundException.class,
            EmailVerificationNotFoundException.class
    })
    public ResponseEntity<ApiError> handleNotFoundException(final Exception exception) {
        LOGGER.error(MESSAGE, exception);
        return ApiError.buildResponse(HttpStatus.NOT_FOUND, exception.getMessage());
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
    public ResponseEntity<ApiError> handleSecurityException(final Exception exception) {
        LOGGER.error(MESSAGE, exception);
        return ApiError.buildResponse(HttpStatus.FORBIDDEN, exception.getMessage());
    }

    @ExceptionHandler({
            NumberFormatException.class,
            ConstraintViolationException.class,
            ArithmeticException.class,
            UnsatisfiedRequestParameterException.class,
            IllegalStateException.class,
            NullPointerException.class,
            HttpMessageNotReadableException.class,
            SQLException.class
    })
    public ResponseEntity<ApiError> handleBadRequestException(final Exception exception) {
        LOGGER.error(MESSAGE, exception);
        return ApiError.buildResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler({
            MessagingException.class,
            UnsupportedEncodingException.class
    })
    public ResponseEntity<ApiError> handleEmailException(final Exception exception) {
        LOGGER.error(MESSAGE, exception);
        return ApiError.buildResponse(HttpStatus.CONFLICT, exception.getMessage());
    }*/
}