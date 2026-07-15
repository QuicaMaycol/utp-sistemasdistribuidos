package hexagonal_PC3.hexagonal.textil.aplicacion.service;

import hexagonal_PC3.hexagonal.textil.aplicacion.port.input.ListarTelasUseCase;
import hexagonal_PC3.hexagonal.textil.aplicacion.port.output.TelaRepositoryPort;
import hexagonal_PC3.hexagonal.textil.dominio.Tela;
import java.util.List;

public class TelaService implements ListarTelasUseCase {

    private final TelaRepositoryPort repositoryPort;

    public TelaService(TelaRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    @Override
    public List<Tela> listar() {
        return repositoryPort.findAll();
    }
}
