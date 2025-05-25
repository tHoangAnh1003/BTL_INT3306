import { memo, useEffect, useState } from "react";
import "./checkIn.scss";

const idTabs = [
  { id: "pnr", label: "Mã đặt chỗ (PNR)" },
  { id: "etkt", label: "Số vé điện tử" },
  { id: "ffp", label: "Số thẻ FFP" },
];

const CheckInPage = () => {
  useEffect(() => {
    const el = document.getElementById("checkin-section");
    el && el.scrollIntoView({ behavior: "smooth", block: "start" });
  }, []);

  const [idType, setIdType] = useState("pnr");
  const [code, setCode] = useState("");
  const [fullname, setName] = useState("");
  const [msg, setMsg] = useState("");

  const renderFormFields = () => {
    switch (idType) {
      case "pnr":
        return (
          <>
            <input
              placeholder="Mã đặt chỗ"
              value={code}
              onChange={(e) => setCode(e.target.value)}
              required
            />
            <input
              placeholder="Họ và tên"
              value={fullname}
              onChange={(e) => setName(e.target.value)}
              required
            />
          </>
        );
      case "etkt":
        return (
          <>
            <input
              placeholder="Số vé điện tử"
              value={code}
              onChange={(e) => setCode(e.target.value)}
              required
            />
            <input
              placeholder="Họ và tên"
              value={fullname}
              onChange={(e) => setName(e.target.value)}
              required
            />
          </>
        );
      case "ffp":
        return (
          <>
            <input
              placeholder="Số thẻ FFP"
              value={code}
              onChange={(e) => setCode(e.target.value)}
              required
            />
            <input
              placeholder="Họ và tên"
              value={fullname}
              onChange={(e) => setName(e.target.value)}
              required
            />
            <select>
              <option value="vna">Vietnam Airlines (VN)</option>
              <option value="other">Hãng khác</option>
            </select>
          </>
        );
      default:
        return null;
    }
  };

  return (
    <div id="checkin-section">
      <h2>LÀM THỦ TỤC TRỰC TUYẾN</h2>
      <p>Quý khách có thể làm thủ tục trực tuyến từ 24 đến 01 tiếng trước chuyến bay</p>

      <ul className="id-tabs">
        {idTabs.map((t) => (
          <li
            key={t.id}
            className={idType === t.id ? "active" : ""}
            onClick={() => setIdType(t.id)}
          >
            {t.label}
          </li>
        ))}
      </ul>

      <form onSubmit={(e) => e.preventDefault()} className="checkin-form">
        {renderFormFields()}
        <button type="button">Làm thủ tục</button>
      </form>

      {msg && <p className="msg">{msg}</p>}
    </div>
  );
};

export default memo(CheckInPage);