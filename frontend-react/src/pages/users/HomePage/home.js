import { memo, useState, useEffect, useRef } from "react";
import { useNavigate } from "react-router-dom";

// import images for the services and news sections
import imgService1 from "../../../assets/imgs/hanh-ly-removebg-preview.png";
import imgService2 from "../../../assets/imgs/nang-hang-chon-cho-removebg-preview.png";
import imgService3 from "../../../assets/imgs/mua-sam-removebg-preview.png";
import imgService4 from "../../../assets/imgs/khach-san-removebg-preview.png";
import imgService5 from "../../../assets/imgs/bao-hiem-removebg-preview.png";
import imgService6 from "../../../assets/imgs/dich-vu-khac-removebg-preview.png";

// import images for the flight section
import flightImg1 from "../../../assets/imgs/hanoi.jpg";
import flightImg2 from "../../../assets/imgs/hochiminhcity.jpg";
// import images for the guide section
import guide1 from "../../../assets/imgs/giay-to-bay-noi-dia.jpg";
import guide2 from "../../../assets/imgs/dat-ve-online.jpg";
import guide3 from "../../../assets/imgs/lam-thu-tuc.jpg";
import guide4 from "../../../assets/imgs/chon-truoc-cho-ngoi.jpg";
import guide5 from "../../../assets/imgs/hanh-ly-xach-tay.jpg";
import guide6 from "../../../assets/imgs/gap-van-de-voi-hanh-ly.jpg";

import "./home.scss"; // Import CSS file for styling
import NewsSlider from "../../../components/slider";

// Giả lập danh sách sân bay

const HomePage = () => {
  const [airports, setAirports] = useState([]);
  const [filters, setFilters] = useState({ route: "", budget: "" });
  const [flights] = useState([
    { id: 1, route: "Hà Nội đến TP. Hồ Chí Minh", price: 1200000, img: flightImg1 },
    { id: 2, route: "TP.Hồ Chí Minh đến Hà Nội", price: 1099000, img: flightImg2 },
    { id: 3, route: "Hà Nội đến Đà Nẵng", price: 1599000, img: flightImg1 },
    { id: 4, route: "Đà Nẵng đến Hà Nội", price: 1299000, img: flightImg2 },
  ]);
  const [budgetInput, setBudgetInput] = useState("");
  const navigate = useNavigate();

  // Lấy danh sách tuyến đường duy nhất từ flights
  const routeOptions = Array.from(new Set(flights.map(f => f.route)));

  const handleFilterChange = (key, value) => {
    setFilters((prev) => ({ ...prev, [key]: value }));
  };

  const handleBudgetChange = (e) => {
    let raw = e.target.value.replace(/[^0-9]/g, "");
    setFilters((prev) => ({ ...prev, budget: raw }));
    if (raw) {
      setBudgetInput(Number(raw).toLocaleString("vi-VN"));
    } else {
      setBudgetInput("");
    }
  };

  const filteredFlights = flights.filter((flight) => {
    const matchesRoute = filters.route ? flight.route === filters.route : true;
    const matchesBudget = filters.budget ? flight.price <= parseFloat(filters.budget) : true;
    return matchesRoute && matchesBudget;
  });

  return (
    <div className="homepage-container">
      {/* PHẦN DỊCH VỤ */}
      <section className="services-section">
        <div className="services-grid">
          <div className="service-item">
            <img src={imgService1} alt="Hành lý trả trước" />
            <p>Hành lý trả trước</p>
          </div>
          <div className="service-item">
            <img src={imgService2} alt="Nâng hạng & Chọn chỗ" />
            <p>Nâng hạng & Chọn chỗ</p>
          </div>
          <div className="service-item">
            <img src={imgService3} alt="Mua sắm" />
            <p>Mua sắm</p>
          </div>
          <div className="service-item">
            <img src={imgService4} alt="Khách sạn & Tour" />
            <p>Khách sạn & Tour</p>
          </div>
          <div className="service-item">
            <img src={imgService5} alt="Bảo hiểm" />
            <p>Bảo hiểm</p>
          </div>
          <div className="service-item">
            <img src={imgService6} alt="Dịch vụ khác" />
            <p>Dịch vụ khác</p>
          </div>
        </div>
      </section>

      {/* PHẦN TIN TỨC */}
      <section className="news-section">
        <div className="news-header">
          <h2>Tin tức:</h2>
          <p>Khám phá các tin tức khu vực, thế giới</p>
          <button>Xem thêm</button>
        </div>
        <NewsSlider />
        {/* Danh sách 6 news item */}
      </section>

      {/* PHẦN CHUYẾN BAY PHỔ BIẾN */}
      <section className="popular-flights">
        <h2>Khám Phá Các Chuyến Bay Phổ Biến Nhất Của Chúng Tôi</h2>
        <div className="flight-filters" style={{ position: "relative" }}>
          <select
            value={filters.route}
            onChange={e => handleFilterChange("route", e.target.value)}
            style={{ minWidth: 180, marginRight: 12 }}
          >
            <option value="">Chọn tuyến đường</option>
            {routeOptions.map(route => (
              <option key={route} value={route}>{route}</option>
            ))}
          </select>
          <input
            type="text"
            value={budgetInput}
            placeholder="Ngân sách (VND)"
            onChange={handleBudgetChange}
            inputMode="numeric"
            min={0}
          />
          <button
            onClick={() => {
              setFilters((prev) => ({ ...prev, budget: "" }));
              setBudgetInput("");
            }}
          >
            XÓA
          </button>
        </div>

        <div className="flight-list">
          {filteredFlights.map((flight) => (
            <div
              className="flight-card"
              key={flight.id}
              style={{ cursor: "pointer" }}
            >
              <img src={flight.img} alt={flight.route} />
              <div className="flight-info">
                <h3>{flight.route}</h3>
                <p>{flight.date}</p>
                <p>{flight.price.toLocaleString("vi-VN")} ₫</p>
              </div>
            </div>
          ))}
        </div>

        <button className="show-more">XEM THÊM</button>
        <p className="flight-note">
          *Giá vé hiển thị được thu thập trong vòng 48 giờ và có thể không còn hiệu lực tại thời điểm đặt chỗ.
          Chúng tôi có thể thu thêm phí và lệ phí cho một số sản phẩm và dịch vụ.
        </p>
      </section>

      {/* PHẦN HƯỚNG DẪN / DỊCH VỤ KHÁC */}
      <section className="guide-section">
        <div className="guide-grid">
          <div className="guide-item">
            <img src={guide1} alt="Giấy tờ bay nội địa" />
            <p>Giấy tờ bay nội địa</p>
          </div>
          <div className="guide-item">
            <img src={guide2} alt="Đặt vé máy bay online" />
            <p>Đặt vé máy bay online</p>
          </div>
          <div className="guide-item">
            <img src={guide3} alt="Làm thủ tục trực tuyến" />
            <p>Làm thủ tục trực tuyến</p>
          </div>
          <div className="guide-item">
            <img src={guide4} alt="Chọn trước chỗ ngồi" />
            <p>Chọn trước chỗ ngồi</p>
          </div>
          <div className="guide-item">
            <img src={guide5} alt="Hành lý xách tay" />
            <p>Hành lý xách tay</p>
          </div>
          <div className="guide-item">
            <img src={guide6} alt="Gặp vấn đề với hành lý" />
            <p>Gặp vấn đề với hành lý</p>
          </div>
        </div>
      </section>
    </div>
  );
};

export default memo(HomePage);