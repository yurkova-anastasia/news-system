package ru.clevertec.news_service.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.clevertec.news_service.client.UserClient;
import ru.clevertec.news_service.controller.response.user.UserResponse;

import java.io.IOException;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserClient userClient;
    public static final String BEARER_PREFIX = "Bearer ";

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest req,
                                    @NonNull HttpServletResponse resp,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        String tokenHeader = req.getHeader(HttpHeaders.AUTHORIZATION);
        if (Objects.isNull(tokenHeader) || !tokenHeader.startsWith(BEARER_PREFIX)) {
            filterChain.doFilter(req, resp);
            return;
        }
        UserResponse userResponse = userClient.getUserDetails(tokenHeader);

        UserDetails userDetails = User.builder()
                .username(userResponse.getUsername())
                .password("")//????
                .authorities(userResponse.getAuthorities().stream()
                        .map(SimpleGrantedAuthority::new)
                        .toList())
                .build();

        UsernamePasswordAuthenticationToken authenticated =
                UsernamePasswordAuthenticationToken
                        .authenticated(userDetails, null, userDetails.getAuthorities());
        authenticated.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
        SecurityContextHolder.getContext().setAuthentication(authenticated);
        filterChain.doFilter(req, resp);
    }

}
