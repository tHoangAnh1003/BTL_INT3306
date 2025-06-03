import { memo, useEffect, useState } from "react";
import { useLocation } from "react-router-dom";
import "./newsPage.scss";

const NewsPage = () => {
  const [news, setNews] = useState([]);
  const location = useLocation();

  useEffect(() => {
    if (location.state?.scrollToNews) {
      const el = document.getElementById("news-main-content");
      if (el) {
        el.scrollIntoView({ behavior: "smooth" });
      }
    }
  }, [location.state]);

  useEffect(() => {
 
    // fetch("http://localhost:8081/api/news")
    //   .then(res => res.json())
    //   .then(data => setNews(data));
    setNews([
      {
        id: 1,
        title: "Giới thiệu về Q Airlines",
        content: "Q Airlines là hãng hàng không hiện đại, an toàn và tiện nghi hàng đầu Việt Nam...",
        date: "2025-06-01",
        type: "Giới thiệu",
      },
      {
        id: 2,
        title: "Khuyến mại hè 2025",
        content: "Giảm giá 30% cho tất cả các chuyến bay nội địa từ 01/06 đến 31/08/2025.",
        date: "2025-05-25",
        type: "Khuyến mại",
      },
      {
        id: 3,
        title: "Thông báo lịch bay mới",
        content: "Q Airlines bổ sung thêm các chuyến bay Hà Nội - Phú Quốc từ tháng 7/2025.",
        date: "2025-05-20",
        type: "Thông báo",
      },
      {
        id: 4,
        title: "Tin tức: Q Airlines nhận giải thưởng dịch vụ xuất sắc",
        content: "Q Airlines vừa được vinh danh tại lễ trao giải thưởng hàng không châu Á.",
        date: "2025-05-15",
        type: "Tin tức",
      },
    ]);
  }, []);

  return (
    <div className="news-page-wrapper" id="news-main-content">
      <h2>Giới thiệu & Tin tức Q Airlines</h2>
      <div className="news-list">
        {news.map(item => (
          <div className="news-item" key={item.id}>
            <div className="news-type">{item.type}</div>
            <h3>{item.title}</h3>
            <div className="news-date">{item.date}</div>
            <p>{item.content}</p>
          </div>
        ))}
      </div>
    </div>
  );
};

export default memo(NewsPage);