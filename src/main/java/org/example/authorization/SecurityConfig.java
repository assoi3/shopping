package org.example.authorization;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        HttpSecurity httpSecurity = http.authorizeRequests(authorizeRequests ->
                authorizeRequests
                        .requestMatchers("/", "/login").permitAll()
                        .anyRequest().authenticated()
        );
        http.oauth2Login(oauth2Login ->
                oauth2Login
                        .loginPage("/login")
                        .defaultSuccessUrl("/home", true)
        );// Configure OAuth2 login
        return http.build();
    }
}
