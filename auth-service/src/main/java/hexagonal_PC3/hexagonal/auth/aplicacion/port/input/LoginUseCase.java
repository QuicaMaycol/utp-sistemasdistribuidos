package hexagonal_PC3.hexagonal.auth.aplicacion.port.input;

public interface LoginUseCase {
    boolean login(String username, String password);
}
