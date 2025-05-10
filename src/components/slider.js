import React from "react";
import Carousel from "react-multi-carousel";
import "react-multi-carousel/lib/styles.css";

import news1 from "../assets/imgs/uu-dai-doi-tac-va.jpg"
import news2 from "../assets/imgs/uu-dai-hoi-vien-va.jpg";
import news3 from "../assets/imgs/quyen-loi-hoi-vien.jpg";
import news4 from "../assets/imgs/ung-dung-di-dong.jpg";
import news5 from "../assets/imgs/trung-tam-tro-giup.jpg";
import news6 from "../assets/imgs/goi-uu-dai.jpg";
import "../styles/styles.scss";

const NewsSlider = () => {
  const newsData = [
    {
      title: "Ưu đãi hội viên",
      image: news1,
    },
    {
      title: "Ưu đãi đối tác",
      image: news2,
    },
    {
      title: "Quyền lợi hội viên",
      image: news3,
    },
    {
      title: "Ứng dụng di động",
      image: news4,
    },
    {
      title: "Trung tâm trợ giúp",
      image: news5,
    },
    {
      title: "Gói ưu đãi",
      image: news6,
    },
  ];

  const responsive = {
    superLargeDesktop: { breakpoint: { max: 4000, min: 3000 }, items: 5 },
    desktop: { breakpoint: { max: 3000, min: 1024 }, items: 4 },
    tablet: { breakpoint: { max: 1024, min: 768 }, items: 2 },
    mobile: { breakpoint: { max: 768, min: 0 }, items: 1 },
  };

  return (
    <Carousel
      swipeable={true}
      draggable={true}
      showDots={false}           // hiển thị/ẩn chấm ở dưới
      responsive={responsive}
      infinite={true}           // trượt vòng lặp
      autoPlay={true}           // tự động trượt
      autoPlaySpeed={3000}      // 3 giây chuyển slide
      keyBoardControl={true}
      customTransition="all .5s"
      transitionDuration={500}
      containerClass="carousel-container"
      removeArrowOnDeviceType={["tablet", "mobile"]}
      itemClass="carousel-item-padding-40-px"
    >
      {newsData.map((item, index) => (
        <div key={index} className="news-slide-item">
          <img src={item.image} alt={item.title} />
          <p>{item.title}</p>
        </div>
      ))}
    </Carousel>
  );
};

export default NewsSlider;
