package XMGJ.base.event;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthenticationSuccessEventListener implements ApplicationListener<AuthenticationSuccessEvent> {

    @Override
    public void onApplicationEvent(final AuthenticationSuccessEvent event) {
        log.info("Successful login for: {}", event.getAuthentication().getPrincipal().toString());
    }
}
