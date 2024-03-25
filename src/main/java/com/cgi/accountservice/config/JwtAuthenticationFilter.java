package com.cgi.accountservice.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * This filter class is used the filter of request if the user is authenticated or not and having
 * jwt tokens or not. This is filter chain execute for each new request for the applicaiton.
 *
 * @author Rajesh Chanda
 * @version 0.1
 */

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final com.cgi.accountservice.config.JwtService jwtService;
  private final UserDetailsService userDetailsService;

  @Override
  protected void doFilterInternal(
          @NonNull HttpServletRequest request,
          @NonNull HttpServletResponse response,
          @NonNull FilterChain filterChain
  ) throws ServletException, IOException {

    final String authHeader = request.getHeader("Authorization");
    final String jwt;
    final String userName;
    boolean isInvalidToken = false;

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }
    jwt = authHeader.substring(7);
    userName = jwtService.extractUsername(jwt);
    if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      UserDetails userDetails = this.userDetailsService.loadUserByUsername(userName);
      if (jwtService.isTokenValid(jwt, userDetails)) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
        authToken.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
        );
        SecurityContextHolder.getContext().setAuthentication(authToken);
      }
    }
    else
    {
      isInvalidToken = true;
    }

    if (isInvalidToken)
    {
      ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "The token is not valid.");
    }
    filterChain.doFilter(request, response);
  }
  @Override
  protected boolean shouldNotFilter(HttpServletRequest request)
          throws ServletException {
    String path = ((HttpServletRequest) request).getRequestURI();
    String[] allowedPaths = com.cgi.accountservice.config.SecurityConfiguration.PUBLIC_REQUEST_MATCHERS;
    for (var allowedPath : allowedPaths) {
      allowedPath = allowedPath.replace("/*", "");
      allowedPath = allowedPath.replace("/**", "");
      if (path.contains(allowedPath)) {
        return true;
      }
    }

    return false;
  }
}
