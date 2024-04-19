package sg.security.api.exception.global;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Data
@Builder
public class ApiError {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime date;
    private String title;
    private int status;
    private List<String> message;


    public static ResponseEntity<ApiError> buildResponse(
            final HttpStatus status,
            final String message) {

        final ApiError error = ApiError.builder()
                .date(LocalDateTime.now())
                .title(status.name())
                .status(status.value())
                .message(Collections.singletonList(message))
                .build();

        return ResponseEntity.status(status).body(error);
    }

    public static ResponseEntity<ApiError> buildResponse(
            final HttpStatus status,
            final List<String> errors) {

        final ApiError errorDTO = ApiError.builder()
                .date(LocalDateTime.now())
                .title(status.name())
                .status(status.value())
                .message(errors)
                .build();

        return ResponseEntity.status(status).body(errorDTO);
    }
}
