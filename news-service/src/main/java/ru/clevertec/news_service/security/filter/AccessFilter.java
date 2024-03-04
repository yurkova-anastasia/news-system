package ru.clevertec.news_service.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.clevertec.handling.exception.ApplicationException;
import ru.clevertec.news_service.client.UserClient;
import ru.clevertec.news_service.controller.response.comment.CommentResponse;
import ru.clevertec.news_service.controller.response.news.NewsResponse;
import ru.clevertec.news_service.controller.response.user.UserResponse;
import ru.clevertec.news_service.exception.code.AuthorizedExceptionCode;
import ru.clevertec.news_service.exception.message.AuthorizedExceptionMessage;
import ru.clevertec.news_service.service.CommentService;
import ru.clevertec.news_service.service.NewsService;

import java.io.IOException;

/**
 * It is used to filter requests to the backend API based on the user's role and permissions.
 * If the user is an admin, all requests are allowed. If the user is a regular user,
 * they can only access resources that they have created or have been shared with them.
 * The code uses Spring's WebMvcConfigurerAdapter to add the filter to the application's filters list.
 *
 * @author Yurkova Anastasia
 */
@Component
@RequiredArgsConstructor
public class AccessFilter extends OncePerRequestFilter {

    private final UserClient userClient;
    private final CommentService commentService;
    private final NewsService newsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest req,
                                    @NonNull HttpServletResponse resp,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        if (req.getMethod().equalsIgnoreCase("GET") || req.getMethod().equalsIgnoreCase("POST")) {
            filterChain.doFilter(req, resp);
            return;
        }

        String tokenHeader = req.getHeader(HttpHeaders.AUTHORIZATION);
        UserResponse user = userClient.getUserDetails(tokenHeader);
        String username = user.getUsername();

        if (user.getRole().equalsIgnoreCase("ADMIN")) {
            filterChain.doFilter(req, resp);
            return;
        }

        String[] URIElements = req.getRequestURI().split("/");

        if (URIElements[3] .equalsIgnoreCase("comments")) {
            String param = URIElements[4];
            long id = Long.parseLong(param);
            CommentResponse comment = commentService.findById(id);

            if (!comment.getPublishedBy().equalsIgnoreCase(username)) {
                throw new ApplicationException(AuthorizedExceptionCode.ACCESS_DENIED,
                        AuthorizedExceptionMessage.ACCESS_DENIED);
            }

            filterChain.doFilter(req, resp);
            return;
        }

        if (URIElements[3] .equalsIgnoreCase("news")) {
            String param = URIElements[4];
            long id = Long.parseLong(param);
            NewsResponse news = newsService.findById(id);

            if (!news.getPublishedBy().equalsIgnoreCase(username)) {
                throw new ApplicationException(AuthorizedExceptionCode.ACCESS_DENIED,
                        AuthorizedExceptionMessage.ACCESS_DENIED);
            }
            filterChain.doFilter(req, resp);
        }

    }
}
