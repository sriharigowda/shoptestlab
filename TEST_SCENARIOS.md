# 🧪 ShopTestLab — Test Scenarios to Master

Use this as your learning checklist. Start from manual testing, then automate.

---

## 📗 API TESTING SCENARIOS (Priority 1)

### Authentication Module
| # | Scenario | Type | Expected |
|---|---|---|---|
| 1 | Register new user with valid data | Positive | 200, returns token |
| 2 | Register with existing username | Negative | 400, "Username already taken" |
| 3 | Register with existing email | Negative | 400, "Email already registered" |
| 4 | Register with invalid email format | Negative | 400, validation error |
| 5 | Register with password < 6 chars | Negative | 400, validation error |
| 6 | Register with empty body | Negative | 400, validation errors |
| 7 | Login with valid credentials | Positive | 200, JWT token |
| 8 | Login with wrong password | Negative | 401, "Invalid username or password" |
| 9 | Login with non-existent user | Negative | 401 |
| 10 | Login with empty credentials | Negative | 400 |

### Products Module
| # | Scenario | Type | Expected |
|---|---|---|---|
| 11 | Get all products (no auth) | Positive | 200, list of 10 products |
| 12 | Get product by valid ID | Positive | 200, product details |
| 13 | Get product by invalid ID (999) | Negative | 404, "Product not found" |
| 14 | Search products by name "iphone" | Positive | 200, matching results |
| 15 | Search with empty name | Edge | 200, behavior check |
| 16 | Filter by category "Electronics" | Positive | 200, 4+ products |
| 17 | Filter by non-existent category | Edge | 200, empty list |

### Cart Module
| # | Scenario | Type | Expected |
|---|---|---|---|
| 18 | Add to cart with auth | Positive | 200, cart item |
| 19 | Add to cart without auth | Negative | 401/403 |
| 20 | Add with expired/invalid token | Negative | 401 |
| 21 | Add non-existent product | Negative | 404 |
| 22 | Add quantity > stock | Negative | 400, "Insufficient stock" |
| 23 | Add same product twice | Positive | Quantity adds up |
| 24 | Update cart quantity | Positive | 200 |
| 25 | Update quantity to 0 | Negative | 400 |
| 26 | Update another user's cart | Negative | 400, unauthorized |
| 27 | Remove item from cart | Positive | 200 |
| 28 | Clear entire cart | Positive | 200 |

### Orders Module
| # | Scenario | Type | Expected |
|---|---|---|---|
| 29 | Checkout with items in cart | Positive | 200, order created, cart cleared |
| 30 | Checkout with empty cart | Negative | 400, "Cart is empty" |
| 31 | Checkout without shipping address | Negative | 400, validation |
| 32 | Verify stock reduced after order | Positive | Stock decreased |
| 33 | Get my orders | Positive | 200, ordered by date desc |
| 34 | Get specific order by ID | Positive | 200 |
| 35 | Access another user's order | Negative | 400, unauthorized |

### Admin Module
| # | Scenario | Type | Expected |
|---|---|---|---|
| 36 | Admin creates product | Positive | 200 |
| 37 | Regular user tries admin endpoint | Negative | 403 Forbidden |
| 38 | Admin updates product | Positive | 200 |
| 39 | Admin deletes product | Positive | 200 |

---

## 🖼️ UI AUTOMATION SCENARIOS (Priority 2)
*Once we build the React frontend:*

### Login Page
- Valid login redirects to home
- Invalid login shows error
- Empty fields show validation
- "Remember me" checkbox works
- Forgot password link navigates correctly

### Product Listing
- All products display with image, name, price
- Search filters products
- Category filter works
- Click product → goes to details
- "Add to Cart" button works

### Cart Page
- Shows all added items
- Update quantity reflects immediately
- Remove item updates cart count
- Total price calculates correctly
- Empty cart shows empty state

### Checkout Flow
- Address form validation
- Order summary shows correct total
- Successful checkout shows confirmation
- Order appears in order history

---

## 🗄️ DATABASE TESTING SCENARIOS (Priority 3)

### Data Validation
- After register API → verify row in `users` table
- After add to cart → verify row in `cart_items`
- After checkout → verify `orders` + `order_items`
- After checkout → verify `cart_items` cleared
- After checkout → verify `products.stock` reduced

### Data Integrity
- No orphan order_items (FK constraint)
- Order total = SUM(quantity × price) of items
- User email is unique
- Username is unique

### Aggregations (from practice_queries.sql)
- Top 5 best-selling products
- Total revenue
- Low stock alerts (< 30)
- Users with most orders

---

## 🎯 Recommended Learning Path

### Week 1: Manual API Testing
- Test all 39 scenarios in Swagger UI
- Document actual vs expected
- Build Postman collection
- Use Postman environments + token variables

### Week 2: RestAssured Automation
- Build framework with Page Object-like structure
- Automate all positive scenarios first
- Then negative scenarios
- Add JSON Schema validation
- TestNG parallel execution
- Extent Reports

### Week 3-4: Selenium UI Automation
- After React frontend is built
- Page Object Model
- All scenarios from UI section
- Data-driven tests with Excel

### Week 5: DB Testing
- JDBC utilities in Java
- Combine with API tests (end-to-end validation)
- Complex SQL queries

---

## 📝 Bug Reporting Template

Use this format when you find a bug (practice!):

```
Bug ID: SHOP-001
Title: [Short description]
Module: [Auth/Products/Cart/Orders]
Severity: [Critical/High/Medium/Low]
Priority: [P1/P2/P3]

Steps to Reproduce:
1. 
2. 
3. 

Expected: 
Actual: 
Environment: Local, Spring Boot 3.2.5, MySQL 8.0
Evidence: [Screenshot/Response]
```

---

Happy testing, Srihari! 💪
