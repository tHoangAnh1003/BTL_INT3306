import { useLocation, useNavigate } from "react-router-dom";
import { memo, useEffect, useState } from "react";
import "./flightResult.scss";

const FlightResultPage = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const [flights, setFlights] = useState([]);
  const [loading, setLoading] = useState(true);

  // Lấy thông tin tìm kiếm từ location.state
  const searchParams = location.state || {};

  useEffect(() => {
    const fetchFlights = async () => {
      setLoading(true);
      try {
        const params = new URLSearchParams({
          from: searchParams.from,
          to: searchParams.to,
          departDate: searchParams.departDate,
          ...(searchParams.returnDate ? { returnDate: searchParams.returnDate } : {}),
        });
        const res = await fetch(`http://localhost:8081/api/flights?${params.toString()}`);
        const data = await res.json();
        setFlights(data);
      } catch (err) {
        setFlights([]);
      }
      setLoading(false);
    };
    fetchFlights();
  }, [searchParams]);

  const handleBook = (flight) => {
    // Điều hướng sang trang đặt vé chi tiết, truyền flight nếu cần
    alert(`Đặt vé cho chuyến bay ${flight.from} - ${flight.to}`);
    // navigate("/booking-detail", { state: { flight } });
  };

  return (
    <div className="flight-result-wrapper">
      <h2>Kết quả tìm kiếm chuyến bay</h2>
      <div className="search-info">
        <span>Điểm đi: <b>{searchParams.from}</b></span>
        <span>Điểm đến: <b>{searchParams.to}</b></span>
        <span>Ngày đi: <b>{searchParams.departDate}</b></span>
        {searchParams.returnDate && (
          <span>Ngày về: <b>{searchParams.returnDate}</b></span>
        )}
      </div>
      {loading ? (
        <div>Đang tải dữ liệu...</div>
      ) : (
        <table className="flight-table">
          <thead>
            <tr>
              <th>Điểm đi</th>
              <th>Điểm đến</th>
              <th>Ngày đi</th>
              <th>Ngày về</th>
              <th>Giá tiền</th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            {flights.length === 0 && (
              <tr>
                <td colSpan={6} style={{ textAlign: "center" }}>Không tìm thấy chuyến bay phù hợp</td>
              </tr>
            )}
            {flights.map(f => (
              <tr key={f.id}>
                <td>{f.from}</td>
                <td>{f.to}</td>
                <td>{f.departDate}</td>
                <td>{f.returnDate || "-"}</td>
                <td>{f.price?.toLocaleString()} VNĐ</td>
                <td>
                  <button onClick={() => handleBook(f)}>Đặt vé</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
};

export default memo(FlightResultPage);