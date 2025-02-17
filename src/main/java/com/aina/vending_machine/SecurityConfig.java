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
//    // Security Filter Chain bean configuration
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                // Disable CSRF (for APIs)
//                .csrf(csrf -> csrf.disable())
//
//                // Define the authorization rules
//                .authorizeHttpRequests(authz -> authz
//                        .requestMatchers("/api/**").authenticated()  // Secure /api/** endpoints
//                        .requestMatchers("/public/**").permitAll()   // Public endpoints
//                )
//
//                // Use HTTP Basic Authentication
//                .httpBasic(httpBasic -> httpBasic.disable());  // Disable basic HTTP auth (if needed, you can configure it)
//
//        return http.build();
//    }
//
//    // PasswordEncoder Bean (for BCrypt encryption)
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}
