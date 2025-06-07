# QAirlines - Airline Booking

QAirlines là hệ thống đặt vé máy bay và quản lý hãng hàng không hiện đại, hỗ trợ cả khách hàng và quản trị viên.

---

## 🚀 Tính năng nổi bật

### Đối với khách hàng
- **Đăng ký, đăng nhập** tài khoản khách hàng.
- **Tìm kiếm, đặt vé máy bay** theo hành trình, ngày đi.
- **Quản lý vé đã đặt**: xem lịch sử, hủy vé trước giờ bay.
- **Xem thông tin chuyến bay**: tra cứu trạng thái, giờ đi/đến.
- **Xem tin tức, ưu đãi, thông báo** từ hãng.
- **Trang cá nhân**: xem thông tin cá nhân.

### Đối với quản trị viên/nhân viên
- **Đăng nhập hệ thống quản trị**.
- **Đăng tin tức, khuyến mại, thông báo** cho khách hàng.
- **Quản lý chuyến bay**: thêm, sửa, xóa chuyến bay.
- **Quản lý tàu bay**: thêm, sửa, xóa tàu bay.
- **Cập nhật giờ delay chuyến bay**.
- **Thống kê, báo cáo** (nếu có).

---

## ⚙️ Hướng dẫn cài đặt & chạy demo

### 1. Backend (Spring Boot)

- **Yêu cầu:** Java 8+, Maven, MySQL
- **Cài đặt:**
  1. Cấu hình `application.properties` với thông tin database.
  2. Build và chạy:
     ```bash
     cd spring-boot
     mvn clean install
     mvn spring-boot:run
     ```
     (Có thể chạy trên các IDE khác như Eclipse hay Intellij)
  3. API chạy tại: `http://localhost:8081/api/`

### 2. Frontend (ReactJS)

- **Yêu cầu:** NodeJS 16+, npm
- **Cài đặt:**
  1. Cài dependencies:
     ```bash
     cd qairline
     npm install
     ```
  2. Chạy ứng dụng:
     ```bash
     npm start
     ```
  3. Truy cập: [http://localhost:3000](http://localhost:3000)

---


## 💡 Công nghệ sử dụng

- **Frontend:** ReactJS, React Router, SCSS, React Icons, React Multi Carousel
- **Backend:** Spring Boot, Spring Security (JWT), JPA, MySQL

---

## 📷 Demo giao diện

- Trang chủ, đặt vé, quản lý vé, tin tức, trang admin, đăng tin, quản lý chuyến bay, tàu bay, delay, v.v.

---

## 📚 Tài liệu mở rộng

- Cấu hình router: [router-config.js](http://_vscodecontentref_/3)
- API backend: `spring-boot/src/main/java/com/airline/api/controller`

---

## 🧑‍💻 Nhóm thực hiện

- [Nguyễn Vũ Phương Đông 21020817]
- [Trần Hoàng Anh 21020802]
- [Đỗ Đình Trường 21020858]

---

## 📄 License

This project is for academic and demonstration purposes only.