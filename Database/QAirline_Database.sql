create database qairline;

use qairline;

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

CREATE TABLE passengers (
    passenger_id INT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    phone VARCHAR(20) NOT NULL,
    passport_number VARCHAR(50) UNIQUE NOT NULL
) ENGINE=InnoDB;

CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) UNIQUE NOT NULL,
    password_hash TEXT NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    role ENUM('Admin', 'Customer', 'Staff') NOT NULL
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

-- Kiểm tra các bảng
SHOW TABLES;

INSERT INTO airports (name, code, city, country) VALUES
('Noi Bai International Airport', 'HAN', 'Hanoi', 'Vietnam'),
('Tan Son Nhat International Airport', 'SGN', 'Ho Chi Minh City', 'Vietnam'),
('Da Nang International Airport', 'DAD', 'Da Nang', 'Vietnam'),
('Ninoy Aquino International Airport', 'MNL', 'Manila', 'Philippines'),
('Changi Airport', 'SIN', 'Singapore', 'Singapore'),
('Suvarnabhumi Airport', 'BKK', 'Bangkok', 'Thailand'),
('Kuala Lumpur International Airport', 'KUL', 'Kuala Lumpur', 'Malaysia'),
('Los Angeles International Airport', 'LAX', 'Los Angeles', 'USA'),
('Heathrow Airport', 'LHR', 'London', 'UK'),
('Narita International Airport', 'NRT', 'Tokyo', 'Japan');

INSERT INTO airlines (name, code) VALUES
('Vietnam Airlines', 'VN'),
('Bamboo Airways', 'QH'),
('VietJet Air', 'VJ'),
('Philippine Airlines', 'PR'),
('Singapore Airlines', 'SQ'),
('Thai Airways', 'TG'),
('Malaysia Airlines', 'MH'),
('American Airlines', 'AA'),
('British Airways', 'BA'),
('Japan Airlines', 'JL');

INSERT INTO passengers (full_name, email, phone, passport_number) VALUES
('Nguyen Van A', 'nguyenvana@example.com', '0987654321', 'VN123456'),
('Tran Thi B', 'tranthib@example.com', '0912345678', 'VN654321'),
('Le Van C', 'levanc@example.com', '0981122334', 'VN112233'),
('Hoang Minh D', 'hoangminhd@example.com', '0977889900', 'VN445566'),
('Nguyen Thi E', 'nguyenthie@example.com', '0905123456', 'VN778899'),
('Pham Van F', 'phamvanf@example.com', '0999888777', 'VN998877'),
('Bui Thi G', 'buithig@example.com', '0966332211', 'VN665544'),
('Doan Minh H', 'doanminhh@example.com', '0922334455', 'VN223344'),
('Truong Van I', 'truongvani@example.com', '0944556677', 'VN556677'),
('Le Thi J', 'lethij@example.com', '0955667788', 'VN667788');

INSERT INTO users (username, password_hash, email, role) VALUES
('admin', 'hashed_password_1', 'admin@example.com', 'Admin'),
('staff1', 'hashed_password_2', 'staff1@example.com', 'Staff'),
('staff2', 'hashed_password_3', 'staff2@example.com', 'Staff'),
('user1', 'hashed_password_4', 'user1@example.com', 'Customer'),
('user2', 'hashed_password_5', 'user2@example.com', 'Customer'),
('user3', 'hashed_password_6', 'user3@example.com', 'Customer'),
('user4', 'hashed_password_7', 'user4@example.com', 'Customer'),
('user5', 'hashed_password_8', 'user5@example.com', 'Customer'),
('user6', 'hashed_password_9', 'user6@example.com', 'Customer'),
('user7', 'hashed_password_10', 'user7@example.com', 'Customer');

