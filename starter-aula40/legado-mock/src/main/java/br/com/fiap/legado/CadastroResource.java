package br.com.fiap.legado;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;

/**
 * Simula o legado em http://localhost:4001/cadastro/{id}.
 * Cada ID representa um cenário usado na prática guiada e no desafio:
 *
 *  1 -> cliente ativo (A), nome com espaços sobrando
 *  2 -> cliente inativo (I)
 *  3 -> situação desconhecida (X)          -> a ACL deve responder 502
 *  4 -> cliente inexistente                -> 404 no legado -> 404 na ACL
 *  5 -> payload JSON inválido              -> a ACL deve responder 502
 *  6 -> demora 5 s (maior que o read-timeout de 2 s) -> indisponibilidade
 *  7 -> COD_CLI não numérico ("ABC")       -> 502
 *  8 -> NOME vazio                         -> 502
 *  9 -> campo extra que a ACL não conhece  -> tolerant reader: ignora e responde 200
 */
@Path("/cadastro")
@Produces(MediaType.APPLICATION_JSON)
public class CadastroResource {

    private static final Logger LOG = Logger.getLogger(CadastroResource.class);

    @GET
    @Path("/{id}")
    public Response buscar(@PathParam("id") String id) throws InterruptedException {
        LOG.infof("legado recebeu GET /cadastro/%s", id);
        return switch (id) {
            case "1" -> ok(new ClienteLegadoDTO("1", "  Ana Souza  ", "A"));
            case "2" -> ok(new ClienteLegadoDTO("2", "Bruno Lima", "I"));
            case "3" -> ok(new ClienteLegadoDTO("3", "Carla Dias", "X"));
            case "4" -> Response.status(404)
                    .entity("{\"ERRO\":\"CLIENTE NAO LOCALIZADO\"}").build();
            case "5" -> Response.ok("<<<ISTO NAO E JSON>>>").build();
            case "6" -> { Thread.sleep(5_000); yield ok(new ClienteLegadoDTO("6", "Lento", "A")); }
            case "7" -> ok(new ClienteLegadoDTO("ABC", "Codigo Invalido", "A"));
            case "8" -> ok(new ClienteLegadoDTO("8", "", "A"));
            case "9" -> Response.ok("{\"COD_CLI\":\"9\",\"NOME\":\"Nova Versao\",\"SIT\":\"A\",\"DT_CAD\":\"2026-10-28\"}").build();
            default -> Response.status(404)
                    .entity("{\"ERRO\":\"CLIENTE NAO LOCALIZADO\"}").build();
        };
    }

    private static Response ok(ClienteLegadoDTO dto) {
        return Response.ok(dto).build();
    }
}
