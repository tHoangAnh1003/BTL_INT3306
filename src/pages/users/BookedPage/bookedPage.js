import { useEffect, useState } from "react";
import "./bookedPage.scss";
const BookedPage = () => {
  const [bookings, setBookings] = useState([]);
  const [loading, setLoading] = useState(true);
  const userId = localStorage.getItem("userId"); // hoặc username/email

  useEffect(() => {
    const fetchBookings = async () => {
      setLoading(true);
      try {
        const res = await fetch(`http://localhost:8081/api/bookings?userId=${userId}`);
        const data = await res.json();
        setBookings(data);
      } catch (err) {
        setBookings([]);
      }
      setLoading(false);
    };
    fetchBookings();
  }, [userId]);

  const handleCancel = async (bookingId) => {
    if (!window.confirm("Bạn chắc chắn muốn hủy vé này?")) return;
    try {
      const res = await fetch(`http://localhost:8081/api/bookings/${bookingId}/cancel`, {
        method: "POST",
      });
      if (res.ok) {
        setBookings(bookings.map(b =>
          b.id === bookingId ? { ...b, status: "Đã hủy" } : b
        ));
      } else {
        alert("Không thể hủy vé. Vui lòng thử lại.");
      }
    } catch {
      alert("Có lỗi xảy ra khi hủy vé.");
    }
  };

  return (
    <div className="booking-manage-wrapper">
      <h2>Quản lý đặt chỗ của bạn</h2>
      {loading ? (
        <div>Đang tải dữ liệu...</div>
      ) : (
        <table className="booking-table">
          <thead>
            <tr>
              <th>ID Khách hàng</th>
              <th>ID Ghế ngồi</th>
              <th>Ngày đặt vé</th>
              <th>Trạng thái</th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            {bookings.length === 0 && (
              <tr>
                <td colSpan={5} style={{ textAlign: "center" }}>Không có vé nào</td>
              </tr>
            )}
            {bookings.map(b => (
              <tr key={b.id}>
                <td>{b.userId}</td>
                <td>{b.seatId}</td>
                <td>{b.bookingDate}</td>
                <td>{b.status}</td>
                <td>
                  {b.status === "Đã đặt" && b.canCancel ? (
                    <button onClick={() => handleCancel(b.id)}>Hủy vé</button>
                  ) : (
                    <span style={{ color: "#888" }}>Không thể hủy</span>
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