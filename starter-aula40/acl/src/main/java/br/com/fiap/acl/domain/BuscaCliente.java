package br.com.fiap.acl.domain;

/**
 * Porta definida pela aplicação (slide 6): o domínio depende desta interface,
 * nunca do sistema externo. Quem implementa é um adaptador.
 */
public interface BuscaCliente {

    /**
     * @throws ClienteNaoEncontradoException   quando o fornecedor indica ausência
     * @throws TraducaoException               quando a resposta não pode ser traduzida
     * @throws FornecedorIndisponivelException quando o fornecedor não responde
     */
    Cliente buscar(long id);
}
