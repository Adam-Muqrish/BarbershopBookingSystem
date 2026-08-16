package com.heroku.java.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                http
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers("/", "/index", "/register", "/adminLogin", "/login",
                                                                "/error")
                                                .permitAll()
                                                .requestMatchers("/auth/**", "/staffAuth/**").permitAll()
                                                .requestMatchers("/css/**", "/js/**", "/images/**", "/resources/**",
                                                                "/uploads/**", "/assetsAdmin/**",
                                                                "/resources/assetsAdmin/**",
                                                                "/webjars/**", "/favicon.ico",
                                                                "/image/**")
                                                .permitAll()
                                                .requestMatchers("/admin/**", "/adminIndex").hasRole("ADMIN")
                                                .requestMatchers("/barber/**").hasRole("BARBER")
                                                .requestMatchers("/profile", "/edit-profile", "/update-profile",
                                                                "/view-appointment", "/appointment-history",
                                                                "/booking/**",
                                                                "/payment/**", "/processPayment", "/receipt/**",
                                                                "/feedback/**",
                                                                "/cancel-appointment", "/edit-appointment",
                                                                "/update-appointment")
                                                .authenticated()
                                                .anyRequest().authenticated())
                                .formLogin(form -> form
                                                .loginPage("/login")
                                                .permitAll())
                                .exceptionHandling(ex -> ex
                                                .authenticationEntryPoint((request, response, authException) -> {
                                                        String uri = request.getRequestURI();
                                                        if (uri.startsWith("/admin") || uri.startsWith("/barber")
                                                                        || uri.startsWith("/listCustomer")
                                                                        || uri.startsWith("/listBarber")
                                                                        || uri.startsWith("/listAppointment")) {
                                                                response.sendRedirect(request.getContextPath() + "/adminLogin");
                                                        } else {
                                                                response.sendRedirect(request.getContextPath() + "/login");
                                                        }
                                                }))
                                .logout(logout -> logout
                                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                                                .logoutSuccessUrl("/index")
                                                .invalidateHttpSession(true)
                                                .deleteCookies("JSESSIONID")
                                                .permitAll())
                                .csrf(csrf -> csrf
                                                .ignoringRequestMatchers("/staffAuth", "/auth")
                                                .ignoringRequestMatchers("/payment/**", "/booking/**", "/feedback/**")
                                                .ignoringRequestMatchers("/admin/**"));

                return http.build();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }
}