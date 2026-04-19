-- =====================================================
-- ShopTestLab - Database Testing Practice Queries
-- Use these queries to validate data after API calls
-- =====================================================

-- Switch to database
USE shoptestlab;

-- =====================================================
-- SECTION 1: VIEW DATA (basic SELECT practice)
-- =====================================================

-- List all users
SELECT id, username, email, role, created_at FROM users;

-- List all products
SELECT id, name, price, stock, category FROM products;

-- List all orders with user info
SELECT o.id, u.username, o.total_amount, o.status, o.created_at
FROM orders o
JOIN users u ON o.user_id = u.id
ORDER BY o.created_at DESC;

-- =====================================================
-- SECTION 2: VALIDATION AFTER API CALLS
-- =====================================================

-- After POST /api/auth/register → verify user created
SELECT * FROM users WHERE username = 'testuser123';

-- After POST /api/cart → verify cart item exists
SELECT c.id, u.username, p.name, c.quantity
FROM cart_items c
JOIN users u ON c.user_id = u.id
JOIN products p ON c.product_id = p.id
WHERE u.username = 'testuser';

-- After POST /api/orders/checkout → verify order + items
SELECT o.id as order_id, o.total_amount, o.status,
       oi.product_id, p.name, oi.quantity, oi.price
FROM orders o
JOIN order_items oi ON oi.order_id = o.id
JOIN products p ON oi.product_id = p.id
WHERE o.user_id = (SELECT id FROM users WHERE username = 'testuser')
ORDER BY o.created_at DESC;

-- Verify cart cleared after checkout
SELECT COUNT(*) as remaining_cart_items
FROM cart_items
WHERE user_id = (SELECT id FROM users WHERE username = 'testuser');

-- Verify stock reduced after order
SELECT id, name, stock FROM products WHERE id = 1;

-- =====================================================
-- SECTION 3: AGGREGATIONS (interview-level queries)
-- =====================================================

-- Total revenue
SELECT SUM(total_amount) as total_revenue FROM orders WHERE status != 'CANCELLED';

-- Top 5 best-selling products
SELECT p.name, SUM(oi.quantity) as total_sold
FROM order_items oi
JOIN products p ON oi.product_id = p.id
GROUP BY p.id, p.name
ORDER BY total_sold DESC
LIMIT 5;

-- Products never ordered
SELECT p.id, p.name FROM products p
LEFT JOIN order_items oi ON oi.product_id = p.id
WHERE oi.id IS NULL;

-- Users with most orders
SELECT u.username, COUNT(o.id) as order_count, SUM(o.total_amount) as total_spent
FROM users u
LEFT JOIN orders o ON o.user_id = u.id
GROUP BY u.id, u.username
ORDER BY order_count DESC;

-- Products low in stock (< 30)
SELECT id, name, stock, category FROM products WHERE stock < 30 ORDER BY stock ASC;

-- =====================================================
-- SECTION 4: DATA INTEGRITY CHECKS
-- =====================================================

-- Find orphan order items (shouldn't exist - FK prevents it)
SELECT oi.* FROM order_items oi
LEFT JOIN orders o ON oi.order_id = o.id
WHERE o.id IS NULL;

-- Find orders without items (shouldn't exist)
SELECT o.* FROM orders o
LEFT JOIN order_items oi ON oi.order_id = o.id
WHERE oi.id IS NULL;

-- Verify order total = sum of (quantity × price)
SELECT o.id, o.total_amount as stored_total,
       SUM(oi.quantity * oi.price) as calculated_total,
       CASE WHEN o.total_amount = SUM(oi.quantity * oi.price)
            THEN 'MATCH' ELSE 'MISMATCH' END as status
FROM orders o
JOIN order_items oi ON oi.order_id = o.id
GROUP BY o.id, o.total_amount;

-- =====================================================
-- SECTION 5: CLEANUP (for test data reset)
-- =====================================================

-- Reset test user data (safe, preserves admin + products)
-- DELETE FROM cart_items WHERE user_id = (SELECT id FROM users WHERE username = 'testuser');
-- DELETE FROM order_items WHERE order_id IN (SELECT id FROM orders WHERE user_id = (SELECT id FROM users WHERE username = 'testuser'));
-- DELETE FROM orders WHERE user_id = (SELECT id FROM users WHERE username = 'testuser');

-- Nuclear option (deletes everything)
-- SET FOREIGN_KEY_CHECKS = 0;
-- TRUNCATE TABLE order_items;
-- TRUNCATE TABLE orders;
-- TRUNCATE TABLE cart_items;
-- TRUNCATE TABLE products;
-- TRUNCATE TABLE users;
-- SET FOREIGN_KEY_CHECKS = 1;
