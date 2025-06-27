//package com.aina.vending_machine;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable() // Disable CSRF protection for API calls
//                .authorizeHttpRequests()
//                .requestMatchers("/api/login/admin").permitAll()  // Allow access to the login endpoint
//                .anyRequest().authenticated()  // All other requests must be authenticated
//                .and()
//                .formLogin().disable(); // Disable Spring's default login page
//
//        return http.build();
//    }
//
//    // Password Encoder Bean (if needed)
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}
