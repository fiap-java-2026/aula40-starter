# Aula 40 — Anti-Corruption Layer e contratos de API · STARTER

Formação Backend Java e Quarkus · Turma 2 · 28/10/2026

Dois projetos Quarkus 3.17 / Java 21: `legado-mock/` (o sistema legado, pronto, porta 4001) e `acl/` (a API nova, porta 4000, **incompleta de propósito**).

## O que falta — e é o desafio (slides 20–21)

| Arquivo | Estado | O que fazer |
|---|---|---|
| `acl/.../adapter/ClienteMapper.java` | `throw new UnsupportedOperationException` | Tradução explícita: A→ativo, I→inativo, qualquer outro código → `TraducaoException`; `COD_CLI` numérico; `NOME` com `trim()` e não vazio |
| `acl/.../adapter/ClienteAdapter.java` | **não existe** | Implementar a porta `BuscaCliente` usando `LegadoClient` (REST Client) + `ClienteMapper`; traduzir 404 do legado em `ClienteNaoEncontradoException`, payload inválido em `TraducaoException`, timeout em `FornecedorIndisponivelException` |

Tudo o mais já existe: porta, DTOs, `LegadoClient` configurado, `ExceptionMapper`s (404/502/503), recurso HTTP, validação do id e **8 testes** (`./mvnw test` → 8 falhas no estado inicial). Pacotes: `domain` (modelo e porta), `adapter` (o que conhece o legado), `api` (o contrato público).

Critérios de aceite: `./mvnw test` com 8 verdes; Runner do Postman todo verde; o JSON de `/clientes/{id}` nunca contém `SIT` nem `COD_CLI`. Extensão (slide 22): um segundo fornecedor com campos diferentes atrás da mesma porta `BuscaCliente`.

## Como rodar (Java 21 + Maven Wrapper incluso; não precisa instalar Maven)

Dois terminais, um por projeto:

```bash
cd legado-mock && ./mvnw quarkus:dev        # legado simulado em http://localhost:4001
cd acl         && ./mvnw quarkus:dev        # API nova em http://localhost:4000  (tecla r = testes contínuos)
```

No Windows sem WSL use `mvnw.cmd` no lugar de `./mvnw`. Quem preferir container: `docker compose up -d && docker compose exec dev bash` e os mesmos comandos dentro de `/workspace`.

Postman: *Import* → `postman/FIAP-Aula-40.postman_collection.json` e `postman/FIAP-Aula-40-local.postman_environment.json`; selecione o ambiente "FIAP Aula 40 — local". O *Runner* na coleção inteira é o critério de aceite: tudo verde, entregou.

Swagger UI: http://localhost:4000/q/swagger-ui · Dev UI: http://localhost:4000/q/dev-ui

## Cenários do legado (`GET http://localhost:4001/cadastro/{id}`)

| id | Legado devolve | ACL deve responder |
|---|---|---|
| 1 | `SIT=A`, nome com espaços | 200 `{"id":1,"nome":"Ana Souza","ativo":true}` |
| 2 | `SIT=I` | 200 `ativo=false` |
| 3 | `SIT=X` | 502 `resposta-invalida-do-legado` |
| 4 | HTTP 404 | 404 `cliente-nao-encontrado` |
| 5 | corpo que não é JSON | 502 |
| 6 | demora 5 s (> read-timeout 2 s) | 503 `legado-indisponivel` |
| 7 | `COD_CLI=ABC` | 502 |
| 8 | `NOME` vazio | 502 |
| 9 | campo extra `DT_CAD` | 200 (tolerant reader) |
| abc | — (validação na ACL) | 400 `id deve ser numérico` |
