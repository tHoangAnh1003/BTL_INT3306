import { useState } from "react";
import "./postPage.scss";

const PostPage = () => {
  const [form, setForm] = useState({
    title: "",
    type: "news",
    content: "",
  });
  const [success, setSuccess] = useState("");
  const [error, setError] = useState("");

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setSuccess("");
    setError("");

    const token = localStorage.getItem("accessToken"); 

    try {
      const res = await fetch("http://localhost:8081/api/news", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          "Authorization": `Bearer ${token}`
         },
        body: JSON.stringify(form),
      });
      if (!res.ok) {
        const data = await res.json();
        setError(data.message || "Lỗi khi đăng thông tin");
        return;
      }
      setSuccess("Đăng thông tin thành công!");
      setForm({ title: "", type: "news", content: "" });
    } catch {
      setError("Lỗi kết nối máy chủ");
    }
  };

  return (
    <div className="post-page">
      <h2>Đăng thông tin hãng</h2>
      <form onSubmit={handleSubmit} className="post-form">
        <label>
          Tiêu đề:
          <input
            name="title"
            value={form.title}
            onChange={handleChange}
            required
          />
        </label>
        <label>
          Loại thông tin:
          <select name="type" value={form.type} onChange={handleChange}>
            <option value="news">Tin tức</option>
            <option value="promotion">Khuyến mại</option>
            <option value="announcement">Thông báo</option>
            <option value="introduction">Giới thiệu</option>
          </select>
        </label>
        <label>
          Nội dung:
          <textarea
            name="content"
            value={form.content}
            onChange={handleChange}
            rows={6}
            required
          />
        </label>
        <button type="submit">Đăng</button>
        {success && <div className="success">{success}</div>}
        {error && <div className="error">{error}</div>}
      </form>
    </div>
  );
};

export default PostPage;