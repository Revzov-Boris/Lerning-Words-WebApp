package com.example.learning_words_app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf ->
                        csrf.ignoringRequestMatchers("/auth/*", "/") // Отключаем CSRF для регистрации
                )
                .authorizeHttpRequests((authorize) -> authorize
                    .requestMatchers("/js/*", "/css/**")
                    .permitAll()
                    .requestMatchers("/", "/auth/registration", "/auth/login", "/languages", "/categories/*", "/categories", "/audio/**")
                    .permitAll()

                    .requestMatchers("/users/**")
                    .authenticated()
                    .anyRequest()
                    .authenticated()

                )
                .anonymous(anonymous -> anonymous
                        .principal("anonymousUser")
                        .authorities("ROLE_ANONYMOUS")
                )
                .formLogin(form -> form
                    .loginPage("/auth/login")
                    .loginProcessingUrl("/auth/login") // URL для POST запроса логина
                    .defaultSuccessUrl("/users/profile")
                    .permitAll()
                )
                .logout(logout -> logout
                    .permitAll()
                )
                .build();
    }
}
