package hexagonal_PC3.hexagonal.textil.aplicacion.service;

import hexagonal_PC3.hexagonal.textil.aplicacion.port.input.GetExchangeRateUseCase;
import hexagonal_PC3.hexagonal.textil.aplicacion.port.output.ExchangeRatePort;
import hexagonal_PC3.hexagonal.textil.dominio.ExchangeRateHistory;
import java.util.List;

public class ExchangeRateService implements GetExchangeRateUseCase {

    private final ExchangeRatePort exchangeRatePort;

    public ExchangeRateService(ExchangeRatePort exchangeRatePort) {
        this.exchangeRatePort = exchangeRatePort;
    }

    @Override
    public double getLatestRate() {
        return exchangeRatePort.getUsdToPenRate();
    }

    @Override
    public List<ExchangeRateHistory> getHistory() {
        return exchangeRatePort.getHistoricalRates();
    }
}
