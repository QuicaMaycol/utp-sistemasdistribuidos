package hexagonal_PC3.hexagonal.auth;

import hexagonal_PC3.hexagonal.auth.aplicacion.port.output.UsuarioRepositoryPort;
import hexagonal_PC3.hexagonal.auth.dominio.Usuario;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableDiscoveryClient
public class AuthServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner initData(UsuarioRepositoryPort usuarioRepositoryPort, PasswordEncoder passwordEncoder) {
        return args -> {
            String testUsername = "admin";
            String testPassword = "123456";

            try {
                if (usuarioRepositoryPort.findByUsername(testUsername).isEmpty()) {
                    Usuario nuevoUsuario = Usuario.builder()
                            .username(testUsername)
                            .password(passwordEncoder.encode(testPassword))
                            .rol("ADMIN")
                            .mfaEnabled(false)
                            .build();

                    usuarioRepositoryPort.save(nuevoUsuario);
                    System.out.println("[AUTH STARTUP] Usuario de prueba 'admin' creado en Supabase con contraseña encriptada.");
                } else {
                    System.out.println("[AUTH STARTUP] El usuario 'admin' ya existe en Supabase.");
                }
            } catch (Exception e) {
                System.err.println("[AUTH STARTUP ERROR] No se pudo verificar/inicializar usuario en Supabase: " + e.getMessage());
            }
        };
    }
}
