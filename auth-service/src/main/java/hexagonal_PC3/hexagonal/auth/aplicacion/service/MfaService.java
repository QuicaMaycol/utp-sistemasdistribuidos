package hexagonal_PC3.hexagonal.auth.aplicacion.service;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;
import hexagonal_PC3.hexagonal.auth.aplicacion.port.input.MfaUseCase;
import hexagonal_PC3.hexagonal.auth.aplicacion.port.output.UsuarioRepositoryPort;
import hexagonal_PC3.hexagonal.auth.dominio.Usuario;
import java.util.Optional;

public class MfaService implements MfaUseCase {

    private final GoogleAuthenticator gAuth;
    private final UsuarioRepositoryPort usuarioRepositoryPort;

    public MfaService(UsuarioRepositoryPort usuarioRepositoryPort) {
        this.usuarioRepositoryPort = usuarioRepositoryPort;
        this.gAuth = new GoogleAuthenticator();
    }

    @Override
    public String generateMfaSetup(String username) {
        Optional<Usuario> usuarioOpt = usuarioRepositoryPort.findByUsername(username);
        if (usuarioOpt.isEmpty()) {
            throw new RuntimeException("Usuario no encontrado: " + username);
        }
        Usuario usuario = usuarioOpt.get();

        GoogleAuthenticatorKey credentials = gAuth.createCredentials();
        String secret = credentials.getKey();

        usuario.setMfaSecret(secret);
        usuario.setMfaEnabled(false);
        usuarioRepositoryPort.save(usuario);

        return GoogleAuthenticatorQRGenerator.getOtpAuthURL("TextilRollManager", username, credentials);
    }

    @Override
    public boolean verifyAndEnableMfa(String username, int code) {
        Optional<Usuario> usuarioOpt = usuarioRepositoryPort.findByUsername(username);
        if (usuarioOpt.isEmpty()) {
            return false;
        }
        Usuario usuario = usuarioOpt.get();

        String secret = usuario.getMfaSecret();
        if (secret == null) {
            return false;
        }

        boolean isValid = gAuth.authorize(secret, code);
        if (isValid) {
            usuario.setMfaEnabled(true);
            usuarioRepositoryPort.save(usuario);
            return true;
        }
        return false;
    }

    @Override
    public boolean verifyMfaCode(String username, int code) {
        Optional<Usuario> usuarioOpt = usuarioRepositoryPort.findByUsername(username);
        if (usuarioOpt.isEmpty()) {
            return false;
        }
        Usuario usuario = usuarioOpt.get();

        String secret = usuario.getMfaSecret();
        if (secret == null || !usuario.isMfaEnabled()) {
            return false;
        }

        return gAuth.authorize(secret, code);
    }
}
