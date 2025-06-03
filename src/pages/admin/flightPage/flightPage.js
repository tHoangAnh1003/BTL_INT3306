import { useState } from "react";
import "./flightPage.scss";

const FlightPage = () => {
  const [form, setForm] = useState({
    flightNumber: "",
    aircraftCode: "",
    departure: "",
    arrival: "",
    departureTime: "",
    arrivalTime: "",
    description: "",
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
    try {
      const res = await fetch("http://localhost:8081/api/admin/flights", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(form),
      });
      if (!res.ok) {
        const data = await res.json();
        setError(data.message || "Lỗi khi lưu dữ liệu");
        return;
      }
      setSuccess("Lưu chuyến bay thành công!");
      setForm({
        flightNumber: "",
        aircraftCode: "",
        departure: "",
        arrival: "",
        departureTime: "",
        arrivalTime: "",
        description: "",
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
          Số hiệu chuyến bay:
          <input
            name="flightNumber"
            value={form.flightNumber}
            onChange={handleChange}
            required
          />
        </label>
        <label>
          Mã tàu bay:
          <input
            name="aircraftCode"
            value={form.aircraftCode}
            onChange={handleChange}
            required
          />
        </label>
        <label>
          Điểm đi:
          <input
            name="departure"
            value={form.departure}
            onChange={handleChange}
            required
          />
        </label>
        <label>
          Điểm đến:
          <input
            name="arrival"
            value={form.arrival}
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
          Ghi chú thêm:
          <textarea
            name="description"
            value={form.description}
            onChange={handleChange}
          />
        </label>
        <button type="submit">Lưu</button>
        {success && <div className="success">{success}</div>}
        {error && <div className="error">{error}</div>}
      </form>
    </div>
  );
};

export default FlightPage;