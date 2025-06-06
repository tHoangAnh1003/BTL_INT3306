import { memo, useState, useEffect, useRef } from "react";
import { Link, useNavigate } from "react-router-dom";

import airlineBackgound from "../../../assets/imgs/background.jpg";
import airlineLogo from "../../../assets/imgs/logo-removebg-preview.png";

import "./header.scss";
import { ROUTERS } from "../../../utils/router-config";

const Header = () => {
  const navigate = useNavigate();
  const [isMenuOpen, setIsMenuOpen] = useState(false);
  const [username, setUsername] = useState("");
  const [showMenu, setShowMenu] = useState(false);
  const avatarRef = useRef(null);

  // Lấy thông tin user từ backend nếu đã đăng nhập (có accessToken)
  useEffect(() => {
    const fetchUser = async () => {
      const token = localStorage.getItem("accessToken");
      if (!token) {
        setUsername("");
        return;
      }
      try {
        const res = await fetch("http://localhost:8081/api/users/me", {
          headers: { Authorization: `Bearer ${token}` },
        });
        if (res.ok) {
          const data = await res.json();
          // Chỉ hiện avatar nếu là khách hàng
          if (data.role && (data.role === "CUSTOMER" || data.role.toLowerCase() === "customer")) {
            setUsername(data.username || data.fullName || data.fullname || "");
          } else {
            setUsername("");
          }
        } else {
          setUsername("");
        }
      } catch {
        setUsername("");
      }
    };
    fetchUser();
    // Lắng nghe sự thay đổi của localStorage (logout ở nơi khác)
    const handleStorage = () => fetchUser();
    window.addEventListener("storage", handleStorage);
    return () => window.removeEventListener("storage", handleStorage);
  }, []);

  // Đóng menu khi click ra ngoài avatar
  useEffect(() => {
    const handleClickOutside = (e) => {
      if (avatarRef.current && !avatarRef.current.contains(e.target)) {
        setShowMenu(false);
      }
    };
    if (showMenu) {
      window.addEventListener("mousedown", handleClickOutside);
    }
    return () => window.removeEventListener("mousedown", handleClickOutside);
  }, [showMenu]);

  const [menus] = useState([
    { name: "Trang chủ", path: ROUTERS.USER.HOME },
    { name: "Mua vé", path: ROUTERS.USER.BOOKING },
    { name: "Quản lý đặt chỗ", path: ROUTERS.USER.BOOKED_FLIGHTS },
    { name: "Thông tin chuyến bay", path: ROUTERS.USER.FLIGHT_INFO },
  ]);

  const toggleMenu = () => {
    setIsMenuOpen(!isMenuOpen);
  };

  // Đăng xuất
  const handleLogout = () => {
    localStorage.removeItem("accessToken");
    localStorage.removeItem("refreshToken");
    localStorage.removeItem("username");
    localStorage.removeItem("user");
    setUsername("");
    setShowMenu(false);
    window.dispatchEvent(new Event("storage"));
    navigate("/");
  };

  return (
    <header
      className="qairline-header"
      style={{ backgroundImage: `url(${airlineBackgound})` }}
    >
      {/* Overlay */}
      <div className={`burger-menu-overlay ${isMenuOpen ? " active" : ""}`}
        onClick={() => setIsMenuOpen(false)}
      />
      {/* Nút burger */}
      <div className="burger" onClick={toggleMenu}>
        <span />
        <span />
      </div>

      {/* Menu trượt bên trái */}
      <div className={`burger-menu-wrapper ${isMenuOpen ? "open" : ""}`}>
        <div className="qairline-logo">
          <h1>Q Airlines</h1>
        </div>
        <div className="burger-menu-login">
          {username ? (
            <span className="login-button">{username}</span>
          ) : (
            <Link to={ROUTERS.ADMIN.LOGIN} className="login-button">
              Đăng nhập
            </Link>
          )}
        </div>
        <div className="burger-nav-menu">
          <ul className="qairline-nav-menu">
            {menus?.map((menu, index) => (
              <li key={index}>
                {menu.name === "Trang chủ" ? (
                  <Link
                    to={menu.path}
                    className="qairline-nav-link"
                    onClick={e => {
                      e.preventDefault();
                      navigate("/");
                      setTimeout(() => window.location.reload(), 0);
                      setIsMenuOpen(false);
                    }}
                  >
                    {menu.name}
                  </Link>
                ) : (menu.name === "Quản lý đặt chỗ" || menu.name === "Thông tin chuyến bay") ? (
                  <a
                    href="#"
                    className="qairline-nav-link"
                    onClick={e => {
                      e.preventDefault();
                      const token = localStorage.getItem("accessToken");
                      setIsMenuOpen(false);
                      if (!token) {
                        navigate(ROUTERS.ADMIN.LOGIN);
                      } else {
                        navigate(menu.path);
                      }
                    }}
                  >
                    {menu.name}
                  </a>
                ) : (
                  <Link
                    to={menu.path}
                    className="qairline-nav-link"
                    onClick={() => setIsMenuOpen(false)}
                  >
                    {menu.name}
                  </Link>
                )}
              </li>
            ))}
            {/* Thêm "Xem hồ sơ" ngay sau "Trang chủ" nếu đã đăng nhập */}
            {username && (
              <li>
                <a
                  href="#"
                  className="qairline-nav-link"
                  onClick={e => {
                    e.preventDefault();
                    setIsMenuOpen(false);
                    navigate("/ho-so");
                  }}
                >
                  Xem hồ sơ
                </a>
              </li>
            )}
            {/* Thêm "Đăng xuất" ở cuối menu nếu đã đăng nhập */}
            {username && (
              <li>
                <button
                  className="qairline-nav-link"
                  style={{
                    width: "100%",
                    background: "none",
                    border: "none",
                    padding: "8px 16px",
                    textAlign: "left",
                    cursor: "pointer",
                    fontSize: 15,
                    color: "#d32f2f"
                  }}
                  onClick={() => {
                    handleLogout();
                    setIsMenuOpen(false);
                  }}
                >
                  Đăng xuất
                </button>
              </li>
            )}
          </ul>
        </div>
      </div>

      {/* Phần logo và menu điều hướng */}
      <nav className="qairline-navbar">
        <div className="qairline-logo">
          <img src={airlineLogo} alt="Q Airlines Logo" className="logo-img" />
          <span>Q Airlines</span>
        </div>

        <ul className="qairline-nav-menu">
          {menus.map((m, i) => (
            <li key={i}>
              {(m.name === "Quản lý đặt chỗ" || m.name === "Thông tin chuyến bay") ? (
                <a
                  href="#"
                  className="qairline-nav-link"
                  onClick={e => {
                    e.preventDefault();
                    const token = localStorage.getItem("accessToken");
                    if (!token) {
                      navigate(ROUTERS.ADMIN.LOGIN);
                    } else {
                      navigate(m.path);
                    }
                  }}
                >
                  {m.name}
                </a>
              ) : (
                <Link to={m.path} className="qairline-nav-link">
                  {m.name}
                </Link>
              )}
            </li>
          ))}
          <li style={{ position: "relative" }} ref={avatarRef}>
            {username ? (
              <>
                <span
                  className="login-avatar"
                  title={username}
                  style={{ cursor: "pointer" }}
                  onClick={() => setShowMenu((v) => !v)}
                >
                  <img
                    src={`https://ui-avatars.com/api/?name=${encodeURIComponent(username)}&background=0071c2&color=fff&size=32`}
                    alt="avatar"
                    style={{
                      width: 32,
                      height: 32,
                      borderRadius: "50%",
                      objectFit: "cover",
                      border: "2px solid #fff",
                      background: "#0071c2",
                      verticalAlign: "middle"
                    }}
                  />
                </span>
                {showMenu && (
                  <div
                    style={{
                      position: "absolute",
                      top: 40,
                      right: 0,
                      background: "#fff",
                      boxShadow: "0 2px 8px rgba(0,0,0,0.15)",
                      borderRadius: 8,
                      minWidth: 140,
                      zIndex: 10,
                      padding: "8px 0"
                    }}
                  >
                    <button
                      style={{
                        width: "100%",
                        background: "none",
                        border: "none",
                        padding: "8px 16px",
                        textAlign: "left",
                        cursor: "pointer",
                        fontSize: 15
                      }}
                      onClick={() => {
                        setShowMenu(false);
                        navigate("/ho-so");
                      }}
                    >
                      Xem hồ sơ
                    </button>
                    <button
                      style={{
                        width: "100%",
                        background: "none",
                        border: "none",
                        padding: "8px 16px",
                        textAlign: "left",
                        cursor: "pointer",
                        fontSize: 15,
                        color: "#d32f2f"
                      }}
                      onClick={handleLogout}
                    >
                      Đăng xuất
                    </button>
                  </div>
                )}
              </>
            ) : (
              <Link to={ROUTERS.ADMIN.LOGIN} className="login-button">
                Đăng nhập
              </Link>
            )}
          </li>
        </ul>
      </nav>

      <div className="burger-open"></div>

      {/* Phần nội dung hero */}
      <div className="qairline-hero-content">
        <h1>Q Airline</h1>
        <p>
          Chào mừng bạn đến với Q Airline - đồng hành cùng bạn trên mọi hành trình bay an toàn và tiện nghi.<br />
          Khám phá những trải nghiệm tuyệt vời và ưu đãi hấp dẫn chỉ có tại Q Airline!
        </p>
        <button
          className="hero-cta-button"
          onClick={() => navigate("/tin-tuc", { state: { scrollToNews: true } })}
        >
          Khám phá các ưu đãi tốt nhất
        </button>
      </div>
    </header>
  );
};

export default memo(Header);