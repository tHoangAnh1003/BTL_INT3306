import { memo, useState, useEffect, useRef } from "react";
import { Link, useNavigate } from "react-router-dom";

import airlineBackgound from "../../../assets/imgs/background.jpg";
import airlineLogo from "../../../assets/imgs/logo-removebg-preview.png";

import "./header.scss";
import { ROUTERS } from "../../../utils/router-config";

const Header = () => {
  const navigate = useNavigate();
  const [isMenuOpen, setIsMenuOpen] = useState(false);
  const [username, setUsername] = useState(localStorage.getItem("username") || "");
  const avatarRef = useRef(null);

  useEffect(() => {
    const fetchUser = async () => {
      const token = localStorage.getItem("accessToken");
      if (!token) {
        setUsername("");
        localStorage.removeItem("username");
        return;
      }
      const localUsername = localStorage.getItem("username");
      if (localUsername) {
        setUsername(localUsername);
        return;
      }
      try {
        const res = await fetch("http://localhost:8081/api/users/me", {
          headers: { Authorization: `Bearer ${token}` },
        });
        if (res.ok) {
          const data = await res.json();
          if (data.role && (data.role === "CUSTOMER" || data.role.toLowerCase() === "customer")) {
            const name = data.username || data.fullName || data.fullname || "";
            setUsername(name);
            localStorage.setItem("username", name);
          } else {
            setUsername("");
            localStorage.removeItem("username");
          }
        } else {
          setUsername("");
          localStorage.removeItem("username");
        }
      } catch {
        setUsername("");
        localStorage.removeItem("username");
      }
    };
    fetchUser();
    const handleStorage = () => {
      setUsername(localStorage.getItem("username") || "");
      fetchUser();
    };
    window.addEventListener("storage", handleStorage);
    return () => window.removeEventListener("storage", handleStorage);
  }, []);

  const [menus] = useState([
    { name: "Trang chủ", path: ROUTERS.USER.HOME },
    { name: "Mua vé", path: ROUTERS.USER.BOOKING },
    { name: "Quản lý đặt chỗ", path: ROUTERS.USER.BOOKED_FLIGHTS },
    { name: "Thông tin chuyến bay", path: ROUTERS.USER.FLIGHT_INFO },
  ]);

  const toggleMenu = () => {
    setIsMenuOpen(!isMenuOpen);
  };

  const handleLogout = () => {
    localStorage.removeItem("accessToken");
    localStorage.removeItem("refreshToken");
    localStorage.removeItem("username");
    localStorage.removeItem("user");
    setUsername("");
    window.dispatchEvent(new Event("storage"));
    navigate("/");
  };

  return (
    <header
      className="qairline-header"
      style={{ backgroundImage: `url(${airlineBackgound})` }}
    >
      <div className={`burger-menu-overlay ${isMenuOpen ? " active" : ""}`}
        onClick={() => setIsMenuOpen(false)}
      />
      <div className="burger" onClick={toggleMenu}>
        <span />
        <span />
      </div>

      <div className={`burger-menu-wrapper ${isMenuOpen ? "open" : ""}`}>
        <div className="qairline-logo">
          <h1>QAirlines</h1>
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
            {username && (
              <li>
                <a
                  href="#"
                  className="qairline-nav-link"
                  onClick={e => {
                    e.preventDefault();
                    setIsMenuOpen(false);
                    navigate("/ho-so", { replace: true });
                  }}
                >
                  Xem hồ sơ
                </a>
              </li>
            )}
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

      <nav className="qairline-navbar">
        <div className="qairline-logo">
          <img src={airlineLogo} alt="QAirlines Logo" className="logo-img" />
          <span>QAirlines</span>
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

          <li>
            {username ? (
              <span
                className="login-button"
                ref={avatarRef}
                style={{ cursor: "pointer" }}
                onClick={() => navigate("/ho-so")}
              >
                {username.charAt(0).toUpperCase()}
              </span>
            ) : (
              <Link to={ROUTERS.ADMIN.LOGIN} className="login-button">
                Đăng nhập
              </Link>
            )}
          </li>
        </ul>
      </nav>

      <div className="burger-open"></div>

      <div className="qairline-hero-content">
        <h1>QAirline</h1>
        <p>
          Chào mừng bạn đến với QAirline - đồng hành cùng bạn trên mọi hành trình bay an toàn và tiện nghi.<br />
          Khám phá những trải nghiệm tuyệt vời và ưu đãi hấp dẫn chỉ có tại QAirline!
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