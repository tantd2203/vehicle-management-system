package com.assignment.vehiclemanagementsystem.config;

import com.assignment.vehiclemanagementsystem.service.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;

import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class SecurityConfiguration {

    private final UserService userService;

    private final PreFilter preFilter;

    private static final String[] WHITE_LIST_URL = {"/auth/**",
            "/actuator/**",
            "/mail/**",
            "/redis/**",
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/webjars/**",
            "/swagger-ui.html"
    };


    /*
     * @author: TanTD1
     * @since: 06/08/2024 10:13
     * @description: encode password

     *
     * */
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*
     * @author: TanTD1
     * @since: 06/08/2024 10:13
     * @description:  access request in while list and save token in  session
     * sessionPolicy STATELESS : no session will be created and save token in session
     * authenticationProvider : block request before it is processed and check token
     * */
    @Bean
    public SecurityFilterChain configure(@NonNull HttpSecurity http) throws Exception {
        log.warn("Configuring security filters tancute  ");
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests.requestMatchers(WHITE_LIST_URL).permitAll().anyRequest().authenticated())
                .sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS))
                .authenticationProvider(provider()).addFilterBefore(preFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return webSecurity ->
                webSecurity.ignoring()
                        .requestMatchers("/actuator/**", "/v3/**", "/webjars/**", "/swagger-ui*/*swagger-initializer.js", "/swagger-ui*/**");
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /*
     * @author: TanTD1
     * @since: 06/08/2024 10:13
     * @description:   provides authentication
     * @update: find user by username and check password
     *
     * */
    @Bean
    public AuthenticationProvider provider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userService.userDetailsService());
        provider.setPasswordEncoder(getPasswordEncoder());

        return provider;
    }

}
