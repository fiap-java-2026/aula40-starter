package br.com.fiap.acl.adapter;

import br.com.fiap.acl.domain.BuscaCliente;
import br.com.fiap.acl.domain.Cliente;
import br.com.fiap.acl.domain.ClienteNaoEncontradoException;
import io.quarkus.arc.DefaultBean;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Map;

/**
 * Fornecedor simulado (slide 17): um bean local que devolve A e I antes de
 * conectar por HTTP. É @DefaultBean: vale enquanto nenhum outro BuscaCliente
 * existir. No momento em que ClienteAdapter entra no projeto, ele assume.
 */
@DefaultBean
@ApplicationScoped
public class FornecedorSimulado implements BuscaCliente {

    private static final Map<Long, ClienteLegado> BASE = Map.of(
            1L, new ClienteLegado("1", "  Ana Souza  ", "A"),
            2L, new ClienteLegado("2", "Bruno Lima", "I"),
            3L, new ClienteLegado("3", "Carla Dias", "X"));

    @Inject
    ClienteMapper mapper;

    @Override
    public Cliente buscar(long id) {
        ClienteLegado dto = BASE.get(id);
        if (dto == null) {
            throw new ClienteNaoEncontradoException(id);
        }
        return mapper.converter(dto);
    }
}
