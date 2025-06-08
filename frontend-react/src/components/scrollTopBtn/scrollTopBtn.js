import { memo, useEffect, useState } from "react";
import { FaChevronUp } from "react-icons/fa";   // đã có sẵn trong react-icons

import "./scrollTopBtn.scss";

const ScrollTopBtn = () => {
  const [visible, setVisible] = useState(false);


  useEffect(() => {
    const onScroll = () => setVisible(window.scrollY > 300);
    window.addEventListener("scroll", onScroll);
    return () => window.removeEventListener("scroll", onScroll);
  }, []);

  const handleClick = () => {
    window.scrollTo({ top: 0, behavior: "smooth" });
  };

  return (
    <button
      className={`scroll-top-btn${visible ? " show" : ""}`}
      aria-label="Lên đầu trang"
      onClick={handleClick}
    >
      <FaChevronUp />
    </button>
  );
};

export default memo(ScrollTopBtn);
