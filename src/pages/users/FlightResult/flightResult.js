import { useLocation, useNavigate } from "react-router-dom";
import { memo, useEffect, useState } from "react";
import "./flightResult.scss";

const FlightResultPage = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const { params, tripType, from, to, departDate, returnDate } = location.state || {};
  const [flightsGo, setFlightsGo] = useState([]);
  const [flightsReturn, setFlightsReturn] = useState([]);
  const [loading, setLoading] = useState(true);
  const [successMsg, setSuccessMsg] = useState("");

  useEffect(() => {
    const fetchFlights = async () => {
      setLoading(true);
      try {
        if (tripType === "oneway" || (tripType === "round" && departDate && !returnDate)) {
          const res = await fetch(`http://localhost:8081/api/flights/search?departure=${from}&arrival=${to}&departureDate=${departDate}`);
          const data = await res.json();
          setFlightsGo(data);
          setFlightsReturn([]);
        } else if (tripType === "round" && !departDate && returnDate) {
          const res = await fetch(`http://localhost:8081/api/flights/search?departure=${to}&arrival=${from}&departureDate=${returnDate}`);
          const data = await res.json();
          setFlightsGo([]);
          setFlightsReturn(data);
        } else if (tripType === "round" && departDate && returnDate) {
          const resGo = await fetch(`http://localhost:8081/api/flights/search?departure=${from}&arrival=${to}&departureDate=${departDate}`);
          const dataGo = await resGo.json();
          setFlightsGo(dataGo);
          const resReturn = await fetch(`http://localhost:8081/api/flights/search?departure=${to}&arrival=${from}&departureDate=${returnDate}`);
          const dataReturn = await resReturn.json();
          setFlightsReturn(dataReturn);
        }
      } catch (err) {
        setFlightsGo([]);
        setFlightsReturn([]);
      }
      setLoading(false);
    };
    fetchFlights();
  }, [params, tripType, from, to, departDate, returnDate]);

  // Hàm đặt vé
  const handleBook = async (flight) => {
    setSuccessMsg("");
    const token = localStorage.getItem("accessToken");
    if (!token) {
      setSuccessMsg("Vui lòng đăng nhập để đặt vé!");
      setTimeout(() => {
        setSuccessMsg("");
        navigate("/login");
      }, 1500);
      return;
    }
    // Lấy số hiệu chuyến bay (aircraftModel)
    const aircraftModel = flight.flightNumber || flight.aircraftModel || flight.model || "Unknown";
    // Lấy seatId (ở đây demo lấy 1, thực tế nên lấy từ dữ liệu ghế)
    const seatId = 1;
    // Lưu thời gian đặt vé hiện tại
    const now = new Date();
    const time = now.toISOString().slice(0, 16); // "YYYY-MM-DDTHH:mm"
    try {
      const res = await fetch("http://localhost:8081/api/bookings", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify({
          aircraftModel,
          seatId,
          status: "CONFIRMED",
          time,
        }),
      });
      if (!res.ok) {
        const data = await res.json();
        setSuccessMsg(data.message || "Đặt vé thất bại!");
        setTimeout(() => setSuccessMsg(""), 3000);
        return;
      }
      setSuccessMsg("Đặt vé thành công");
      setTimeout(() => setSuccessMsg(""), 3000);
    } catch {
      setSuccessMsg("Lỗi kết nối máy chủ");
      setTimeout(() => setSuccessMsg(""), 3000);
    }
  };

  // Bảng chuyến bay (dùng cho cả chiều đi/lẫn về)
  const renderFlightTable = (flights) => (
    <table className="flight-table">
      <thead>
        <tr>
          <th>Số hiệu chuyến bay</th>
          <th>Điểm đi</th>
          <th>Điểm đến</th>
          <th>Giờ khởi hành</th>
          <th>Giờ đến</th>
          <th>Trạng thái</th>
          <th></th>
        </tr>
      </thead>
      <tbody>
        {flights.map((f, idx) => (
          <tr key={idx}>
            <td>{f.flightNumber}</td>
            <td>{f.departure}</td>
            <td>{f.arrival}</td>
            <td>{f.departureTime}</td>
            <td>{f.arrivalTime}</td>
            <td>{f.status}</td>
            <td>
              <button className="book-btn" onClick={() => handleBook(f)}>
                Đặt vé
              </button>
            </td>
          </tr>
        ))}
      </tbody>
    </table>
  );

  return (
    <div className="flight-result-wrapper">
      <h2>Kết quả tìm kiếm chuyến bay</h2>
      {successMsg && (
        <div className="booking-success-dialog">
          {successMsg}
        </div>
      )}
      {loading ? (
        <div>Đang tải dữ liệu...</div>
      ) : (
        <>
          {flightsGo.length > 0 && (
            <>
              <h3>Chuyến bay chiều đi</h3>
              {renderFlightTable(flightsGo)}
            </>
          )}
          {flightsReturn.length > 0 && (
            <>
              <h3>Chuyến bay chiều về</h3>
              {renderFlightTable(flightsReturn)}
            </>
          )}
          {flightsGo.length === 0 && flightsReturn.length === 0 && (
            <div style={{ textAlign: "center" }}>Không tìm thấy chuyến bay phù hợp</div>
          )}
        </>
      )}
    </div>
  );
};

export default memo(FlightResultPage);