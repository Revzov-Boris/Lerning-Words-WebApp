package com.example.learning_words_app.config;

import com.example.learning_words_app.entities.Role;
import com.example.learning_words_app.repositories.UserRepository;
import com.example.learning_words_app.services.CustomUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

@Configuration
@Slf4j
public class SecurityConfig {
    private final UserRepository userRepository;

    public SecurityConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, SecurityContextRepository securityContextRepository) throws Exception {
        return http
                .csrf(csrf -> csrf.disable()
                        //csrf.ignoringRequestMatchers("/auth/*", "/") // Отключаем CSRF для регистрации
                )
                .authorizeHttpRequests((authorize) -> authorize
                    // доступ к статическим ресурсам для всех
                    .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                    .permitAll()
                    .requestMatchers("/favicon.ico", "/error")
                    .permitAll()
                    // публичные страницы
                    .requestMatchers("/", "/auth/*", "/languages", "/categories/*", "/categories", "/audio/**")
                    .permitAll()
                    // информация о пользователе только для авторизированных
                    .requestMatchers("/users/**")
                    .authenticated()
                    // редактирование слов, категорий, языков только для админа
                    .requestMatchers("/languages/admin/**", "/categories/admin/**", "/words/admin/**")
                    .hasRole(Role.ADMIN.name())
                    // остальные запросы только для авторизированных
                    .anyRequest()
                    .authenticated()
                )
                .formLogin(form -> form
                    .loginPage("/auth/login")
                    .defaultSuccessUrl("/users/profile")
                    .usernameParameter("nickname") // как в html-форме
                    .passwordParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY) // стандартный: password
                    .failureForwardUrl("/auth/login-error")
                    .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/auth/logout")
                        .logoutSuccessUrl("/") // после выхода - на главную
                    .permitAll()
                )
                .securityContext(securityContext -> securityContext
                        .securityContextRepository(securityContextRepository))
                .build();
    }


    @Bean
    public SecurityContextRepository securityContextRepository() {
        return new DelegatingSecurityContextRepository(
                new RequestAttributeSecurityContextRepository(),
                new HttpSessionSecurityContextRepository()
        );
    }


    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService(userRepository);
    }
}
