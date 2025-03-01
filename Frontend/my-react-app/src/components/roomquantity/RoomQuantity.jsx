// import React, { useState } from 'react';
// import "./RoomQuantityStyles.css";

// const RoomBooking = ({maxQuantity}) => {
//   const [selectedRooms, setSelectedRooms] = useState(0);

//   const roomOptions = Array.from({ length: maxQuantity +1 }, (_, i) => i);

//   const handleRoomChange = (event) => {
//     setSelectedRooms(event.target.value);
//   };

//   return (
//     <div className='selection-container'>
//       <label htmlFor="room-select">Rooms </label>
//       <select
//         id="room-select"
//         value={selectedRooms}
//         onChange={handleRoomChange}
//       >
//         {roomOptions.map((room) => (
//           <option key={room} value={room}>
//             {room}
//           </option>
//         ))}
//       </select>
//     </div>
//   );
// };

// export default RoomBooking;
"use client"

import { useState, useEffect } from "react"
import "./RoomQuantityStyles.css"

const RoomQuantity = ({ maxQuantity, onChange, initialValue = 0 }) => {
  const [selectedRooms, setSelectedRooms] = useState(initialValue)

  useEffect(() => {
    setSelectedRooms(initialValue)
  }, [initialValue])

  const handleRoomChange = (event) => {
    const newValue = Number.parseInt(event.target.value)
    setSelectedRooms(newValue)
    onChange(newValue)
  }

  const roomOptions = Array.from({ length: maxQuantity + 1 }, (_, i) => i)

  return (
    <div className="selection-container">
      <label htmlFor="room-select">Rooms </label>
      <select id="room-select" value={selectedRooms} onChange={handleRoomChange}>
        {roomOptions.map((room) => (
          <option key={room} value={room}>
            {room}
          </option>
        ))}
      </select>
    </div>
  )
}

export default RoomQuantity

