import { memo } from "react";
import { useLocation } from "react-router-dom";
import AdminSidebar from "../../../components/adminSideBar/adminSideBar";
import { ROUTERS } from "../../../utils/router-config";

const loginPaths = [ROUTERS.ADMIN.LOGIN, ROUTERS.ADMIN.REGISTER];

const MasterAdLayout = ({ children, ...props }) => {
  const location = useLocation();
  const hideSidebar = loginPaths.includes(location.pathname);

  return (
    <div {...props} style={{ display: "flex" }}>
      {!hideSidebar && <AdminSidebar />}
      <div style={{ flex: 1, marginLeft: !hideSidebar ? 70 : 0 }}>
        {children}
      </div>
    </div>
  );
};

export default memo(MasterAdLayout);