import { memo, useEffect, useState } from "react";
import "./flightInfo.scss";

const FlightInfoPage = () => {
  const [flights, setFlights] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const el = document.getElementById("flight-info-section");
    el && el.scrollIntoView({ behavior: "smooth", block: "start" });

    // Lấy dữ liệu chuyến bay từ backend
    const fetchFlights = async () => {
      setLoading(true);
      try {
        const res = await fetch("http://localhost:8081/api/flights");
        const data = await res.json();
        setFlights(data);
      } catch (err) {
        setFlights([]);
      }
      setLoading(false);
    };
    fetchFlights();
  }, []);

  return (
    <div id="flight-info-section">
      <h2>Thông tin các chuyến bay</h2>
      <table className="flight-table">
        <thead>
          <tr>
            <th>Số hiệu chuyến bay</th>
            <th>Điểm đi</th>
            <th>Điểm đến</th>
            <th>Thời gian khởi hành</th>
            <th>Thời gian đến</th>
            <th>Trạng thái</th>
          </tr>
        </thead>
        <tbody>
          {loading ? (
            <tr>
              <td colSpan={6} style={{ textAlign: "center" }}>
                Đang tải dữ liệu...
              </td>
            </tr>
          ) : flights.length === 0 ? (
            <tr>
              <td colSpan={6} style={{ textAlign: "center" }}>
                Không có chuyến bay nào
              </td>
            </tr>
          ) : (
            flights.map((f) => (
              <tr key={f.id || f.flightNumber}>
                <td>{f.flightNumber || f.id}</td>
                <td>{f.departure}</td>
                <td>{f.arrival}</td>
                <td>{f.departureTime}</td>
                <td>{f.arrivalTime}</td>
                <td>{f.status}</td>
              </tr>
            ))
          )}
        </tbody>
      </table>
    </div>
  );
};

export default memo(FlightInfoPage);