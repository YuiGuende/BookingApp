"use client";

import { useState } from "react";
import { useNavigate } from "react-router-dom";

import { useDispatch } from "react-redux";
import { fetchHotels } from "../store/hotelSlice";
import Header from "../components/header/Header";
import SearchBar from "../components/searchbar/SearchBar";

export default function Home() {
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

  // Hàm xử lý khi người dùng nhấn “Tìm kiếm”
  const handleSearch = async (params) => {
    setSearchParams(params);
    
    // Gọi fetchHotels để fetch dữ liệu 1 lần
    await dispatch(fetchHotels(params));
    // Sau khi fetch xong, chuyển hướng sang HotelResult
    navigate("/HotelResult");
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
    </div>
  );
}