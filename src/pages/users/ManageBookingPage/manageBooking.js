import { memo, useEffect, useState } from "react";
import "./manageBooking.scss";

const ManageBookingPage = () => {
  // Scroll to the booking section on mount
  useEffect(() => {
    const el = document.getElementById("manage-section");
    el && el.scrollIntoView({ behavior: "smooth", block: "start" });
  }, []);

  const [code, setCode] = useState("");
  const [fullname, setFullname] = useState("");
  const [booking, setBooking] = useState(null);
  const [msg, setMsg] = useState("");

  // const handleSearch = async () => {
  //   const data = await getBooking(code.trim(), fullname.trim());
  //   setBooking(data);
  //   setMsg("");
  // };

  // const handleCancel = async () => {
  //   const res = await cancelBooking(code.trim());
  //   setMsg(res.message || "Đã hủy vé!");
  //   setBooking(null);
  // };

  return (
    <div id="manage-section">
      <h2>QUẢN LÝ ĐẶT CHỖ</h2>

      <form onSubmit={(e) => e.preventDefault()} className="manage-form">
        <input placeholder="Mã đặt chỗ"
          value={code} onChange={(e) => setCode(e.target.value)} required />
        <input placeholder="Họ và tên"
          value={fullname} onChange={(e) => setFullname(e.target.value)} required />
        <button type="button">Tìm kiếm</button>
      </form>

      {msg && <p className="msg">{msg}</p>}
    </div>
  );
}

export default memo(ManageBookingPage);