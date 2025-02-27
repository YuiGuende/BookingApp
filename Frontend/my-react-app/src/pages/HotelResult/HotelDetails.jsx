import { Link } from "react-router-dom";

export default function HotelDetails({ hotel }) {
  return (
    <section className="hotel-details">
      <img src={hotel.hotelWithRoomsDTO.image} alt={hotel.hotelWithRoomsDTO.hotelName} />
      <div className="hotel-info">
        <h2>{hotel.hotelWithRoomsDTO.hotelName}</h2>
        {/* <p>{hotel.hotelWithRoomsDTO.description}</p> */}
        <p>⭐ {hotel.hotelWithRoomsDTO.stars} stars</p>
        <p>📍 {hotel.hotelWithRoomsDTO.address.city}, {hotel.hotelWithRoomsDTO.address.state}</p>
        <p className="price">VND {hotel.hotelWithRoomsDTO.price.toLocaleString()}</p>
        <div className="hotel-rating">
          <span className="badge">Genius</span>
          <span>{hotel.hotelWithRoomsDTO.rate.rate} / 10</span>
        </div>
        <Link to={`/hotel/${hotel.hotelWithRoomsDTO.hotelId}`}>
          <button className="btn">Xem chỗ trống</button>
        </Link>
      </div>
    </section>
  );
}

