import { memo, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import logo from "../../../assets/imgs/logo-removebg-preview.png";
import { ROUTERS } from "../../../utils/router-config";
import "../loginPage/loginPage.scss";

const SignupPage = () => {
  const navigate = useNavigate();
  const [form, setForm] = useState({ username: "", email: "", passwordHash: "" });
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  const handleChange = (e) =>
    setForm({ ...form, [e.target.name]: e.target.value });

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");
    setSuccess("");
    try {
      const res = await fetch("http://localhost:8081/api/users/register", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(form),
      });
      const data = await res.json();
      if (!res.ok) {
        setError(data.message || "Đăng ký thất bại");
        return;
      }
      setSuccess("Đăng ký thành công! Vui lòng đăng nhập.");
      setTimeout(() => navigate(ROUTERS.ADMIN.LOGIN), 1500);
    } catch (err) {
      setError("Lỗi kết nối máy chủ");
    }
  };

  return (
    <div className="login-wrapper">
      <header className="login-header">
        <div
          style={{ display: "flex", alignItems: "center", cursor: "pointer", gap: 8 }}
          onClick={() => navigate("/")}
        >
          <img src={logo} alt="logo" />
          <h1 style={{ margin: 0 }}>Q Airlines</h1>
        </div>
      </header>

      <form className="login-card" onSubmit={handleSubmit}>
        <h2>ĐĂNG KÝ</h2>

        <label htmlFor="username">Tên đăng nhập</label>
        <input
          id="username"
          name="username"
          type="text"
          placeholder="Tên đăng nhập"
          value={form.username}
          onChange={handleChange}
          required
        />

        <label htmlFor="email">Gmail</label>
        <input
          id="email"
          name="email"
          type="email"
          placeholder="abc@gmail.com"
          value={form.email}
          onChange={handleChange}
          required
        />

        <label htmlFor="passwordHash">Mật khẩu</label>
        <input
          id="passwordHash"
          name="passwordHash"
          type="passwordHash"
          placeholder="••••••••"
          value={form.passwordHash}
          onChange={handleChange}
          required
        />

        {error && <div className="login-error">{error}</div>}
        {success && <div className="login-success">{success}</div>}

        <button type="submit" className="btn-submit">
          Đăng ký
        </button>

        <div className="login-footer">
          <p>Đã có tài khoản?</p>
          <Link to={ROUTERS.ADMIN.LOGIN}>Đăng nhập tại đây</Link>
        </div>
      </form>
    </div>
  );
};

export default memo(SignupPage);