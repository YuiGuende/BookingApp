
// import React, { useEffect, useState } from "react";
// import { useParams } from "react-router-dom";
// import axios from "axios";
// import "./HotelInfor.css";

// const HotelInfor = () => {
//     const { id } = useParams();
//     const [hotel, setHotel] = useState(null);
//     const [loading, setLoading] = useState(true);
//     const [error, setError] = useState(null);

//     useEffect(() => {
//         axios.get(`http://localhost:8080/api/customer/getHotel/${id}`)
//             .then(response => {
//                 setHotel(response.data.data);
//                 setLoading(false);
//             })
//             .catch(error => {
//                 setError(error.message);
//                 setLoading(false);
//             });
//     }, [id]);

//     if (loading) return <p>Loading...</p>;
//     if (error) return <p>Error: {error}</p>;
//     if (!hotel) return <p>No hotel data available</p>;

//     return (
//         <div className="hotel-container">
//             <h1 className="hotel-name">{hotel.hotelName}</h1>
//             <p className="hotel-description">{hotel.description}</p>
//             <p className="hotel-rating">⭐ {hotel.rate.rate} ({hotel.rate.rateQuantity} đánh giá)</p>
//             <div className="hotel-gallery">
//                 <img src={hotel.image} alt={hotel.hotelName} className="hotel-main-image" />
//             </div>
//             <h2>Phòng có sẵn</h2>
//             <div className="room-list">
//                 {hotel.rooms.map(room => (
//                     <div key={room.id} className="room-item">
//                         <img src={room.images[0]} alt={room.name} className="room-image" />
//                         <div className="room-info">
//                             <h3>{room.name}</h3>
//                             <p>{room.description}</p>
//                             <p>Loại: {room.type}</p>
//                             <p>Sức chứa: {room.maxAdults} người lớn, {room.maxChildrens} trẻ em</p>
//                             <p>Giá: {room.price.toLocaleString()} VND</p>
//                             <button className="book-button">Đặt ngay</button>
//                         </div>
//                     </div>
//                 ))}
//             </div>
//         </div>
//     );
// };

// export default HotelInfor;

import { useParams } from "react-router-dom";
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './HotelInfor.css';

const HotelInfor = ({ hotelId }) => {
    const { id } = useParams();
    const [hotel, setHotel] = useState(null);
    const [selectedRoom, setSelectedRoom] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    useEffect(() => {
        axios.get(`http://localhost:8080/api/customer/getHotel/${id}`)
            .then(response => {
                setHotel(response.data.data);
                console.log(response.data.data)
                setLoading(false);
            })
            .catch(error => {
                setError(error.message);
                setLoading(false);
            });
    }, [id]);

    if (loading) return <p>Loading...</p>;
    if (error) return <p>Error: {error}</p>;
    if (!hotel) return <p>No hotel data available</p>;

    return (
        <div className="hotel-container-infor">
            <div className="hotel-header-infor">
                <h1 className="hotel-title">{hotel.hotelName}</h1>
                <span className="hotel-rating">{hotel.stars} ★ ({hotel.rate.rateQuantity} đánh giá)</span>
            </div>

            {/* Ảnh chính */}
            <div className="hotel-images">
                <img src={hotel.images} alt={hotel.hotelName} className="main-image" />
            </div>

            {/* Danh sách ảnh nhỏ */}
            <div className="hotel-gallery">
                {hotel.images.map((img, index) => (
                    <img key={index} src={img} alt={`Hotel ${index}`} className="hotel-thumbnail" />
                ))}
            </div>

            <p className="hotel-description">{hotel.description}</p>

            <div className="room-list">
                {hotel.rooms.map((room) => (
                    <div key={room.id} className="room-card" onClick={() => setSelectedRoom(room)}>
                        <img src={room.images[0]} alt={room.name} className="room-image" />
                        <div className="room-details">
                            <h3 className="room-title">{room.name}</h3>
                            <p className="room-description">{room.description}</p>
                            <p className="room-price">${room.price} / đêm</p>
                        </div>
                    </div>
                ))}
            </div>

            {/* Cửa sổ chi tiết phòng */}
            {selectedRoom && (
                <div className="room-modal">
                    <div className="modal-content">
                        <span className="close-btn" onClick={() => setSelectedRoom(null)}>&times;</span>
                        <h2>{selectedRoom.name}</h2>
                        <div className="room-modal-images">
                            {selectedRoom.images.map((img, index) => (
                                <img key={index} src={img} alt={`Room ${index}`} />
                            ))}
                        </div>
                        <p><strong>Mô tả:</strong> {selectedRoom.description}</p>
                        <p><strong>Giá:</strong> ${selectedRoom.price} / đêm</p>
                        <p><strong>Sức chứa:</strong> {selectedRoom.maxAdults} người lớn, {selectedRoom.maxChildrens} trẻ em</p>
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
    );
};

export default HotelInfor;
