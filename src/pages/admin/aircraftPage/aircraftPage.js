import { useState } from "react";
import "./aircraftPage.scss";

const AircraftPage = () => {
  const [form, setForm] = useState({
    airline: "",
    model: "",
    capacity: "",
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
      const res = await fetch("http://localhost:8081/api/aircrafts", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          airline: form.airline,
          model: form.model,
          capacity: Number(form.capacity),
        }),
      });
      if (!res.ok) {
        const data = await res.json();
        setError(data.message || "Lỗi khi lưu dữ liệu");
        return;
      }
      setSuccess("Lưu tàu bay thành công!");
      setForm({ airline: "", model: "", capacity: "" });
    } catch {
      setError("Lỗi kết nối máy chủ");
    }
  };

  return (
    <div className="aircraft-page">
      <h2>Nhập thông tin tàu bay</h2>
      <form onSubmit={handleSubmit} className="aircraft-form">
        <label>
          Hãng hàng không:
          <input
            name="airline"
            value={form.airline}
            onChange={handleChange}
            required
            placeholder="Vietnam Airlines"
          />
        </label>
        <label>
          Model:
          <input
            name="model"
            value={form.model}
            onChange={handleChange}
            required
            placeholder="Boeing 787"
          />
        </label>
        <label>
          Sức chứa:
          <input
            name="capacity"
            type="number"
            min={1}
            value={form.capacity}
            onChange={handleChange}
            required
            placeholder="500"
          />
        </label>
        <button type="submit">Lưu</button>
        {success && <div className="success">{success}</div>}
        {error && <div className="error">{error}</div>}
      </form>
    </div>
  );
};

export default AircraftPage;