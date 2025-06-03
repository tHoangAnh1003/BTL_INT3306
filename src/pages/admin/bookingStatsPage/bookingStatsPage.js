import { useEffect, useState } from "react";
import "./bookingStatsPage.scss";

const BookingStatsPage = () => {
  const [bookings, setBookings] = useState([]);
  const [stats, setStats] = useState({
    total: 0,
    byFlight: {},
    byDate: {},
  });
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    const fetchBookings = async () => {
      setLoading(true);
      setError("");
      try {
        const res = await fetch("http://localhost:8081/api/admin/bookings");
        if (!res.ok) throw new Error("Lỗi khi lấy dữ liệu đặt vé");
        const data = await res.json();
        setBookings(data);

        // Thống kê tổng số, theo chuyến bay, theo ngày
        const byFlight = {};
        const byDate = {};
        data.forEach(b => {
          byFlight[b.flightNumber] = (byFlight[b.flightNumber] || 0) + 1;
          const date = b.bookingDate?.slice(0, 10) || "Không rõ";
          byDate[date] = (byDate[date] || 0) + 1;
        });
        setStats({
          total: data.length,
          byFlight,
          byDate,
        });
      } catch (err) {
        setError(err.message || "Lỗi không xác định");
      }
      setLoading(false);
    };
    fetchBookings();
  }, []);

  return (
    <div className="booking-stats-page">
      <h2>Thống kê đặt vé khách hàng</h2>
      {loading && <div>Đang tải dữ liệu...</div>}
      {error && <div className="error">{error}</div>}
      {!loading && !error && (
        <>
          <div className="stats-summary">
            <div>Tổng số lượt đặt: <b>{stats.total}</b></div>
            <div>
              <b>Thống kê theo chuyến bay:</b>
              <ul>
                {Object.entries(stats.byFlight).map(([flight, count]) => (
                  <li key={flight}>
                    {flight}: {count} lượt đặt
                  </li>
                ))}
              </ul>
            </div>
            <div>
              <b>Thống kê theo ngày đặt:</b>
              <ul>
                {Object.entries(stats.byDate).map(([date, count]) => (
                  <li key={date}>
                    {date}: {count} lượt đặt
                  </li>
                ))}
              </ul>
            </div>
          </div>
          <h3>Danh sách đặt vé</h3>
          <div className="booking-table-wrapper">
            <table className="booking-table">
              <thead>
                <tr>
                  <th>Mã đặt vé</th>
                  <th>Khách hàng</th>
                  <th>Chuyến bay</th>
                  <th>Ghế</th>
                  <th>Ngày đặt</th>
                  <th>Trạng thái</th>
                </tr>
              </thead>
              <tbody>
                {bookings.map(b => (
                  <tr key={b.id}>
                    <td>{b.code || b.id}</td>
                    <td>{b.customerName || b.customerEmail}</td>
                    <td>{b.flightNumber}</td>
                    <td>{b.seatNumber}</td>
                    <td>{b.bookingDate?.slice(0, 19).replace("T", " ")}</td>
                    <td>{b.status}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </>
      )}
    </div>
  );
};

export default BookingStatsPage;