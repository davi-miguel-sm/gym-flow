# 💪 Gym Flow — Diário de Cargas

Aplicativo Web para acompanhamento de treinos com foco em **registro diário de cargas**, **seleção de exercícios por grupo muscular** e **visualização da progressão** através de gráficos. Ideal para quem deseja monitorar sua evolução na academia de forma prática e detalhada.

---

## 🧱 Estrutura do Projeto

```
app-fitness/
├── backend/         → API REST em Java (Spring Boot)
├── frontend/        → Aplicação Web em TypeScript (Angular)
├── containers/      → Infraestrutura (Docker Compose: PostgreSQL + MinIO)
└── README.md        → Documentação do projeto
```

---

## 🚀 Funcionalidades

- **Autenticação OAuth2** com suporte para login social (futuro upgrade)
- **Registro diário de cargas** de exercícios
- **Seleção de exercícios** por grupo muscular (inspirado na interface do MuscleWiki)
- **Upload de imagens e vídeos** associados aos exercícios via API compatível com S3
- **Geração de gráficos de evolução** para acompanhamento do progresso

---

## 🧰 Tecnologias Utilizadas

| Camada         | Tecnologia                  |
| -------------- | --------------------------- |
| Frontend       | Angular                     |
| Backend        | Java + Spring Boot          |
| Banco de Dados | PostgreSQL                  |
| Armazenamento  | MinIO (S3 local compatível) |
| Infraestrutura | Docker + Docker Compose     |

---

## 🔧 Como Rodar Localmente

### Pré-requisitos

- [Docker](https://www.docker.com/) e [Docker Compose](https://docs.docker.com/compose/)
- Java 21+ (para o backend)
- Node.js 18+ (para o frontend)
- Emulador ou dispositivo móvel (para testar o app)

---

### Subir os Containers

1. Navegue até a pasta `containers`:
   ```
   cd containers
   ```
2. Suba os containers com:
   ```
   docker-compose up -d
   ```

Os serviços serão disponibilizados em:

- **PostgreSQL:** `localhost:5432`
- **MinIO API (S3):** `http://localhost:9000`
- **MinIO Web UI:** `http://localhost:9001`

---

### Rodando o Backend

1. Navegue até a pasta `backend`:
   ```
   cd ../backend
   ```
2. Compile e rode a aplicação (exemplo com Maven):
   ```
   ./mvnw spring-boot:run
   ```

---

### Rodando o Frontend

1. Navegue até a pasta `frontend`:
   ```
   cd ../frontend
   ```
2. Instale as dependências e inicie o app:
   ```
   npm install
   npm start
   ```

---

## 📁 Configuração de Variáveis de Ambiente

### containers/.env

```env
# PostgreSQL
POSTGRES_DB=fitnessapp
POSTGRES_USER=admin
POSTGRES_PASSWORD=admin123

# MinIO
MINIO_ROOT_USER=admin
MINIO_ROOT_PASSWORD=admin123
```

### backend/.env ou application.yml (exemplo)

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/${POSTGRES_DB}
    username: ${POSTGRES_USER}
    password: ${POSTGRES_PASSWORD}
  jpa:
    hibernate:
      ddl-auto: update
    properties:
      hibernate:
        dialect: org.hibernate.dialect.PostgreSQLDialect

minio:
  endpoint: http://localhost:9000
  access-key: ${MINIO_ROOT_USER}
  secret-key: ${MINIO_ROOT_PASSWORD}
```

---

## 📊 Progresso do Projeto

- [x] Estrutura de pastas definida
- [x] Setup inicial dos containers (PostgreSQL + MinIO)
- [x] Autenticação OAuth2 e integração de segurança
- [x] Modelagem completa do banco de dados
- [x] Implementação do endpoint para upload de imagens
- [ ] Tela de seleção de exercícios com imagens/vídeos
- [ ] Registro e visualização dos treinos com gráficos

---

## 👨‍💻 Autor

Desenvolvido com 💪 por [Davi Miguel](https://www.linkedin.com/in/dev-dmiguelsm/)

---

## 📄 Licença

Este projeto é livre para fins de estudo, aprendizado e uso pessoal.
