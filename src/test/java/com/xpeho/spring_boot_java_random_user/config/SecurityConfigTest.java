package com.xpeho.spring_boot_java_random_user.config;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SecurityConfigTest {

    private final SecurityConfig securityConfig = new SecurityConfig();

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(securityConfig, "adminUsername", "admin");
        ReflectionTestUtils.setField(securityConfig, "adminPassword", "admin123");
        ReflectionTestUtils.setField(securityConfig, "userUsername", "apiuser");
        ReflectionTestUtils.setField(securityConfig, "userPassword", "changeit");
        ReflectionTestUtils.setField(securityConfig, "testUsername", "testuser");
        ReflectionTestUtils.setField(securityConfig, "testPassword", "testpass");
    }

    @Test
    void shouldEncodePasswordsWithBcrypt() {
        PasswordEncoder passwordEncoder = securityConfig.passwordEncoder();

        assertThat(passwordEncoder).isInstanceOf(BCryptPasswordEncoder.class);
        assertThat(passwordEncoder.matches("admin123", passwordEncoder.encode("admin123"))).isTrue();
    }

    @Test
    void shouldCreateInMemoryUsersWithExpectedRoles() {
        PasswordEncoder passwordEncoder = securityConfig.passwordEncoder();

        UserDetailsService userDetailsService = securityConfig.userDetailsService(passwordEncoder);

        UserDetails admin = userDetailsService.loadUserByUsername("admin");
        UserDetails user = userDetailsService.loadUserByUsername("apiuser");
        UserDetails test = userDetailsService.loadUserByUsername("testuser");

        assertThat(admin.getAuthorities()).extracting("authority").containsExactly("ROLE_ADMIN");
        assertThat(user.getAuthorities()).extracting("authority").containsExactly("ROLE_USER");
        assertThat(test.getAuthorities()).extracting("authority").containsExactly("ROLE_TEST");
    }

    @Test
    void shouldRecognizeBasicAuthRequestsOnRandomUsersPath() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setServletPath("/random-users/123");
        request.addHeader("Authorization", "Basic dGVzdDp0ZXN0");

        boolean result = ReflectionTestUtils.invokeMethod(securityConfig, "isBasicAuthRequest", request);

        assertThat(result).isTrue();
    }

    @Test
    void shouldRejectNonBasicAuthOrNonRandomUsersRequests() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setServletPath("/health");
        request.addHeader("Authorization", "Bearer token");

        boolean result = ReflectionTestUtils.invokeMethod(securityConfig, "isBasicAuthRequest", request);

        assertThat(result).isFalse();
    }

    @Test
    void shouldRejectRandomUsersRequestWithoutAuthHeader() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setServletPath("/random-users/123");

        boolean result = ReflectionTestUtils.invokeMethod(securityConfig, "isBasicAuthRequest", request);

        assertThat(result).isFalse();
    }

    @Test
    void shouldRejectRandomUsersRequestWithNonBasicAuthHeader() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setServletPath("/random-users/123");
        request.addHeader("Authorization", "Bearer token");

        boolean result = ReflectionTestUtils.invokeMethod(securityConfig, "isBasicAuthRequest", request);

        assertThat(result).isFalse();
    }

    @Test
    void shouldRejectWhenServletPathIsNull() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getServletPath()).thenReturn(null);
        when(request.getHeader("Authorization")).thenReturn("Basic dGVzdDp0ZXN0");

        boolean result = ReflectionTestUtils.invokeMethod(securityConfig, "isBasicAuthRequest", request);

        assertThat(result).isFalse();
    }

    @Test
    void shouldExposePublicEndpoints() {
        String[] endpoints = ReflectionTestUtils.invokeMethod(securityConfig, "getPublicEndpoints");

        assertThat(endpoints).contains(
                "/api/**",
                "/swagger-ui/**",
                "/swagger-ui.html",
                "/v3/api-docs/**",
                "/actuator/health"
        );
    }

    @Test
    void shouldWrapFilterChainConfigurationException() {
        assertThatThrownBy(() -> securityConfig.securityFilterChain(null))
                .isInstanceOf(SecurityConfigurationException.class)
                .hasMessage("Failed to build Spring Security filter chain")
                .hasCauseInstanceOf(NullPointerException.class);
    }
}
