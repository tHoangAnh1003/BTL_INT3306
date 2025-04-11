import {memo} from "react";

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
const HomePage  = () => {
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
        {/* <div className="news-grid">
          <div className="news-item">
            <img src={news1} alt="Ưu đãi hội viên" />
            <p>Ưu đãi hội viên</p>
          </div>
          <div className="news-item">
            <img src={news2} alt="Ưu đãi đối tác" />
            <p>Ưu đãi đối tác</p>
          </div>
          <div className="news-item">
            <img src={news3} alt="Đặc quyền hạng Phổ thông" />
            <p>Đặc quyền hạng Phổ thông Đặc biệt</p>
          </div>
          <div className="news-item">
            <img src={news4} alt="Ứng dụng di động" />
            <p>Ứng dụng di động</p>
          </div>
          <div className="news-item">
            <img src={news5} alt="Trung tâm trợ giúp" />
            <p>Trung tâm trợ giúp</p>
          </div>
          <div className="news-item">
            <img src={news6} alt="Khác" />
            <p>Khác</p>
          </div>
        </div> */}
      </section>

      {/* PHẦN CHUYẾN BAY PHỔ BIẾN */}
      <section className="popular-flights">
        <h2>Khám Phá Các Chuyến Bay Phổ Biến Nhất Của Chúng Tôi</h2>

        {/* Thanh filter */}
        <div className="flight-filters">
          <button>Chọn tuyến đường</button>
          <button>Ngân sách</button>
          <button>XÓA</button>
        </div>

        <div className="flight-list">
          <div className="flight-card">
            <img src={flightImg1} alt="Hà Nội đến TP. Hồ Chí Minh" />
            <div className="flight-info">
              <h3>Hà Nội đến TP. Hồ Chí Minh</h3>
              <p>14/04/2025</p>
              <p>$10.99</p>
            </div>
          </div>

          <div className="flight-card">
            <img src={flightImg2} alt="TP.Hồ Chí Minh đến Hà Nội" />
            <div className="flight-info">
              <h3>TP.Hồ Chí Minh đến Hà Nội</h3>
              <p>21/04/2025</p>
              <p>$10.99</p>
            </div>
          </div>
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