import { useEffect, useState } from "react";
import "./bookedPage.scss";

const BookedPage = () => {
  const [bookings, setBookings] = useState([]);
  const [loading, setLoading] = useState(true);
  const [msg, setMsg] = useState("");

  useEffect(() => {
    fetchBookings();
    // eslint-disable-next-line
  }, []);

  useEffect(() => {
    const el = document.getElementById("booked-page");
    el && el.scrollIntoView({ behavior: "smooth", block: "start" });
  }, []);

  // Màu sắc cho status
  const statusColor = (status) => {
    if (!status) return "";
    const s = status.toLowerCase();
    if (s === "confirmed") return "#28a745";
    if (s === "cancelled") return "#dc3545";
    if (s === "pending") return "#ff9800";
    return "#333";
  };

  const fetchBookings = async () => {
    setLoading(true);
    try {
      const token = localStorage.getItem("accessToken");
      const res = await fetch("http://localhost:8081/api/bookings/my", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      const data = await res.json();
      setBookings(data);
    } catch (err) {
      setBookings([]);
    }
    setLoading(false);
  };

  // Kiểm tra còn hạn hủy: chưa bị hủy và chưa khởi hành
  const canCancel = (booking) => {
    if (booking.status && booking.status.toLowerCase() === "cancelled") return false;
    if (!booking.departureTime) return false;
    const now = new Date();
    // Chuyển departureTime về dạng Date, ví dụ "02 Apr 2025, 21:00"
    const dep = new Date(
      booking.departureTime.replace(/(\d{2}) (\w{3}) (\d{4}), (\d{2}):(\d{2})/, (m, d, mon, y, h, min) => {
        const months = { Jan:0, Feb:1, Mar:2, Apr:3, May:4, Jun:5, Jul:6, Aug:7, Sep:8, Oct:9, Nov:10, Dec:11 };
        return `${y}-${String(months[mon]+1).padStart(2,"0")}-${d}T${h}:${min}:00`;
      })
    );
    return now < dep;
  };

  // Hàm hủy vé
  const handleCancel = async (booking, idx) => {
    setMsg("");
    try {
      const token = localStorage.getItem("accessToken");
      const bookingId = booking.bookingId || booking.id || booking.booking_id;
      const res = await fetch(
        `http://localhost:8081/api/bookings/cancel/${bookingId}`,
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
        setTimeout(() => {
          setMsg("");
        }, 1500);
        return;
      }
      setMsg("Hủy vé thành công!");
      // setSuccessMsg("Vui lòng đăng nhập để đặt vé!");
      setTimeout(() => {
        setMsg("");
      }, 1500);
      fetchBookings();
    } catch {
      setMsg("Lỗi kết nối máy chủ");
      setTimeout(() => {
        setMsg("");
      }, 1500);
    }
  };

  return (
    <div id="booked-page">
      <h2>Lịch sử đặt vé của bạn</h2>
      {msg && <div className="booking-msg">{msg}</div>}
      {loading ? (
        <div>Đang tải dữ liệu...</div>
      ) : (
        <table className="booked-table">
          <thead>
            <tr>
              <th>Số hiệu chuyến bay</th>
              <th>Số ghế</th>
              {/* <th>Thời gian đặt vé</th> */}
              <th>Ngày đặt</th>
              <th>Tuyến bay</th>
              <th>Giờ khởi hành</th>
              <th>Giờ đến</th>
              <th>Trạng thái</th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            {bookings.length === 0 ? (
              <tr>
                <td colSpan={9} style={{ textAlign: "center" }}>Bạn chưa đặt vé nào</td>
              </tr>
            ) : (
              bookings.map((b, idx) => (
                <tr key={idx}>
                  <td>{b.aircraftModel || b.flightNumber}</td>
                  <td>{b.seatId || b.seatNumber}</td>
                  {/* <td>{b.time || b.bookingDate}</td> */}
                  <td>{b.bookingDate}</td>
                  <td>{b.route}</td>
                  <td>{b.departureTime}</td>
                  <td>{b.arrivalTime}</td>
                  {/* <td>{b.status}</td> */}
                  <td>
                  <span style={{ color: statusColor(b.status), fontWeight: 600 }}>
                    {b.status || "Confirmed"}
                  </span>
                </td>
                  <td>
                    {canCancel(b) && (
                      <button
                        className="cancel-btn"
                        onClick={() => handleCancel(b, idx)}
                      >
                        Hủy vé
                      </button>
                    )}
                  </td>
                </tr>
              ))
            )}
          </tbody>
        </table>
      )}
    </div>
  );
};

export default BookedPage;