import { memo, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import logo from "../../../assets/imgs/logo-removebg-preview.png"; // Import logo image
import { ROUTERS } from "../../../utils/router-config";
import "./loginPage.scss"; // Import CSS file for styling

const LoginPage = () => {
  const navigate = useNavigate();
  const [form, setForm] = useState({ email: "", password: "" });

  const handleChange = (e) =>
    setForm({ ...form, [e.target.name]: e.target.value });

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      // Gọi API test quyền admin
      const res = await fetch("http://localhost:8080/api/auth/admin-only", {
        method: "GET",
      });
      if (res.ok) {
        navigate("/admin/dashboard");
      } else {
        alert("Lỗi khi kiểm tra quyền admin!");
      }
    } catch (err) {
      alert("Lỗi khi kiểm tra quyền admin!");
    }
  };

  return (
    <div className="login-wrapper">
      <header className="login-header">
        <img src={logo} alt="logo" />
        <h1>Q Airlines</h1>
      </header>

      <form className="login-card" onSubmit={handleSubmit}>
        <h2>ĐĂNG NHẬP</h2>

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

        <label htmlFor="password">Mật khẩu</label>
        <input
          id="password"
          name="password"
          type="password"
          placeholder="••••••••"
          value={form.password}
          onChange={handleChange}
          required
        />

        <button type="submit" className="btn-submit">
          Đăng nhập
        </button>

        <div className="login-footer">
          <p>*Bạn chưa có tài khoản khách hàng</p>
          <Link to={ROUTERS.ADMIN.REGISTER}>Đăng ký tại đây</Link>
        </div>
      </form>
    </div>
  );
}

export default memo(LoginPage);