package br.com.fiap.acl.adapter;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

/**
 * Cliente REST declarativo (slide 16). A URL e os timeouts vêm de
 * quarkus.rest-client.legado.* no application.properties.
 */
@Path("/cadastro")
@RegisterRestClient(configKey = "legado")
public interface LegadoClient {

    @GET
    @Path("/{id}")
    ClienteLegado buscar(@PathParam("id") long id);
}
