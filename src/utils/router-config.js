export const ADMIN_PATH = "/admin";

export const ROUTERS = {
  USER: {
    HOME: "",
    BOOKING: "dat-ve",
    MANAGE: "quan-ly-dat-ve",
    CHECK_IN: "lam-thu-tuc",
    COMPLAINT: "khieu-nai",
    INFO: "thong-tin-chuyen-bay",
  },
  ADMIN : {
    LOGIN: `${ADMIN_PATH}/dang-nhap`,
    REGISTER: `${ADMIN_PATH}/dang-ky`,
    POST: `${ADMIN_PATH}/dang-thong-tin`,
  },
}