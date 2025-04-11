import { memo, useState } from "react";

import airlineBackgound from "../../../assets/imgs/background.jpg";
import airlineLogo from "../../../assets/imgs/logo-removebg-preview.png"; // Import logo image

import "./header.scss"; // Import CSS file for styling

import { ROUTERS } from "../../../utils/router-config";
import { Link } from "react-router-dom";

const Header = () => {

  const [menus, setMenus] = useState([
    { name: "Trang chủ", path: ROUTERS.USER.HOME },
    { name: "Mua vé", path: ROUTERS.USER.BOOKING },
    { name: "Quản lý đặt chỗ", path: ROUTERS.USER.MANAGE },
    { name: "Làm thủ tục", path: ROUTERS.USER.CHECK_IN },
  ]);

  return (
    <header
      className="qairline-header"
      style={{ backgroundImage: `url(${airlineBackgound})` }}
    >
      {/* Phần logo và menu điều hướng */}
      <nav className="qairline-navbar">
        <div className="qairline-logo">
          <img src={airlineLogo} alt="Q Airlines Logo" className="logo-img" />
          <span>Q Airlines</span>
        </div>
        <ul className="qairline-nav-menu">
          {menus?.map((menu, index) => (
            <li key={index}>
              <Link to={menu.path} className="qairline-nav-link">
                {menu.name}
              </Link>
            </li>
          ))}
          <li>
            <button className="complaint-button">Khiếu nại</button>
            <button className="login-button">Đăng nhập</button>
          </li>
        </ul>
      </nav>

      {/* Phần nội dung hero */}
      <div className="qairline-hero-content">
        <h1>Q Airline</h1>
        <p>
          Hổ sa cơ cũng không đến lượt chó mèo lên tiếng.
          <br />
          Rồng mắc cạn cũng không ngang hàng với tép tôm.
        </p>
        <button className="hero-cta-button">Khám phá các ưu đãi tốt nhất</button>
      </div>
    </header>
  );
};

export default memo(Header);