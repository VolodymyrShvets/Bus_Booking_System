package com.booking.system.config;

import com.booking.system.security.userdetails.MyUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SpringSecurityConfig {
    private MyUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        String[] staticResources = {
                "/", "/home**", "/sign-in**", "/sign-up**", "/assets/**", "/bus/search**", "/preorder/**", "/checkout/**", "/payment**", "/error"
        };

        http
                .userDetailsService(userDetailsService)
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers(staticResources)
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/sign-in")
                .defaultSuccessUrl("/", true)
                .failureUrl("/sign-in?error=true")
                .and()
                .logout()
                .permitAll()
                .logoutSuccessUrl("/?logout=true");
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() { // TODO move this bean to Config class
        return new BCryptPasswordEncoder(11);
    }
}
