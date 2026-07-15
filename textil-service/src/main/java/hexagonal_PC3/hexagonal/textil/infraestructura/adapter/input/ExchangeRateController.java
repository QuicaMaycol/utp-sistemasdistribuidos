package hexagonal_PC3.hexagonal.textil.infraestructura.adapter.input;

import hexagonal_PC3.hexagonal.textil.aplicacion.port.input.GetExchangeRateUseCase;
import hexagonal_PC3.hexagonal.textil.dominio.ExchangeRateHistory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/exchange-rate")
public class ExchangeRateController {

    private final GetExchangeRateUseCase getExchangeRateUseCase;

    public ExchangeRateController(GetExchangeRateUseCase getExchangeRateUseCase) {
        this.getExchangeRateUseCase = getExchangeRateUseCase;
    }

    @GetMapping("/latest")
    public double getLatestRate() {
        return getExchangeRateUseCase.getLatestRate();
    }

    @GetMapping("/history")
    public List<ExchangeRateHistory> getHistory() {
        return getExchangeRateUseCase.getHistory();
    }
}
