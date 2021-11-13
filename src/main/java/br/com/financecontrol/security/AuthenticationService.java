package br.com.financecontrol.security;

import br.com.financecontrol.entity.User;
import br.com.financecontrol.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class AuthenticationService implements UserDetailsService {
	
	private final UserRepository userRepository;

	public AuthenticationService(
			UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		return userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException(String.format("User %s not found.", username)));
	}

	public User getUser() {
		 return ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
	}
}
