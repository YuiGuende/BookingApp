import { Link } from "react-router-dom";
import "./HotelResult.css";

export default function HotelDetails({ hotel }) {
  return (
    <section className="hotel-details">
      <img src={hotel.hotelWithRoomsDTO.image} alt={hotel.hotelWithRoomsDTO.hotelName} />
      <div className="hotel-info">
        <div className="header-detail">
          <h2>{hotel.hotelWithRoomsDTO.hotelName}</h2>
          <div className="hotel-rate">
            <span className="badge">Genius</span>
            <span>{hotel.hotelWithRoomsDTO.rate.rate} / 10</span>
          </div>
        </div>
        {/* <p>{hotel.hotelWithRoomsDTO.description}</p> */}
        <p>Cách điểm bạn chọn {hotel.distance.toFixed(2)}km </p>
        <p>⭐ {hotel.hotelWithRoomsDTO.stars} stars</p>
        <p>📍 {hotel.hotelWithRoomsDTO.address.city}, {hotel.hotelWithRoomsDTO.address.state}</p>
        <p className="price">VND {hotel.hotelWithRoomsDTO.price.toLocaleString()}</p>
        <Link to={`/hotel/${hotel.hotelWithRoomsDTO.hotelId}`}>
          <button className="btn">Xem chỗ trống</button>
        </Link>
      </div>
    </section>
  );
}