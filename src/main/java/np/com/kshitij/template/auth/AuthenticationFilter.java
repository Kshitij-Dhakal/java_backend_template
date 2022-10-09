package np.com.kshitij.template.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import np.com.kshitij.template.beans.JwtHelper;
import np.com.kshitij.template.user.User;
import org.apache.commons.io.IOUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtHelper jwtHelper;
    private final ObjectMapper objectMapper;
    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String contentType = request.getContentType();
        UsernamePasswordAuthenticationToken token;
        if (Objects.equals(contentType, APPLICATION_JSON_VALUE)) {
            try {
                var user = objectMapper
                        .readValue(IOUtils.toString(request.getReader()),
                                User.class);
                token = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
            } catch (IOException e) {
                token = null;
            }
        } else {
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            token = new UsernamePasswordAuthenticationToken(email, password);
        }
        return authenticationManager.authenticate(token);
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult)
            throws IOException {
        String userId = (String) authResult.getPrincipal();
        //noinspection unchecked
        List<GrantedAuthority> authorities = (List<GrantedAuthority>) authResult.getAuthorities();
        String at = jwtHelper.generateAccessToken(userId, authorities, request.getRequestURL().toString());
        String rt = jwtHelper.generateRefreshToken(userId, authorities, request.getRequestURL().toString());

        Map<String, String> tokens = new LinkedHashMap<>();
        tokens.put("access_token", at);
        tokens.put("refresh_token", rt);
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }
}
