

// import { useParams } from "react-router-dom";
// import React, { useState, useEffect } from 'react';
// import axios from 'axios';
// import './HotelInfor.css';
// import RoomQuantity from "../../components/roomquantity/RoomQuantity";
// import Header from "../../components/header/Header";
// import SearchBar from "../../components/searchbar/SearchBar";
// import { Link } from "react-router-dom";
// const HotelInfor = ({ hotelId }) => {
//     const { id } = useParams();
//     const [hotel, setHotel] = useState(null);
//     const [selectedRooms, setSelectedRooms] = useState({})
//     const [loading, setLoading] = useState(true);
//     const [error, setError] = useState(null);
//     useEffect(() => {
//         axios.get(`http://localhost:8080/api/customer/getHotel/${id}`)
//             .then(response => {
//                 setHotel(response.data.data);
//                 console.log(response.data.data)
//                 setLoading(false);
//             })
//             .catch(error => {
//                 setError(error.message);
//                 setLoading(false);
//             });
//     }, [id]);

//     const handleQuantityChange = (roomId, quantity) => {
//         setSelectedRooms((prev) => ({
//             ...prev,
//             [roomId]: quantity,
//         }))
//     }

//     // Add this function to handle booking
//     const handleBooking = () => {
//         const roomsToBook = Object.entries(selectedRooms)
//             .filter(([_, quantity]) => quantity > 0)
//             .map(([roomId, quantity]) => {
//                 const roomData = hotel.rooms.find((r) => r.room.id === Number.parseInt(roomId))
//                 return {
//                     ...roomData.room,
//                     selectedQuantity: quantity,
//                     totalPrice: roomData.room.price * quantity,
//                 }
//             })

//         if (roomsToBook.length === 0) {
//             alert("Please select at least one room")
//             return
//         }

//         const bookingData = {
//             hotelName: hotel.hotelName,
//             hotelId: hotel.id,
//             rooms: roomsToBook,
//             totalPrice: roomsToBook.reduce((sum, room) => sum + room.totalPrice, 0),
//         }

//         localStorage.setItem("bookingData", JSON.stringify(bookingData))
//         navigate("/Booking")
//     }

//     // const RoomQuantity = ({ maxQuantity, onChange }) => {
//     //     const [quantity, setQuantity] = useState(0)

//     //     const handleIncrement = () => {
//     //         if (quantity < maxQuantity) {
//     //             setQuantity(quantity + 1)
//     //             onChange(quantity + 1)
//     //         }
//     //     }

//     //     const handleDecrement = () => {
//     //         if (quantity > 0) {
//     //             setQuantity(quantity - 1)
//     //             onChange(quantity - 1)
//     //         }
//     //     }

//     //     return (
//     //         <div className="room-quantity-selector">
//     //             <button onClick={handleDecrement}>-</button>
//     //             <span>{quantity}</span>
//     //             <button onClick={handleIncrement}>+</button>
//     //         </div>
//     //     )
//     // }

//     if (loading) return <p>Loading...</p>;
//     if (error) return <p>Error: {error}</p>;
//     if (!hotel) return <p>No hotel data available</p>;

//     return (
//         <>
//             <div className="header-container">
//                 <Header />
//                 <SearchBar />
//             </div>
//             <div className="hotel-container-infor">
//                 <div className="hotel-header-infor">
//                     <h1 className="hotel-title">{hotel.hotelName}</h1>
//                     <span className="hotel-rating">{hotel.stars} ★ ({hotel.rate.rateQuantity} đánh giá)</span>
//                 </div>

//                 {/* Ảnh chính */}
//                 <div className="hotel-images">
//                     <img src={hotel.images} alt={hotel.hotelName} className="main-image" />
//                 </div>

//                 {/* Danh sách ảnh nhỏ */}
//                 <div className="hotel-gallery">
//                     {hotel.images.map((img, index) => (
//                         <img key={index} src={img} alt={`Hotel ${index}`} className="hotel-thumbnail" />
//                     ))}
//                 </div>

