"use client"

import { useState } from "react"
import useHotel from "./useHotel"
import HotelDetails from "./HotelDetails"
import "./index.css"
import Header from "./Header"
import Button from "./button"

export default function Home() {
  const [hotelId, setHotelId] = useState(1)
  const { hotel, loading, error } = useHotel(hotelId)

  const handleIdChange = (e) => {
    setHotelId(Number(e.target.value))
  }

  return (
    <div className="container">
      <Header></Header>
      <nav className="search-bar">
        {/* <label htmlFor="hotelId" className="myLabel">
          Enter Hotel ID:
        </label> */}
        <input
          type="number"
          id="hotelId"
          value={hotelId}
          onChange={handleIdChange}
          placeholder="Enter Hotel ID"
          className="input-field"
        />
        <Button></Button>
      </nav>
      {loading && <p>Loading...</p>}
      {error && <p className="error-msg">{error}</p>}
      {hotel && <HotelDetails hotel={hotel} />}
    </div>
  )
}

