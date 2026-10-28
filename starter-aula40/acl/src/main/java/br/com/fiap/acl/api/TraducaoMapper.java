package br.com.fiap.acl.api;

import br.com.fiap.acl.domain.TraducaoException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;

/** Erro de tradução / payload inválido da dependência -> 502 (slides 11 e 20). */
@Provider
public class TraducaoMapper implements ExceptionMapper<TraducaoException> {

    private static final Logger LOG = Logger.getLogger(TraducaoMapper.class);

    @Context
    HttpHeaders headers;

    @Override
    public Response toResponse(TraducaoException e) {
        String correlacao = Correlacao.de(headers);
        LOG.errorf("[%s] falha de tradução: %s", correlacao, e.getMessage());
        return Response.status(Response.Status.BAD_GATEWAY)
                .type(MediaType.APPLICATION_JSON)
                .entity(new ErroResponse("resposta-invalida-do-legado", e.getMessage(), correlacao))
                .build();
    }
}
