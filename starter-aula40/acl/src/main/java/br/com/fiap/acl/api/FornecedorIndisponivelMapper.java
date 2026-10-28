package br.com.fiap.acl.api;

import br.com.fiap.acl.domain.FornecedorIndisponivelException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;

/** Erro de transporte (timeout, conexão) -> 503 (slide 13: "uma ACL não corrige indisponibilidade"). */
@Provider
public class FornecedorIndisponivelMapper implements ExceptionMapper<FornecedorIndisponivelException> {

    private static final Logger LOG = Logger.getLogger(FornecedorIndisponivelMapper.class);

    @Context
    HttpHeaders headers;

    @Override
    public Response toResponse(FornecedorIndisponivelException e) {
        String correlacao = Correlacao.de(headers);
        LOG.errorf("[%s] legado indisponível: %s", correlacao, e.getMessage());
        return Response.status(Response.Status.SERVICE_UNAVAILABLE)
                .type(MediaType.APPLICATION_JSON)
                .header("Retry-After", "5")
                .entity(new ErroResponse("legado-indisponivel", e.getMessage(), correlacao))
                .build();
    }
}
