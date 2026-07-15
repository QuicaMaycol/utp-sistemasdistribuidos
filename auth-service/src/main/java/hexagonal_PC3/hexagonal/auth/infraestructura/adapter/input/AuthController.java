package hexagonal_PC3.hexagonal.auth.infraestructura.adapter.input;

import hexagonal_PC3.hexagonal.auth.aplicacion.port.input.LoginUseCase;
import hexagonal_PC3.hexagonal.auth.aplicacion.port.input.MfaUseCase;
import hexagonal_PC3.hexagonal.auth.aplicacion.port.output.UsuarioRepositoryPort;
import hexagonal_PC3.hexagonal.auth.dominio.Usuario;
import hexagonal_PC3.hexagonal.auth.infraestructura.config.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final LoginUseCase loginUseCase;
    private final MfaUseCase mfaUseCase;
    private final UsuarioRepositoryPort usuarioRepositoryPort;
    private final JwtUtil jwtUtil;

    public AuthController(
            LoginUseCase loginUseCase,
            MfaUseCase mfaUseCase,
            UsuarioRepositoryPort usuarioRepositoryPort,
            JwtUtil jwtUtil) {
        this.loginUseCase = loginUseCase;
        this.mfaUseCase = mfaUseCase;
        this.usuarioRepositoryPort = usuarioRepositoryPort;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        boolean success = loginUseCase.login(request.getUsername(), request.getPassword());
        if (success) {
            Optional<Usuario> usuarioOpt = usuarioRepositoryPort.findByUsername(request.getUsername());
            if (usuarioOpt.isPresent()) {
                Usuario usuario = usuarioOpt.get();
                if (usuario.isMfaEnabled()) {
                    LoginResponse response = new LoginResponse();
                    response.setMensaje("Doble factor requerido.");
                    response.setMfaRequired(true);
                    response.setUsername(usuario.getUsername());
                    return ResponseEntity.ok(response);
                } else {
                    String token = jwtUtil.generateToken(usuario.getUsername(), usuario.getRol());
                    LoginResponse response = new LoginResponse();
                    response.setMensaje("Login exitoso.");
                    response.setMfaRequired(false);
                    response.setUsername(usuario.getUsername());
                    response.setToken(token);
                    return ResponseEntity.ok(response);
                }
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponse("Credenciales incorrectas.", false, null, null));
    }

    @GetMapping("/mfa/setup")
    public ResponseEntity<?> setupMfa(@RequestParam String username) {
        try {
            String qrCodeUrl = mfaUseCase.generateMfaSetup(username);
            Optional<Usuario> usuarioOpt = usuarioRepositoryPort.findByUsername(username);
            String secret = usuarioOpt.map(Usuario::getMfaSecret).orElse("");

            return ResponseEntity.ok(new MfaSetupResponse(qrCodeUrl, secret));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al generar configuración 2FA: " + e.getMessage());
        }
    }

    @PostMapping("/mfa/enable")
    public ResponseEntity<?> enableMfa(@RequestBody MfaVerifyRequest request) {
        boolean success = mfaUseCase.verifyAndEnableMfa(request.getUsername(), request.getCode());
        if (success) {
            return ResponseEntity.ok(new SimpleMessageResponse("2FA habilitado correctamente."));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new SimpleMessageResponse("Código incorrecto, intente de nuevo."));
    }

    @PostMapping("/mfa/verify")
    public ResponseEntity<?> verifyMfaLogin(@RequestBody MfaVerifyRequest request) {
        boolean success = mfaUseCase.verifyMfaCode(request.getUsername(), request.getCode());
        if (success) {
            Optional<Usuario> usuarioOpt = usuarioRepositoryPort.findByUsername(request.getUsername());
            String rol = usuarioOpt.map(Usuario::getRol).orElse("USER");
            String token = jwtUtil.generateToken(request.getUsername(), rol);

            return ResponseEntity.ok(new LoginResponse("Login 2FA exitoso.", false, request.getUsername(), token));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new SimpleMessageResponse("Código incorrecto."));
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginRequest {
        private String username;
        private String password;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginResponse {
        private String mensaje;
        private boolean mfaRequired;
        private String username;
        private String token;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MfaSetupResponse {
        private String qrCodeUrl;
        private String secret;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MfaVerifyRequest {
        private String username;
        private int code;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SimpleMessageResponse {
        private String mensaje;
    }
}
