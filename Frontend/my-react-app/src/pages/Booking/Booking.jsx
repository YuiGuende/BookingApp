"use client"

import { useState, useEffect } from "react"
import { useNavigate } from "react-router-dom"
import "./Booking.css"

const Booking = () => {
  const navigate = useNavigate()
  const [bookingData, setBookingData] = useState(null)
  const [formData, setFormData] = useState({
    firstName: "",
    lastName: "",
    email: "",
    phone: "",
    country: "VN",
    specialRequests: "",
  })

  useEffect(() => {
    const data = localStorage.getItem("bookingData")
    if (!data) {
      navigate("/")
      return
    }
    setBookingData(JSON.parse(data))
  }, [navigate])

  const handleInputChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    })
  }

  const handleSubmit = (e) => {
    e.preventDefault()
    // Handle booking submission here
    console.log("Booking submitted:", { bookingData, formData })
  }

  if (!bookingData) return null

  return (
    <div className="booking-page">
      <div className="booking-progress">
        <div className="progress-step completed">
          <span className="step-number">1</span>
          <span className="step-text">Bạn chọn</span>
        </div>
        <div className="progress-step active">
          <span className="step-number">2</span>
          <span className="step-text">Chi tiết về bạn</span>
        </div>
        <div className="progress-step">
          <span className="step-number">3</span>
          <span className="step-text">Hoàn tất đặt phòng</span>
        </div>
      </div>

      <div className="booking-container">
        <div className="booking-form">
          <h2>Thông tin chi tiết của bạn</h2>
          <form onSubmit={handleSubmit}>
            <div className="form-row">
              <div className="form-group">
                <label>Họ</label>
                <input type="text" name="firstName" value={formData.firstName} onChange={handleInputChange} required />
              </div>
              <div className="form-group">
                <label>Tên</label>
                <input type="text" name="lastName" value={formData.lastName} onChange={handleInputChange} required />
              </div>
            </div>

            <div className="form-group">
              <label>Email</label>
              <input type="email" name="email" value={formData.email} onChange={handleInputChange} required />
            </div>

            <div className="form-group">
              <label>Số điện thoại</label>
              <div className="phone-input">
                <select name="country" value={formData.country} onChange={handleInputChange}>
                  <option value="VN">+84</option>
                  {/* Add more country codes as needed */}
                </select>
                <input type="tel" name="phone" value={formData.phone} onChange={handleInputChange} required />
              </div>
            </div>

            <div className="form-group">
              <label>Yêu cầu đặc biệt</label>
              <textarea name="specialRequests" value={formData.specialRequests} onChange={handleInputChange} rows="4" />
            </div>

            <button type="submit" className="submit-button">
              Hoàn tất đặt phòng
            </button>
          </form>
        </div>

        <div className="booking-summary">
          <div className="hotel-info">
            <h3>{bookingData.hotelName}</h3>
            <div className="selected-rooms">
              {bookingData.rooms.map((room, index) => (
                <div key={index} className="room-summary">
                  <h4>{room.name}</h4>
                  <p>Số lượng: {room.selectedQuantity}</p>
                  <p className="room-price">
                    VND{room.price} x {room.selectedQuantity} đêm = VND{room.totalPrice}
                  </p>
                </div>
              ))}
            </div>
            <div className="total-price">
              <h4>Tổng cộng</h4>
              <p>${bookingData.totalPrice}</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  )
}

export default Booking

