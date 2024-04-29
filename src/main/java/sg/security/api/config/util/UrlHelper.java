package sg.security.api.config.util;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
@Component
public class UrlHelper {

    public String getApplicationUrl(HttpServletRequest request) {

        URI uri;
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();
        String contextPath = request.getContextPath();

        try {

            uri = new URI(scheme, null, serverName, serverPort, contextPath, null, null);

        } catch (URISyntaxException e) {
            log.error(e.getMessage());
            return null;
        }

        return uri.toString();
    }
}
