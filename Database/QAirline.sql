CREATE DATABASE AIRLINE;

USE AIRLINE;

-- Các tables
CREATE TABLE airports (
    airport_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    code VARCHAR(10) UNIQUE NOT NULL,
    city VARCHAR(100) NOT NULL,
    country VARCHAR(100) NOT NULL
) ENGINE=InnoDB;

CREATE TABLE airlines (
    airline_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    code VARCHAR(10) UNIQUE NOT NULL
) ENGINE=InnoDB;

CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) UNIQUE NOT NULL,
    password_hash TEXT NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    role ENUM('Admin', 'Customer', 'Staff') NOT NULL
) ENGINE=InnoDB;

CREATE TABLE passengers (
    passenger_id INT AUTO_INCREMENT PRIMARY KEY,	
    full_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    phone VARCHAR(20) NOT NULL,
    passport_number VARCHAR(50) UNIQUE NOT NULL,
    user_id INT UNIQUE,

    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE flights (
    flight_id INT AUTO_INCREMENT PRIMARY KEY,
    airline_id INT NOT NULL,
    flight_number VARCHAR(20) NOT NULL,
    departure_airport INT NOT NULL,
    arrival_airport INT NOT NULL,
    departure_time DATETIME NOT NULL,
    arrival_time DATETIME NOT NULL,
    status ENUM('Scheduled', 'Delayed', 'Cancelled', 'Departed', 'Arrived') NOT NULL,

    CONSTRAINT fk_flight_airline FOREIGN KEY (airline_id) REFERENCES airlines(airline_id) ON DELETE CASCADE,
    CONSTRAINT fk_departure_airport FOREIGN KEY (departure_airport) REFERENCES airports(airport_id) ON DELETE CASCADE,
    CONSTRAINT fk_arrival_airport FOREIGN KEY (arrival_airport) REFERENCES airports(airport_id) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE aircrafts (
    aircraft_id INT AUTO_INCREMENT PRIMARY KEY,
    airline_id INT NOT NULL,
    model VARCHAR(100) NOT NULL,
    capacity INT NOT NULL,

    CONSTRAINT fk_aircraft_airline FOREIGN KEY (airline_id) REFERENCES airlines(airline_id) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE flight_seats (
    seat_id INT AUTO_INCREMENT PRIMARY KEY,
    flight_id INT NOT NULL,
    seat_number VARCHAR(10) NOT NULL,
    class ENUM('Economy', 'Business', 'First Class') NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    is_booked BOOLEAN DEFAULT FALSE,

    CONSTRAINT fk_flight_seat FOREIGN KEY (flight_id) REFERENCES flights(flight_id) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE bookings (
    booking_id INT AUTO_INCREMENT PRIMARY KEY,
    passenger_id INT NOT NULL,
    flight_id INT NOT NULL,
    seat_id INT NOT NULL,
    booking_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status ENUM('Confirmed', 'Cancelled', 'Pending') NOT NULL,

    CONSTRAINT fk_booking_passenger FOREIGN KEY (passenger_id) REFERENCES passengers(passenger_id) ON DELETE CASCADE,
    CONSTRAINT fk_booking_flight FOREIGN KEY (flight_id) REFERENCES flights(flight_id) ON DELETE CASCADE,
    CONSTRAINT fk_booking_seat FOREIGN KEY (seat_id) REFERENCES flight_seats(seat_id) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE payments (
    payment_id INT AUTO_INCREMENT PRIMARY KEY,
    booking_id INT NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    payment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    payment_method ENUM('Credit Card', 'PayPal', 'Bank Transfer') NOT NULL,
    status ENUM('Paid', 'Pending', 'Failed') NOT NULL,

    CONSTRAINT fk_payment_booking FOREIGN KEY (booking_id) REFERENCES bookings(booking_id) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE news (
    news_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    created_at DATETIME NOT NULL,
    author VARCHAR(100)
) ENGINE=InnoDB;

-- Insert Data
INSERT INTO airports (name, code, city, country) VALUES
('Noi Bai International Airport', 'HAN', 'Hanoi', 'Vietnam'),
('Tan Son Nhat International Airport', 'SGN', 'Ho Chi Minh City', 'Vietnam'),
('Da Nang International Airport', 'DAD', 'Da Nang', 'Vietnam'),
('Cam Ranh International Airport', 'CXR', 'Nha Trang', 'Vietnam'),
('Phu Quoc International Airport', 'PQC', 'Phu Quoc', 'Vietnam'),
('Noibai Domestic Terminal', 'NBD', 'Hanoi', 'Vietnam'),
('Suvarnabhumi Airport', 'BKK', 'Bangkok', 'Thailand'),
('Changi Airport', 'SIN', 'Singapore', 'Singapore'),
('Kuala Lumpur International Airport', 'KUL', 'Kuala Lumpur', 'Malaysia'),
('Incheon International Airport', 'ICN', 'Seoul', 'South Korea');

INSERT INTO airlines (name, code) VALUES
('Vietnam Airlines', 'VN'),
('VietJet Air', 'VJ'),
('Bamboo Airways', 'QH'),
('Jetstar Pacific', 'BL'),
('Thai Airways', 'TG'),
('Singapore Airlines', 'SQ'),
('AirAsia', 'AK'),
('Korean Air', 'KE'),
('Qatar Airways', 'QR'),
('Emirates', 'EK');

INSERT INTO users (username, password_hash, email, role) VALUES
-- Admin & Staff
('admin01', '$2a$10$MaonPOBcRp..PIQSQTsg6.EaZ20t8UoO4u2MyTbcdrV5gLuIqFWCi', 'admin@qairline.com', 'Admin'),
('staff01', '$2a$10$MaonPOBcRp..PIQSQTsg6.EaZ20t8UoO4u2MyTbcdrV5gLuIqFWCi', 'staff01@qairline.com', 'Staff'),
('staff02', '$2a$10$MaonPOBcRp..PIQSQTsg6.EaZ20t8UoO4u2MyTbcdrV5gLuIqFWCi', 'staff02@qairline.com', 'Staff'),

-- Customers
('nguyenvanan', '$2a$10$MaonPOBcRp..PIQSQTsg6.EaZ20t8UoO4u2MyTbcdrV5gLuIqFWCi', 'nguyenvanan@gmail.com', 'Customer'),
('tranthibichngoc', '$2a$10$MaonPOBcRp..PIQSQTsg6.EaZ20t8UoO4u2MyTbcdrV5gLuIqFWCi', 'tranthibichngoc@gmail.com', 'Customer'),
('phamducthien', '$2a$10$MaonPOBcRp..PIQSQTsg6.EaZ20t8UoO4u2MyTbcdrV5gLuIqFWCi', 'phamducthien@gmail.com', 'Customer'),
('lethikimanh', '$2a$10$MaonPOBcRp..PIQSQTsg6.EaZ20t8UoO4u2MyTbcdrV5gLuIqFWCi', 'lethikimanh@gmail.com', 'Customer'),
('nguyenvanquan', '$2a$10$MaonPOBcRp..PIQSQTsg6.EaZ20t8UoO4u2MyTbcdrV5gLuIqFWCi', 'nguyenvanquan@gmail.com', 'Customer'),
('doanthanhha', '$2a$10$MaonPOBcRp..PIQSQTsg6.EaZ20t8UoO4u2MyTbcdrV5gLuIqFWCi', 'doanthanhha@gmail.com', 'Customer'),
('hoangminhtu', '$2a$10$MaonPOBcRp..PIQSQTsg6.EaZ20t8UoO4u2MyTbcdrV5gLuIqFWCi', 'hoangminhtu@gmail.com', 'Customer'),
('nguyenvanthanh', '$2a$10$MaonPOBcRp..PIQSQTsg6.EaZ20t8UoO4u2MyTbcdrV5gLuIqFWCi', 'nguyenvanthanh@gmail.com', 'Customer'),
('tranthihongnhung', '$2a$10$MaonPOBcRp..PIQSQTsg6.EaZ20t8UoO4u2MyTbcdrV5gLuIqFWCi', 'tranthihongnhung@gmail.com', 'Customer'),
('phamminhquang', '$2a$10$MaonPOBcRp..PIQSQTsg6.EaZ20t8UoO4u2MyTbcdrV5gLuIqFWCi', 'phamminhquang@gmail.com', 'Customer');

INSERT INTO passengers (full_name, email, phone, passport_number, user_id) VALUES
('Nguyen Van An', 'nguyenvanan@gmail.com', '0901234567', 'P12345678', 4),
('Tran Thi Bich Ngoc', 'tranthibichngoc@gmail.com', '0912345678', 'P23456789', 5),
('Pham Duc Thien', 'phamducthien@gmail.com', '0923456789', 'P34567890', 6),
('Le Thi Kim Anh', 'lethikimanh@gmail.com', '0934567890', 'P45678901', 7),
('Nguyen Van Quan', 'nguyenvanquan@gmail.com', '0945678901', 'P56789012', 8),
('Doan Thanh Ha', 'doanthanhha@gmail.com', '0956789012', 'P67890123', 9),
('Hoang Minh Tu', 'hoangminhtu@gmail.com', '0967890123', 'P78901234', 10),
('Nguyen Van Thanh', 'nguyenvanthanh@gmail.com', '0978901234', 'P89012345', 11),
('Tran Thi Hong Nhung', 'tranthihongnhung@gmail.com', '0989012345', 'P90123456', 12),
('Pham Minh Quang', 'phamminhquang@gmail.com', '0990123456', 'P01234567', 13);

INSERT INTO flights
(airline_id, flight_number, departure_airport, arrival_airport, departure_time, arrival_time, status) VALUES

-- Khứ hồi nội địa: HAN (1) ↔ SGN (2)
(1, 'VN101', 1, 2, '2025-06-05 08:00:00', '2025-06-05 10:00:00', 'Scheduled'),
(1, 'VN102', 2, 1, '2025-06-10 16:00:00', '2025-06-10 18:00:00', 'Scheduled'),

-- Khứ hồi nội địa: DAD (3) ↔ CXR (4)
(2, 'VJ201', 3, 4, '2025-06-15 09:30:00', '2025-06-15 11:00:00', 'Scheduled'),
(2, 'VJ202', 4, 3, '2025-06-20 14:00:00', '2025-06-20 15:30:00', 'Scheduled'),

-- Khứ hồi quốc tế: HAN (1) ↔ SIN (8)
(1, 'VN301', 1, 8, '2025-07-15 09:00:00', '2025-07-15 14:00:00', 'Scheduled'),
(1, 'VN302', 8, 1, '2025-07-22 15:00:00', '2025-07-22 20:00:00', 'Scheduled'),

-- Khứ hồi quốc tế: SGN (2) ↔ BKK (7)
(2, 'VJ401', 2, 7, '2025-07-05 07:00:00', '2025-07-05 09:30:00', 'Scheduled'),
(2, 'VJ402', 7, 2, '2025-07-12 13:00:00', '2025-07-12 15:30:00', 'Scheduled'),

-- Một chiều nội địa
(3, 'BL301', 5, 1, '2025-06-25 20:00:00', '2025-06-25 22:00:00', 'Scheduled'),
(1, 'VN103', 2, 3, '2025-07-01 09:00:00', '2025-07-01 11:15:00', 'Scheduled'),
(2, 'VJ203', 3, 5, '2025-07-10 14:30:00', '2025-07-10 16:45:00', 'Delayed'),
(3, 'BL304', 4, 2, '2025-07-18 18:00:00', '2025-07-18 20:00:00', 'Cancelled'),
(1, 'VN105', 1, 4, '2025-07-25 07:00:00', '2025-07-25 09:30:00', 'Scheduled'),
(2, 'VJ204', 5, 3, '2025-08-05 12:00:00', '2025-08-05 14:15:00', 'Scheduled'),

-- Một chiều quốc tế
(1, 'VN303', 8, 7, '2025-07-30 10:00:00', '2025-07-30 12:30:00', 'Scheduled'),
(2, 'VJ403', 7, 8, '2025-08-01 16:00:00', '2025-08-01 18:30:00', 'Scheduled'),
(3, 'BL305', 9, 10, '2025-08-03 11:00:00', '2025-08-03 16:00:00', 'Scheduled'),

-- Một chiều nội địa khác
(1, 'VN106', 4, 5, '2025-08-06 09:00:00', '2025-08-06 10:45:00', 'Scheduled'),
(2, 'VJ205', 6, 1, '2025-08-08 07:30:00', '2025-08-08 09:00:00', 'Scheduled');


INSERT INTO flight_seats (flight_id, seat_number, class, price, is_booked) VALUES
-- Flight 1 (1 chiều)
(1, 'A1', 'Economy', 100.00, FALSE),
(1, 'A2', 'Economy', 100.00, FALSE),
(1, 'B1', 'Business', 200.00, FALSE),
(1, 'B2', 'Business', 200.00, FALSE),
(1, 'C1', 'First Class', 300.00, FALSE),
(1, 'C2', 'First Class', 300.00, FALSE),

-- Flight 2 (1 chiều)
(2, 'A1', 'Economy', 110.00, FALSE),
(2, 'A2', 'Economy', 110.00, FALSE),
(2, 'B1', 'Business', 210.00, FALSE),
(2, 'B2', 'Business', 210.00, FALSE),
(2, 'C1', 'First Class', 310.00, FALSE),
(2, 'C2', 'First Class', 310.00, FALSE),

-- Flight 3 (khứ hồi)
(3, 'A1', 'Economy', 120.00, FALSE),
(3, 'A2', 'Economy', 120.00, FALSE),
(3, 'B1', 'Business', 220.00, FALSE),
(3, 'B2', 'Business', 220.00, FALSE),
(3, 'C1', 'First Class', 320.00, FALSE),
(3, 'C2', 'First Class', 320.00, FALSE),

-- Flight 4 (khứ hồi)
(4, 'A1', 'Economy', 130.00, FALSE),
(4, 'A2', 'Economy', 130.00, FALSE),
(4, 'B1', 'Business', 230.00, FALSE),
(4, 'B2', 'Business', 230.00, FALSE),
(4, 'C1', 'First Class', 330.00, FALSE),
(4, 'C2', 'First Class', 330.00, FALSE),

-- Flight 5 (1 chiều)
(5, 'A1', 'Economy', 140.00, FALSE),
(5, 'A2', 'Economy', 140.00, FALSE),
(5, 'B1', 'Business', 240.00, FALSE),
(5, 'B2', 'Business', 240.00, FALSE),
(5, 'C1', 'First Class', 340.00, FALSE),
(5, 'C2', 'First Class', 340.00, FALSE),

-- Flight 6 (1 chiều)
(6, 'A1', 'Economy', 150.00, FALSE),
(6, 'A2', 'Economy', 150.00, FALSE),
(6, 'B1', 'Business', 250.00, FALSE),
(6, 'B2', 'Business', 250.00, FALSE),
(6, 'C1', 'First Class', 350.00, FALSE),
(6, 'C2', 'First Class', 350.00, FALSE),

-- Flight 7 (khứ hồi)
(7, 'A1', 'Economy', 160.00, FALSE),
(7, 'A2', 'Economy', 160.00, FALSE),
(7, 'B1', 'Business', 260.00, FALSE),
(7, 'B2', 'Business', 260.00, FALSE),
(7, 'C1', 'First Class', 360.00, FALSE),
(7, 'C2', 'First Class', 360.00, FALSE),

-- Flight 8 (khứ hồi)
(8, 'A1', 'Economy', 170.00, FALSE),
(8, 'A2', 'Economy', 170.00, FALSE),
(8, 'B1', 'Business', 270.00, FALSE),
(8, 'B2', 'Business', 270.00, FALSE),
(8, 'C1', 'First Class', 370.00, FALSE),
(8, 'C2', 'First Class', 370.00, FALSE),

-- Flight 9 (1 chiều)
(9, 'A1', 'Economy', 180.00, FALSE),
(9, 'A2', 'Economy', 180.00, FALSE),
(9, 'B1', 'Business', 280.00, FALSE),
(9, 'B2', 'Business', 280.00, FALSE),
(9, 'C1', 'First Class', 380.00, FALSE),
(9, 'C2', 'First Class', 380.00, FALSE),

-- Flight 10 (1 chiều)
(10, 'A1', 'Economy', 190.00, FALSE),
(10, 'A2', 'Economy', 190.00, FALSE),
(10, 'B1', 'Business', 290.00, FALSE),
(10, 'B2', 'Business', 290.00, FALSE),
(10, 'C1', 'First Class', 390.00, FALSE),
(10, 'C2', 'First Class', 390.00, FALSE);


INSERT INTO bookings
(passenger_id, flight_id, seat_id, booking_date, status) VALUES
(1, 1, 1, '2025-06-04 09:00:00', 'Confirmed'),
(2, 2, 2, '2025-06-05 10:30:00', 'Confirmed'),
(3, 3, 3, '2025-06-06 11:00:00', 'Pending'),
(4, 4, 4, '2025-06-07 12:00:00', 'Cancelled'),
(5, 5, 5, '2025-06-08 13:00:00', 'Confirmed'),
(6, 6, 6, '2025-06-09 14:00:00', 'Confirmed'),
(7, 7, 7, '2025-06-10 15:00:00', 'Pending'),
(8, 8, 8, '2025-06-11 16:00:00', 'Confirmed'),
(9, 9, 9, '2025-06-12 17:00:00', 'Confirmed'),
(10, 10, 10, '2025-06-13 18:00:00', 'Confirmed');


INSERT INTO payments
(booking_id, amount, payment_date, payment_method, status) VALUES
(1, 250.00, '2025-06-05 10:15:00', 'Credit Card', 'PAID'),
(2, 150.50, '2025-06-10 12:30:00', 'Paypal', 'PAID'),
(3, 320.75, '2025-06-15 09:45:00', 'Credit Card', 'PENDING'),
(4, 180.00, '2025-06-20 14:20:00', 'Bank Transfer', 'PAID'),
(5, 275.25, '2025-07-01 16:00:00', 'Credit Card', 'FAILED'),
(6, 220.00, '2025-07-05 11:10:00', 'Paypal', 'PAID'),
(7, 195.50, '2025-07-12 08:40:00', 'Credit Card', 'PAID'),
(8, 300.00, '2025-07-18 17:25:00', 'Bank Transfer', 'PENDING'),
(9, 400.00, '2025-07-25 13:15:00', 'Credit Card', 'PAID'),
(10, 180.75, '2025-08-01 15:50:00', 'Paypal', 'PAID');


INSERT INTO aircrafts (aircraft_id, airline_id, model, capacity) VALUES
(1, 1, 'Airbus A320', 180),
(2, 1, 'Boeing 737', 175),
(3, 2, 'Boeing 787 Dreamliner', 240),
(4, 2, 'Airbus A350', 300),
(5, 3, 'Embraer E195', 120),
(6, 3, 'Bombardier CS300', 130),
(7, 4, 'Boeing 777', 280),
(8, 4, 'Airbus A380', 500),
(9, 1, 'Airbus A321', 190),
(10, 2, 'Boeing 737 MAX', 200);

INSERT INTO news (title, content, created_at, author) VALUES
('Thông báo hủy chuyến bay VN101', 
'Nay, chuyến bay VN101 dự kiến khởi hành ngày 10/06/2025 buộc phải hủy do sự cố kỹ thuật. Chúng tôi xin lỗi vì sự bất tiện này và sẽ hỗ trợ hành khách hoàn vé hoặc đổi chuyến.', 
'2025-06-01 09:00:00', 'admin'),

('Mẹo du lịch mùa hè an toàn và thoải mái', 
'Để có chuyến đi mùa hè suôn sẻ, quý khách nên kiểm tra dự báo thời tiết, chuẩn bị đồ dùng phù hợp và đến sân bay sớm ít nhất 2 giờ trước giờ khởi hành.', 
'2025-06-03 10:30:00', 'staff'),

('Quy định an toàn mới áp dụng từ tháng 7', 
'Từ ngày 01/07/2025, tất cả hành khách khi làm thủ tục tại sân bay sẽ phải hoàn thành tờ khai y tế nhằm đảm bảo an toàn sức khỏe cộng đồng trong mùa dịch.', 
'2025-06-05 08:45:00', 'admin'),

('Chương trình giảm giá 15% cho tất cả chuyến bay nội địa', 
'Nhằm tri ân khách hàng, hãng mở chương trình ưu đãi giảm giá 15% trên tất cả các chuyến bay nội địa khi đặt vé trước ngày 15/07/2025.', 
'2025-06-07 12:00:00', 'staff'),

('Sân bay Nội Bài nâng cấp phòng chờ hạng thương gia', 
'Phòng chờ hạng thương gia tại sân bay Nội Bài sẽ đóng cửa từ 20/06 đến 10/07 để thực hiện nâng cấp tiện nghi, nhằm mang đến trải nghiệm tốt hơn cho hành khách.', 
'2025-06-10 14:20:00', 'admin'),

('Tăng cường chuyến bay dịp Quốc Khánh', 
'Để phục vụ nhu cầu đi lại tăng cao trong dịp Quốc Khánh, hãng sẽ bổ sung thêm nhiều chuyến bay đặc biệt giữa Hà Nội, TP.HCM và các thành phố lớn.', 
'2025-06-15 16:00:00', 'staff'),

('Cập nhật chính sách hành lý mới', 
'Từ ngày 01/08/2025, chính sách hành lý sẽ được điều chỉnh với quy định cụ thể về trọng lượng và kích thước để đảm bảo an toàn và thuận tiện cho hành khách.', 
'2025-06-18 11:15:00', 'admin'),

('Nâng cao dịch vụ hỗ trợ khách hàng', 
'Chúng tôi đã mở rộng giờ phục vụ tổng đài chăm sóc khách hàng nhằm giúp hành khách giải đáp thắc mắc và xử lý vấn đề nhanh chóng hơn.', 
'2025-06-22 09:30:00', 'staff'),

('Khai trương đường bay thẳng SGN – ICN', 
'Tuyến bay thẳng từ TP.HCM đến Seoul chính thức khai trương từ ngày 01/08/2025, giúp hành khách tiết kiệm thời gian và thuận tiện hơn trong việc di chuyển quốc tế.', 
'2025-06-25 13:00:00', 'admin'),

('Khảo sát ý kiến khách hàng', 
'Chúng tôi rất mong nhận được sự tham gia của quý khách trong khảo sát ý kiến để không ngừng cải thiện chất lượng dịch vụ và đáp ứng tốt hơn nhu cầu của hành khách.', 
'2025-06-28 10:00:00', 'staff');

