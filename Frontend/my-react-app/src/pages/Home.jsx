// "use client";

// import { useState } from "react";
// import { useNavigate } from "react-router-dom";

// import { useDispatch } from "react-redux";
// import { fetchHotels } from "../store/hotelSlice";
// import Header from "../components/header/Header";
// import SearchBar from "../components/searchbar/SearchBar";

// export default function Home() {
//   const navigate = useNavigate();

//   const dispatch = useDispatch();

//   const [searchParams, setSearchParams] = useState({
//     fullAddress: "",
//     checkInDate: null,
//     checkOutDate: null,
//     maxAdults: 1,
//     maxChildren: 0,
//     rooms: 1,
//   });

//   // Hàm xử lý khi người dùng nhấn “Tìm kiếm”
//   const handleSearch = async (params) => {
//     setSearchParams(params);

//     // Gọi fetchHotels để fetch dữ liệu 1 lần
//     await dispatch(fetchHotels(params));
//     // Sau khi fetch xong, chuyển hướng sang HotelResult
//     navigate("/HotelResult");
//   };

//   return (
//     <div className="container">
//       <Header />
//       <div className="welcome-sentence">
//         <h1>Find your next place to stay</h1>
//         <h3>Find hotel deals, home stays and more...</h3>
//       </div>
//       <div className="searchbar">
//         <SearchBar onSearch={handleSearch} initialParams={searchParams} />
//       </div>
//     </div>
//   );
// }
"use client";

import { useState, useRef } from "react";
import { useNavigate } from "react-router-dom";
import { useDispatch } from "react-redux";
import { fetchHotels } from "../store/hotelSlice";
import Header from "../components/header/Header";
import SearchBar from "../components/searchbar/SearchBar";
import { ChevronLeft, ChevronRight } from 'lucide-react';
import "../assets/index.css"; // Import CSS thông thường

