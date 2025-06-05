import { memo, useState, useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";

import airlineBackgound from "../../../assets/imgs/background.jpg";
import airlineLogo from "../../../assets/imgs/logo-removebg-preview.png";

import "./header.scss";
import { ROUTERS } from "../../../utils/router-config";

const Header = () => {
  const navigate = useNavigate();
  const [isMenuOpen, setIsMenuOpen] = useState(false);
  const [username, setUsername] = useState(localStorage.getItem("username") || "");

  useEffect(() => {
    // Lắng nghe sự thay đổi của localStorage (nếu có logout ở nơi khác)
    const handleStorage = () => setUsername(localStorage.getItem("username") || "");
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

  useEffect(() => {
    const userData = JSON.parse(localStorage.getItem("user"));
    if (userData && userData.username) {
      localStorage.setItem("username", userData.username);
    }
  }, []);

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
          <li>
            {username ? (
              <span className="login-avatar" title={username}>
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
                  onClick={() => navigate("/ho-so")}
                />
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

      {/* Phần nội dung hero */}
      <div className="qairline-hero-content">
        <h1>Q Airline</h1>
        <p>
          Hổ sa cơ cũng không đến lượt chó mèo lên tiếng.
          <br />
          Rồng mắc cạn cũng không ngang hàng với tép tôm.
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