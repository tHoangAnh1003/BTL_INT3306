export const ADMIN_PATH = "/admin";

export const ROUTERS = {
  USER: {
    HOME: "",
    BOOKING: "dat-ve",
    MANAGE: "quan-ly-dat-ve",
    CHECK_IN: "lam-thu-tuc",
    COMPLAINT: "khieu-nai",
    FLIGHT_INFO: "thong-tin-chuyen-bay",
    FLIGHT_RESULT: "ket-qua-chuyen-bay",
    BOOKED_FLIGHTS: "chuyen-bay-da-dat",
    NEWS: "tin-tuc",
  },
  ADMIN : {
    LOGIN: `${ADMIN_PATH}/dang-nhap`,
    REGISTER: `${ADMIN_PATH}/dang-ky`,
    POST: `${ADMIN_PATH}/dang-thong-tin`,
  },
}