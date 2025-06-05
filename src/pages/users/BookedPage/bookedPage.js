import { useEffect, useState } from "react";
import "./bookedPage.scss";

const formatDate = (dateStr) => {
  if (!dateStr) return "";
  const d = new Date(dateStr);
  if (isNaN(d)) return dateStr;
  return d.toLocaleString("vi-VN", { hour12: false });
};

const statusColor = (status) => {
  if (!status) return "";
  const s = status.toLowerCase();
  if (s === "confirmed") return "#28a745";
  if (s === "cancelled") return "#dc3545";
  if (s === "pending") return "#ff9800";
  return "#333";
};

const BookedPage = () => {
  const [bookings, setBookings] = useState([]);
  const [loading, setLoading] = useState(true);
  const [msg, setMsg] = useState("");

  useEffect(() => {
    const el = document.getElementById("booked-page");
    el && el.scrollIntoView({ behavior: "smooth", block: "start" });
  }, []);

  useEffect(() => {
    fetchBookings();
    // eslint-disable-next-line
  }, []);

  const fetchBookings = async () => {
    setLoading(true);
    setMsg("");
    try {
      const token = localStorage.getItem("accessToken");
      const res = await fetch("http://localhost:8081/api/bookings/my", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      if (!res.ok) throw new Error("Không thể lấy dữ liệu đặt vé");
      const data = await res.json();
      // Chỉ hiển thị các vé đã đặt (status = Confirmed hoặc Pending)
      const filtered = Array.isArray(data)
        ? data.filter(
            (b) =>
              b.status &&
              (b.status.toLowerCase() === "confirmed" ||
                b.status.toLowerCase() === "pending")
          )
        : [];
      setBookings(filtered);
    } catch (err) {
      setBookings([]);
      setMsg("Không thể lấy dữ liệu đặt vé");
    }
    setLoading(false);
  };

  // Kiểm tra còn hạn hủy: chưa bị hủy và chưa khởi hành
  const canCancel = (booking) => {
    if (!booking.status || booking.status.toLowerCase() === "cancelled") return false;
    if (!booking.departureTime) return false;
    const now = new Date();
    const dep = new Date(booking.departureTime);
    return now < dep;
  };

  // Hàm hủy vé
  const handleCancel = async (booking) => {
    setMsg("");
    try {
      const token = localStorage.getItem("accessToken");
      const bookingId = booking.bookingId || booking.id || booking.booking_id;
      const res = await fetch(
        `http://localhost:8081/api/bookings/${bookingId}/cancel`,
        {
          method: "DELETE",
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      if (!res.ok) {
        const data = await res.json();
        setMsg(data.message || "Hủy vé thất bại");
        return;
      }
      setMsg("Hủy vé thành công!");
      fetchBookings();
    } catch {
      setMsg("Lỗi kết nối máy chủ");
    }
  };

  return (
    <div id="booked-page" className="booked-page-wrapper">
      <h2>Lịch sử đặt vé của bạn</h2>
      {msg && <div className="booking-msg">{msg}</div>}
      {loading ? (
        <div>Đang tải dữ liệu...</div>
      ) : bookings.length === 0 ? (
        <div style={{ textAlign: "center", margin: "2rem 0" }}>
          Bạn chưa có vé đã đặt nào.
        </div>
      ) : (
        <table className="booked-table">
          <thead>
            <tr>
              <th>Số hiệu chuyến bay</th>
              <th>Số ghế</th>
              <th>Thời gian đặt vé</th>
              <th>Tuyến bay</th>
              <th>Giờ khởi hành</th>
              <th>Giờ đến</th>
              <th>Trạng thái</th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            {bookings.map((b, idx) => (
              <tr key={idx}>
                <td>{b.aircraftModel || b.flightNumber}</td>
                <td>{b.seatId || b.seatNumber}</td>
                <td>{formatDate(b.time || b.bookingDate)}</td>
                <td>{b.route}</td>
                <td>{formatDate(b.departureTime)}</td>
                <td>{formatDate(b.arrivalTime)}</td>
                <td>
                  <span style={{ color: statusColor(b.status), fontWeight: 600 }}>
                    {b.status || "Confirmed"}
                  </span>
                </td>
                <td>
                  {canCancel(b) && (
                    <button
                      className="cancel-btn"
                      onClick={() => handleCancel(b)}
                    >
                      Hủy vé
                    </button>
                  )}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
};

export default BookedPage;