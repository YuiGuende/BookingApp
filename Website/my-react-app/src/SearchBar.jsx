"use client"

import { useState } from "react"
import Button from "./Button"
import DatePicker from "react-datepicker"
import "react-datepicker/dist/react-datepicker.css"

function SearchBar({ onSearch, initialParams }) {
  const [searchParams, setSearchParams] = useState({
    fullAddress: "",
    checkInDate: null,
    checkOutDate: null,
    capacity: 1,
    ...initialParams,
  })

  const handleChange = (name, value) => {
    setSearchParams((prev) => ({ ...prev, [name]: value }))
  }

  const handleSubmit = (e) => {
    e.preventDefault()
    onSearch(searchParams)
  }

  return (
    <form className="search-bar" onSubmit={handleSubmit}>
      <input
        type="text"
        value={searchParams.fullAddress}
        onChange={(e) => handleChange("fullAddress", e.target.value)}
        placeholder="Enter full address"
        className="input-field"
      />
      <div className="date-input">
        <DatePicker
          selected={searchParams.checkInDate}
          onChange={(date) => handleChange("checkInDate", date)}
          placeholderText="Check-in Date"
          className="input-field"
        />
        <DatePicker
          selected={searchParams.checkOutDate}
          onChange={(date) => handleChange("checkOutDate", date)}
          placeholderText="Check-out Date"
          className="input-field"
        />
      </div>
      <div className="capacity-input">
          <label htmlFor="capacity">Capacity:</label>
          <div className="capacity-controls">
            <button type="button" onClick={() => handleCapacityChange(-1)} className="capacity-button">
              -
            </button>
            <input
              type="number"
              id="capacity"
              value={searchParams.capacity}
              onChange={(e) => handleChange("capacity", Number.parseInt(e.target.value) || 1)}
              className="capacity-value"
              min="1"
            />
            <button type="button" onClick={() => handleCapacityChange(1)} className="capacity-button">
              +
            </button>
          </div>
        </div>
      <Button type="submit">Search</Button>
    </form>
  )
}

export default SearchBar

