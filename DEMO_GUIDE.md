# HƯỚNG DẪN DEMO CIRCUIT BREAKER (RESILIENCE4J)

Tài liệu này hướng dẫn cách chạy và demo cơ chế Circuit Breaker + Fallback đã được tích hợp vào `order-service` khi gọi sang `auth-service`.

---

## PHẦN D: HƯỚNG DẪN CHẠY

1. **Khởi động:** Do đã thêm dependency cho `order-service` vào `pom.xml`, bạn cần đóng cả 2 service lại và MAVEN reload/rebuild project (Nếu dùng Docker thì chạy `docker-compose down` sau đó `docker-compose up --build -d`).
2. **Kiểm tra service Start được:** Chắc chắn cả 2 microservices `auth-service` và `order-service` (mặc định trên cổng `8081` internal) đã chạy thành công. Ở ngoài máy host, port của order-service có thể là `8081` và auth-service là `8082` tùy theo `docker-compose.yml` của bạn.
3. **Thứ tự test:**
   - Lấy `token` JWT qua `/api/auth/login`.
   - Init 1 order `/api/orders/init`.
   - Tiến hành demo qua Postman/curl theo script bên dưới.

---

## PHẦN E: CURL/POSTMAN SCRIPT DEMO 10 PHÚT

Trong các script dưới đây, thay `{order_port}` và `{auth_port}` bằng port thực tế bạn đang mở ra host (Ví dụ: order là 8081, auth là 8082).

**Bước 1: Lấy Token**
Thực hiện login như bình thường của project để lấy biến `$TOKEN`.

**Bước 2: Init Order**
```bash
curl -X POST http://localhost:{order_port}/api/orders/init \
     -H "Authorization: Bearer $TOKEN"
```

**Bước 3: Demo Mode NORMAL (Hoạt động bình thường)**
```bash
# Set về NORMAL cho chắc chắn
curl -X POST "http://localhost:{auth_port}/api/demo/mode?value=NORMAL"

# Gọi checkout
curl -X POST http://localhost:{order_port}/api/orders/checkout \
     -H "Authorization: Bearer $TOKEN"

# Kết quả mong đợi: Response trả về nhanh chóng, có thông tin user thật.
# {"fallbackUsed": false, "downstreamStatus": "REAL", ...}
```

**Bước 4: Demo Mode SLOW (Mô phỏng đứt cáp/chậm, test Timeout + Fallback)**
```bash
# Đổi auth-service sang mode SLOW (sẽ tự động sleep 5s mỗi request)
curl -X POST "http://localhost:{auth_port}/api/demo/mode?value=SLOW"

# Gọi lại checkout, order-service sẽ chờ theo cấu hình RestTemplate (timeout ~2s) rồi ngắt
curl -X POST http://localhost:{order_port}/api/orders/checkout \
     -H "Authorization: Bearer $TOKEN"

# Kết quả mong đợi: Trả response CỰC NHANH (sau 2s chờ của order-service) với thông tin Fallback.
# {"fallbackUsed": true, "downstreamStatus": "FALLBACK_TIMEOUT", ...}
```

