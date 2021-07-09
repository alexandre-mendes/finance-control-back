package br.com.financeirojavaspring.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.UUID;

public class TokenAuthenticationService {
	
	// EXPIRATION_TIME = 2 horas
	static final long EXPIRATION_TIME = 7200000;
	static final String SECRET = "53d0bb9de7c0860675a13873722759c0fb3245e5154e51e133bf6ab6ade88a80";
	static final String TOKEN_PREFIX = "Bearer";
	static final String HEADER_STRING = "Authorization";
	
	public static void addAuthentication(HttpServletResponse response, String username) throws IOException {
		String JWT = Jwts.builder()
				.setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, SECRET)
				.compact();
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
		response.getWriter().write("{\"Authorization\": \"" + TOKEN_PREFIX + " " + JWT + "\"}");
	}
	
	public static Authentication getAuthentication(HttpServletRequest request) {
		String token = request.getHeader(HEADER_STRING);
		
		if (token != null) {

			String user = Jwts.parser()
					.setSigningKey(SECRET)
					.parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
					.getBody()
					.getSubject();
			
			if (user != null) {
				return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
			}
		}
		return null;
	}
	
}