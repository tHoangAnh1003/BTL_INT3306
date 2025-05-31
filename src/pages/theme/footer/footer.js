import { memo } from "react";
import "./footer.scss"; // Import CSS file for styling

const Footer = () => {
  return (
    <footer className="qairline-footer">
      <div className="footer-content">
        {/* Thương hiệu và liên kết */}
        <div className="footer-brand-section">
          <h3 className="footer-brand">Q Airlines</h3>
          <ul className="footer-links">
            <li>Điều khoản và điều kiện</li>
            <li>Chính sách bảo mật</li>
            <li>Hỗ trợ khách hàng</li>
            <li>Thông tin về hành lý</li>
          </ul>
        </div>

        {/* Thông tin liên hệ và mạng xã hội */}
        <div className="footer-contact">
          <div className="contact-info">
            <p>Hotline: 1900-1234</p>
            <p>Email: support@qairlines.com</p>
          </div>
          <div className="social-icons">
            <a href="#!" aria-label="Facebook">
              <i className="fab fa-facebook-f"></i>
            </a>
            <a href="#!" aria-label="LinkedIn">
              <i className="fab fa-linkedin-in"></i>
            </a>
            <a href="#!" aria-label="YouTube">
              <i className="fab fa-youtube"></i>
            </a>
            <a href="#!" aria-label="Instagram">
              <i className="fab fa-instagram"></i>
            </a>
          </div>
        </div>

        {/* Các cột nội dung */}
        <div className="footer-columns">
          <div className="footer-column">
            <h4>Thông tin</h4>
            <p>Giới thiệu về chúng tôi</p>
            <p>Đối tác</p>
            <p>Tuyển dụng</p>
          </div>
          <div className="footer-column">
            <h4>Dịch vụ</h4>
            <p>Đặt vé máy bay</p>
            <p>Quản lý đặt chỗ</p>
            <p>Hành lý và phí dịch vụ</p>
          </div>
          <div className="footer-column">
            <h4>Hỗ trợ</h4>
            <p>Câu hỏi thường gặp</p>
            <p>Liên hệ</p>
            <p>Chính sách hoàn vé</p>
          </div>
        </div>
      </div>
    </footer>
  );
};

export default memo(Footer);