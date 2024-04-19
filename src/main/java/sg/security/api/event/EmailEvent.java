package sg.security.api.event;

import lombok.Builder;
import lombok.Data;
import org.springframework.context.ApplicationEvent;
import sg.security.api.dto.User;

@Data
@Builder
public class EmailEvent extends ApplicationEvent {

    private User user;
    private String applicationUrl;

    public EmailEvent(User user, String applicationUrl) {
        super(user);
        this.user = user;
        this.applicationUrl = applicationUrl;
    }
}