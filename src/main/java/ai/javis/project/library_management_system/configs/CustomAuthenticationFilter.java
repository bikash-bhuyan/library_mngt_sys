package ai.javis.project.library_management_system.configs;

import ai.javis.project.library_management_system.enums.ResponseMessages;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
public class CustomAuthenticationFilter extends HttpFilter {
    @Value("${app.security.access_token}")
    private String accessToken;
    @Override
    public void doFilter(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws IOException, ServletException {

        String authorizationValue = httpServletRequest.getHeader("Authorization");

        if(authorizationValue == null || !authorizationValue.startsWith("Bearer ")){
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, ResponseMessages.TOKEN_NOT_FOUND.getValue());
            return ;
        }

        String token = authorizationValue.substring(7);

        if(!accessToken.equals(token)){
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, ResponseMessages.TOKEN_IS_INVALID.getValue());
            return ;
        }

        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}
