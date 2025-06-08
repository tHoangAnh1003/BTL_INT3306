import { useEffect, useState } from "react";
import "./bookingStatsPage.scss";

const BookingStatsPage = () => {
  const [stats, setStats] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    const fetchStats = async () => {
      setLoading(true);
      setError("");
      try {
        const token = localStorage.getItem("accessToken");
        const res = await fetch("http://localhost:8081/api/bookings/statistics", {
          headers: {
            "Authorization": `Bearer ${token}`
          },
        });
        if (!res.ok) throw new Error("Lỗi khi lấy dữ liệu thống kê");
        const data = await res.json();
        setStats(data);
      } catch (err) {
        setError(err.message || "Lỗi không xác định");
      }
      setLoading(false);
    };
    fetchStats();
  }, []);

  return (
    <div className="booking-stats-page">
      <h2>Thống kê đặt vé khách hàng</h2>
      {loading && <div>Đang tải dữ liệu...</div>}
      {error && <div className="error">{error}</div>}
      {!loading && !error && (
        <div className="booking-table-wrapper">
          <table className="booking-table">
            <thead>
              <tr>
                <th>Model tàu bay</th>
                <th>Tuyến bay</th>
                <th>Giờ khởi hành</th>
                <th>Tên khách hàng</th>
                <th>Trạng thái vé</th>
              </tr>
            </thead>
            <tbody>
              {stats.length === 0 ? (
                <tr>
                  <td colSpan={4} style={{ textAlign: "center" }}>Không có dữ liệu</td>
                </tr>
              ) : (
                stats.map((b, idx) => (
                  <tr key={idx}>
                    <td>{b.aircraftModel}</td>
                    <td>{b.route}</td>
                    <td>{b.departureTime}</td>
                    <td>{b.passengerName}</td>
                    <td>{b.status}</td>
                  </tr>
                ))
              )}
            </tbody>
          </table>
        </div>
      )}
    </div>
  );
};

export default BookingStatsPage;