export const ADMIN_PATH = "/admin";

export const ROUTERS = {
  USER: {
    HOME: "",
    BOOKING: "dat-ve",
    CHECK_IN: "lam-thu-tuc",
    COMPLAINT: "khieu-nai",
    FLIGHT_INFO: "thong-tin-chuyen-bay",
    BOOKED_FLIGHTS: "chuyen-bay-da-dat",
    NEWS: "tin-tuc",
    PROFILE: "/ho-so",
  },
  ADMIN : {
    LOGIN: `${ADMIN_PATH}/dang-nhap`,
    REGISTER: `${ADMIN_PATH}/dang-ky`,
    POST: `${ADMIN_PATH}/dang-thong-tin`,
    AIRCRAFT: `${ADMIN_PATH}/quan-ly-tau-bay`,
    FLIGHTS: `${ADMIN_PATH}/quan-ly-chuyen-bay`,
    STATISTICS: `${ADMIN_PATH}/thong-ke-dat-ve`,
    DELAY: `${ADMIN_PATH}/thay-doi-gio-bay`,
  },
}