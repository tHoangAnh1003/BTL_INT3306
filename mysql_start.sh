#!/bin/bash

# Thông tin kết nối (giống như trong cấu hình Spring Boot)
HOST="172.24.208.1"
PORT="3306"
USER="root"
# PASSWORD="HoangAnh1003"
DATABASE="qairline"

# Kết nối MySQL
mysql -h $HOST -P $PORT -u $USER -p
