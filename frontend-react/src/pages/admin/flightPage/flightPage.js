import { useState } from "react";
import "./flightPage.scss";

const FlightPage = () => {
  const [form, setForm] = useState({
    airline: "",
    flightNumber: "",
    departureAirport: "",
    arrivalAirport: "",
    departureTime: "",
    arrivalTime: "",
    status: "SCHEDULED",
  });
  const [success, setSuccess] = useState("");
  const [error, setError] = useState("");

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setSuccess("");
    setError("");
    const token = localStorage.getItem("accessToken"); 
    try {
      const res = await fetch("http://localhost:8081/api/flights", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          "Authorization": `Bearer ${token}`,
        },
        body: JSON.stringify(form),
      });
      if (!res.ok) {
        const data = await res.json();
        setError(data.message || "Lỗi khi lưu dữ liệu");
        return;
      }
      setSuccess("Lưu chuyến bay thành công!");
      setForm({
        airline: "",
        flightNumber: "",
        departureAirport: "",
        arrivalAirport: "",
        departureTime: "",
        arrivalTime: "",
        status: "SCHEDULED",
      });
    } catch {
      setError("Lỗi kết nối máy chủ");
    }
  };

  return (
    <div className="flight-page">
      <h2>Nhập thông tin chuyến bay</h2>
      <form onSubmit={handleSubmit} className="flight-form">
        <label>
          Hãng hàng không:
          <input
            name="airline"
            value={form.airline}
            onChange={handleChange}
            required
          />
        </label>
        <label>
          Số hiệu chuyến bay:
          <input
            name="flightNumber"
            value={form.flightNumber}
            onChange={handleChange}
            required
          />
        </label>
        <label>
          Điểm đi:
          <input
            name="departureAirport"
            value={form.departureAirport}
            onChange={handleChange}
            required
          />
        </label>
        <label>
          Điểm đến:
          <input
            name="arrivalAirport"
            value={form.arrivalAirport}
            onChange={handleChange}
            required
          />
        </label>
        <label>
          Giờ khởi hành:
          <input
            name="departureTime"
            type="datetime-local"
            value={form.departureTime}
            onChange={handleChange}
            required
          />
        </label>
        <label>
          Giờ đến:
          <input
            name="arrivalTime"
            type="datetime-local"
            value={form.arrivalTime}
            onChange={handleChange}
            required
          />
        </label>
        <label>
          Trạng thái:
          <select name="status" value={form.status} onChange={handleChange}>
            <option value="SCHEDULED">SCHEDULED</option>
            <option value="DELAYED">DELAYED</option>
            <option value="CANCELLED">CANCELLED</option>
            <option value="DEPARTED">DEPARTED</option>
            <option value="ARRIVED">ARRIVED</option>
          </select>
        </label>
        <button type="submit">Lưu</button>
        {success && <div className="success">{success}</div>}
        {error && <div className="error">{error}</div>}
      </form>
    </div>
  );
};

export default FlightPage;