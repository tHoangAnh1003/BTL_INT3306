import { memo, useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import "../Booking/booking.scss";


const tripTabs = [
  {
    id: "round", label: "Khứ hồi"
  },
  {
    id: "oneway", label: "Một chiều"
  },
]

const BookingPage = () => {
  const navigate = useNavigate();

  const [tripType, setTripType] = useState("round");
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

  useEffect(() => {
    // Lấy danh sách sân bay từ backend
    const fetchAirports = async () => {
      setLoadingAirports(true);
      try {
        const res = await fetch("http://localhost:8081/api/airports");
        const data = await res.json();
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
  const handleSearch = () => {
    navigate("/ket-qua-chuyen-bay", { state: form });
  };

  return (
    <div id="booking-section">
      <h1>Mua vé</h1>

      {/* Tabs loại chuyến */}
      <div className="recent-box">
        {/* <h3>Tra cứu gần đây</h3> */}
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
            {airports.map(a => (
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
          <input id="departDate" type="date" name="departDate" value={form.departDate} onChange={onChange} required />
        </div>
        {tripType === "round" && (
          <div>
            <label htmlFor="returnDate">Ngày về</label>
            <input id="returnDate" type="date" name="returnDate" value={form.returnDate} onChange={onChange} required />
          </div>
        )}
        
        <button type="button" onClick={handleSearch} disabled={loadingAirports}>
          {loadingAirports ? "Đang tải..." : "Tìm chuyến bay"}
        </button>
      </form>

      {/* Danh sách chuyến bay */}
      {/* content */}
    </div>
  );
}

export default memo(BookingPage);