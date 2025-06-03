import { useNavigate } from "react-router-dom";
import { ROUTERS } from "../../utils/router-config";
import { FaPlane, FaRegNewspaper, FaChartBar, FaClock, FaTools } from "react-icons/fa";
import { MdAirplanemodeActive } from "react-icons/md";
import "./adminSideBar.scss";

const sidebarItems = [
  {
    icon: <FaRegNewspaper />,
    label: "Đăng tin",
    path: ROUTERS.ADMIN.POST,
  },
  {
    icon: <FaPlane />,
    label: "Chuyến bay",
    path: ROUTERS.ADMIN.FLIGHTS,
  },
  {
    icon: <MdAirplanemodeActive />,
    label: "Tàu bay",
    path: ROUTERS.ADMIN.AIRCRAFT,
  },
  {
    icon: <FaChartBar />,
    label: "Thống kê",
    path: ROUTERS.ADMIN.STATISTICS,
  },
  {
    icon: <FaClock />,
    label: "Delay",
    path: ROUTERS.ADMIN.DELAY,
  },
];

const AdminSidebar = () => {
  const navigate = useNavigate();

  return (
    <div className="admin-sidebar">
      {sidebarItems.map((item, idx) => (
        <button
          key={idx}
          className="sidebar-btn"
          title={item.label}
          onClick={() => navigate(item.path)}
        >
          {item.icon}
          <span className="sidebar-label">{item.label}</span>
        </button>
      ))}
    </div>
  );
};

export default AdminSidebar;