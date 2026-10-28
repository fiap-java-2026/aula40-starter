package br.com.fiap.acl.adapter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * DTO externo: o contrato recebido do legado (slide 8), com os nomes dele.
 * ignoreUnknown = tolerant reader (slide 12): um campo novo no legado não quebra a ACL.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record ClienteLegado(String COD_CLI, String NOME, String SIT) {
}
