package hexagonal_PC3.hexagonal.textil.aplicacion.port.output;

import hexagonal_PC3.hexagonal.textil.dominio.Tela;
import java.util.List;

public interface TelaRepositoryPort {
    List<Tela> findAll();
}
