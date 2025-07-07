# Twitter Backend API Simulation

Uma simulação simplificada de uma API backend do Twitter desenvolvida com Spring Boot.

## 📋 Descrição

Este projeto implementa uma API REST que gerencia tweets com funcionalidades básicas de criação, listagem e exclusão, seguindo os princípios RESTful.

## 🚀 Como Executar

### Pré-requisitos
- Java 17 ou superior
- Maven 3.6 ou superior

### Executando a Aplicação
```bash
mvn spring-boot:run
Executando os Testes
mvn test
Acessando a API
A API estará disponível em: http://localhost:8080

📚 Endpoints da API
1. Criar Tweet
POST /tweets
Body:
{
  "content": "This is my first tweet!",
  "authorHandle": "@my_awesome_user"
}
Responses:
201 Created - Tweet criado com sucesso
400 Bad Request - Requisição inválida
422 Unprocessable Entity - Dados inválidos
2. Listar Todos os Tweets
GET /tweets
Response: 200 OK com array de tweets ordenados por data (mais recente primeiro)
3. Listar Tweets por Autor
GET /tweets/{authorHandle}
Exemplo: GET /tweets/@my_awesome_user
Responses:
200 OK - Lista de tweets do autor
400 Bad Request - Handle inválido
404 Not Found - Nenhum tweet encontrado
4. Deletar Tweet
DELETE /tweets/{id}
Responses:
204 No Content - Tweet deletado com sucesso
404 Not Found - Tweet não encontrado
🔧 Tecnologias Utilizadas
Java 17
Spring Boot 3.2.0
Spring Web
Spring Boot DevTools (para live reload)
JUnit 5 (para testes)
Mockito (para mocks nos testes)
📝 Regras de Negócio
Validações de Tweet:
Conteúdo não pode estar vazio ou conter apenas espaços
Conteúdo não pode exceder 280 caracteres
AuthorHandle deve começar com @ e conter apenas caracteres alfanuméricos e underscore
Armazenamento:
Todos os dados são armazenados em memória (sem banco de dados)
IDs únicos gerados automaticamente (UUID)
Timestamps no formato ISO 8601
🐳 Docker (Opcional)
Se você tiver o Docker Compose configurado:

docker-compose up
🧪 Estrutura de Testes
O projeto inclui testes unitários para:

Serviços (TweetService)
Controladores (TweetController)
Validações e regras de negócio
Tratamento de exceções
📁 Estrutura do Projeto
src/
├── main/
│   ├── java/com/practice/twitter/
│   │   ├── controller/    # Controladores REST
│   │   ├── dto/           # Data Transfer Objects
│   │   ├── exception/     # Exceções customizadas
│   │   ├── model/         # Modelos de dados
│   │   ├── repository/    # Repositórios (armazenamento em memória)
│   │   ├── service/       # Lógica de negócio
│   │   └── TwitterApiApplication.java
│   └── resources/
│       └── application.properties
└── test/
  └── java/com/practice/twitter/
      ├── controller/    # Testes dos controladores
      └── service/       # Testes dos serviços
📋 Exemplos de Uso
Criar um tweet:
curl -X POST http://localhost:8080/tweets \
-H "Content-Type: application/json" \
-d '{"content": "Hello World!", "authorHandle": "@testuser"}'
Listar todos os tweets:
curl http://localhost:8080/tweets
Listar tweets de um autor:
curl http://localhost:8080/tweets/@testuser
Deletar um tweet:
curl -X DELETE http://localhost:8080/tweets/{tweet-id}
🤝 Contribuição
Fork o projeto
Crie uma branch para sua feature (git checkout -b feature/AmazingFeature)
Commit suas mudanças (git commit -m 'Add some AmazingFeature')
Push para a branch (git push origin feature/AmazingFeature)
Abra um Pull Request
📄 Licença
Este projeto é apenas para fins educacionais e de demonstração.


Alternativamente, você também pode criar outros arquivos úteis:

## `COMMANDS.md` (arquivo separado para comandos)

```markdown
# Comandos Úteis

## Desenvolvimento

### Executar a aplicação
```bash
mvn spring-boot:run
Executar testes
mvn test
Executar apenas testes unitários
mvn test -Dtest=*Test
Compilar o projeto
mvn compile
Limpar e compilar
mvn clean compile
Gerar JAR
mvn clean package
Executar JAR gerado
java -jar target/twitter-api-0.0.1-SNAPSHOT.jar
Docker (se configurado)
Subir aplicação com Docker
docker-compose up
Subir em background
docker-compose up -d
Parar containers
docker-compose down
URLs Importantes
API Base URL: http://localhost:8080
Health Check: http://localhost:8080/actuator/health (se configurado)

## `.gitignore` (para não versionar arquivos desnecessários)

```gitignore
# Compiled class file
*.class

# Log file
*.log

# BlueJ files
*.ctxt

# Mobile Tools for Java (J2ME)
.mtj.tmp/

# Package Files #
*.jar
*.war
*.nar
*.ear
*.zip
*.tar.gz
*.rar

# virtual machine crash logs
hs_err_pid*

# Maven
target/
pom.xml.tag
pom.xml.releaseBackup
pom.xml.versionsBackup
pom.xml.next
release.properties
dependency-reduced-pom.xml
buildNumber.properties
.mvn/timing.properties
.mvn/wrapper/maven-wrapper.jar

# IDE
.idea/
*.iws
*.iml
*.ipr
.vscode/
.classpath
.project
.settings/

# OS
.DS_Store
Thumbs.db