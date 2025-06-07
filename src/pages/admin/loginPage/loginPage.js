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
      console.log(form);
      if (!res.ok) {
        const data = await res.json();
        setError(data.message || "Đăng nhập thất bại");
        return;
      }
      const data = await res.json();
      console.log("Booking statistics data:", data);
      localStorage.setItem("accessToken", data.accessToken);
      localStorage.setItem("refreshToken", data.refreshToken);

      // Decode token và lấy role
      let role = "";
      try {
        const decoded = jwtDecode(data.accessToken);
        console.log("Decoded accessToken:", decoded);
        role = decoded.role;
        // Lưu username/email nếu muốn
        localStorage.setItem("username", decoded.email || decoded.sub || "");
        window.dispatchEvent(new Event("storage"));
      } catch (err) {
        console.log("Không thể decode token", err);
      }

      // Điều hướng theo role
      if (role && role.toLowerCase() === "admin") {
        navigate(ROUTERS.ADMIN.POST || "/admin/post");
      } else {
        navigate("/");
      }
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