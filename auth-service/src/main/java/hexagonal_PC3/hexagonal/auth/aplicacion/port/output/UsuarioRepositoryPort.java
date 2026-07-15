package hexagonal_PC3.hexagonal.auth.aplicacion.port.output;

import hexagonal_PC3.hexagonal.auth.dominio.Usuario;
import java.util.Optional;

public interface UsuarioRepositoryPort {
    Optional<Usuario> findByUsername(String username);
    Usuario save(Usuario usuario);
}
