# ⚡ ShopTestLab — 10-Minute Quick Start

## Step 1: Extract & Open (1 min)
1. Unzip `shoptestlab.zip`
2. Open **IntelliJ IDEA Community**
3. File → Open → Select the `shoptestlab` folder
4. Wait for Maven to download dependencies (~2-3 min first time)

---

## Step 2: Install MySQL (if not already) (3 min)
1. Download: https://dev.mysql.com/downloads/installer/
2. Install with default settings
3. Set root password (remember it!)
4. Open MySQL Workbench
5. Run: `CREATE DATABASE shoptestlab;`

---

## Step 3: Configure Password (1 min)
Open `src/main/resources/application.properties`

Change line 7:
```properties
spring.datasource.password=YOUR_MYSQL_PASSWORD_HERE
```

---

## Step 4: Run the App (1 min)
In IntelliJ:
- Open `ShopTestLabApplication.java`
- Click the green ▶ next to `public class`
- Select "Run"

You should see in console:
```
==============================================
ShopTestLab is running!
Swagger UI: http://localhost:8080/swagger-ui.html
==============================================
>>> Admin user created: admin / admin123
>>> Test user created: testuser / test123
>>> 10 sample products seeded
```

---

## Step 5: Test via Swagger (2 min)
1. Open browser: http://localhost:8080/swagger-ui.html
2. Expand **"1. Authentication"** → `POST /api/auth/login`
3. Click **Try it out**, paste:
```json
{
  "username": "testuser",
  "password": "test123"
}
```
4. Click **Execute**
5. Copy the `token` value from response

### Authenticate Swagger
1. Click green **Authorize** button (top right)
2. Paste: `Bearer YOUR_TOKEN_HERE`
3. Click **Authorize** → Close

Now you can test protected endpoints!

---

## Step 6: Test Complete Flow (2 min)
Try these in order:

### Add to Cart
`POST /api/cart`
```json
{ "productId": 1, "quantity": 2 }
```

### View Cart
`GET /api/cart` → See your added item

### Checkout
`POST /api/orders/checkout`
```json
{ "shippingAddress": "Mandya, Karnataka, India" }
```

### View Orders
`GET /api/orders` → See your order!

---

## Step 7: Verify in Database
Open MySQL Workbench, run:
```sql
USE shoptestlab;
SELECT * FROM orders;
SELECT * FROM order_items;
```

You just completed an **end-to-end e-commerce flow**! 🎉

---

## ✅ What's Working Now
- ✅ User registration + login (JWT)
- ✅ Product catalog (search, filter)
- ✅ Shopping cart (add, update, remove)
- ✅ Checkout → creates order, reduces stock, clears cart
- ✅ Order history
- ✅ Admin CRUD on products

## 🎯 What's Next (Ask Claude!)
- **RestAssured automation framework** for all APIs
- **React frontend** for Selenium UI tests
- **JDBC test utilities** for DB validation
- **Jenkins pipeline** for CI/CD
- **Extent Reports** integration
