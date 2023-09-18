package com.ulises.backend.usersapp.demo.auth.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ulises.backend.usersapp.demo.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import static com.ulises.backend.usersapp.demo.auth.TokenJwtConfig.*;

import java.io.IOException;
import java.util.*;

public class  JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {


    private AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override/*SE HACE EL LOGIN*/
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        User user = null;
        String username = null;
        String password = null;

        try {                                /*OBTIENE LOS DATOS QUE ESTAN EN JSON Y LOS POBLAMOS*/
            user = new ObjectMapper().readValue(request.getInputStream(), User.class);
            username = user.getUsername();
            password = user.getPassword();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);

        return authenticationManager.authenticate(authToken);/*AUTENTICACION*/
    }

    @Override/*SI SALE BIEN, UNA RESPUESTA*/
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {

                                            /*CASTEAMOS AL USER DE SPRING SECURITY*/
        String username = ((org.springframework.security.core.userdetails.User) authResult.getPrincipal())
                .getUsername();

        Collection<? extends GrantedAuthority> roles = authResult.getAuthorities();

        boolean isAdmin = roles.stream().anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));

        Claims claims= Jwts.claims();/* ObjectMapper CONVIERTE UN JSON A UN OBJETO  */
        claims.put("authorities", new ObjectMapper().writeValueAsString(roles));
        claims.put("isAdmin", isAdmin);

        String token = Jwts.builder()
                       .setClaims(claims)
                       .setSubject(username)
                       .signWith(SECRET_KEY)
                       .setIssuedAt(new Date()).setExpiration(new Date(new Date().getTime() + 3600000))/*FECHA DE EXPIRACION DEL TOKEN EN MILISEGUNDOS*/
                       .compact();

        response.addHeader( HEADER_AUTHORIZATION, PREFIX_TOKEN + token);

        Map<String, Object> body = new HashMap<>();
        body.put("token", token);
        body.put("message", String.format("Hola %s, sos pete", username));
        body.put("username", username);
                                    /*TRANSFORMA AL MAP EN UN JSON ObjectMapper().writeValueAsString()*/
        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setStatus(200);
        response.setContentType("application/json");
    }


    @Override/*SI SALE MAL, UNA RESPUESTA*/
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed)
            throws IOException, ServletException {

        Map<String, Object> body = new HashMap<>();

        body.put("message", "Error en la autenticacion username o password incorrecto");
        body.put("error", failed.getMessage());

        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setStatus(401);
        response.setContentType("application/json");
    }
}
