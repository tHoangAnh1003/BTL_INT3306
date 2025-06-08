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
          className="active"
          onClick={() => setActiveTab("info")}
        >
          Thông tin cá nhân
        </button>
      </div>
      <div className="profile-tab-content">
        {loading ? (
          <div>Đang tải dữ liệu...</div>
        ) : (
          <div className="profile-info">
            <div><b>Họ tên:</b> {user.fullname || user.fullName || user.name}</div>
            <div><b>Email:</b> {user.email}</div>
            <div><b>Tên đăng nhập:</b> {user.username}</div>
          </div>
        )}
      </div>
    </div>
  );
};

export default ProfilePage;