export default function Home() {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const searchBarRef = useRef(null);
  const [searchParams, setSearchParams] = useState({
    fullAddress: "",
    checkInDate: null,
    checkOutDate: null,
    maxAdults: 1,
    maxChildren: 0,
    rooms: 1,
  });

  // Thêm state cho carousel
  const [carouselIndex, setCarouselIndex] = useState(0);

  // Data cho các điểm đến thịnh hành
  const trendingDestinations = [
    {
      name: "Hà Nội",
      image: "https://cf.bstatic.com/xdata/images/city/600x600/981517.jpg?k=2268f51ad34ab94115ea9e42155bc593aa8d48ffaa6fc58432a8760467dc4ea6&o=",
      stays: "3,817 chỗ nghỉ"
    },
    {
      name: "Hội An",
      image: "https://cf.bstatic.com/xdata/images/city/600x600/688866.jpg?k=fc9d2cb9fe2f6d1160e10542cd2b83f5a8008401d33e8750ee3c2691cf4d4f7e&o=",
      stays: "1,610 chỗ nghỉ"
    },
    {
      name: "TP. Hồ Chí Minh",
      image: "https://cf.bstatic.com/xdata/images/city/600x600/688893.jpg?k=d32ef7ff94e5d02b90908214fb2476185b62339549a1bd7544612bdac51fda31&o=",
      stays: "5,608 chỗ nghỉ"
    },
    {
      name: "Ninh Bình",
      image: "https://cf.bstatic.com/xdata/images/city/600x600/688853.jpg?k=f6427c8fccdf777e4bbc75fcd245e7c66204280181bea23350388c76c57348d1&o=",
      stays: "443 chỗ nghỉ"
    },
    {
      name: "Đà Nẵng",
      image: "https://cf.bstatic.com/xdata/images/city/600x600/688844.jpg?k=02892d6c86c2410289370b71c47814c820ed0c4c8b6ce9d9c93e8af9a1d8b6d2&o=",
      stays: "2,509 chỗ nghỉ"
    }
  ];

  // Data cho khám phá Việt Nam
  const exploreVietnam = [
    {
      name: "Hà Nội",
      image: "https://cf.bstatic.com/xdata/images/city/600x600/981517.jpg?k=2268f51ad34ab94115ea9e42155bc593aa8d48ffaa6fc58432a8760467dc4ea6&o=",
      stays: "3,817 chỗ nghỉ"
    },
    {
      name: "TP. Hồ Chí Minh",
      image: "https://cf.bstatic.com/xdata/images/city/600x600/688893.jpg?k=d32ef7ff94e5d02b90908214fb2476185b62339549a1bd7544612bdac51fda31&o=",
      stays: "5,608 chỗ nghỉ"
    },
    {
      name: "Đà Nẵng",
      image: "https://cf.bstatic.com/xdata/images/city/600x600/688844.jpg?k=02892d6c86c2410289370b71c47814c820ed0c4c8b6ce9d9c93e8af9a1d8b6d2&o=",
      stays: "2,509 chỗ nghỉ"
    },
    {
      name: "Hội An",
      image: "https://cf.bstatic.com/xdata/images/city/600x600/688866.jpg?k=fc9d2cb9fe2f6d1160e10542cd2b83f5a8008401d33e8750ee3c2691cf4d4f7e&o=",
      stays: "1,610 chỗ nghỉ"
    },
    {
      name: "Sa Pa",
      image: "https://cf.bstatic.com/xdata/images/city/600x600/688891.jpg?k=9a550fedccb750cfa3f2e8eaf507287d4007f21def68e6566cc00b200946e876&o=",
      stays: "443 chỗ nghỉ"
    },
    {
      name: "Vịnh Hạ Long",
      image: "https://cf.bstatic.com/xdata/images/city/600x600/688831.jpg?k=7b999c7babe3487598fc4dd89365db2c4778827eac8cb2a47d48505c97959a78&o=",
      stays: "1,292 chỗ nghỉ"
    }
  ];

  // Hàm xử lý khi người dùng nhấn "Tìm kiếm"
  const handleSearch = async (params) => {
    setSearchParams(params);
    await dispatch(fetchHotels(params));
    navigate("/HotelResult");
  };

  // Hàm xử lý khi click vào điểm đến
  const handleDestinationClick = (destinationName) => {
    setSearchParams((prev) => (
      {
        ...prev,
        fullAddress: destinationName
      }
    ));
    console.log("searchparam", searchParams)
  };

  // Hàm điều khiển carousel
  const handleCarouselScroll = (direction) => {
    const container = document.getElementById('exploreCarousel');
    const scrollAmount = 300; // Điều chỉnh khoảng cách scroll

    if (direction === 'left') {
      container.scrollBy({ left: -scrollAmount, behavior: 'smooth' });
    } else {
      container.scrollBy({ left: scrollAmount, behavior: 'smooth' });
    }
  };

  return (
    <div className="container">
      <Header />
      <div className="welcome-sentence">
        <h1>Find your next place to stay</h1>
        <h3>Find hotel deals, home stays and more...</h3>
      </div>
      <div className="searchbar">
        <SearchBar onSearch={handleSearch} initialParams={searchParams} />
      </div>

      {/* Điểm đến đang thịnh hành */}
      <div className="trending-section">
        <h2>Điểm đến đang thịnh hành</h2>
        <p>Du khách tìm kiếm về Việt Nam cũng đặt chỗ ở những nơi này</p>
        <div className="trending-grid">
          {trendingDestinations.map((destination, index) => (
            <div
              key={index}
              className="destination-card"
              onClick={() => handleDestinationClick(destination.name)}
            >
              <div
                className="destination-image"
                style={{ backgroundImage: `url(${destination.image})` }}
              >
                <div className="destination-info">
                  <h3>{destination.name} <span className="flag">🇻🇳</span></h3>
                  <p>{destination.stays}</p>
                </div>
              </div>
            </div>
          ))}
        </div>
      </div>

      {/* Khám phá Việt Nam */}
      <div className="explore-section">
        <h2>Khám phá Việt Nam</h2>
        <p>Các điểm đến phổ biến này có nhiều điều chờ đón bạn</p>
        <div className="carousel-container">
          <button
            className="carousel-button carousel-button-left"
            onClick={() => handleCarouselScroll('left')}
          >
            <ChevronLeft />
          </button>
          <div className="carousel-wrapper" id="exploreCarousel">
            {exploreVietnam.map((place, index) => (
              <div
                key={index}
                className="explore-card"
                onClick={() => handleDestinationClick(place.name)}
              >
                <div
                  className="explore-image"
                  style={{ backgroundImage: `url(${place.image})` }}
                />
                <div className="explore-info">
                  <h4>{place.name}</h4>
                  <p>{place.stays}</p>
                </div>
              </div>
            ))}
          </div>
          <button
            className="carousel-button carousel-button-right"
            onClick={() => handleCarouselScroll('right')}
          >
            <ChevronRight />
          </button>
        </div>
      </div>
    </div>
  );
}