package np.com.kshitij.template.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import np.com.kshitij.commons.ErrorResponse;
import np.com.kshitij.template.beans.JwtHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RequiredArgsConstructor
public class AuthorizationFilter extends OncePerRequestFilter {
    private static final String BEARER = "Bearer ";
    private final JwtHelper jwtHelper;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (StringUtils.isNotBlank(authorizationHeader)
                && authorizationHeader.startsWith(BEARER)) {
            try {
                String token = authorizationHeader.substring(BEARER.length());
                UsernamePasswordAuthenticationToken authenticationToken = jwtHelper.verifyToken(token);
                log.trace("Authorized request with : {}", authenticationToken.getPrincipal());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            } catch (Exception e) {
                log.error("Error authorizing the request : {}", e.getMessage());
                ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                new ObjectMapper().writeValue(response.getOutputStream(), errorResponse);
            }
        }
        filterChain.doFilter(request, response);

    }
}
