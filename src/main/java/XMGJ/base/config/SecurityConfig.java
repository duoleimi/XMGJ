package XMGJ.base.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.session.web.http.HeaderHttpSessionStrategy;
import org.springframework.session.web.http.HttpSessionStrategy;

/**
 * 安全认证配置
 * 
 * @author lll
 */
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private SecurityProperties security;
	@Autowired
	private CustomUserDetailsService userservice;

	@Bean
	public PasswordEncoder passwordEncoder() {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.userDetailsService(userservice).passwordEncoder(
				passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		if (!security.isEnableCsrf()) {
			http.csrf().disable();
		} else {
			http.csrf().ignoringAntMatchers("/login");
		}
		http.authorizeRequests()
				.antMatchers(
						"/swagger-ui.html**",
						"/swagger-resources/**",
						"/v2/api-docs/**",
						"/files/**",
						"/api",
						"/api/login",
						"/api/token",
						"/api/socket/msg"
						).permitAll()
				.anyRequest()
					.fullyAuthenticated()
				.and()
					.formLogin()
					.loginProcessingUrl("/api/login")
					.successForwardUrl("/api/token")
					.failureHandler(new AuthFailHandler()).permitAll()
				.and()
					.logout()
					.logoutUrl("/api/logout")
					.logoutSuccessUrl("/api/logoutsucc").permitAll()
				.and().sessionManagement()
					.maximumSessions(1)
					.expiredUrl("/api/expired");
	}
	
	// Register HttpSessionEventPublisher
	@Bean
	public static ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
		return new ServletListenerRegistrationBean<HttpSessionEventPublisher>(new HttpSessionEventPublisher());
	}

	
	/**
	 * 使用header 作为session策略
	 * @return
	 */
	@Bean
    public HttpSessionStrategy httpSessionStrategy() {
            return new HeaderHttpSessionStrategy(); 
    }
}
