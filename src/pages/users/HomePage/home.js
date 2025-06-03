import { memo, useState, useEffect, useRef } from "react";

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

// Giả lập danh sách sân bay (bạn nên fetch từ API thực tế)


const HomePage = () => {
  const [airports, setAirports] = useState([]);
  const [filters, setFilters] = useState({ route: "", budget: "" });
  const [flights] = useState([
    { id: 1, route: "Hà Nội đến TP. Hồ Chí Minh", date: "14/04/2025", price: 10.99, img: flightImg1 },
    { id: 2, route: "TP.Hồ Chí Minh đến Hà Nội", date: "21/04/2025", price: 10.99, img: flightImg2 },
    { id: 3, route: "Hà Nội đến Đà Nẵng", date: "25/04/2025", price: 15.99, img: flightImg1 },
    { id: 4, route: "Đà Nẵng đến Hà Nội", date: "28/04/2025", price: 12.99, img: flightImg2 },
  ]);
  const [showRoutePopup, setShowRoutePopup] = useState(false);
  const [routeInput, setRouteInput] = useState({ from: "", to: "" });
  const [suggestions, setSuggestions] = useState({ from: [], to: [] });
  const popupRef = useRef();
  const btnRef = useRef();

  const handleFilterChange = (key, value) => {
    setFilters((prev) => ({ ...prev, [key]: value }));
  };

  const filteredFlights = flights.filter((flight) => {
    const matchesRoute = filters.route ? flight.route.includes(filters.route) : true;
    const matchesBudget = filters.budget ? flight.price <= parseFloat(filters.budget) : true;
    return matchesRoute && matchesBudget;
  });

  // Fetch airports when popup opens
  useEffect(() => {
    if (showRoutePopup && airports.length === 0) {
      fetch("/api/airports")
        .then(res => res.json())
        .then(data => setAirports(data));
    }
  }, [showRoutePopup, airports.length]);

  // Đóng popup khi click ra ngoài
  useEffect(() => {
    if (!showRoutePopup) return;
    const handleClick = (e) => {
      if (
        popupRef.current &&
        !popupRef.current.contains(e.target) &&
        btnRef.current &&
        !btnRef.current.contains(e.target)
      ) {
        setShowRoutePopup(false);
      }
    };
    document.addEventListener("mousedown", handleClick);
    return () => document.removeEventListener("mousedown", handleClick);
  }, [showRoutePopup]);

  // Gợi ý sân bay khi nhập
  const handleAirportInput = (field, value) => {
    setRouteInput((prev) => ({ ...prev, [field]: value }));
    setSuggestions((prev) => ({
      ...prev,
      [field]: airports.filter((a) =>
        a.name.toLowerCase().includes(value.toLowerCase()) ||
        a.code.toLowerCase().includes(value.toLowerCase()) ||
        (a.city && a.city.toLowerCase().includes(value.toLowerCase()))
      )
    }));
  };

  // Áp dụng filter
  const handleApplyRoute = () => {
    setFilters({
      ...filters,
      route: routeInput.from && routeInput.to
        ? `${routeInput.from} đến ${routeInput.to}`
        : ""
    });
    setShowRoutePopup(false);
  };

  // Đặt lại filter
  const handleResetRoute = () => {
    setRouteInput({ from: "", to: "" });
    setFilters({ ...filters, route: "" });
    setShowRoutePopup(false);
  };

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
          <button
            type="button"
            ref={btnRef}
            onClick={() => setShowRoutePopup((v) => !v)}
            style={{ minWidth: 150 }}
            id="route-btn"
          >
            {filters.route ? filters.route : "Chọn tuyến đường"}
          </button>
          <input
            type="number"
            value={filters.budget}
            placeholder="Ngân sách (USD)"
            onChange={(e) => handleFilterChange("budget", e.target.value)}
          />
          <button onClick={() => setFilters({ route: "", budget: "" })}>XÓA</button>

          {/* Popup chọn tuyến đường */}
          {showRoutePopup && (
            <div className="route-popup" ref={popupRef}>
              <div className="route-inputs">
                <div>
                  <label>Từ</label>
                  <input
                    value={routeInput.from}
                    onChange={e => handleAirportInput("from", e.target.value)}
                    placeholder="Nhập điểm đi"
                    autoFocus
                  />
                  {routeInput.from && (
                    <ul className="suggestions">
                      {suggestions.from.map(a => (
                        <li key={a.code} onClick={() => setRouteInput(r => ({ ...r, from: a.name }))}>
                          <span role="img" aria-label="location">📍</span> {a.name}
                        </li>
                      ))}
                    </ul>
                  )}
                </div>
                <div>
                  <label>Đến</label>
                  <input
                    value={routeInput.to}
                    onChange={e => handleAirportInput("to", e.target.value)}
                    placeholder="Nhập điểm đến"
                  />
                  {routeInput.to && (
                    <ul className="suggestions">
                      {suggestions.to.map(a => (
                        <li key={a.code} onClick={() => setRouteInput(r => ({ ...r, to: a.name }))}>
                          <span role="img" aria-label="location">📍</span> {a.name}
                        </li>
                      ))}
                    </ul>
                  )}
                </div>
              </div>
              <div className="popup-actions">
                <button type="button" onClick={handleResetRoute}>ĐẶT LẠI</button>
                <button type="button" onClick={handleApplyRoute} style={{ color: "#0071c2" }}>ÁP DỤNG</button>
              </div>
            </div>
          )}
        </div>

        <div className="flight-list">
          {filteredFlights.map((flight) => (
            <div className="flight-card" key={flight.id}>
              <img src={flight.img} alt={flight.route} />
              <div className="flight-info">
                <h3>{flight.route}</h3>
                <p>{flight.date}</p>
                <p>${flight.price.toFixed(2)}</p>
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