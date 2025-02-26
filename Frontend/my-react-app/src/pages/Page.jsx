import { Routes, Route } from "react-router-dom";
import Home from "./Home";
import HotelResult from "./HotelResult/HotelResult";
import HotelInfor from "./Hotel_infor/HotelInfor";

export default function Page() {
  return (
    <Routes>
      <Route path="/" element={<Home />} />
      <Route path="/HotelResult" element={<HotelResult />} />
      <Route path="/hotel/:id" element={<HotelInfor/>} />
    </Routes>
  );
}
