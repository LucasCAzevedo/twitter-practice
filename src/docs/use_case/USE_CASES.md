# Use Cases - Twitter API Simulation

## UC001 - Criar Tweet

### Descrição
O sistema deve permitir a criação de um novo tweet com conteúdo e identificação do autor.

### Atores
- Cliente da API (Sistema externo)

### Pré-condições
- O sistema está operacional
- O cliente envia uma requisição HTTP válida

### Fluxo Principal
1. Cliente envia requisição POST para `/tweets`
2. Sistema valida formato da requisição JSON
3. Sistema valida se campos obrigatórios estão presentes (content, authorHandle)
4. Sistema valida conteúdo do tweet:
- Não está vazio ou apenas espaços em branco
- Não excede 280 caracteres
5. Sistema valida formato do authorHandle:
- Inicia com "@"
- Contém apenas caracteres alfanuméricos e underscore
6. Sistema gera ID único (UUID)
7. Sistema registra timestamp atual
8. Sistema armazena tweet em memória
9. Sistema retorna tweet criado com status 201

### Fluxos Alternativos

#### FA001 - Requisição JSON inválida
- **Ponto de Desvio:** Passo 2
- **Fluxo:** Sistema retorna status 400 Bad Request
- **Retorno:** Fluxo encerra

#### FA002 - Campos obrigatórios ausentes
- **Ponto de Desvio:** Passo 3
- **Fluxo:** Sistema retorna status 400 Bad Request
- **Retorno:** Fluxo encerra

#### FA003 - Conteúdo inválido
- **Ponto de Desvio:** Passo 4
- **Fluxo:** Sistema retorna status 422 Unprocessable Entity
- **Retorno:** Fluxo encerra

#### FA004 - AuthorHandle inválido
- **Ponto de Desvio:** Passo 5
- **Fluxo:** Sistema retorna status 422 Unprocessable Entity
- **Retorno:** Fluxo encerra

### Pós-condições
- Tweet é armazenado no sistema
- Tweet possui ID único e timestamp
- Cliente recebe confirmação da criação

---

## UC002 - Listar Todos os Tweets

### Descrição
O sistema deve retornar todos os tweets armazenados, ordenados do mais recente para o mais antigo.

### Atores
- Cliente da API (Sistema externo)

### Pré-condições
- O sistema está operacional

### Fluxo Principal
1. Cliente envia requisição GET para `/tweets`
2. Sistema busca todos os tweets armazenados
3. Sistema ordena tweets por timestamp (mais recente primeiro)
4. Sistema retorna lista de tweets com status 200

### Fluxos Alternativos

#### FA001 - Nenhum tweet encontrado
- **Ponto de Desvio:** Passo 2
- **Fluxo:** Sistema retorna array vazio com status 200
- **Retorno:** Fluxo principal

### Pós-condições
- Cliente recebe lista de todos os tweets ordenados

---

## UC003 - Listar Tweets por Autor

### Descrição
O sistema deve retornar todos os tweets de um autor específico, identificado pelo authorHandle.

### Atores
- Cliente da API (Sistema externo)

### Pré-condições
- O sistema está operacional

### Fluxo Principal
1. Cliente envia requisição GET para `/tweets/{authorHandle}`
2. Sistema valida formato do authorHandle
3. Sistema busca tweets do autor especificado
4. Sistema verifica se encontrou tweets
5. Sistema ordena tweets por timestamp (mais recente primeiro)
6. Sistema retorna lista de tweets com status 200

### Fluxos Alternativos

#### FA001 - AuthorHandle com formato inválido
- **Ponto de Desvio:** Passo 2
- **Fluxo:** Sistema retorna status 400 Bad Request
- **Retorno:** Fluxo encerra

#### FA002 - Nenhum tweet encontrado para o autor
- **Ponto de Desvio:** Passo 4
- **Fluxo:** Sistema retorna status 404 Not Found
- **Retorno:** Fluxo encerra

### Pós-condições
- Cliente recebe lista de tweets do autor especificado

---

## UC004 - Deletar Tweet

### Descrição
O sistema deve permitir a exclusão de um tweet específico através do seu ID.

### Atores
- Cliente da API (Sistema externo)

### Pré-condições
- O sistema está operacional
- Tweet existe no sistema

### Fluxo Principal
1. Cliente envia requisição DELETE para `/tweets/{id}`
2. Sistema busca tweet pelo ID fornecido
3. Sistema verifica se tweet existe
4. Sistema remove tweet do armazenamento
5. Sistema retorna status 204 No Content

### Fluxos Alternativos

#### FA001 - Tweet não encontrado
- **Ponto de Desvio:** Passo 3
- **Fluxo:** Sistema retorna status 404 Not Found
- **Retorno:** Fluxo encerra

### Pós-condições
- Tweet é removido do sistema
- Cliente recebe confirmação da exclusão

---

## Matriz de Rastreabilidade

| Requisito | UC001 | UC002 | UC003 | UC004 |
|-----------|-------|-------|-------|-------|
| POST /tweets | ✓ | | | |
| GET /tweets | | ✓ | | |
| GET /tweets/{authorHandle} | | | ✓ | |
| DELETE /tweets/{id} | | | | ✓ |
| Validação de conteúdo | ✓ | | | |
| Validação de authorHandle | ✓ | | ✓ | |
| Armazenamento em memória | ✓ | ✓ | ✓ | ✓ |
| Ordenação por timestamp | | ✓ | ✓ | |
| Geração de ID único | ✓ | | | |
| Tratamento de erros | ✓ | | ✓ | ✓ |