package hexagonal_PC3.hexagonal.textil.dominio;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tela {
    private Long id;
    private String tipoTela;
    private String color;
    private Double metrosPorRollo;
    private Double precio;
}
