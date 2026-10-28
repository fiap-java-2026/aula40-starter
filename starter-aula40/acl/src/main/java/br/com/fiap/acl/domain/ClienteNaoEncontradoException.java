package br.com.fiap.acl.domain;

/** Erro de negócio: o cliente não existe (slide 11 -> HTTP 404). */
public class ClienteNaoEncontradoException extends RuntimeException {
    private final long id;

    public ClienteNaoEncontradoException(long id) {
        super("Cliente " + id + " não encontrado");
        this.id = id;
    }

    public long id() {
        return id;
    }
}
