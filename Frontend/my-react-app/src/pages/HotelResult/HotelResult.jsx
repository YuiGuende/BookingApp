
"use client";

import { useSelector } from "react-redux";
import HotelDetails from "./HotelDetails";
import "./HotelResult.css";
import SearchBar from "../../components/searchbar/SearchBar";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useDispatch } from "react-redux";
import { fetchHotels } from "../../store/hotelSlice";
import Header from "../../components/header/Header";
import AmenityDetails from "./AmentityDetails";

export default function HotelResult() {
  const { hotels, loading, error } = useSelector((state) => state.hotel);
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const [searchParams, setSearchParams] = useState({
    fullAddress: "",
    checkInDate: null,
    checkOutDate: null,
    maxAdults: 1,
    maxChildren: 0,
    rooms: 1,
  });

  const handleSearch = async (params) => {
    setSearchParams(params);
    // Gọi fetchHotels để fetch dữ liệu 1 lần
    await dispatch(fetchHotels(params));
    navigate("/HotelResult");
  };
  // {loading && <p className="loading-msg">Loading...</p>}
  // {error && <p className="error-msg">Error: {error}</p>}

  return (
    <>
      <Header />
      <SearchBar onSearch={handleSearch} initialParams={searchParams} />
      <div className="hotel-container">
        <AmenityDetails />
        <div className="hotel-list">
          {hotels && hotels.length > 0 ? (
            hotels.map((hotel) => <HotelDetails key={hotel.hotelId} hotel={hotel} />)
          ) : (
            <p>No hotels found. Try adjusting your search criteria.</p>
          )}
        </div>
      </div>
    </>
  );
}



/*
{
    "status": "success",
    "message": "Hotels found",
    "data": [
        {
            "hotelWithRoomsDTO": {
                "rooms": [
                    {
                        "id": 9,
                        "name": "Family Suite",
                        "type": "Suite",
                        "description": "Large suite perfect for families.",
                        "price": 200.0,
                        "maxAdults": 4,
                        "maxChildrens": 2,
                        "images": [
                            "https://cf.bstatic.com/xdata/images/hotel/max1024x768/640579486.jpg?k=fd836b5e327a6d9d07f5fbadb909420d347640d29928f84f302dd54dc2ec0e27&o=",
                            "https://cf.bstatic.com/xdata/images/hotel/max1024x768/638835698.jpg?k=925fcc53249e59cd48fc375ef863df34bcd82779a035e1d1071f27978d7eb3dc&o="
                        ],
                        "amenity": [],
                        "subRoomTypes": []
                    }
                ],
                "hotelId": 3,
                "hotelName": "Aman Riverside Hotel",
                "image": "https://cf.bstatic.com/xdata/images/hotel/max1024x768/647707996.jpg?k=4c7cab5459c1ceec297aed0a1cff92800ad5fad9bc7586d77c2268950f526dbf&o=&hp=1",
                "address": {
                    "id": 3,
                    "fullAddress": "19 Thoại Ngọc Hầu, Cẩm Phô, Hội An, Việt Nam",
                    "positioning": "15.875741968306269, 108.32193448297025",
                    "number": "19",
                    "street": "Thoại Ngọc Hầu",
                    "city": "Hội An",
                    "state": "Quảng Nam",
                    "country": "Việt Nam",
                    "zipCode": "56000"
                },
                "description": "Aman Riverside Hotel có hồ bơi ngoài trời, khu vườn, sân hiên và nhà hàng ở Hội An. Chỗ nghỉ này tọa lạc cách Sân golf Montgomerie Links 14 km, Sân golf Montgomerie Links Vietnam Golf Club 14 km và Ngũ Hành Sơn 19 km. Chỗ nghỉ cung cấp lễ tân 24/24, dịch vụ đưa đón sân bay, dịch vụ phòng và Wi-Fi miễn phí.Tại khách sạn, tất cả các phòng có bàn làm việc. Với phòng tắm riêng được trang bị vòi xịt/chậu rửa vệ sinh và đồ vệ sinh cá nhân miễn phí, phòng khách tại Aman Riverside Hotel có TV màn hình phẳng và điều hòa, trong đó một số phòng có ban công. Tại chỗ nghỉ, phòng nào cũng có ga trải giường và khăn tắm. Aman Riverside Hotel có phục vụ bữa sáng kiểu lục địa và kiểu Á. Đi xe đạp là hoạt động được ưa chuộng trong khu vực. Ngoài ra, khách sạn 4 sao này có dịch vụ thuê xe đạp.Các điểm tham quan nổi tiếng gần khách sạn bao gồm Chùa Cầu, Bảo tàng lịch sử Hội An và Hội Quán Chi Hội Triều Châu Trung Quốc. Sân bay Quốc tế Đà Nẵng cách 29 km.",
                "rate": {
                    "rate": 8.5,
                    "rateQuantity": 34
                },
                "price": 0.0,
                "stars": 5
            },
            "distance": 1.1523961771851443
        }
    ]
}
*/