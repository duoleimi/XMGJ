package XMGJ.base.config;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

@Component
public class CustomUserDetailsService implements UserDetailsService {

	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	private static List<SimpleGrantedAuthority> authorities = Lists
			.newArrayList(new SimpleGrantedAuthority("R_DEVICE"));
	private static String password = null;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		// if (deviceService.countByUid(username) <= 0) {
		// throw new UsernameNotFoundException("not found");
		// }
		// if (StringUtils.isBlank(password)) {
		// password = encoder.encode("");
		// }

		User user = new User(username, password, authorities);

		return user;
	}

}
