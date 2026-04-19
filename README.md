# ShopTestLab — E-Commerce QA Testing Playground

A complete Spring Boot e-commerce backend built specifically for practicing:
- **API Testing** (RestAssured, Postman)
- **UI Automation** (Selenium — frontend coming next)
- **Database Testing** (MySQL, JDBC)

---

## 🛠️ Prerequisites

Install these on your machine (all free):

| Tool | Version | Download |
|---|---|---|
| Java JDK | 17+ | https://adoptium.net |
| MySQL Community | 8.0+ | https://dev.mysql.com/downloads/ |
| Maven | 3.8+ | Bundled with IntelliJ |
| IntelliJ IDEA | Community | https://www.jetbrains.com/idea/download |
| Postman | Latest | https://www.postman.com/downloads/ |

---

## 🚀 Setup Steps

### 1. Setup MySQL
Open MySQL Workbench or command line and run:
```sql
CREATE DATABASE shoptestlab;
```
(The app will auto-create tables via JPA)

### 2. Configure Database Password
Open `src/main/resources/application.properties` and change:
```properties
spring.datasource.password=root
```
Replace `root` with your actual MySQL password.

### 3. Run the Application
From project root:
```bash
mvn spring-boot:run
```

Or in IntelliJ: right-click `ShopTestLabApplication.java` → Run

### 4. Verify it's Running
Open in browser:
- **Swagger UI:** http://localhost:8080/swagger-ui.html
- **API Docs JSON:** http://localhost:8080/v3/api-docs

---

## 👤 Pre-Seeded Test Accounts

| Role | Username | Password |
|---|---|---|
| Admin | `admin` | `admin123` |
| User | `testuser` | `test123` |

10 sample products are automatically seeded across categories: Electronics, Footwear, Clothing, Books.

---

## 📡 API Endpoints

### 🔓 Public
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/auth/register` | Register new user |
| POST | `/api/auth/login` | Login (returns JWT) |
| GET | `/api/products` | List all products |
| GET | `/api/products/{id}` | Get product by ID |
| GET | `/api/products/search?name=...` | Search products |
| GET | `/api/products/category/{category}` | Filter by category |

### 🔒 Authenticated (need JWT in `Authorization: Bearer <token>`)
| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/cart` | View cart |
| POST | `/api/cart` | Add to cart |
| PUT | `/api/cart/{id}` | Update quantity |
| DELETE | `/api/cart/{id}` | Remove item |
| DELETE | `/api/cart` | Clear cart |
| POST | `/api/orders/checkout` | Place order |
| GET | `/api/orders` | My orders |
| GET | `/api/orders/{id}` | Get order details |

### 👑 Admin Only (ROLE_ADMIN)
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/admin/products` | Create product |
| PUT | `/api/admin/products/{id}` | Update product |
| DELETE | `/api/admin/products/{id}` | Delete product |

---

## 🧪 Sample Test Flow

### 1. Register via Swagger or curl:
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"srihari","email":"srihari@test.com","password":"pass123","fullName":"Srihari"}'
```

### 2. Login:
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"srihari","password":"pass123"}'
```
Copy the `token` from response.

### 3. Add to cart (use token):
```bash
curl -X POST http://localhost:8080/api/cart \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"productId":1,"quantity":2}'
```

### 4. Checkout:
```bash
curl -X POST http://localhost:8080/api/orders/checkout \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"shippingAddress":"Mandya, Karnataka"}'
```

### 5. Verify in DB:
```sql
SELECT * FROM orders WHERE user_id = (SELECT id FROM users WHERE username = 'srihari');
```

---

## 📚 Your QA Learning Journey

### Phase 1 — API Testing (Weeks 1-2)
1. **Manual:** Test all endpoints in Swagger UI
2. **Postman:** Build collections, environments, use Postman variables for token
3. **RestAssured:** (next session) — We'll build a full framework

### Phase 2 — UI Automation (Weeks 3-4)
1. React frontend (coming in next session)
2. Selenium Page Object Model
3. Cross-browser, data-driven tests

### Phase 3 — DB Testing (Week 5)
1. Practice queries in `database/practice_queries.sql`
2. JDBC automation to validate API responses against DB

---

## 📁 Project Structure

```
shoptestlab/
├── pom.xml
├── README.md
├── database/
│   └── practice_queries.sql
├── docs/
│   └── ShopTestLab.postman_collection.json
└── src/main/java/com/shoptestlab/
    ├── ShopTestLabApplication.java
    ├── config/          (Security, Swagger, DataLoader)
    ├── controller/      (REST endpoints)
    ├── service/         (Business logic)
    ├── repository/      (JPA repos)
    ├── model/           (Entities)
    ├── dto/             (Request/Response objects)
    ├── security/        (JWT)
    └── exception/       (Error handling)
```

---

## 🐛 Troubleshooting

**Error: "Access denied for user 'root'"**
→ Update password in `application.properties`

**Port 8080 already in use**
→ Change `server.port=8080` to `server.port=8081`

**"Table doesn't exist"**
→ Make sure `spring.jpa.hibernate.ddl-auto=update` and restart app

---

Built with ❤️ for Srihari's QA mastery journey.
