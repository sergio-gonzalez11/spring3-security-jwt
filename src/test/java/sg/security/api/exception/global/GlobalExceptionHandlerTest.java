package sg.security.api.exception.global;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import sg.security.api.exception.EmailVerificationExpiredException;
import sg.security.api.exception.UserNotAuthorizedException;
import sg.security.api.exception.UserNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    @InjectMocks
    GlobalExceptionHandler globalExceptionHandler;

    @Test
    void handleBadRequestException() {

        MethodParameter methodArgumentNotValidException = mock(MethodParameter.class);
        BindingResult bindingResult = mock(BindingResult.class);

        final MethodArgumentNotValidException exception = assertThrows(MethodArgumentNotValidException.class, () -> {
            throw new MethodArgumentNotValidException(methodArgumentNotValidException, bindingResult);
        });

        final ResponseEntity<ApiError> responseEntity = this.globalExceptionHandler.handleBadRequestException(exception);
        final ApiError result = responseEntity.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNotNull(result);

    }

    @Test
    void handleAuthenticationException() {

        final UserNotAuthorizedException exception = assertThrows(UserNotAuthorizedException.class, () -> {
            throw new UserNotAuthorizedException();
        });

        final ResponseEntity<ApiError> responseEntity = this.globalExceptionHandler.handleAuthenticationException(exception);
        final ApiError result = responseEntity.getBody();

        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
        assertNotNull(result);
    }

    @Test
    void handleSecurityException() {

        final AccessDeniedException exception = assertThrows(AccessDeniedException.class, () -> {
            throw new AccessDeniedException("ERROR");
        });

        final ResponseEntity<ApiError> responseEntity = this.globalExceptionHandler.handleSecurityException(exception);
        final ApiError result = responseEntity.getBody();

        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
        assertNotNull(result);
    }

    @Test
    void handleNotFoundException() {

        final UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            throw new UserNotFoundException("ERROR");
        });

        final ResponseEntity<ApiError> responseEntity = this.globalExceptionHandler.handleNotFoundException(exception);
        final ApiError result = responseEntity.getBody();

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNotNull(result);
    }

    @Test
    void handleInternalServerException() {

        final RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            throw new RuntimeException("ERROR");
        });

        final ResponseEntity<ApiError> responseEntity = this.globalExceptionHandler.handleInternalServerException(exception);
        final ApiError result = responseEntity.getBody();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertNotNull(result);
    }

    @Test
    void handleException() {

        final EmailVerificationExpiredException exception = assertThrows(EmailVerificationExpiredException.class, () -> {
            throw new EmailVerificationExpiredException("ERROR");
        });

        final ResponseEntity<ApiError> responseEntity = this.globalExceptionHandler.handleException(exception);
        final ApiError result = responseEntity.getBody();

        assertEquals(HttpStatus.ALREADY_REPORTED, responseEntity.getStatusCode());
        assertNotNull(result);
    }


}
