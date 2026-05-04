package com.xpeho.spring_boot_java_random_user.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.NullSecurityContextRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        private static final String RANDOM_USERS_PATH = "/random-users/**";
        private static final String RANDOM_USERS_PREFIX = "/random-users";
        private static final String ADMIN_ROLE = "ADMIN";

        @Value("${app.security.admin.username}")
        private String adminUsername;

        @Value("${app.security.admin.password}")
        private String adminPassword;

        @Value("${app.security.user.username}")
        private String userUsername;

        @Value("${app.security.user.password}")
        private String userPassword;

        @Value("${app.security.test.username}")
        private String testUsername;

        @Value("${app.security.test.password}")
        private String testPassword;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        try {
                return http
                    .csrf(csrf -> csrf.ignoringRequestMatchers(this::isBasicAuthRequest))
                    .securityContext(context -> context.securityContextRepository(new NullSecurityContextRepository()))
                    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .httpBasic(Customizer.withDefaults())
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers(getPublicEndpoints()).permitAll()
                            .requestMatchers(HttpMethod.GET, RANDOM_USERS_PATH).hasAnyRole(ADMIN_ROLE, "USER", "TEST")
                            .requestMatchers(HttpMethod.POST, RANDOM_USERS_PATH).hasRole(ADMIN_ROLE)
                            .requestMatchers(HttpMethod.PUT, RANDOM_USERS_PATH).hasRole(ADMIN_ROLE)
                            .requestMatchers(HttpMethod.DELETE, RANDOM_USERS_PATH).hasRole(ADMIN_ROLE)
                            .anyRequest().authenticated()
                    )
                    .build();
        } catch (Exception e) {
            throw new SecurityConfigurationException("Failed to build Spring Security filter chain", e);
        }
    }


    private boolean isBasicAuthRequest(HttpServletRequest request) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String servletPath = request.getServletPath();
        boolean isRandomUsersPath = servletPath != null && servletPath.startsWith(RANDOM_USERS_PREFIX);
        return isRandomUsersPath && authHeader != null && authHeader.startsWith("Basic ");
    }

    private String[] getPublicEndpoints() {
        return new String[]{
                "/api/**",
                "/swagger-ui/**",
                "/swagger-ui.html",
                "/v3/api-docs/**",
                "/actuator/health"
        };
    }

    @Bean
    UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails admin = User.withUsername(adminUsername)
                .password(passwordEncoder.encode(adminPassword))
                .roles(ADMIN_ROLE)
                .build();

        UserDetails user = User.withUsername(userUsername)
                .password(passwordEncoder.encode(userPassword))
                .roles("USER")
                .build();

        UserDetails test = User.withUsername(testUsername)
                .password(passwordEncoder.encode(testPassword))
                .roles("TEST")
                .build();

        return new InMemoryUserDetailsManager(admin, user, test);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
