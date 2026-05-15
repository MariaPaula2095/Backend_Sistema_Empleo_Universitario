package org.example.backend_sistema_empleo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtUtil jwtUtil;

    public SecurityConfig(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtFilter jwtFilter() {
        return new JwtFilter(jwtUtil);
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config
    ) throws Exception {

        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .cors(Customizer.withDefaults())

                .csrf(csrf -> csrf.disable())

                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .authorizeHttpRequests(auth -> auth

                        // =========================
                        // SWAGGER
                        // =========================
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).permitAll()

                        // =========================
                        // OPTIONS
                        // =========================
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // =========================
                        // ENDPOINTS PUBLICOS
                        // =========================

                        .requestMatchers("/api/usuarios/guardar").permitAll()
                        .requestMatchers("/api/usuarios/login").permitAll()
                        .requestMatchers("/api/usuarios/recuperar-password").permitAll()

                        .requestMatchers("/api/empresas/login").permitAll()
                        .requestMatchers("/api/empresas/listar").permitAll()
                        .requestMatchers("/api/empresas/top").permitAll()

                        .requestMatchers("/api/empresas-pendientes/enviar").permitAll()

                        .requestMatchers("/api/ofertas/listar").permitAll()
                        .requestMatchers("/api/ofertas/activas").permitAll()

                        // =========================
                        // ADMIN Y EMPRESA
                        // =========================

                        .requestMatchers("/api/usuarios/listar")
                        .hasAnyRole("ADMIN", "EMPRESA")

                        .requestMatchers(HttpMethod.GET,
                                "/api/usuarios/**")
                        .hasAnyRole("ADMIN", "EMPRESA")

                        // =========================
                        // SOLO ADMIN
                        // =========================

                        .requestMatchers("/api/empresas-pendientes/listar")
                        .hasRole("ADMIN")

                        .requestMatchers("/api/empresas-pendientes/aprobar/**")
                        .hasRole("ADMIN")

                        .requestMatchers("/api/empresas-pendientes/rechazar/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.DELETE,
                                        "/api/empresas-pendientes/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET,
                                        "/api/postulaciones/listar")
                        .hasRole("ADMIN")

                        // =========================
                        // EMPRESA
                        // =========================

                        .requestMatchers(HttpMethod.PUT,
                                "/api/postulaciones/actualizar/**")
                        .hasAnyRole("ADMIN", "EMPRESA", "ESTUDIANTE")

                        .requestMatchers(HttpMethod.GET,
                                "/api/postulaciones/oferta/**")
                        .hasAnyRole("ADMIN", "EMPRESA")

                        .requestMatchers(HttpMethod.POST,
                                "/api/ofertas/guardar")
                        .hasRole("EMPRESA")

                        // =========================
                        // ESTUDIANTE
                        // =========================

                        .requestMatchers(HttpMethod.POST,
                                "/api/postulaciones/guardar")
                        .hasRole("ESTUDIANTE")

                        // =========================
                        // CUALQUIER OTRO ENDPOINT
                        // =========================

                        .anyRequest().authenticated()
                )

                .addFilterBefore(
                        jwtFilter(),
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}