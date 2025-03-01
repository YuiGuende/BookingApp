"use client"

import { useState } from "react"
import "./CapacityStyles.css"

function Capacity({ adultQuantity, childrenQuantity, roomQuantity, onChange }) {
  const [isOpen, setIsOpen] = useState(false)

  const handleChange = (type, change) => {
    let newValue
    switch (type) {
      case "adultQuantity":
        newValue = Math.max(1, adultQuantity + change)
        break
      case "childrenQuantity":
        newValue = Math.max(0, childrenQuantity + change)
        break
      case "rooms":
        newValue = Math.max(1, roomQuantity + change)
        break
      default:
        return
    }
    onChange(type, newValue)
  }

  const buttonText = `${adultQuantity} adults · ${childrenQuantity} children · ${roomQuantity} room`

  return (
    <div className="capacity-dropdown-button">
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
                onClick={() => handleChange("adultQuantity", -1)}
                className="counter-button"
                disabled={adultQuantity <= 1}
              >
                −
              </button>
              <span className="counter-value">{adultQuantity}</span>
              <button type="button" onClick={() => handleChange("adultQuantity", 1)} className="counter-button">
                +
              </button>
            </div>
          </div>

          <div className="capacity-row">
            <span>Children</span>
            <div className="counter-controls">
              <button
                type="button"
                onClick={() => handleChange("childrenQuantity", -1)}
                className="counter-button"
                disabled={childrenQuantity <= 0}
              >
                −
              </button>
              <span className="counter-value">{childrenQuantity}</span>
              <button type="button" onClick={() => handleChange("childrenQuantity", 1)} className="counter-button">
                +
              </button>
            </div>
          </div>

          <div className="capacity-row">
            <span>Rooms</span>
            <div className="counter-controls">
              <button
                type="button"
                onClick={() => handleChange("roomQuantity", -1)}
                className="counter-button"
                disabled={roomQuantity <= 1}
              >
                −
              </button>
              <span className="counter-value">{roomQuantity}</span>
              <button type="button" onClick={() => handleChange("roomQuantity", 1)} className="counter-button">
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