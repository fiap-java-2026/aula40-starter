package br.com.fiap.acl.api;

import br.com.fiap.acl.adapter.ClienteLegado;
import br.com.fiap.acl.adapter.LegadoClient;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.when;

/**
 * Testes do contrato HTTP (slide 11). O LegadoClient é substituído por um mock,
 * então estes testes não dependem do legado-mock estar no ar.
 */
@QuarkusTest
class ClienteResourceTest {

    @InjectMock
    @RestClient
    LegadoClient legado;

    @Test
    @DisplayName("6. 200 e somente o modelo interno: sem SIT nem COD_CLI")
    void retornaModeloInterno() {
        when(legado.buscar(1L)).thenReturn(new ClienteLegado("1", "  Ana  ", "A"));

        given().when().get("/clientes/1")
                .then().statusCode(200)
                .body("id", is(1))
                .body("nome", is("Ana"))
                .body("ativo", is(true))
                .body("$", not(hasKey("SIT")))
                .body("$", not(hasKey("COD_CLI")));
    }

    @Test
    @DisplayName("7. Ausência no legado vira 404 com corpo estável e correlação")
    void ausenciaVira404() {
        when(legado.buscar(4L)).thenThrow(
                new WebApplicationException(Response.status(404).build()));

        given().when().get("/clientes/4")
                .then().statusCode(404)
                .body("erro", is("cliente-nao-encontrado"))
                .body("correlacao", notNullValue());
    }

    @Test
    @DisplayName("8. Payload inválido da dependência vira 502")
    void payloadInvalidoVira502() {
        when(legado.buscar(5L)).thenThrow(new ProcessingException("corpo não é JSON"));

        given().when().get("/clientes/5")
                .then().statusCode(502)
                .body("erro", is("resposta-invalida-do-legado"))
                .body("correlacao", notNullValue());
    }
}
