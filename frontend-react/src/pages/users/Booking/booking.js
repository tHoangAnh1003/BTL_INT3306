import { memo, useEffect, useState } from "react";
// import { useNavigate } from "react-router-dom";
import "../Booking/booking.scss";
import FlightResultPage from "../FlightResult/flightResult";
// const { tripType, from, to, departDate, returnDate } = flightState || {};

const tripTabs = [
  { id: "round", label: "Khứ hồi" },
  { id: "oneway", label: "Một chiều" },
];

const BookingPage = () => {
  // const navigate = useNavigate();

  const [tripType, setTripType] = useState("oneway");
  const [form, setForm] = useState({
    from: "",
    to: "",
    departDate: "",
    returnDate: "",
    pax: 1,
    email: ""
  });

  const [airports, setAirports] = useState([]);
  const [loadingAirports, setLoadingAirports] = useState(true);

  const [showFlightResult, setShowFlightResult] = useState(false);
  const [flightResultState, setFlightResultState] = useState(null);

  useEffect(() => {
    // Lấy danh sách sân bay từ backend
    const fetchAirports = async () => {
      setLoadingAirports(true);
      try {
        const res = await fetch("http://localhost:8081/api/airports");
        let data = await res.json();
        if (!Array.isArray(data)) data = [];
        setAirports(data);
      } catch (err) {
        setAirports([]);
      }
      setLoadingAirports(false);
    };
    fetchAirports();
  }, []);

  useEffect(() => {
    const el = document.getElementById("booking-section");
    el && el.scrollIntoView({ behavior: "smooth", block: "start" });
  }, []);

  const onChange = e => setForm({ ...form, [e.target.name]: e.target.value });

  // Xử lý tìm chuyến bay
  const handleSearch = () => {
    let params = {};
    if (tripType === "oneway") {
      params = {
        departure: form.from,
        arrival: form.to,
        departureDate: form.departDate,
      };
    } else {
      // Khứ hồi
      if (form.departDate && form.returnDate) {
        params = {
          departure: form.from,
          arrival: form.to,
          departureDate: form.departDate,
          returnDate: form.returnDate,
        };
      } else if (form.departDate) {
        params = {
          departure: form.from,
          arrival: form.to,
          departureDate: form.departDate,
        };
      } else if (form.returnDate) {
        params = {
          departure: form.to, // Đảo chiều cho chuyến về
          arrival: form.from,
          departureDate: form.returnDate,
        };
      }
    }
    // navigate("/ket-qua-chuyen-bay", {
    //   state: { ...form, params: new URLSearchParams(params).toString(), tripType }
    // });
    setFlightResultState({ ...form, params: new URLSearchParams(params).toString(), tripType });
    setShowFlightResult(true);
  };

  return (
    <div id="booking-section">
      <h1>Mua vé</h1>
      <div className="recent-box">
        <ul className="trip-tabs">
          {tripTabs.map(t => (
            <li key={t.id}
                className={tripType === t.id ? "active" : ""}
                onClick={() => setTripType(t.id)}>
              {t.label}
            </li>
          ))}
        </ul>
      </div>

      {/* Form tìm chuyến bay */}
      <form className="booking-form" onSubmit={e => e.preventDefault()}>
        <div>
          <label htmlFor="from">Từ</label>
          <select
            id="from"
            name="from"
            value={form.from}
            onChange={onChange}
            required
            disabled={loadingAirports}
          >
            <option value="">Chọn điểm đi</option>
            {Array.isArray(airports) && airports.map(a => (
              <option key={a.code || a.id} value={a.name}>{a.name}</option>
            ))}
          </select>
        </div>
        <div>
          <label htmlFor="to">Đến</label>
          <select
            id="to"
            name="to"
            value={form.to}
            onChange={onChange}
            required
            disabled={loadingAirports}
          >
            <option value="">Chọn điểm đến</option>
            {airports.map(a => (
              <option key={a.code || a.id} value={a.name}>{a.name}</option>
            ))}
          </select>
        </div>
        <div>
          <label htmlFor="departDate">Ngày đi</label>
          <input id="departDate" type="date" name="departDate" value={form.departDate} onChange={onChange} />
        </div>
        {tripType === "round" && (
          <div>
            <label htmlFor="returnDate">Ngày về</label>
            <input id="returnDate" type="date" name="returnDate" value={form.returnDate} onChange={onChange} />
          </div>
        )}
        <button type="button" onClick={handleSearch} disabled={loadingAirports}>
          {loadingAirports ? "Đang tải..." : "Tìm chuyến bay"}
        </button>
      </form>

        {/* Hiển thị kết quả tìm kiếm chuyến bay bên dưới form */}
      {showFlightResult && (
        <div style={{ marginTop: 32 }}>
          {/* <FlightResultPage location={{ state: flightResultState }} /> */}
          <FlightResultPage flightState={flightResultState} />
        </div>
      )}

    </div>
  );
};

export default memo(BookingPage);