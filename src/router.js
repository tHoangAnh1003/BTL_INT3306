import { Route, Routes, useLocation } from "react-router-dom";
import HomePage from "./pages/users/HomePage/home";
import { ADMIN_PATH, ROUTERS } from "./utils/router-config";
import MasterLayout from "./pages/theme/masterLayout/masterLayout";
import MasterAdLayout from "./pages/theme/masterAdLayout/masterAdLayout";
import LoginPage from "./pages/admin/loginPage/loginPage";
import SignupPage from "./pages/admin/signupPage/signupPage";


const renderUserRouter = () => {
  const userRouters = [
    {
      path: ROUTERS.USER.HOME,
      component: <HomePage />
    },
    // {
    //   path: ROUTERS.USER.BOOKING,
    //   component: <BookingPage />
    // },
    // {
    //   path: ROUTERS.USER.MANAGE,
    //   component: <ManageBookingPage />
    // },
    // {
    //   path: ROUTERS.USER.CHECK_IN,
    //   component: <CheckInPage />
    // }
  ];
  return (
    <MasterLayout>
      <Routes>
        {
          userRouters.map((item, key) => (
            <Route key={key} path={item.path} element={item.component} />
          ))
        }
      </Routes>
    </MasterLayout>
  );
};

const renderAdminRouter = () => {
  const adminRouters = [
    {
      path: ROUTERS.ADMIN.LOGIN,
      component: <LoginPage />
    },
    {
      path: ROUTERS.ADMIN.REGISTER,
      component: <SignupPage />
    }
  ];
  return (
    <MasterAdLayout>
      <Routes>
        {
          adminRouters.map((item, key) => (
            <Route key={key} path={item.path} element={item.component} />
          ))
        }
      </Routes>
    </MasterAdLayout>
  );
};

const RouterCustom = () => {
  const location = useLocation();
  const isAdminRouters = location.pathname.startsWith(ADMIN_PATH);
  return isAdminRouters ? renderAdminRouter() : renderUserRouter();
};

export default RouterCustom;