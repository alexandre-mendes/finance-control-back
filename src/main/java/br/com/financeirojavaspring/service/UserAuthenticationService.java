package br.com.financeirojavaspring.service;

import br.com.financeirojavaspring.model.User;
import br.com.financeirojavaspring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserAuthenticationService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	private User user;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException(String.format("User %s not found.", username)));
		return user;
	}

	public User getUser() {
		return user;
	}
}
