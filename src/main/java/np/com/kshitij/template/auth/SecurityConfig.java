package np.com.kshitij.template.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import np.com.kshitij.template.beans.JwtHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtHelper jwtHelper;
    private final AuthenticationManager authManager;
    private final ObjectMapper objectMapper;

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        AuthenticationFilter filter = new AuthenticationFilter(jwtHelper, objectMapper, authManager);
        filter.setFilterProcessesUrl("/v1/login");
        http.cors()
                .and()
                .csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .anyRequest()
                .permitAll()
                .and()
                .addFilter(filter)
                .addFilterBefore(new AuthorizationFilter(jwtHelper), AuthenticationFilter.class);
        return http
                .build();
    }
}
