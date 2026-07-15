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
public class ExchangeRateHistory {
    private String fecha;
    private String monedaBase;
    private String monedaDestino;
    private double compra;
    private double venta;
}
