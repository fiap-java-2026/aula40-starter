package br.com.fiap.acl.api;

import br.com.fiap.acl.domain.Cliente;

/** DTO público estável (slide 8). É o contrato que os consumidores enxergam. */
public record ClienteResponse(long id, String nome, boolean ativo) {

    public static ClienteResponse de(Cliente c) {
        return new ClienteResponse(c.id(), c.nome(), c.ativo());
    }
}
