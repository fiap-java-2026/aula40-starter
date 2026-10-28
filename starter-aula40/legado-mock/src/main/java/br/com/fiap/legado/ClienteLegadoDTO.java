package br.com.fiap.legado;

/**
 * Contrato "feio" do legado: nomes em caixa alta, situação codificada em uma letra.
 * Os nomes dos componentes do record viram os nomes dos campos JSON (COD_CLI, NOME, SIT).
 */
public record ClienteLegadoDTO(String COD_CLI, String NOME, String SIT) {
}
