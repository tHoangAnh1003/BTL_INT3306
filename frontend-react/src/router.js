import { Route, Routes, useLocation } from "react-router-dom";
import HomePage from "./pages/users/HomePage/home";
import { ADMIN_PATH, ROUTERS } from "./utils/router-config";
import MasterLayout from "./pages/theme/masterLayout/masterLayout";
import MasterAdLayout from "./pages/theme/masterAdLayout/masterAdLayout";
import LoginPage from "./pages/admin/loginPage/loginPage";
import SignupPage from "./pages/admin/signupPage/signupPage";
import BookingPage from "./pages/users/Booking/booking";
import FlightInfoPage from "./pages/users/FlighInfomation/flightInfo";
import PostPage from "./pages/admin/postPage/postPage";
import BookedPage from "./pages/users/BookedPage/bookedPage";
import NewsPage from "./pages/users/NewsPage/newsPage";
import AircraftPage from "./pages/admin/aircraftPage/aircraftPage";
import FlightPage from "./pages/admin/flightPage/flightPage";
import BookingStatsPage from "./pages/admin/bookingStatsPage/bookingStatsPage";
import DelayFlightPage from "./pages/admin/delayFlightPage/delayFlightPage";
import ProfilePage from "./pages/users/ProfilePage/profilePage";

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
      path: ROUTERS.USER.FLIGHT_INFO,
      component: <FlightInfoPage />
    },
    {
      path: ROUTERS.USER.BOOKED_FLIGHTS,
      component: <BookedPage />
    },
    {
      path: ROUTERS.USER.NEWS,
      component: <NewsPage />
    },
    {
      path: ROUTERS.USER.PROFILE,
      component: <ProfilePage />
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
    },
    {
      path: ROUTERS.ADMIN.AIRCRAFT,
      component: <AircraftPage />
    },
    {
      path: ROUTERS.ADMIN.FLIGHTS,
      component: <FlightPage />
    },
    {
      path: ROUTERS.ADMIN.STATISTICS,
      component: <BookingStatsPage />
    },
    {
      path: ROUTERS.ADMIN.DELAY,
      component: <DelayFlightPage />
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

  // Nếu đúng route /ho-so thì render ProfilePage không bọc masterlayout
  if (location.pathname === ROUTERS.USER.PROFILE) {
    return (
      <Routes>
        <Route path={ROUTERS.USER.PROFILE} element={<ProfilePage />} />
      </Routes>
    );
  }

  return isAdminRouters ? renderAdminRouter() : renderUserRouter();
};

export default RouterCustom;