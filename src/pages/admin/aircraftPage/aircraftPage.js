import { useState } from "react";
import "./aircraftPage.scss";

const AircraftPage = () => {
  const [form, setForm] = useState({
    code: "",
    manufacturer: "",
    seatInfo: "",
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
      const res = await fetch("http://localhost:8081/api/admin/aircraft", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(form),
      });
      if (!res.ok) {
        const data = await res.json();
        setError(data.message || "Lỗi khi lưu dữ liệu");
        return;
      }
      setSuccess("Lưu thành công!");
      setForm({ code: "", manufacturer: "", seatInfo: "", description: "" });
    } catch {
      setError("Lỗi kết nối máy chủ");
    }
  };

  return (
    <div className="aircraft-page">
      <h2>Nhập thông tin tàu bay</h2>
      <form onSubmit={handleSubmit} className="aircraft-form">
        <label>
          Mã tàu bay:
          <input
            name="code"
            value={form.code}
            onChange={handleChange}
            required
          />
        </label>
        <label>
          Hãng sản xuất:
          <input
            name="manufacturer"
            value={form.manufacturer}
            onChange={handleChange}
            required
          />
        </label>
        <label>
          Thông tin ghế (ví dụ: 180 ghế, chia hạng...):
          <input
            name="seatInfo"
            value={form.seatInfo}
            onChange={handleChange}
            required
          />
        </label>
        <label>
          Mô tả thêm:
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

export default AircraftPage;