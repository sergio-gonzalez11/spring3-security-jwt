package sg.security.api.config.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import sg.security.api.constant.Constants;
import sg.security.api.entity.user.UserJpa;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return getCurrentAuditorName();
    }

    public static Optional<String> getCurrentAuditorName() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()
                || authentication instanceof AnonymousAuthenticationToken) {

            return Optional.of(Constants.USER_APPLICATION);
        }

        UserJpa userPrincipal = (UserJpa) authentication.getPrincipal();
        return Optional.ofNullable(userPrincipal.getUsername());
    }
}
