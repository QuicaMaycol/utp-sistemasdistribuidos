package hexagonal_PC3.hexagonal.auth.infraestructura.adapter.output;

import hexagonal_PC3.hexagonal.auth.aplicacion.port.output.UsuarioRepositoryPort;
import hexagonal_PC3.hexagonal.auth.dominio.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import java.util.*;

public class SupabaseUsuarioAdapter implements UsuarioRepositoryPort {

    private final RestTemplate restTemplate;

    @Value("${supabase.url}")
    private String supabaseUrl;

    @Value("${supabase.anon-key}")
    private String anonKey;

    @Value("${supabase.schema}")
    private String schema;

    public SupabaseUsuarioAdapter() {
        this.restTemplate = new RestTemplate();
    }

    private HttpHeaders getHeaders(boolean isWrite) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("apikey", anonKey);
        headers.set("Authorization", "Bearer " + anonKey);
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (isWrite) {
            headers.set("Content-Profile", schema);
            headers.set("Prefer", "return=representation");
        } else {
            headers.set("Accept-Profile", schema);
        }
        return headers;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Optional<Usuario> findByUsername(String username) {
        String url = supabaseUrl + "/rest/v1/usuarios?username=eq." + username;
        HttpEntity<?> entity = new HttpEntity<>(getHeaders(false));
        try {
            ResponseEntity<Map[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map[].class);
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null && response.getBody().length > 0) {
                Map<String, Object> map = response.getBody()[0];
                return Optional.of(mapToDomain(map));
            }
        } catch (Exception e) {
            System.err.println("[SUPABASE ERROR] Fallo al buscar usuario: " + e.getMessage());
        }
        return Optional.empty();
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Usuario save(Usuario usuario) {
        Map<String, Object> body = new HashMap<>();
        if (usuario.getId() != null) {
            body.put("id", usuario.getId());
        }
        body.put("username", usuario.getUsername());
        body.put("password", usuario.getPassword());
        body.put("rol", usuario.getRol());
        body.put("mfa_secret", usuario.getMfaSecret());
        body.put("mfa_enabled", usuario.isMfaEnabled());

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, getHeaders(true));
        try {
            if (usuario.getId() == null) {
                String url = supabaseUrl + "/rest/v1/usuarios";
                ResponseEntity<Map[]> response = restTemplate.exchange(url, HttpMethod.POST, entity, Map[].class);
                if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null && response.getBody().length > 0) {
                    return mapToDomain(response.getBody()[0]);
                }
            } else {
                String url = supabaseUrl + "/rest/v1/usuarios?id=eq." + usuario.getId();
                ResponseEntity<Map[]> response = restTemplate.exchange(url, HttpMethod.PATCH, entity, Map[].class);
                if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null && response.getBody().length > 0) {
                    return mapToDomain(response.getBody()[0]);
                }
            }
        } catch (Exception e) {
            System.err.println("[SUPABASE ERROR] Fallo al guardar usuario: " + e.getMessage());
        }
        return usuario;
    }

    private Usuario mapToDomain(Map<String, Object> map) {
        Number idNum = (Number) map.get("id");
        Long id = idNum != null ? idNum.longValue() : null;
        String username = (String) map.get("username");
        String password = (String) map.get("password");
        String rol = (String) map.get("rol");
        String mfaSecret = (String) map.get("mfa_secret");
        
        Object mfaEnabledObj = map.get("mfa_enabled");
        boolean mfaEnabled = false;
        if (mfaEnabledObj instanceof Boolean) {
            mfaEnabled = (Boolean) mfaEnabledObj;
        } else if (mfaEnabledObj instanceof Number) {
            mfaEnabled = ((Number) mfaEnabledObj).intValue() == 1;
        }

        return Usuario.builder()
                .id(id)
                .username(username)
                .password(password)
                .rol(rol)
                .mfaSecret(mfaSecret)
                .mfaEnabled(mfaEnabled)
                .build();
    }
}
