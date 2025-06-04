import { useEffect, useState } from "react";
import "./delayFlightPage.scss";

const DelayFlightPage = () => {
  const [flights, setFlights] = useState([]);
  const [selectedFlight, setSelectedFlight] = useState("");
  const [newDepartureTime, setNewDepartureTime] = useState("");
  const [newArrivalTime, setNewArrivalTime] = useState("");
  const [success, setSuccess] = useState("");
  const [error, setError] = useState("");

  useEffect(() => {
    // Lấy danh sách chuyến bay
    const fetchFlights = async () => {
      try {
        const res = await fetch("http://localhost:8081/api/flights");
        if (!res.ok) throw new Error("Lỗi khi lấy danh sách chuyến bay");
        const data = await res.json();
        setFlights(data);
      } catch (err) {
        setError(err.message || "Lỗi không xác định");
      }
    };
    fetchFlights();
  }, []);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setSuccess("");
    setError("");
    if (!selectedFlight || !newDepartureTime) {
      setError("Vui lòng chọn chuyến bay và nhập giờ khởi hành mới.");
      return;
    }
    try {
      const token = localStorage.getItem("accessToken");
      const body = { newDepartureTime };
      if (newArrivalTime) body.newArrivalTime = newArrivalTime;
      const res = await fetch(`http://localhost:8081/api/flights/${selectedFlight}/delay`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(body),
      });
      if (!res.ok) {
        const data = await res.json();
        setError(data.message || "Lỗi khi cập nhật giờ khởi hành");
        return;
      }
      setSuccess("Cập nhật giờ khởi hành thành công!");
      setNewDepartureTime("");
      setNewArrivalTime("");
      setSelectedFlight("");
    } catch {
      setError("Lỗi kết nối máy chủ");
    }
  };

  return (
    <div className="delay-flight-page">
      <h2>Thay đổi giờ khởi hành chuyến bay (Delay)</h2>
      <form onSubmit={handleSubmit} className="delay-form">
        <label>
          Chọn chuyến bay:
          <select
            value={selectedFlight}
            onChange={e => setSelectedFlight(e.target.value)}
            required
          >
            <option value="">-- Chọn chuyến bay --</option>
            {flights.map(f => (
              <option key={f.id || f.flightId || f.flightNumber} value={f.id || f.flightId}>
                {f.flightNumber} | {f.departure} → {f.arrival} | Giờ cũ: {f.departureTime?.replace("T", " ").slice(0, 16)}
              </option>
            ))}
          </select>
        </label>
        <label>
          Giờ khởi hành mới:
          <input
            type="datetime-local"
            value={newDepartureTime}
            onChange={e => setNewDepartureTime(e.target.value)}
            required
          />
        </label>
        <label>
          Giờ đến mới (nếu có):
          <input
            type="datetime-local"
            value={newArrivalTime}
            onChange={e => setNewArrivalTime(e.target.value)}
          />
        </label>
        <button type="submit">Cập nhật giờ khởi hành</button>
        {success && <div className="success">{success}</div>}
        {error && <div className="error">{error}</div>}
      </form>
    </div>
  );
};

export default DelayFlightPage;