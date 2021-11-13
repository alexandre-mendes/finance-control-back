package br.com.financecontrol.security;

import br.com.financecontrol.entity.User;
import br.com.financecontrol.repository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.util.Objects.isNull;

public class AuthenticationTokenFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserRepository userRepository;

    public AuthenticationTokenFilter(TokenService tokenService, UserRepository userRepository) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
         final String token = retrieveToken(request);

         final boolean isValid = tokenService.isTokenValid(token);
         if (isValid) {
             authenticateClient(token);
         }

         filterChain.doFilter(request, response);
    }

    private void authenticateClient(final String token) {
        final String uuidUser = tokenService.getUuidUser(token);
        final User user = userRepository.findByUuid(uuidUser);
        final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private String retrieveToken(final HttpServletRequest request) {
        final String token = request.getHeader("Authorization");
        if (isNull(token) || token.isEmpty() || !token.startsWith("Bearer ")) {
            return null;
        }
        return token.substring(7, token.length());
    }
}
