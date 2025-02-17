"use client"

import { useState } from "react"

function Capacity({ maxAdults, maxChildren, rooms, onChange }) {
  const [isOpen, setIsOpen] = useState(false)

  const handleChange = (type, change) => {
    let newValue
    switch (type) {
      case "maxAdults":
        newValue = Math.max(1, maxAdults + change)
        break
      case "maxChildren":
        newValue = Math.max(0, maxChildren + change)
        break
      case "rooms":
        newValue = Math.max(1, rooms + change)
        break
      default:
        return
    }
    onChange(type, newValue)
  }

  const buttonText = `${maxAdults} adults · ${maxChildren} children · ${rooms} room`

  return (
    <div className="capacity-dropdown">
      <button type="button" className="capacity-button" onClick={() => setIsOpen(!isOpen)}>
        <div className="capacity-button-content">
          <span>{buttonText}</span><i className="arrow down"></i>
        </div>
      </button>

      {isOpen && (
        <div className="capacity-dropdown-content">
          <div className="capacity-row">
            <span>Adults</span>
            <div className="counter-controls">
              <button
                type="button"
                onClick={() => handleChange("maxAdults", -1)}
                className="counter-button"
                disabled={maxAdults <= 1}
              >
                −
              </button>
              <span className="counter-value">{maxAdults}</span>
              <button type="button" onClick={() => handleChange("maxAdults", 1)} className="counter-button">
                +
              </button>
            </div>
          </div>

          <div className="capacity-row">
            <span>Children</span>
            <div className="counter-controls">
              <button
                type="button"
                onClick={() => handleChange("maxChildren", -1)}
                className="counter-button"
                disabled={maxChildren <= 0}
              >
                −
              </button>
              <span className="counter-value">{maxChildren}</span>
              <button type="button" onClick={() => handleChange("maxChildren", 1)} className="counter-button">
                +
              </button>
            </div>
          </div>

          <div className="capacity-row">
            <span>Rooms</span>
            <div className="counter-controls">
              <button
                type="button"
                onClick={() => handleChange("rooms", -1)}
                className="counter-button"
                disabled={rooms <= 1}
              >
                −
              </button>
              <span className="counter-value">{rooms}</span>
              <button type="button" onClick={() => handleChange("rooms", 1)} className="counter-button">
                +
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  )
}

export default Capacity

