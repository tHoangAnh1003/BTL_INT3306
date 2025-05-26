import {memo} from "react";
import Header from "../header/header";
import Footer from "../footer/footer";
import ScrollTopBtn from "../../../components/scrollTopBtn/scrollTopBtn";

const MasterLayout = ({ children, ...props}) => {
  return (
    <div {...props}>
      <Header />
      {children}
      <ScrollTopBtn />
      <Footer />
    </div>
  );
};

export default memo(MasterLayout);