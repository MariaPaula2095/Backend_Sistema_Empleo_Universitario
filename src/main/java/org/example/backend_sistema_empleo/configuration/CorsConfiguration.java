package org.example.backend_sistema_empleo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // Le indica a Spring que esta clase contiene configuración del proyecto.

public class CorsConfiguration {

    @Bean // Indica que el método devolverá un bean administrado por Spring.
    public WebMvcConfigurer corsConfigurer() {  // Declara un método que retorna un objeto de tipo WebMvcConfigurer.
        return new WebMvcConfigurer() {
            // Crea e retorna una implementación anónima de WebMvcConfigurer.

            @Override // Indica que este método sobrescribe uno definido en la interfaz.
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**") // Aplica esta configuración a todas las rutas que empiecen por /api/.
                        .allowedOrigins("http://192.168.18.17:8080") // Permite solicitudes que vengan desde esa dirección específica.
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Permite esos métodos HTTP en las peticiones al backend.
                        .allowedHeaders("*") // Permite cualquier encabezado HTTP en la solicitud.
                        .allowCredentials(true);  // Permite enviar credenciales como cookies o tokens de sesión.

            }
        };
    }
}



