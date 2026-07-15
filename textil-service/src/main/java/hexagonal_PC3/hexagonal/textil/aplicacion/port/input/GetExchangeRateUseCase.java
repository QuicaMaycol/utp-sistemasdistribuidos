package hexagonal_PC3.hexagonal.textil.aplicacion.port.input;

import hexagonal_PC3.hexagonal.textil.dominio.ExchangeRateHistory;
import java.util.List;

public interface GetExchangeRateUseCase {
    double getLatestRate();
    List<ExchangeRateHistory> getHistory();
}
