package com.challenge.module.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.config.PageableHandlerMethodArgumentResolverCustomizer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EntityScan(basePackages = "com.challenge.module.entities")
@EnableJpaRepositories(basePackages = "com.challenge.module.repositories")
@EnableScheduling
public class ChallengeApplicationConfig {

    private static final int MAXIMUM_PAGE_SIZE = 5;

    @Value("${spring.security.user.name}")
    private String username;

    @Value("${spring.security.user.password}")
    private String password;

    @Configuration
    static class PageableCustomizer implements PageableHandlerMethodArgumentResolverCustomizer {
        @Override
        public void customize(final PageableHandlerMethodArgumentResolver resolver) {
            resolver.setMaxPageSize(MAXIMUM_PAGE_SIZE);
        }
    }

    @Bean
    public InMemoryUserDetailsManager userDetails() {
        UserDetails user = User.builder()
                .username(username)
                .password(passwordEncoder().encode(password))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(final AuthenticationConfiguration builder) throws Exception {
        return builder.getAuthenticationManager();
    }
}
