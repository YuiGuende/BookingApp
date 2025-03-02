import { Routes, Route } from "react-router-dom";
import Home from "./Home";
import HotelResult from "./HotelResult/HotelResult";
import HotelInfor from "./Hotel_infor/HotelInfor";
import Booking from "./Booking/Booking";
import Confirm from "./Confirm/Confirm";
import Login from "./login/Login";
import Signup from "./signup/Signup";

export default function Page() {
  return (
    <Routes>
      <Route path="/" element={<Home />} />
      <Route path="/HotelResult" element={<HotelResult />} />
      <Route path="/hotel/:id" element={<HotelInfor/>} />
      <Route path="booking" element={<Booking/>}/>
      <Route path="/confirm" element={<Confirm/>}/>
      <Route path="/login" element={<Login></Login>}/>
      <Route path="/signup" element={<Signup/>}/>
    </Routes>
  );
}
