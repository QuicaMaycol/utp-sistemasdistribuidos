package hexagonal_PC3.hexagonal.auth.aplicacion.port.input;

public interface MfaUseCase {
    String generateMfaSetup(String username);
    boolean verifyAndEnableMfa(String username, int code);
    boolean verifyMfaCode(String username, int code);
}
