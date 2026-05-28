package org.example.projetfinal.Security;

import org.example.projetfinal.Services.UsersService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilter securityFilter(){
        return new SecurityFilter();
    }

    @Bean
    public UsersService usersService(){
        return new UsersService();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider(usersService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder()); // vérifier le mdp
        return daoAuthenticationProvider;
    }
    @Bean
    public SecurityFilterChain apiSecurity(HttpSecurity http) throws Exception {

        return http
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                .exceptionHandling(excHand -> excHand.accessDeniedPage("/error/403"))
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers(org.springframework.http.HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/", "/api/auth/**","/api/categories/**","/api/products/**","/error/**", "/images/**").permitAll()
                        .requestMatchers("/api/admin/**").hasAuthority("ADMIN")
                        .requestMatchers("/api/user/**").hasAnyRole("USER", "ADMIN")
                        //.requestMatchers("/file/upload").hasAnyAuthority("ADMIN","ENTREPRISE")
                        .anyRequest().authenticated()
                )
                //.exceptionHandling(ex -> ex.accessDeniedHandler(accessDeniedHandler()))
                .cors(Customizer.withDefaults())
                .addFilterBefore(securityFilter(), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // Autoriser votre port Angular
        config.setAllowedOrigins(Arrays.asList("http://localhost:4200"));

        // AJOUT DE "PATCH" ICI - C'était la cause principale du blocage
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS", "HEAD"));

        // Autoriser les headers nécessaires pour le JWT
        config.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Authorization", "X-Requested-With"));

        // Permettre l'envoi du token dans les headers
        config.setAllowCredentials(true);

        source.registerCorsConfiguration("/**", config);
        return source;
    }

}