//                 <p className="hotel-description">{hotel.description}</p>

//                 <div className="room-list">
//                     {hotel.rooms.map((roomData) => (
//                         <div key={roomData.room.id} className="room-card">
//                             <img src={roomData.room.images[0] || "/placeholder.svg"} alt={roomData.room.name} className="room-image" />
//                             <div className="room-details">
//                                 <h3 className="room-title" onClick={() => setSelectedRoom(roomData.room)}>
//                                     {roomData.room.name}
//                                 </h3>
//                                 <p className="room-description">{roomData.room.description}</p>
//                                 <p className="room-price">${roomData.room.price} / đêm</p>
//                                 <p className="room-quantity">Số lượng còn lại: {roomData.quantity}</p>
//                             </div>
//                             <RoomQuantity
//                                 maxQuantity={roomData.quantity}
//                                 onChange={(quantity) => handleQuantityChange(roomData.room.id, quantity)} />
//                         </div>
//                     ))}
//                 </div>


//                 <div className="booking">
//                     <button id="bookBtn" onClick={handleBooking}>BOOK</button>
//                 </div>



//                 {/* Cửa sổ chi tiết phòng */}
//                 {selectedRoom && (
//                     <div className="room-modal">
//                         <div className="modal-content">
//                             <span className="close-btn" onClick={() => setSelectedRoom(null)}>&times;</span>
//                             <h2>{selectedRoom.name}</h2>
//                             <div className="room-modal-images">
//                                 {selectedRoom.images.map((img, index) => (
//                                     <img key={index} src={img} alt={`Room ${index}`} />
//                                 ))}
//                             </div>
//                             <p><strong>Mô tả:</strong> {selectedRoom.description}</p>
//                             <p><strong>Giá:</strong> VND {selectedRoom.price} / đêm</p>
//                             <p><strong>Sức chứa:</strong> {selectedRoom.maxAdults} người lớn, {selectedRoom.maxChildrens} trẻ em</p>
//                             <h3>Tiện nghi:</h3>
//                             <ul className="room-amenities">
//                                 {selectedRoom.amenity.map((a, index) => (
//                                     <li key={index}>✔ {a.name}</li>
//                                 ))}
//                             </ul>
//                         </div>
//                     </div>
//                 )}
//             </div>
//         </>
//     );
// };

// export default HotelInfor;
"use client"

import { useParams, useNavigate } from "react-router-dom"
import { useState, useEffect } from "react"
import axios from "axios"
import "./HotelInfor.css"
import RoomQuantity from "../../components/roomquantity/RoomQuantity"
import Header from "../../components/header/Header"
import SearchBar from "../../components/searchbar/SearchBar"

