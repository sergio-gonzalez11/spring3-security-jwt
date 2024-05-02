package sg.security.api.event;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.context.ApplicationEvent;
import sg.security.api.dto.user.User;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class EmailEvent extends ApplicationEvent implements Serializable {
    @Serial
    private static final long serialVersionUID = 8421210018193144077L;
    private User user;
    private String applicationUrl;

    public EmailEvent(User user, String applicationUrl) {
        super(user);
        this.user = user;
        this.applicationUrl = applicationUrl;
    }
}