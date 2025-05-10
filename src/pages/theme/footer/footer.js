import {memo} from "react";
import "./footer.scss"; // Import CSS file for styling
const Footer = () => {
  return (
    <footer className="qairline-footer">
      
      <div className="footer-top">
        <h3 className="footer-brand">Q Airlines</h3>
        <ul className="footer-top-links">
          <li>Pháp lý</li>
          <li>Hỗ trợ</li>
          <li>Vận tải Hàng Hóa</li>
          <li>Thông tin hữu ích</li>
        </ul>
      </div>

      
      <div className="footer-bottom">
        <div className="footer-left">
          <div className="site-name">Site name</div>
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
            <h4>Topic</h4>
            <p>Page</p>
            <p>Page</p>
            <p>Page</p>
          </div>
          <div className="footer-column">
            <h4>Topic</h4>
            <p>Page</p>
            <p>Page</p>
            <p>Page</p>
          </div>
          <div className="footer-column">
            <h4>Topic</h4>
            <p>Page</p>
            <p>Page</p>
            <p>Page</p>
          </div>
        </div>
      </div>
    </footer>
  );
};

export default memo(Footer);