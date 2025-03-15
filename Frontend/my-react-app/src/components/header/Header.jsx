"use client"

import "./HeaderStyles.css"
import { useNavigate } from "react-router-dom"
import { useState, useEffect, useRef } from "react"

function Header() {
  const navigate = useNavigate()
  const [user, setUser] = useState(null)
  const [showDropdown, setShowDropdown] = useState(false)
  const dropdownRef = useRef(null)

  useEffect(() => {
    const userInfo = localStorage.getItem("customerInfor")
    if (userInfo) {
      setUser(JSON.parse(userInfo))
    }
  }, [])

  useEffect(() => {
    // Close dropdown when clicking outside
    function handleClickOutside(event) {
      if (dropdownRef.current && !dropdownRef.current.contains(event.target)) {
        setShowDropdown(false)
      }
    }

    document.addEventListener("mousedown", handleClickOutside)
    return () => {
      document.removeEventListener("mousedown", handleClickOutside)
    }
  }, [])

  const handleSignOut = () => {
    localStorage.removeItem("customerInfor")
    setUser(null)
    setShowDropdown(false)
    navigate("/")
  }

  return (
    <header>
      <a className="return-home" href="/">
        Booking
      </a>
      <div className="startBtn">
        {user ? (
          <div className="user-dropdown-container" ref={dropdownRef}>
            <button className="user-avatar-button" onClick={() => setShowDropdown(!showDropdown)}>
              <div className="user-avatar">{user.name ? user.name.charAt(0).toUpperCase() : "U"}</div>
            </button>

            {showDropdown && (
              <div className="dropdown-menu">
                <div className="user-info">
                  <div className="user-avatar-large">{user.name ? user.name.charAt(0).toUpperCase() : "U"}</div>
                  <div className="user-name">{user.name || "User"}</div>
                </div>
                <div className="dropdown-divider"></div>

                <button
                  className="dropdown-item"
                  onClick={() => {
                    navigate("/bookingHistory")
                    setShowDropdown(false)
                  }}
                >
                  <span className="dropdown-icon">📋</span>
                  <span>Những nơi đã book</span>
                </button>

                <div className="dropdown-divider"></div>

                <button className="dropdown-item" onClick={handleSignOut}>
                  <span className="dropdown-icon">🚪</span>
                  <span>Đăng xuất</span>
                </button>
              </div>
            )}
          </div>
        ) : (
          <>
            <button id="loginBtn" onClick={() => navigate("/Login")}>
              Log In
            </button>
            <button id="signupBtn" onClick={() => navigate("/Signup")}>
              Sign Up
            </button>
          </>
        )}
      </div>
    </header>
  )
}

export default Header

