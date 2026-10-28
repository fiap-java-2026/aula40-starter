package br.com.fiap.acl.api;

import br.com.fiap.acl.domain.BuscaCliente;
import jakarta.inject.Inject;
import jakarta.validation.constraints.Pattern;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

/**
 * Recurso HTTP (slide 11): expõe somente o modelo interno e injeta a PORTA,
 * nunca o adaptador. Não sabe que existe um legado.
 */
@Path("/clientes")
@Produces(MediaType.APPLICATION_JSON)
public class ClienteResource {

    @Inject
    BuscaCliente buscaCliente;

    @GET
    @Path("/{id}")
    @Operation(summary = "Busca um cliente pelo id interno")
    @APIResponse(responseCode = "200", description = "Cliente encontrado")
    @APIResponse(responseCode = "400", description = "Id inválido (não numérico)")
    @APIResponse(responseCode = "404", description = "Cliente inexistente")
    @APIResponse(responseCode = "502", description = "Resposta inválida da dependência (legado)")
    @APIResponse(responseCode = "503", description = "Dependência indisponível (timeout)")
    public ClienteResponse buscar(
            @PathParam("id") @Pattern(regexp = "\\d{1,18}", message = "id deve ser numérico") String id) {
        return ClienteResponse.de(buscaCliente.buscar(Long.parseLong(id)));
    }
}
