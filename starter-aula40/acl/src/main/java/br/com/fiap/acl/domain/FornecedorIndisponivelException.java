package br.com.fiap.acl.domain;

/** Erro de transporte (slide 13): timeout, conexão recusada, 5xx do legado. HTTP 503. */
public class FornecedorIndisponivelException extends RuntimeException {
    public FornecedorIndisponivelException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