INSERT INTO flights (airline_id, flight_number, departure_airport, arrival_airport, departure_time, arrival_time, status) VALUES
(1, 'VN101', 1, 2, '2025-04-01 10:00:00', '2025-04-01 12:00:00', 'Scheduled'),
(2, 'QH202', 3, 1, '2025-04-02 14:00:00', '2025-04-02 16:00:00', 'Scheduled'),
(3, 'VJ303', 2, 3, '2025-04-03 08:00:00', '2025-04-03 10:00:00', 'Delayed'),
(4, 'PR404', 4, 5, '2025-04-04 20:00:00', '2025-04-04 22:00:00', 'Scheduled'),
(5, 'SQ505', 5, 6, '2025-04-05 09:00:00', '2025-04-05 11:00:00', 'Cancelled'),
(6, 'TG606', 6, 7, '2025-04-06 15:00:00', '2025-04-06 17:00:00', 'Scheduled'),
(7, 'MH707', 7, 8, '2025-04-07 18:00:00', '2025-04-07 20:00:00', 'Scheduled'),
(8, 'AA808', 8, 9, '2025-04-08 06:00:00', '2025-04-08 10:00:00', 'Scheduled'),
(9, 'BA909', 9, 10, '2025-04-09 07:00:00', '2025-04-09 12:00:00', 'Departed'),
(10, 'JL1010', 10, 1, '2025-04-10 11:00:00', '2025-04-10 18:00:00', 'Arrived');


INSERT INTO payments (booking_id, amount, payment_method, status) VALUES
(1, 500.00, 'Credit Card', 'Paid'),
(2, 600.00, 'PayPal', 'Paid'),
(3, 300.00, 'Bank Transfer', 'Pending'),
(4, 450.00, 'Credit Card', 'Failed'),
(5, 550.00, 'PayPal', 'Paid'),
(6, 700.00, 'Credit Card', 'Paid'),
(7, 400.00, 'Bank Transfer', 'Pending'),
(8, 750.00, 'Credit Card', 'Paid'),
(9, 850.00, 'PayPal', 'Paid'),
(10, 900.00, 'Bank Transfer', 'Failed');

INSERT INTO flight_seats (flight_id, seat_number, class, price, is_booked) VALUES
(1, '12A', 'Economy', 500.00, FALSE),
(1, '12B', 'Economy', 500.00, FALSE),
(2, '14A', 'Business', 800.00, FALSE),
(2, '14B', 'Business', 800.00, FALSE),
(3, '15A', 'Economy', 450.00, FALSE),
(3, '15B', 'Economy', 450.00, FALSE),
(4, '16A', 'Economy', 400.00, FALSE),
(4, '16B', 'Economy', 400.00, FALSE),
(5, '17A', 'Business', 850.00, FALSE),
(5, '17B', 'Business', 850.00, FALSE);

INSERT INTO aircrafts (airline_id, model, capacity) VALUES
(1, 'Airbus A320', 180),
(1, 'Boeing 787-9', 294),
(2, 'Boeing 737 MAX 8', 189),
(2, 'Airbus A321neo', 230),
(3, 'Airbus A320neo', 188),
(3, 'Boeing 737-800', 186),
(4, 'Airbus A350-900', 325),
(5, 'Boeing 777-300ER', 396),
(6, 'Airbus A380-800', 555),
(7, 'Boeing 747-8', 410);

INSERT INTO payments (booking_id, amount, payment_date, payment_method, status) VALUES
(1, 500.00, '2025-03-20 16:00:00', 'Credit Card', 'Paid'),
(2, 500.00, '2025-03-21 16:30:00', 'PayPal', 'Paid'),
(3, 800.00, '2025-03-22 17:30:00', 'Bank Transfer', 'Pending'),
(4, 800.00, '2025-03-23 18:30:00', 'Credit Card', 'Failed'),
(5, 850.00, '2025-03-24 19:00:00', 'PayPal', 'Paid'),
(6, 700.00, '2025-03-25 20:00:00', 'Credit Card', 'Paid'),
(7, 600.00, '2025-03-26 21:00:00', 'Bank Transfer', 'Pending'),
(8, 900.00, '2025-03-27 22:00:00', 'Credit Card', 'Paid'),
(9, 750.00, '2025-03-28 23:00:00', 'PayPal', 'Failed'),
(10, 650.00, '2025-03-29 12:00:00', 'Bank Transfer', 'Paid');


INSERT INTO bookings (passenger_id, flight_id, seat_id, status) VALUES
(1, 1, 1, 'Confirmed'),
(2, 1, 2, 'Confirmed'),
(3, 2, 3, 'Pending'),
(4, 2, 4, 'Cancelled'),
(5, 3, 5, 'Confirmed'),
(6, 3, 6, 'Confirmed'),
(7, 4, 7, 'Pending'),
(8, 4, 8, 'Confirmed'),
(9, 5, 9, 'Confirmed'),
(10, 5, 10, 'Cancelled');


