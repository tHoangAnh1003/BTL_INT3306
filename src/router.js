import { Route, Routes, useLocation } from "react-router-dom";
import HomePage from "./pages/users/HomePage/home";
import { ADMIN_PATH, ROUTERS } from "./utils/router-config";
import MasterLayout from "./pages/theme/masterLayout/masterLayout";
import MasterAdLayout from "./pages/theme/masterAdLayout/masterAdLayout";
import LoginPage from "./pages/admin/loginPage/loginPage";
import SignupPage from "./pages/admin/signupPage/signupPage";
import BookingPage from "./pages/users/Booking/booking";
import ManageBookingPage from "./pages/users/ManageBookingPage/manageBooking";
import CheckInPage from "./pages/users/CheckinPage/checkIn";
import FlightInfoPage from "./pages/users/FlighInfomation/flightInfo";
import PostPage from "./pages/admin/postPage/postPage";

const renderUserRouter = () => {
  const userRouters = [
    {
      path: ROUTERS.USER.HOME,
      component: <HomePage />
    },
    {
      path: ROUTERS.USER.BOOKING,
      component: <BookingPage />
    },
    {
      path: ROUTERS.USER.MANAGE,
      component: <ManageBookingPage />
    },
    {
      path: ROUTERS.USER.CHECK_IN,
      component: <CheckInPage />
    },
    {
      path: ROUTERS.USER.INFO,
      component: <FlightInfoPage />
    }
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
    },
    {
      path: ROUTERS.ADMIN.POST,
      component: <PostPage />
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