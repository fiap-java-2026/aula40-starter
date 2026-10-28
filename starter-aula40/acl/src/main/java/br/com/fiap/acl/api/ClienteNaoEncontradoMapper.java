package br.com.fiap.acl.api;

import br.com.fiap.acl.domain.ClienteNaoEncontradoException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

/** Erro de negócio -> 404 (slide 11). */
@Provider
public class ClienteNaoEncontradoMapper implements ExceptionMapper<ClienteNaoEncontradoException> {

    @Context
    HttpHeaders headers;

    @Override
    public Response toResponse(ClienteNaoEncontradoException e) {
        return Response.status(Response.Status.NOT_FOUND)
                .type(MediaType.APPLICATION_JSON)
                .entity(new ErroResponse("cliente-nao-encontrado", e.getMessage(), Correlacao.de(headers)))
                .build();
    }
}