**Bước 5: Demo Mode FAIL (Mô phỏng Lỗi 500/Exception -> kích hoạt Circuit Breaker OPEN)**
```bash
# Đổi auth-service sang mode FAIL (sẽ ném RuntimeException ngay lập tức)
curl -X POST "http://localhost:{auth_port}/api/demo/mode?value=FAIL"

# Gọi checkout LẦN 1. Auth lỗi, order-service bắt lỗi.
curl -X POST http://localhost:{order_port}/api/orders/checkout \
     -H "Authorization: Bearer $TOKEN"
# Kết quả: "downstreamStatus": "FALLBACK_FAILURE"

# Gọi checkout LẦN 2. Auth tiếp tục lỗi.
curl -X POST http://localhost:{order_port}/api/orders/checkout \
     -H "Authorization: Bearer $TOKEN"
# Kết quả: "downstreamStatus": "FALLBACK_FAILURE" 
# TRẠNG THÁI HIỆN TẠI: Số lần lỗi = 2. Đã đạt ngưỡng (slidingWindowSize=4, min calls=2, failure rate 50%).
# -> CIRCUIT BREAKER ĐÃ "OPEN" (Ngắt cầu dao).

# Gọi checkout LẦN 3. BẠN SẼ THẤY ĐIỀU KHÁC BIỆT!
curl -X POST http://localhost:{order_port}/api/orders/checkout \
     -H "Authorization: Bearer $TOKEN"
# Kết quả: "downstreamStatus": "FALLBACK_CIRCUIT_OPEN". 
# Ý nghĩa: Order-service KHÔNG HỀ GỌI XUỐNG auth-service nữa mà lập tức rẽ ngang vào hàm Fallback vì biết chắc chắn auth đang lỗi.
```

**Bước 6: Xem dashboard trạng thái Circuit Breaker**
```bash
# Truy cập actuator của order-service để chứng minh với người xem là CB đang can thiệp
curl http://localhost:{order_port}/actuator/circuitbreakerevents
```

**Bước 7: Demo Recovery (Mô phỏng khắc phục sự cố xong)**
```bash
# Sửa xong lỗi, auth-service bình thường trở lại
curl -X POST "http://localhost:{auth_port}/api/demo/mode?value=NORMAL"

# Đợi khoảng 10 giây (theo config waitDurationInOpenState: 10s).
# Trạng thái sẽ chuyển từ OPEN sang HALF_OPEN.

# Gọi checkout. Request đầu tiên qua Half-Open thành công lấy được data.
curl -X POST http://localhost:{order_port}/api/orders/checkout \
     -H "Authorization: Bearer $TOKEN"

# Circuit Breaker xác nhận auth đã khỏe rẽ, tự động đóng cầu dao (CLOSED).
# Kết quả mong đợi: Dữ liệu thật quay trở lại, không còn fallback.
# {"fallbackUsed": false, "downstreamStatus": "REAL", ...}
```

---

## PHẦN F: LỜI BÌNH ĐỂ TRÌNH BÀY (Cực ngắn & dễ hiểu)

* **Timeout là gì?** \
  "Thuật ngữ chỉ thời gian chờ tối đa. Quá x giây không có phản hồi, ta ngừng chờ ngay lập tức để không làm treo hệ thống."
* **Retry là gì?** \
  "Cơ chế thử lại ngay. Rất hữu ích với các lỗi mạng chập chờn tạm thời trong thời gian cực ngắn."
* **Circuit Breaker là gì?** \
  "Giống hệt cái Cầu Dao điện nhà bạn. Khi service con (auth) chết, việc order gọi cố không để làm gì cả, chỉ gây nghẽn toàn hệ thống. Cầu Dao sẽ NGẮT (OPEN), chặn đứng mọi luồng gọi tới service đó. Khi service kia khỏe lại, nó sẽ tự động tính toán để ĐÓNG (CLOSED) cầu dao cho dữ liệu lưu thông bình thường."
* **Fallback là gì?** \
  "Phương án dự phòng (Plan B). Khi xảy ra Timeout hoặc Cầu Dao ngắt, hệ thống không báo lỗi trắng màn hình mà rẽ sang trả về 'dữ liệu mặc định' – giúp user vẫn tiếp tục flow công việc mà không bị block."
* **Tại sao xài Circuit Breaker + Fallback ở đây?** \
  "Ở bài Cửa Hàng, khi module Thanh Toán/Giỏ Hàng gọi sang module Auth để lấy profile nhưng bị đơ, ta ngắt kết nối (Circuit Breaker) rồi điền dữ liệu giả như 'User_Demo' (Fallback), cốt để Đơn đặt hàng vẫn được chốt, giữ chân khách mua thay vì quăng lỗi 500 vào mặt họ."
