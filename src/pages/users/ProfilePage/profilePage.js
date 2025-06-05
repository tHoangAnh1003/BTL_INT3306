import { useEffect, useState } from "react";
import "./profilePage.scss";
import { useNavigate } from "react-router-dom";
import { ROUTERS } from "../../../utils/router-config";

const ProfilePage = () => {
  const [activeTab, setActiveTab] = useState("info");
  const [user, setUser] = useState({});
  const [bookings, setBookings] = useState([]);
  const [loading, setLoading] = useState(true);
  const [msg, setMsg] = useState("");
  const navigate = useNavigate();

  useEffect(() => {
    const fetchProfile = async () => {
      setLoading(true);
      try {
        const token = localStorage.getItem("accessToken");
        if (!token) {
          // navigate(ROUTERS.ADMIN.LOGIN);
          return;
        }
        // Lấy thông tin user
        const resUser = await fetch("http://localhost:8081/api/users/me", {
          headers: { Authorization: `Bearer ${token}` },
        });
        if (resUser.ok) {
          const data = await resUser.json();
          setUser(data);
        }
        // Lấy danh sách vé đã đặt
        const resBookings = await fetch("http://localhost:8081/api/bookings/my", {
          headers: { Authorization: `Bearer ${token}` },
        });
        if (resBookings.ok) {
          const data = await resBookings.json();
          setBookings(data);
        }
      } catch {
        setMsg("Lỗi kết nối máy chủ");
      }
      setLoading(false);
    };
    fetchProfile();
  }, [navigate]);

  // Hủy vé
  const handleCancel = async (bookingId) => {
    setMsg("");
    try {
      const token = localStorage.getItem("accessToken");
      const res = await fetch(`http://localhost:8081/api/bookings/${bookingId}/cancel`, {
        method: "DELETE",
        headers: { Authorization: `Bearer ${token}` },
      });
      if (!res.ok) {
        const data = await res.json();
        setMsg(data.message || "Hủy vé thất bại");
        return;
      }
      setMsg("Hủy vé thành công!");
      setBookings(bookings => bookings.filter(b => b.bookingId !== bookingId));
    } catch {
      setMsg("Lỗi kết nối máy chủ");
    }
  };

  // Đăng xuất
  const handleLogout = () => {
    localStorage.removeItem("accessToken");
    localStorage.removeItem("refreshToken");
    localStorage.removeItem("username");
    localStorage.removeItem("user");
    window.dispatchEvent(new Event("storage")); // Để header cập nhật lại avatar
    navigate("/");
  };

  return (
    <div className="profile-page">
      <h2>Trang cá nhân</h2>
      <div style={{ display: "flex", gap: 12, marginBottom: 16 }}>
        <button onClick={() => navigate("/")} className="profile-btn">
          Quay về trang chủ
        </button>
        <button onClick={handleLogout} className="profile-btn logout-btn">
          Đăng xuất
        </button>
      </div>
      <div className="profile-tabs">
        <button
          className={activeTab === "info" ? "active" : ""}
          onClick={() => setActiveTab("info")}
        >
          Thông tin cá nhân
        </button>
        <button
          className={activeTab === "bookings" ? "active" : ""}
          onClick={() => setActiveTab("bookings")}
        >
          Chuyến bay đã đặt
        </button>
      </div>
      <div className="profile-tab-content">
        {loading ? (
          <div>Đang tải dữ liệu...</div>
        ) : activeTab === "info" ? (
          <div className="profile-info">
            <div><b>Họ tên:</b> {user.fullname || user.fullName || user.name}</div>
            <div><b>Email:</b> {user.email}</div>
            <div><b>Tên đăng nhập:</b> {user.username}</div>
          </div>
        ) : (
          <div>
            <h3>Chuyến bay đã đặt</h3>
            {msg && <div className="profile-msg">{msg}</div>}
            <table className="profile-booking-table">
              <thead>
                <tr>
                  <th>Số hiệu chuyến bay</th>
                  <th>Số ghế</th>
                  <th>Thời gian đặt vé</th>
                  <th>Trạng thái</th>
                  <th></th>
                </tr>
              </thead>
              <tbody>
                {bookings.length === 0 ? (
                  <tr>
                    <td colSpan={5} style={{ textAlign: "center" }}>Bạn chưa đặt vé nào</td>
                  </tr>
                ) : (
                  bookings.map((b, idx) => (
                    <tr key={idx}>
                      <td>{b.aircraftModel || b.flightNumber}</td>
                      <td>{b.seatId || b.seatNumber}</td>
                      <td>{b.time || b.bookingDate}</td>
                      <td>{b.status || "Confirmed"}</td>
                      <td>
                        {b.status !== "Cancelled" && (
                          <button
                            className="cancel-btn"
                            onClick={() => handleCancel(b.bookingId || b.id)}
                          >
                            Hủy vé
                          </button>
                        )}
                      </td>
                    </tr>
                  ))
                )}
              </tbody>
            </table>
          </div>
        )}
      </div>
    </div>
  );
};

export default ProfilePage;