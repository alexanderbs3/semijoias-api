# 💍 Semijoia API

API RESTful para e-commerce de semijoias, desenvolvida com **Spring Boot 3**, **Spring Security** e **JWT**. Permite gerenciamento de produtos, categorias, carrinho de compras, pedidos e avaliações.

---

## 🚀 Tecnologias

| Tecnologia | Versão | Uso |
|---|---|---|
| Java | 17+ | Linguagem principal |
| Spring Boot | 3.x | Framework base |
| Spring Security | 6.x | Autenticação e autorização |
| JWT (JJWT) | - | Tokens de acesso |
| MySQL | 8.0 | Banco de dados relacional |
| Flyway | - | Versionamento de schema |
| MapStruct | - | Mapeamento de DTOs |
| Lombok | - | Redução de boilerplate |
| Docker / Docker Compose | - | Ambiente de desenvolvimento |

---

## 📁 Estrutura do Projeto

```
src/
└── main/
    └── java/br/leetjourney/semijoiasapi/
        ├── api/
        │   ├── controller/        # AuthController, CartController, OrderController, ProductController
        │   ├── dto/
        │   │   ├── request/       # LoginRequestDTO, ProductRequestDTO, CartItemRequestDTO...
        │   │   └── response/      # LoginResponseDTO, ProductResponseDTO, OrderResponseDTO...
        │   ├── exception/         # GlobalExceptionHandler, BusinessException, ResourceNotFoundException
        │   ├── mapper/            # OrderMapper, ProductMapper (MapStruct)
        │   └── web/               # WebConfig (CORS)
        ├── config/
        │   ├── SecurityConfig.java
        │   └── SecurityFilter.java
        └── core/
            ├── entity/            # User, Product, Category, Order, OrderItem, CartItem
            ├── enums/             # UserRole, OrderStatus
            ├── repository/        # Interfaces JPA
            ├── security/          # JwtService
            └── service/           # CartService, OrderService, ProductService
```

---

## ⚙️ Configuração e Execução

### Pré-requisitos

- Java 17+
- Maven 3.8+
- Docker e Docker Compose

### 1. Suba o banco de dados com Docker

```bash
docker-compose up -d
```

Isso irá inicializar um container MySQL 8.0 com as seguintes credenciais:

| Parâmetro | Valor |
|---|---|
| Database | `semijoia_db` |
| Usuário | `user_dev` |
| Senha | `dev_password` |
| Porta | `3306` |

### 2. Configure as variáveis de ambiente (opcional)

Por padrão, um segredo JWT de desenvolvimento já está configurado no `application.yml`. Em **produção**, defina a variável de ambiente:

```bash
export JWT_SECRET=seu_segredo_super_seguro_aqui
```

### 3. Execute a aplicação

```bash
./mvnw spring-boot:run
```

A API estará disponível em: `http://localhost:8080`

---

## 🔐 Autenticação

A API utiliza **JWT (Bearer Token)**. O fluxo é:

1. Realize o login via `POST /api/auth/login`
2. Copie o token retornado
3. Inclua no header de todas as requisições protegidas:

```
Authorization: Bearer <seu_token>
```

---

## 📌 Endpoints

### Auth

| Método | Rota | Acesso | Descrição |
|---|---|---|---|
| `POST` | `/api/auth/login` | Público | Autentica usuário e retorna JWT |

**Body (login):**
```json
{
  "email": "admin@semijoia.com",
  "password": "admin123"
}
```

**Resposta:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "name": "Admin",
  "role": "ADMIN"
}
```

---

### Produtos

| Método | Rota | Acesso | Descrição |
|---|---|---|---|
| `GET` | `/api/products` | Público | Lista produtos (suporta filtros) |
| `POST` | `/api/products` | `ADMIN` | Cadastra novo produto |

**Query params (GET):**
- `categoryId` — filtra por categoria
- `name` — busca por nome (parcial, case-insensitive)

**Body (POST):**
```json
{
  "name": "Anel Solitário Cristal",
  "description": "Anel clássico folheado a ouro 18k",
  "price": 89.90,
  "stockQuantity": 50,
  "imageUrl": "https://link-imagem.com/anel1.jpg",
  "categoryId": 1
}
```

---

### Carrinho

| Método | Rota | Acesso | Descrição |
|---|---|---|---|
| `POST` | `/api/cart` | Autenticado | Adiciona item ao carrinho |
| `GET` | `/api/cart` | Autenticado | Lista itens do carrinho |
| `DELETE` | `/api/cart/{productId}` | Autenticado | Remove item do carrinho |

**Body (POST):**
```json
{
  "productId": 1,
  "quantity": 2
}
```

---

### Pedidos

| Método | Rota | Acesso | Descrição |
|---|---|---|---|
| `POST` | `/api/orders/checkout` | Autenticado | Finaliza compra e cria pedido |
| `GET` | `/api/orders` | Autenticado | Lista pedidos do usuário |

O checkout:
- Valida estoque de todos os itens
- Deduz estoque automaticamente
- Calcula frete (R$ 10,00 para SP / R$ 20,00 para demais estados)
- Limpa o carrinho após a confirmação

---

## 🗄️ Banco de Dados

### Diagrama de Entidades

```
users ──────┬── orders ──── order_items ──── products ──── categories
            │                                    │
            └── cart_items ────────────────────┘
            │
            └── reviews ──── products
```

### Tabelas

| Tabela | Descrição |
|---|---|
| `users` | Usuários da plataforma (USER / ADMIN) |
| `categories` | Categorias de produtos |
| `products` | Catálogo de produtos |
| `cart_items` | Itens no carrinho (único por user + product) |
| `orders` | Pedidos finalizados |
| `order_items` | Itens de cada pedido (snapshot de preço) |
| `reviews` | Avaliações de produtos (único por user + product) |

---

## 🛡️ Segurança

- **Stateless**: Sem sessões no servidor, autenticação 100% via JWT
- **BCrypt**: Senhas armazenadas com hash BCrypt
- **RBAC**: Controle de acesso por roles (`USER`, `ADMIN`)
- **CORS**: Configurado para aceitar requisições de `http://localhost:5173` (Vite/React)

### Rotas públicas

- `POST /api/auth/**`
- `GET /api/products/**`
- `GET /api/categories/**`

### Rotas protegidas por role

- `POST /api/products` → apenas `ADMIN`
- Demais rotas → qualquer usuário autenticado

---

## 🧪 Dados Iniciais (Seed)

O arquivo de migração já inclui dados de exemplo:

**Categorias:** Anéis, Brincos, Colares, Pulseiras

**Produtos de exemplo:** Anel Solitário Cristal, Brinco Argola M, Colar Ponto de Luz

**Usuário admin:**

| Campo | Valor |
|---|---|
| Email | `admin@semijoia.com` |
| Senha | `admin123` |
| Role | `ADMIN` |

---

## 🔄 Status de Pedidos

```
PENDING → PROCESSING → SHIPPED → DELIVERED
                ↓
            CANCELLED
```

| Status | Descrição |
|---|---|
| `PENDING` | Pedido criado, aguardando processamento |
| `PROCESSING` | Em processamento |
| `SHIPPED` | Enviado ao cliente |
| `DELIVERED` | Entregue |
| `CANCELLED` | Cancelado |

---

## 🐳 Docker Compose

```yaml
# Sobe apenas o banco de dados
docker-compose up -d

# Para os containers
docker-compose down

# Remove volumes (apaga todos os dados)
docker-compose down -v
```

