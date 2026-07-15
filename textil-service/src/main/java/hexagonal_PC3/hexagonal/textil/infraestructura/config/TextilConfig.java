package hexagonal_PC3.hexagonal.textil.infraestructura.config;

import hexagonal_PC3.hexagonal.textil.aplicacion.port.input.ListarTelasUseCase;
import hexagonal_PC3.hexagonal.textil.aplicacion.port.input.GetExchangeRateUseCase;
import hexagonal_PC3.hexagonal.textil.aplicacion.port.output.TelaRepositoryPort;
import hexagonal_PC3.hexagonal.textil.aplicacion.port.output.ExchangeRatePort;
import hexagonal_PC3.hexagonal.textil.aplicacion.service.TelaService;
import hexagonal_PC3.hexagonal.textil.aplicacion.service.ExchangeRateService;
import hexagonal_PC3.hexagonal.textil.infraestructura.adapter.output.SupabaseTelaAdapter;
import hexagonal_PC3.hexagonal.textil.infraestructura.adapter.output.ExchangeRateApiAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TextilConfig {

    @Bean
    public TelaRepositoryPort telaRepositoryPort() {
        return new SupabaseTelaAdapter();
    }

    @Bean
    public ListarTelasUseCase listarTelasUseCase(TelaRepositoryPort repositoryPort) {
        return new TelaService(repositoryPort);
    }

    @Bean
    public ExchangeRatePort exchangeRatePort() {
        return new ExchangeRateApiAdapter();
    }

    @Bean
    public GetExchangeRateUseCase getExchangeRateUseCase(ExchangeRatePort exchangeRatePort) {
        return new ExchangeRateService(exchangeRatePort);
    }
}
