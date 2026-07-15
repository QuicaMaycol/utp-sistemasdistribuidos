package hexagonal_PC3.hexagonal.auth.aplicacion.service;

import hexagonal_PC3.hexagonal.auth.aplicacion.port.input.LoginUseCase;
import hexagonal_PC3.hexagonal.auth.aplicacion.port.output.UsuarioRepositoryPort;
import hexagonal_PC3.hexagonal.auth.dominio.Usuario;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;

public class LoginService implements LoginUseCase {

    private final UsuarioRepositoryPort usuarioRepositoryPort;
    private final PasswordEncoder passwordEncoder;

    public LoginService(UsuarioRepositoryPort usuarioRepositoryPort, PasswordEncoder passwordEncoder) {
        this.usuarioRepositoryPort = usuarioRepositoryPort;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean login(String username, String password) {
        Optional<Usuario> usuarioOpt = usuarioRepositoryPort.findByUsername(username);
        if (usuarioOpt.isEmpty()) {
            return false;
        }
        Usuario usuario = usuarioOpt.get();
        return passwordEncoder.matches(password, usuario.getPassword());
    }
}
