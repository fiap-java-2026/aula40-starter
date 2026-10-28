package br.com.fiap.acl.adapter;

import br.com.fiap.acl.domain.Cliente;
import br.com.fiap.acl.domain.TraducaoException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Testes de tradução (slide 18): JUnit puro, sem subir o Quarkus.
 * Rodam em segundos com a tecla "r" do quarkus:dev (continuous testing).
 */
class ClienteMapperTest {

    private final ClienteMapper mapper = new ClienteMapper();

    @Test
    @DisplayName("1. A vira ativo; nome é normalizado com trim")
    void converteClienteAtivo() {
        var entrada = new ClienteLegado("42", " Ana ", "A");
        Cliente saida = mapper.converter(entrada);
        assertEquals(42L, saida.id());
        assertEquals("Ana", saida.nome());
        assertTrue(saida.ativo());
    }

    @Test
    @DisplayName("2. I vira inativo")
    void converteClienteInativo() {
        Cliente saida = mapper.converter(new ClienteLegado("7", "Bruno", "I"));
        assertFalse(saida.ativo());
    }

    @Test
    @DisplayName("3. Situação desconhecida é falha identificável, nunca ativo=false")
    void rejeitaSituacaoDesconhecida() {
        var ex = assertThrows(TraducaoException.class,
                () -> mapper.converter(new ClienteLegado("1", "Carla", "X")));
        assertTrue(ex.getMessage().contains("X"));
    }

    @Test
    @DisplayName("4. COD_CLI não numérico não vaza NumberFormatException")
    void rejeitaCodigoNaoNumerico() {
        assertThrows(TraducaoException.class,
                () -> mapper.converter(new ClienteLegado("ABC", "Carla", "A")));
    }

    @Test
    @DisplayName("5. Nome vazio é rejeitado")
    void rejeitaNomeVazio() {
        assertThrows(TraducaoException.class,
                () -> mapper.converter(new ClienteLegado("1", "   ", "A")));
    }
}
