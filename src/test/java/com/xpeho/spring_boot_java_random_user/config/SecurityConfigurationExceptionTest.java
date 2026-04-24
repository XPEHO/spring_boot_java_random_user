package com.xpeho.spring_boot_java_random_user.config;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SecurityConfigurationExceptionTest {

    @Test
    void shouldExposeMessageAndCause() {
        IllegalStateException cause = new IllegalStateException("boom");
        SecurityConfigurationException exception = new SecurityConfigurationException("Failed to build Spring Security filter chain", cause);

        assertThat(exception)
                .hasMessage("Failed to build Spring Security filter chain")
                .hasCause(cause);
    }
}