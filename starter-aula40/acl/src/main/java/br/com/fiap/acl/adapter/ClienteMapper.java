package br.com.fiap.acl.adapter;

import br.com.fiap.acl.domain.Cliente;
import br.com.fiap.acl.domain.TraducaoException;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * PRÁTICA GUIADA — passo 2 (slide 15): implementar a tradução explícita.
 *
 * Regras (slide 10):
 *  - "A" -> ativo=true, "I" -> ativo=false; qualquer outra situação é TraducaoException
 *  - COD_CLI vira o id numérico; se não for numérico, TraducaoException (nunca NumberFormatException)
 *  - NOME recebe trim(); vazio ou nulo é TraducaoException
 *  - nunca inventar valor de negócio
 *
 * Os testes em ClienteMapperTest ficam vermelhos até isto ser implementado.
 */
@ApplicationScoped
public class ClienteMapper {

    public Cliente converter(ClienteLegado dto) {
        // TODO (prática guiada): substituir por tradução explícita com switch em dto.SIT()
        throw new UnsupportedOperationException("ClienteMapper.converter ainda não foi implementado");
    }

    // Dica: um método privado por campo (converterId, converterNome, converterSituacao)
    // deixa cada regra testável e legível.
    @SuppressWarnings("unused")
    private static void exemploDeFalha() {
        throw new TraducaoException("exemplo");
    }
}
