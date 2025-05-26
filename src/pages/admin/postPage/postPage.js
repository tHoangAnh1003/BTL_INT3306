import React, { useState } from "react";
import "./postPage.scss";

const PostPage = () => {
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  const [category, setCategory] = useState("news");

  // const handleSubmit = (e) => {
  //   e.preventDefault();
  //   console.log({ title, content, category });
  //   // Gửi dữ liệu lên server hoặc xử lý logic tại đây
  // };

  return (
    <div className="post-page">
      <h1>Đăng Thông Tin</h1>
      <form>
        <div className="form-group">
          <label htmlFor="title">Tiêu đề</label>
          <input
            type="text"
            id="title"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
            required
          />
        </div>
        <div className="form-group">
          <label htmlFor="content">Nội dung</label>
          <textarea
            id="content"
            value={content}
            onChange={(e) => setContent(e.target.value)}
            required
          ></textarea>
        </div>
        <div className="form-group">
          <label htmlFor="category">Danh mục</label>
          <select
            id="category"
            value={category}
            onChange={(e) => setCategory(e.target.value)}
          >
            <option value="news">Tin tức</option>
            <option value="promotion">Khuyến mại</option>
            <option value="announcement">Thông báo</option>
            <option value="introduction">Giới thiệu</option>
          </select>
        </div>
        <button type="submit">Đăng</button>
      </form>
    </div>
  );
};

export default PostPage;