# 🛒 E-Commerce Backend API

## 📖 Giới thiệu
Dự án **E-Commerce Backend** được xây dựng bằng **Spring Boot** với mục tiêu tạo REST API cho website bán điện thoại và máy tính.  
Hỗ trợ **đăng nhập, quản lý sản phẩm, giỏ hàng, đặt hàng và phân quyền ADMIN/USER**.

---

## 🧠 Công nghệ sử dụng
| Công nghệ | Mô tả |
|------------|--------|
| Java 17 | Ngôn ngữ chính |
| Spring Boot 3 | Framework backend |
| Spring Security + JWT | Xác thực & phân quyền |
| JPA (Hibernate) | ORM thao tác DB |
| MySQL | Cơ sở dữ liệu |
| Postman | Test API |

---

## ⚙️ Cấu trúc dự án
```
src
┣ 📂controller → REST API endpoints
┣ 📂service → Xử lý logic nghiệp vụ
┣ 📂repository → Tầng giao tiếp DB (JPA)
┣ 📂entity → Các model / bảng DB
┣ 📂dto → Data Transfer Object
┣ 📂mapper → Chuyển đổi Entity ↔ DTO (MapStruct)
┣ 📂security → Cấu hình JWT & Spring Security
┗ 📂exception → Xử lý lỗi toàn cục
```

## 🚀 Chức năng chính
### 👤 Authentication
- Đăng ký / Đăng nhập (JWT)
- Refresh token, Logout (xoá refresh token trong DB)

### 🧾 Product & Category
- CRUD sản phẩm và danh mục

### 🛒 Cart
- Thêm / xoá sản phẩm khỏi giỏ
- Xem giỏ hàng của user

### 🧺 Order
- Tạo đơn hàng từ giỏ (Order + OrderDetail)
- Xem lịch sử đơn hàng của user
- ADMIN xem tất cả đơn hàng

---

## 🔐 JWT Authentication
- Access Token: hiệu lực 15 phút  
- Refresh Token: hiệu lực 7 ngày (lưu trong DB)  
- Logout: xoá refresh token khỏi DB → bắt buộc đăng nhập lại

---

👨‍💻 Tác giả
Bùi Quang Minh 
📧 Email: buiminh272002@gmail.com
