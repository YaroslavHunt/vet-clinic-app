package com.vetclinic.vetclinicapp.filter;

import com.vetclinic.vetclinicapp.service.auth.UserService;
import com.vetclinic.vetclinicapp.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER_PREFIX = "Bearer ";

    private final JwtUtil jwtUtil;

    private final UserService userService;

    @Override
    @SneakyThrows
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
    {
        String authHeaderValue = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeaderValue == null || !authHeaderValue.startsWith(AUTHORIZATION_HEADER_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeaderValue.substring(AUTHORIZATION_HEADER_PREFIX.length());


        try {
            if (jwtUtil.isTokenExpired(token)) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token has expired");
                return;
            }

            String username = jwtUtil.extractUsername(token);

            if (StringUtils.isNotBlank(username)) {
                UserDetails userDetails = userService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        } catch (Exception e) {
            log.error("Invalid token", e);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
