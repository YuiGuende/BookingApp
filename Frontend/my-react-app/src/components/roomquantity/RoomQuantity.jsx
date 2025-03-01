import React, { useState } from 'react';
import "./RoomQuantityStyles.css";

const RoomBooking = ({maxQuantity}) => {
  const [selectedRooms, setSelectedRooms] = useState(0);

  const roomOptions = Array.from({ length: maxQuantity +1 }, (_, i) => i);

  const handleRoomChange = (event) => {
    setSelectedRooms(event.target.value);
  };

  return (
    <div className='selection-container'>
      <label htmlFor="room-select">Rooms </label>
      <select
        id="room-select"
        value={selectedRooms}
        onChange={handleRoomChange}
      >
        {roomOptions.map((room) => (
          <option key={room} value={room}>
            {room}
          </option>
        ))}
      </select>
    </div>
  );
};

export default RoomBooking;
