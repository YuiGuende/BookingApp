import { Routes, Route } from "react-router-dom";
import Home from "./Home";
import HotelResult from "./HotelResult/HotelResult";
import HotelInfor from "./Hotel_infor/HotelInfor";
import Booking from "./booking/Booking";
import Confirm from "./Confirm/Confirm";
import Login from "./login/Login";
import Signup from "./signup/Signup";
import BookingHistory from "./bookingHistory/BookingHistory";
import PaymentReturn from "./payment/return";
import Hotel_Reception from "./hotel_reception/Hotel_Reception";

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
      <Route path="/bookingHistory" element={<BookingHistory/>}/>
      <Route path="/payment/return" element={<PaymentReturn></PaymentReturn>}></Route>
      <Route path="/reception" element={<Hotel_Reception></Hotel_Reception>}></Route>
    </Routes>
  );
}
