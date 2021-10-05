package com.SimbirSoft.Practice.config.filter;


import com.SimbirSoft.Practice.exception.InvalidTokenException;
import com.SimbirSoft.Practice.exception.NotFoundException;
import com.SimbirSoft.Practice.model.User;
import com.SimbirSoft.Practice.model.enums.Role;
import com.SimbirSoft.Practice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collections;

import static org.springframework.util.StringUtils.hasText;

@Component
@AllArgsConstructor
public class JwtFilter extends GenericFilterBean {

    private static final String AUTHORIZATION = "Authorization";

    private final UserService userService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException, IOException, ServletException {
        if (servletRequest.getAttribute("user") != null) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        String token = getTokenFromRequest((HttpServletRequest) servletRequest);
        try {
            User user = userService.getByAuthToken(token);
            servletRequest.setAttribute("user", user);
            Role authorities = getAuthorities(user);
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, null, Collections.singleton(authorities));
            SecurityContextHolder.getContext().setAuthentication(auth);
        } catch (InvalidTokenException | NotFoundException ignored) {
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    public static Role getAuthorities(User user) {
        return user.getRole();
    }

    public static String getTokenFromRequest(HttpServletRequest request) {
        String bearer = request.getHeader(AUTHORIZATION);
        if (hasText(bearer) && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }
}