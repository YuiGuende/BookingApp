"use client"

import { useState, useEffect } from "react"
import { useNavigate } from "react-router-dom"
import axios from "axios"
import "./Confirm.css"
import Header from "../../components/header/Header"


const Confirm = () => {
  const navigate = useNavigate()
  const [bookingData, setBookingData] = useState(null)

  useEffect(() => {
    const data = localStorage.getItem("validatedBooking")
    if (!data) {
      navigate("/booking")
      return
    }
    setBookingData(JSON.parse(data))
  }, [navigate])

  const handleConfirm = async () => {
    try {
      const response = await axios.post("http://localhost:8080/api/customer/booking/add", bookingData)
      if (response.data.status === "success") {
        alert("Booking confirmed successfully!")
        localStorage.removeItem("bookingData")
        localStorage.removeItem("validatedBooking")
        navigate("/") // Or to a booking confirmation page
      } else {
        alert(response.data.message)
      }
    } catch (error) {
      console.error("Error confirming booking:", error)
      alert("An error occurred while confirming your booking. Please try again.")
    }
  }

  if (!bookingData) return null

  return (
    <>
    <div className="header"><Header/></div>
    <div className="confirm-page">
      <div className="booking-progress">
        <div className="progress-step completed">
          <span className="step-number">1</span>
          <span className="step-text">Bạn chọn</span>
        </div>
        <div className="progress-step completed">
          <span className="step-number">2</span>
          <span className="step-text">Chi tiết về bạn</span>
        </div>
        <div className="progress-step active">
          <span className="step-number">3</span>
          <span className="step-text">Hoàn tất đặt phòng</span>
        </div>
      </div>
      <h1 id="confirm-header">Confirm Your Booking</h1>
      <div className="booking-details">
        <div className="detail-line">
          <p>
            <strong>Name:</strong>
          </p>
          <p>{bookingData.booking.name}</p>
        </div>
        <div className="detail-line">
          <p>
            <strong>Email:</strong>
          </p>
          <p>{bookingData.booking.email}</p>
        </div>
        <div className="detail-line">
          <p>
            <strong>Phone:</strong>
          </p>
          <p>{bookingData.booking.phone}</p>
        </div>
        <div className="detail-line">
          <p>
            <strong>Check-in Date:</strong>
          </p>
          <p>{bookingData.booking.checkInDate}</p>
        </div>
        <div className="detail-line">
          <p>
            <strong>Check-out Date:</strong>
          </p>
          <p>{bookingData.booking.checkOutDate}</p>
        </div>
        <div className="detail-line">
          <p>
            <strong>Total Price:</strong>
          </p>
          <p>{bookingData.booking.totalPrice}</p>
        </div>
          <h3>Rooms:</h3>
          <ul>
            {bookingData.rooms.map((bookingRoom, index) => (
              <li key={index}> ⭐{bookingRoom.roomName}
              </li>
            ))}
          </ul>
        </div>
      </div>
      <button id='confirmBtn' onClick={handleConfirm}>Confirm Booking</button>
    </>
  )
}

export default Confirm

