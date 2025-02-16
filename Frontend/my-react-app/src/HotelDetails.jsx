export default function HotelDetails({ hotel }) {
  return (
    <div className="hotel-details">
      <h2>Name: {hotel.hotelWithRoomsDTO.hotelName}</h2>
      <p>Description: {hotel.hotelWithRoomsDTO.description}</p>
      <p>Stars: {hotel.hotelWithRoomsDTO.stars}</p>
      <p>Distance: {hotel.distance.toFixed(2)} km</p>
      <p>Address: {hotel.hotelWithRoomsDTO.address.number},
                  {hotel.hotelWithRoomsDTO.address.street},
                  {hotel.hotelWithRoomsDTO.address.city}, 
                  {hotel.hotelWithRoomsDTO.address.state}, 
                  {hotel.hotelWithRoomsDTO.address.country}</p>
      {hotel.rate && (
        <div>
          <p>Ratings:</p>
          <ul>
            <li>Cleanliness: {hotel.rate.cleanliness}</li>
            <li>Service: {hotel.rate.service}</li>
            <li>Value: {hotel.rate.value}</li>
            <li>Overall: {hotel.rate.overall}</li>
          </ul>
        </div>
      )}
      {hotel.rooms && hotel.rooms.length > 0 && (
        <div>
          <h3>Rooms:</h3>
          <ul>
            {hotel.rooms.map((room, index) => (
              <li key={index}>
                {room.name} - Type: {room.type}, Price: ${room.price}, Capacity: {room.capacity}
              </li>
            ))}
          </ul>
        </div>
      )}
    </div>
  )
}

