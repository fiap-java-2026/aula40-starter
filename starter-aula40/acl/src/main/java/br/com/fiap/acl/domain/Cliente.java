package br.com.fiap.acl.domain;

/** Vocabulário da aplicação nova (slide 14). Nunca contém COD_CLI nem SIT. */
public record Cliente(long id, String nome, boolean ativo) {
}