const HotelInfor = () => {
  const { id } = useParams()
  const navigate = useNavigate()
  const [hotel, setHotel] = useState(null)
  const [selectedRooms, setSelectedRooms] = useState({})
  const [selectedRoom, setSelectedRoom] = useState(null)
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)

  useEffect(() => {
    axios
      .get(`http://localhost:8080/api/customer/getHotel/${id}`)
      .then((response) => {
        setHotel(response.data.data)
        console.log(response.data.data)
        setLoading(false)
      })
      .catch((error) => {
        setError(error.message)
        setLoading(false)
      })
  }, [id])

  const handleQuantityChange = (roomId, quantity) => {
    console.log(roomId)
    console.log(quantity)
    setSelectedRooms((prev) => {
      if (quantity === 0) {
        const newSelected = { ...prev }
        delete newSelected[roomId]
        return newSelected
      }
      return { ...prev, [roomId]: quantity }
    })
  }

  const handleBooking = () => {
    console.log("Selected Rooms:", selectedRooms) // Debugging line

    const roomsToBook = Object.entries(selectedRooms)
      .filter(([_, quantity]) => quantity > 0)
      .map(([roomId, quantity]) => {
        const roomData = hotel.rooms.find((r) => r.room.id === Number.parseInt(roomId))
        if (!roomData) {
          console.error(`Room with id ${roomId} not found`)
          return null
        }
        return {
          ...roomData.room,
          selectedQuantity: quantity,
          totalPrice: roomData.room.price * quantity,
        }
      })
      .filter((room) => room !== null)

    console.log("Rooms to book:", roomsToBook) // Debugging line

    if (roomsToBook.length === 0) {
      alert("Please select at least one room")
      return
    }

    const bookingData = {
      hotelName: hotel.hotelName,
      hotelId: hotel.id,
      rooms: roomsToBook,
      totalPrice: roomsToBook.reduce((sum, room) => sum + room.totalPrice, 0),
    }

    localStorage.setItem("bookingData", JSON.stringify(bookingData))
    navigate("/Booking")
  }

  if (loading) return <p>Loading...</p>
  if (error) return <p>Error: {error}</p>
  if (!hotel) return <p>No hotel data available</p>

  return (
    <>
      <div className="header-container">
        <Header />
        <SearchBar />
      </div>
      <div className="hotel-container-infor">
        <div className="hotel-header-infor">
          <h1 className="hotel-title">{hotel.hotelName}</h1>
          <span className="hotel-rating">
            {hotel.stars} ★ ({hotel.rate.rateQuantity} đánh giá)
          </span>
        </div>

        {/* Ảnh chính */}
        <div className="hotel-images">
          <img src={hotel.images[0] || "/placeholder.svg"} alt={hotel.hotelName} className="main-image" />
        </div>

        {/* Danh sách ảnh nhỏ */}
        <div className="hotel-gallery">
          {hotel.images.map((img, index) => (
            <img key={index} src={img || "/placeholder.svg"} alt={`Hotel ${index}`} className="hotel-thumbnail" />
          ))}
        </div>

        <p className="hotel-description">{hotel.description}</p>

        <div className="room-list">
          {hotel.rooms.map((roomData) => (
            <div key={roomData.room.id} className="room-card">
              <img
                src={roomData.room.images[0] || "/placeholder.svg"}
                alt={roomData.room.name}
                className="room-image"
              />
              <div className="room-details">
                <h3 className="room-title" onClick={() => setSelectedRoom(roomData.room)}>
                  {roomData.room.name}
                </h3>
                <p className="room-description">{roomData.room.description}</p>
                <p className="room-price">${roomData.room.price} / đêm</p>
                <p className="room-quantity">Số lượng còn lại: {roomData.quantity}</p>
              </div>
              <RoomQuantity
                maxQuantity={roomData.quantity}
                onChange={(quantity) => handleQuantityChange(roomData.room.id, quantity)}
                initialValue={selectedRooms[roomData.room.id] || 0}
              />
            </div>
          ))}
        </div>

        <div className="booking">
          <button id="bookBtn" onClick={handleBooking}>
            BOOK
          </button>
        </div>

        {/* Cửa sổ chi tiết phòng */}
        {selectedRoom && (
          <div className="room-modal">
            <div className="modal-content">
              <span className="close-btn" onClick={() => setSelectedRoom(null)}>
                &times;
              </span>
              <h2>{selectedRoom.name}</h2>
              <div className="room-modal-images">
                {selectedRoom.images.map((img, index) => (
                  <img key={index} src={img || "/placeholder.svg"} alt={`Room ${index}`} />
                ))}
              </div>
              <p>
                <strong>Mô tả:</strong> {selectedRoom.description}
              </p>
              <p>
                <strong>Giá:</strong> VND {selectedRoom.price} / đêm
              </p>
              <p>
                <strong>Sức chứa:</strong> {selectedRoom.maxAdults} người lớn, {selectedRoom.maxChildrens} trẻ em
              </p>
              <h3>Tiện nghi:</h3>
              <ul className="room-amenities">
                {selectedRoom.amenity.map((a, index) => (
                  <li key={index}>✔ {a.name}</li>
                ))}
              </ul>
            </div>
          </div>
        )}
      </div>
    </>
  )
}

export default HotelInfor

