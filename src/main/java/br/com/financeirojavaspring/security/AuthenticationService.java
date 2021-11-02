package br.com.financeirojavaspring.security;

import br.com.financeirojavaspring.entity.User;
import br.com.financeirojavaspring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class AuthenticationService implements UserDetailsService {
	
	private final UserRepository userRepository;
	private User user;

	public AuthenticationService(
			UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException(String.format("User %s not found.", username)));
		return user;
	}

	public User getUser() {
		return user;
	}
}
