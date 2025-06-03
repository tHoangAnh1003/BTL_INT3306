import { memo, useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { ROUTERS } from "../../../utils/router-config";
import "./manageBooking.scss";

const ManageBookingPage = () => {
  // Scroll to the booking section on mount
  useEffect(() => {
    const el = document.getElementById("manage-section");
    el && el.scrollIntoView({ behavior: "smooth", block: "start" });
  }, []);

  const [code, setCode] = useState("");
  const [seatId, setSeatId] = useState("");
  const [msg, setMsg] = useState("");
  const navigate = useNavigate();

  const handleSearch = async () => {
    setMsg("");
    if (!code || !seatId) {
      setMsg("Vui lòng nhập đầy đủ mã khách hàng và mã ghế ngồi.");
      return;
    }
    try {
      const res = await fetch(
        `http://localhost:8081/api/bookings/search?customerId=${encodeURIComponent(
          code
        )}&seatId=${encodeURIComponent(seatId)}`
      );
      if (!res.ok) {
        setMsg("Không tìm thấy thông tin đặt chỗ.");
        return;
      }
      const data = await res.json();
      if (!data || Object.keys(data).length === 0) {
        setMsg("Không tìm thấy thông tin đặt chỗ.");
        return;
      }
      // Điều hướng sang trang đã đặt, truyền dữ liệu booking qua state
      navigate(ROUTERS.USER.BOOKED_FLIGHTS, { state: { booking: data } });
    } catch (err) {
      setMsg("Có lỗi xảy ra khi tìm kiếm.");
    }
  };

  return (
    <div id="manage-section">
      <h2>QUẢN LÝ ĐẶT CHỖ</h2>

      <form onSubmit={(e) => e.preventDefault()} className="manage-form">
        <input
          placeholder="Mã khách hàng"
          value={code}
          onChange={(e) => setCode(e.target.value)}
          required
        />
        <input
          placeholder="Mã ghế ngồi"
          value={seatId}
          onChange={(e) => setSeatId(e.target.value)}
          required
        />
        <button type="button" onClick={handleSearch}>
          Tìm kiếm
        </button>
      </form>

      {msg && <p className="msg">{msg}</p>}
    </div>
  );
};

export default memo(ManageBookingPage);