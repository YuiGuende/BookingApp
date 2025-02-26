import { Routes, Route } from "react-router-dom";
import Home from "./Home";
import HotelResult from "./HotelResult";

export default function Page() {
  return (
    <Routes>
      <Route path="/" element={<Home />} />
      <Route path="/HotelResult" element={<HotelResult />} />
    </Routes>
  );
}
