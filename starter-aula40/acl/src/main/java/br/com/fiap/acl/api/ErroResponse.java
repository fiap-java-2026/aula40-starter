package br.com.fiap.acl.api;

/** Corpo de erro estável para 404/502/503 (briefing: "ProblemDetails ou JSON simples"). */
public record ErroResponse(String erro, String mensagem, String correlacao) {
}
