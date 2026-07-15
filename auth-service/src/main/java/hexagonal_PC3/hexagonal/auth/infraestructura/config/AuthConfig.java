package hexagonal_PC3.hexagonal.auth.infraestructura.config;

import hexagonal_PC3.hexagonal.auth.aplicacion.port.input.LoginUseCase;
import hexagonal_PC3.hexagonal.auth.aplicacion.port.input.MfaUseCase;
import hexagonal_PC3.hexagonal.auth.aplicacion.port.output.UsuarioRepositoryPort;
import hexagonal_PC3.hexagonal.auth.aplicacion.service.LoginService;
import hexagonal_PC3.hexagonal.auth.aplicacion.service.MfaService;
import hexagonal_PC3.hexagonal.auth.infraestructura.adapter.output.SupabaseUsuarioAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AuthConfig {

    @Bean
    public UsuarioRepositoryPort usuarioRepositoryPort() {
        return new SupabaseUsuarioAdapter();
    }

    @Bean
    public LoginUseCase loginUseCase(UsuarioRepositoryPort usuarioRepositoryPort, PasswordEncoder passwordEncoder) {
        return new LoginService(usuarioRepositoryPort, passwordEncoder);
    }

    @Bean
    public MfaUseCase mfaUseCase(UsuarioRepositoryPort usuarioRepositoryPort) {
        return new MfaService(usuarioRepositoryPort);
    }
}
