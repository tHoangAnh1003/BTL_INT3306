import { memo, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import logo from "../../../assets/imgs/logo-removebg-preview.png";
import { ROUTERS } from "../../../utils/router-config";
import "./loginPage.scss";

const LoginPage = () => {
  const navigate = useNavigate();
  const [form, setForm] = useState({ email: "", password: "" });
  const [error, setError] = useState("");

  const handleChange = (e) =>
    setForm({ ...form, [e.target.name]: e.target.value });

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");
    try {
      const res = await fetch("http://localhost:8081/api/auth/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(form),
      });
      
      if (!res.ok) {
        const data = await res.json();
        setError(data.message || "Đăng nhập thất bại");
        return;
      }
      
      const data = await res.json();
      
      // Debug để kiểm tra response
      console.log("Login response:", data);
      
      // Lưu thông tin vào localStorage
      localStorage.setItem("accessToken", data.accessToken);
      localStorage.setItem("refreshToken", data.refreshToken);
      
      if (data.username) {
        localStorage.setItem("username", data.username);
      }
      
      // Lưu role để sử dụng sau này
      if (data.role) {
        localStorage.setItem("userRole", data.role);
      }
      
      // Logic điều hướng đã sửa
      const userRole = data.role?.toLowerCase();
      
      if (userRole === "admin" || userRole === "staff") {
        // Admin và Staff đều vào trang admin
        navigate(ROUTERS.ADMIN.POST || "/admin/post");
      } else if (userRole === "customer") {
        // Customer về trang chủ dành cho user
        navigate("/");
      } else {
        // Trường hợp role không xác định
        console.warn("Unknown role:", data.role);
        setError("Vai trò người dùng không hợp lệ");
      }
      
    } catch (err) {
      console.error("Login error:", err);
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

        {error && <div className="login-error">{error}</div>}

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
};

export default memo(LoginPage);