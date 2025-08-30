package com.ems.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

	private final JwtUtil jwtUtil;
	private final UserDetailsService userDetailsService;

	public JwtAuthFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
		this.jwtUtil = jwtUtil;
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// 1. Get Authorization header
		String authHeader = request.getHeader("Authorization");

		String username = null;
		String token = null;

		// 2. Check header and extract token
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			token = authHeader.substring(7); // remove "Bearer "
			try {
				username = jwtUtil.extractUsername(token);
			} catch (io.jsonwebtoken.ExpiredJwtException ex) {
				// Token expired
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.setContentType("application/json");
				response.getWriter()
						.write("{\"success\": false, \"message\": \"Session expired. Please log in again.\"}");
				return; // stop filter chain
			} catch (Exception ex) {
				// Invalid token
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.setContentType("application/json");
				response.getWriter().write("{\"success\": false, \"message\": \"Invalid token.\"}");
				return; // stop filter chain
			}
		}

		// 3. Validate and set authentication
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);

			if (jwtUtil.validateToken(token)) {
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
						null, userDetails.getAuthorities());

				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				// Set authentication in context
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}

		// 4. Continue the filter chain
		filterChain.doFilter(request, response);
	}
}
