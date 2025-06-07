import { memo, useEffect, useState } from "react";
import { useLocation } from "react-router-dom";
import "./newsPage.scss";

const PAGE_SIZE = 4; // Số tin mỗi trang

const NewsPage = () => {
  const [news, setNews] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [currentPage, setCurrentPage] = useState(1);
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
    const fetchNews = async () => {
      setLoading(true);
      setError("");
      try {
        const res = await fetch("http://localhost:8081/api/news");
        if (!res.ok) throw new Error("Không thể lấy dữ liệu tin tức");
        const data = await res.json();
        setNews(data);
      } catch (err) {
        setError(err.message || "Lỗi không xác định");
        setNews([]);
      }
      setLoading(false);
    };
    fetchNews();
  }, []);

  // Tính toán phân trang
  const totalPages = Math.ceil(news.length / PAGE_SIZE);
  const pagedNews = news.slice((currentPage - 1) * PAGE_SIZE, currentPage * PAGE_SIZE);

  const handlePageChange = (page) => {
    setCurrentPage(page);
    window.scrollTo({ top: 0, behavior: "smooth" });
  };

  return (
    <div className="news-page-wrapper" id="news-main-content">
      <h2>Giới thiệu & Tin tức Q Airlines</h2>
      {loading && <div>Đang tải dữ liệu...</div>}
      {error && <div style={{ color: "red" }}>{error}</div>}
      <div className="news-list">
        {pagedNews.map(item => (
          <div className="news-item" key={item.newsId || item.id}>
            <div className="news-type">{item.type || item.newsType}</div>
            <h3>{item.title}</h3>
            <div className="news-date">{item.date || item.createdAt?.slice(0,10)}</div>
            <p>{item.content}</p>
          </div>
        ))}
      </div>
      {/* Phân trang */}
      {totalPages > 1 && (
        <div className="pagination">
          {Array.from({ length: totalPages }, (_, idx) => (
            <button
              key={idx + 1}
              className={currentPage === idx + 1 ? "active" : ""}
              onClick={() => handlePageChange(idx + 1)}
            >
              {idx + 1}
            </button>
          ))}
        </div>
      )}
    </div>
  );
};

export default memo(NewsPage);