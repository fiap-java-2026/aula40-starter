package br.com.fiap.acl.domain;

/**
 * Erro de tradução (slide 13): o legado respondeu, mas com algo que a ACL não
 * sabe converter — situação desconhecida, código não numérico, nome vazio,
 * payload inválido. Mapeado para HTTP 502 (resposta inválida da dependência).
 */
public class TraducaoException extends RuntimeException {
    public TraducaoException(String mensagem) {
        super(mensagem);
    }

    public TraducaoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
