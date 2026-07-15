package hexagonal_PC3.hexagonal.textil.infraestructura.adapter.output;

import hexagonal_PC3.hexagonal.textil.aplicacion.port.output.TelaRepositoryPort;
import hexagonal_PC3.hexagonal.textil.dominio.Tela;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import java.util.*;

public class SupabaseTelaAdapter implements TelaRepositoryPort {

    private final RestTemplate restTemplate;

    @Value("${supabase.url}")
    private String supabaseUrl;

    @Value("${supabase.anon-key}")
    private String anonKey;

    @Value("${supabase.schema}")
    private String schema;

    public SupabaseTelaAdapter() {
        this.restTemplate = new RestTemplate();
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("apikey", anonKey);
        headers.set("Authorization", "Bearer " + anonKey);
        headers.set("Accept-Profile", schema);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public List<Tela> findAll() {
        String url = supabaseUrl + "/rest/v1/telas";
        HttpEntity<?> entity = new HttpEntity<>(getHeaders());
        List<Tela> list = new ArrayList<>();
        try {
            ResponseEntity<Map[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map[].class);
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                for (Map map : response.getBody()) {
                    list.add(mapToDomain(map));
                }
            }
        } catch (Exception e) {
            System.err.println("[SUPABASE ERROR] Fallo al listar telas: " + e.getMessage());
        }
        return list;
    }

    private Tela mapToDomain(Map<String, Object> map) {
        Number idNum = (Number) map.get("id");
        Long id = idNum != null ? idNum.longValue() : null;
        String color = (String) map.get("color");
        String tipoMaterial = (String) map.get("tipo_material");
        String nombre = (String) map.get("nombre");
        
        Number stockNum = (Number) map.get("stock");
        Double metrosPorRollo = stockNum != null ? stockNum.doubleValue() : 0.0;
        
        Number precioNum = (Number) map.get("precio");
        Double precio = precioNum != null ? precioNum.doubleValue() : 0.0;

        return Tela.builder()
                .id(id)
                .tipoTela(tipoMaterial != null ? tipoMaterial : (nombre != null ? nombre : "Tela"))
                .color(color)
                .metrosPorRollo(metrosPorRollo)
                .precio(precio)
                .build();
    }
}
