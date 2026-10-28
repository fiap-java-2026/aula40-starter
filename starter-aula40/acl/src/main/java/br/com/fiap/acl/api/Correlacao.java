package br.com.fiap.acl.api;

import jakarta.ws.rs.core.HttpHeaders;

import java.util.UUID;

/** Correlação sem dados pessoais (slide 13): reaproveita X-Correlation-Id ou gera um. */
final class Correlacao {
    static final String HEADER = "X-Correlation-Id";

    private Correlacao() {
    }

    static String de(HttpHeaders headers) {
        String id = headers == null ? null : headers.getHeaderString(HEADER);
        return (id == null || id.isBlank()) ? UUID.randomUUID().toString() : id;
    }
}
