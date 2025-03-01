"use client"

import { useState, useEffect } from "react"
import { useNavigate } from "react-router-dom"
import axios from "axios"
import "./Confirm.css"

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
    <div className="confirm-page">
      <h1>Confirm Your Booking</h1>
      <div className="booking-details">
        <h2>Booking Details</h2>
        <p>
          <strong>Name:</strong> {bookingData.booking.customer.name}
        </p>
        <p>
          <strong>Email:</strong> {bookingData.booking.customer.email}
        </p>
        <p>
          <strong>Phone:</strong> {bookingData.booking.customer.phone}
        </p>
        <p>
          <strong>Check-in Date:</strong> {bookingData.booking.checkInDate}
        </p>
        <p>
          <strong>Check-out Date:</strong> {bookingData.booking.checkOutDate}
        </p>
        <p>
          <strong>Total Price:</strong> ${bookingData.booking.totalPrice}
        </p>
        <h3>Rooms:</h3>
        <ul>
          {bookingData.rooms.map((bookingRoom, index) => (
            <li key={index}>
              Room ID: {bookingRoom.id}, Quantity: {bookingRoom.quantity}
            </li>
          ))}
        </ul>
      </div>
      <button onClick={handleConfirm}>Confirm Booking</button>
    </div>
  )
}

export default Confirm

