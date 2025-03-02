
"use client";

import { useParams, useNavigate } from "react-router-dom";
import { useState, useEffect } from "react";
import axios from "axios";
import "./HotelInfor.css";
import RoomQuantity from "../../components/roomquantity/RoomQuantity";
import Header from "../../components/header/Header";
import SearchBar from "../../components/searchbar/SearchBar";

const HotelInfor = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [hotel, setHotel] = useState(null);
  const [selectedRooms, setSelectedRooms] = useState({});
  const [selectedRoom, setSelectedRoom] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    axios
      .get(`http://localhost:8080/api/customer/getHotel/${id}`)
      .then((response) => {
        const rawRooms = response.data.data.rooms;

        // Nhóm các phòng cùng name và type lại để hiển thị UI nhưng vẫn giữ danh sách gốc
        const groupedRooms = {};
        rawRooms.forEach(({ room, quantity }) => {
          const key = `${room.name}-${room.type}`; // Nhóm theo tên và loại
          if (!groupedRooms[key]) {
            groupedRooms[key] = { ...room, totalQuantity: 0, originalRooms: [] };
          }
          groupedRooms[key].totalQuantity += quantity;
          groupedRooms[key].originalRooms.push({ ...room, availableQuantity: quantity });
        });

        setHotel({
          ...response.data.data,
          rooms: Object.values(groupedRooms), // Chỉ hiển thị nhóm đã gộp
          allRooms: rawRooms.map(({ room, quantity }) => ({ ...room, availableQuantity: quantity })), // Danh sách gốc
        });

        setLoading(false);
      })
      .catch((error) => {
        setError(error.message);
        setLoading(false);
      });
  }, [id]);

  const handleQuantityChange = (roomKey, quantity) => {
    console.log("Selected Room:", roomKey, "Quantity:", quantity);

    setSelectedRooms((prev) => {
      if (quantity === 0) {
        const newSelected = { ...prev };
        delete newSelected[roomKey];
        return newSelected;
      }

      return { ...prev, [roomKey]: quantity };
    });
  };

  const handleBooking = () => {
    console.log("Selected Rooms:", selectedRooms);

    const roomsToBook = [];

    Object.entries(selectedRooms).forEach(([roomKey, quantity]) => {
      const groupedRoom = hotel.rooms.find((r) => `${r.name}-${r.type}` === roomKey);
      if (!groupedRoom) {
        console.error(`Room group ${roomKey} not found`);
        return;
      }

      let remainingQuantity = quantity;
      groupedRoom.originalRooms.forEach((roomData) => {
        if (remainingQuantity > 0) {
          const toAdd = Math.min(roomData.availableQuantity, remainingQuantity);
          for (let i = 0; i < toAdd; i++) {
            roomsToBook.push({
              ...roomData,
              selectedQuantity: 1,
              totalPrice: roomData.price,
            });
          }
          remainingQuantity -= toAdd;
        }
      });
    });

    console.log("Rooms to book:", roomsToBook);

    if (roomsToBook.length === 0) {
      alert("Please select at least one room");
      return;
    }

    const bookingData = {
      hotelName: hotel.hotelName,
      hotelId: hotel.id,
      rooms: roomsToBook,
      totalPrice: roomsToBook.reduce((sum, room) => sum + room.totalPrice, 0),
    };

    localStorage.setItem("bookingData", JSON.stringify(bookingData));
    navigate("/Booking");
  };

  if (loading) return <p>Loading...</p>;
  if (error) return <p>Error: {error}</p>;
  if (!hotel) return <p>No hotel data available</p>;

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

        <div className="hotel-images">
          <img src={hotel.images[0] || "/placeholder.svg"} alt={hotel.hotelName} className="main-image" />
        </div>

        <div className="hotel-gallery">
          {hotel.images.map((img, index) => (
            <img key={index} src={img || "/placeholder.svg"} alt={`Hotel ${index}`} className="hotel-thumbnail" />
          ))}
        </div>

        <p className="hotel-description">{hotel.description}</p>

        <div className="room-list">
          {hotel.rooms.map((room) => (
            <div key={`${room.name}-${room.type}`} className="room-card">
              <img src={room.images[0] || "/placeholder.svg"} alt={room.name} className="room-image" />
              <div className="room-details">
                <h3 className="room-title" onClick={() => setSelectedRoom(room)}>
                  {room.name}
                </h3>
                <p className="room-description">{room.description}</p>
                <p className="room-price">${room.price} / đêm</p>
                <p className="room-quantity">Số lượng còn lại: {room.totalQuantity}</p>
              </div>
              <RoomQuantity
                maxQuantity={room.totalQuantity}
                onChange={(quantity) => handleQuantityChange(`${room.name}-${room.type}`, quantity)}
                initialValue={selectedRooms[`${room.name}-${room.type}`] || 0}
              />
            </div>
          ))}
        </div>

        <div className="booking">
          <button id="bookBtn" onClick={handleBooking}>
            BOOK
          </button>
        </div>

        {selectedRoom && (
          <div className="room-modal">
            <div className="modal-container">
              <div className="modal-header">
                <span className="close-btn" onClick={() => setSelectedRoom(null)}>
                  &times;
                </span>
                <h2>{selectedRoom.name}</h2>
              </div>
              <div className="modal-content">
                <div className="room-modal-images">
                  {/* Ảnh chính */}
                  <div className="room-images">
                    <img src={selectedRoom.images[0] || "/placeholder.svg"} alt={selectedRoom.roomName} className="main-image" />
                  </div>
                  {/* Danh sách ảnh nhỏ */}
                  <div className="room-gallery">
                    {selectedRoom.images.map((img, index) => (
                      <img key={index} src={img || "/placeholder.svg"} alt={`Room ${index}`} className="room-thumbnail" />
                    ))}
                  </div>
                  {/* {selectedRoom.images.map((img, index) => (
                        <img key={index} src={img || "/placeholder.svg"} alt={`Room ${index}`} />
                    ))} */}
                </div>
                <div className="room-descript">
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
            </div>
          </div>
        )}
      </div>
    </>
  );
};

export default HotelInfor;



