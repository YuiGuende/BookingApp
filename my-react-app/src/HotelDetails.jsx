import PropTypes from "prop-types"
import "./index.css"

export default function HotelDetails({ hotel }) {
  return (
    <div className="bg-white shadow-md rounded-lg p-6">
      <h2 className="text-2xl font-bold mb-4">{hotel.hotelName}</h2>
      {hotel.imageUrl && (
        <img
          src={hotel.imageUrl || "/placeholder.svg"}
          alt={hotel.hotelName}
          className="hotel-picture"
        />
      )}
      <p className="text-gray-600 mb-2">{hotel.description}</p>
      <div className="flex items-center mb-2">
        <span className="text-yellow-500 mr-1">★</span>
        <span>{hotel.stars} stars</span>
      </div>
      <p className="text-gray-600 mb-2">
        {hotel.address.city}, {hotel.address.country}
      </p>
      <p className="text-gray-600 mb-4">{hotel.address.fullAddress}</p>
      <h3 className="text-xl font-semibold mb-2">Rooms:</h3>
      <ul className="list-disc pl-5">
        {hotel.rooms.map((room) => (
          <li key={room.id} className="mb-1">
            {room.name} ({room.type}) - ${room.price}/night - Capacity: {room.capacity}
          </li>
        ))}
      </ul>
      {hotel.rate && (
        <div className="mt-4">
          <h3 className="text-xl font-semibold mb-2">Ratings:</h3>
          <p>Cleanliness: {hotel.rate.cleanliness}</p>
          <p>Service: {hotel.rate.service}</p>
          <p>Value: {hotel.rate.value}</p>
          <p>Overall: {hotel.rate.overall}</p>
        </div>
      )}
    </div>
  )
}

HotelDetails.propTypes = {
  hotel: PropTypes.shape({
    hotelId: PropTypes.number.isRequired,
    hotelName: PropTypes.string.isRequired,
    imageUrl: PropTypes.string,
    description: PropTypes.string,
    stars: PropTypes.number,
    address: PropTypes.shape({
      fullAddress: PropTypes.string,
      city: PropTypes.string,
      country: PropTypes.string,
    }),
    rooms: PropTypes.arrayOf(
      PropTypes.shape({
        id: PropTypes.number,
        name: PropTypes.string,
        type: PropTypes.string,
        description: PropTypes.string,
        price: PropTypes.number,
        capacity: PropTypes.number,
      }),
    ),
    rate: PropTypes.shape({
      cleanliness: PropTypes.number,
      service: PropTypes.number,
      value: PropTypes.number,
      overall: PropTypes.number,
    }),
  }).isRequired,
}

