package hexagonal_PC3.hexagonal.textil.aplicacion.port.output;

import hexagonal_PC3.hexagonal.textil.dominio.ExchangeRateHistory;
import java.util.List;

public interface ExchangeRatePort {
    double getUsdToPenRate();
    List<ExchangeRateHistory> getHistoricalRates();
}
