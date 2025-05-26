import { memo, useEffect, useState } from "react";
import "../Booking/booking.scss";
//import API flight serice

const tripTabs = [
  {
    id: "round", label: "Khứ hồi"
  },
  {
    id: "oneway", label: "Một chiều"
  },
]

const BookingPage = () => {

  useEffect(() => {
    const el = document.getElementById("booking-section");
    el && el.scrollIntoView({ behavior: "smooth", block: "start" });
  }, []);

  const [tripType, setTripType] = useState("round");
  const [form, setForm] = useState({
    from: "Hà Nội (HAN), Việt Nam",
    to: "",
    departDate: "",
    returnDate: "",
    pax: 1,
    email: ""
  });
  // const [flights, setFlights] = useState([]);
  // const [selected, setSelected] = useState(null);
  // const [msg, setMsg] = useState("");

  /* ---------- handlers ---------- */
  const onChange = e => setForm({ ...form, [e.target.name]: e.target.value });

  // const handleSearch = async () => {
  //   const data = await searchFlights({ ...form, tripType });
  //   setFlights(data);
  //   setSelected(null);
  //   setMsg("");
  // };

  // const handleBook = async () => {
  //   if (!selected) return setMsg("Vui lòng chọn chuyến bay.");
  //   const res = await createBooking({ flightId: selected.flight_id, ...form });
  //   setMsg(res.message || "Đặt vé thành công!");
  // };

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
          <input id="from" name="from" value={form.from} onChange={onChange} placeholder="Từ" required />
        </div>
        <div>
          <label htmlFor="to">Đến</label>
          <input id="to" name="to" value={form.to} onChange={onChange} placeholder="Đến" required />
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
        <div>
          <label htmlFor="pax">Hành khách</label>
          <select id="pax" name="pax" value={form.pax} onChange={onChange}>
            {[...Array(9)].map((_, i) => (
              <option key={i + 1} value={i + 1}>{i + 1}</option>
            ))}
          </select>
        </div>
        <div>
          <label htmlFor="email">Email</label>
          <input id="email" type="email" name="email" value={form.email} onChange={onChange} placeholder="example@gmail.com" required />
        </div>
        <button type="button">Tìm chuyến bay</button>
      </form>

      {/* Danh sách chuyến bay */}
      {/* content */}
    </div>
  );
}

export default memo(BookingPage);