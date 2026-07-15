package hexagonal_PC3.hexagonal.textil.infraestructura.adapter.input;

import hexagonal_PC3.hexagonal.textil.aplicacion.port.input.ListarTelasUseCase;
import hexagonal_PC3.hexagonal.textil.dominio.Tela;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/telas")
public class TelaController {

    private final ListarTelasUseCase listarTelasUseCase;

    public TelaController(ListarTelasUseCase listarTelasUseCase) {
        this.listarTelasUseCase = listarTelasUseCase;
    }

    @GetMapping
    public List<Tela> listarTelas() {
        return listarTelasUseCase.listar();
    }
}
