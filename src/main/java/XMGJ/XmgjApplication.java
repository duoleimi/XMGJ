package XMGJ;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import XMGJ.base.config.SecurityConfig;

@EnableCaching
@EnableAsync()
@EnableScheduling
@SpringBootApplication
public class XmgjApplication {
	
	@Bean
    public SecurityConfig applicationSecurity() {
        return new SecurityConfig();
    }
	
	public static void main(String[] args) {
		SpringApplication.run(XmgjApplication.class, args);
	}
}
