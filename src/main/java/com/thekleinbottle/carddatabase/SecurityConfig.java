package com.thekleinbottle.carddatabase;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static org.springframework.security.config.Customizer.withDefaults;

import com.thekleinbottle.carddatabase.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final UserDetailsServiceImpl userDetailsService;
    private final AuthenticationFilter authenticationFilter;
    private final AuthEntryPoint exceptionHandler;

    public SecurityConfig(UserDetailsServiceImpl userDetailsService, 
            AuthenticationFilter authenticationFilter, AuthEntryPoint exceptionHandler) {
        this.userDetailsService = userDetailsService;
        this.authenticationFilter = authenticationFilter;
        this.exceptionHandler = exceptionHandler;
    }

    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
    

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable).cors(withDefaults())
            .authorizeHttpRequests((authorizeHttpRequests) -> 
                authorizeHttpRequests.anyRequest().permitAll());

        // http.csrf((csrf) -> csrf.disable()).cors(withDefaults())
        //     .sessionManagement((sessionManagement) -> 
        //         sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        //     .authorizeHttpRequests((authorizeHttpRequests) -> 
        //         authorizeHttpRequests.requestMatchers(HttpMethod.POST, "/login").permitAll().anyRequest().authenticated())
        //         .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
        //         .exceptionHandling((exceptionHandling) -> exceptionHandling.authenticationEntryPoint(exceptionHandler));

        http.headers(header -> header.addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Origin", "*")));
        
        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(List.of("*"));
        configuration.setAllowedHeaders(List.of("*"));

        configuration.setAllowCredentials(false);
        // configuration.setExposedHeaders(Arrays.asList("*"));
        configuration.applyPermitDefaultValues();

        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
