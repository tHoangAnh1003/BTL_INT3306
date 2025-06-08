import { useNavigate } from "react-router-dom";
import { memo, useEffect, useState } from "react";
import "./flightResult.scss";

const getAirportCodeByCity = async (city) => {
  const res = await fetch(`http://localhost:8081/api/airports/code?city=${encodeURIComponent(city)}`);
  if (!res.ok) throw new Error("Không tìm thấy mã sân bay cho thành phố " + city);
  const data = await res.json();
  return data.code;
};


const FlightResultPage = ({ flightState }) => {
  const navigate = useNavigate();

  
  const { tripType, from, to, departDate, returnDate } = flightState;

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
  }, [tripType, from, to, departDate, returnDate]);

  // Hàm đặt vé - đã sửa để gửi thêm thông tin ngàys
  const handleBook = async (flight, isReturnFlight = false) => {
    setSuccessMsg("");
    const token = localStorage.getItem("accessToken");
    if (!token) {
      setSuccessMsg("Vui lòng đăng nhập để đặt vé!");
      setTimeout(() => {
        setSuccessMsg("");
      }, 1500);
      return;
    }

    // Xác định ngày khởi hành dựa trên loại chuyến bay
    let flightDate;
    if (isReturnFlight) {
      flightDate = returnDate;
    } else {
      flightDate = departDate;
    }

    const departureCity = flight.departure.split(" - ")[0].trim();
    const arrivalCity = flight.arrival.split(" - ")[0].trim();

    // Gọi API lấy code từ city
    const departureCode = await getAirportCodeByCity(departureCity);
    const arrivalCode = await getAirportCodeByCity(arrivalCity);
    // Chuyển đổi format ngày nếu cần (từ DD/MM/YYYY sang YYYY-MM-DD)
    const formatDateForBackend = (dateStr) => {
      if (!dateStr) return null;
      // Nếu đã là format YYYY-MM-DD
      if (dateStr.includes('-') && dateStr.indexOf('-') === 4) {
        return dateStr;
      }
      // Nếu là format DD/MM/YYYY
      if (dateStr.includes('/')) {
        const parts = dateStr.split('/');
        if (parts.length === 3) {
          return `${parts[2]}-${parts[1].padStart(2, '0')}-${parts[0].padStart(2, '0')}`;
        }
      }
      return dateStr;
    };

    try {
      const res = await fetch("http://localhost:8081/api/bookings", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },

      
        body: JSON.stringify({
          departureAirportCode: departureCode,
          arrivalAirportCode: arrivalCode,
          departureDate: formatDateForBackend(flightDate)
        })     
      });

      const text = await res.text(); // Lấy raw text response

      if (!res.ok) {
        setSuccessMsg(text || "Đặt vé thất bại!");
        setTimeout(() => setSuccessMsg(""), 3000);
        return;
      }

      // Nếu ok thì mới parse JSON
      const data = JSON.parse(text);
      
      setSuccessMsg(`Đặt vé thành công! Ghế: ${data.seatNumber || 'N/A'}`);
      setTimeout(() => setSuccessMsg(""), 3000);
      
    } catch (error) {
      console.error('Booking error:', error);
      setSuccessMsg("Lỗi kết nối máy chủ");
      setTimeout(() => setSuccessMsg(""), 3000);
    }
  };

  // Bảng chuyến bay (dùng cho cả chiều đi/lẫn về)
  const renderFlightTable = (flights, isReturnFlight = false) => (
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
              <button 
                className="book-btn" 
                onClick={() => handleBook(f, isReturnFlight)}
              >
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
              {renderFlightTable(flightsGo, false)}
            </>
          )}
          {flightsReturn.length > 0 && (
            <>
              <h3>Chuyến bay chiều về</h3>
              {renderFlightTable(flightsReturn, true)}
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