package sg.security.api.config.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import sg.security.api.entity.user.UserJpa;

import java.util.Optional;

@Component
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return getCurrentAuditorName();
    }

    public static Optional<String> getCurrentAuditorName() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()
                || authentication instanceof AnonymousAuthenticationToken) {

            return Optional.empty();
        }

        UserJpa userPrincipal = (UserJpa) authentication.getPrincipal();
        return Optional.ofNullable(userPrincipal.getUsername());
    }
}
