package com.ulises.backend.usersapp.demo.auth;

import com.ulises.backend.usersapp.demo.auth.filters.JwtAuthenticationFilter;
import com.ulises.backend.usersapp.demo.auth.filters.JwtValidationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfig {

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager() throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        return http.authorizeHttpRequests()
                .requestMatchers(HttpMethod.GET, "/users").permitAll()
                .anyRequest().authenticated()/*CUALQUIER OTRO METODO QUE NO SEA EL DE LA LINEA 16 NECESITA AUTENTICACION*/
                .and()/*PARA PONBER OTRO TIPO DE CONFIG*/
                .addFilter(new JwtAuthenticationFilter(authenticationConfiguration.getAuthenticationManager()))
                .addFilter(new JwtValidationFilter(authenticationConfiguration.getAuthenticationManager()))
                .csrf(config -> config.disable())/*CSRF (Cross-Site Request Forgery), SE UTILIZA CUANDO USAMOS SPRING CON VISTAS, LO TENEMOS QUE DESACTIVAR*/
                .sessionManagement(managment -> managment.sessionCreationPolicy(SessionCreationPolicy.STATELESS))/*LA SESSION STORAGE SE UTILIZA DEL FRONT, NO VAMOS A MANEJAR LA SESION CON ESTADO, LO VAMOS A ENVIAR CON EL TOKEN*/
                .build();

    }
}
