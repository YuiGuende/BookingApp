
"use client"

import { useState, useEffect } from "react"
import { useNavigate } from "react-router-dom"
import axios from "axios"
import "./Booking.css"
import Header from "../../components/header/Header"

const Booking = () => {
    const navigate = useNavigate()
    const [bookingData, setBookingData] = useState(null)
    const [searchInfo, setSearchInfo] = useState(null)
    const [customer, setCustomer] = useState({//fix lại chỗ này, nếu customer trong session đã có thì lấy nó lưu vào đ
        name: "",
        email: "",
        phone: "",
    })
    
    const customerInfor = localStorage.getItem("customerInfor")
    useEffect(() => {
        setCustomer(JSON.parse(customerInfor))
    }, [customerInfor]
    )
 
    const [acceptNewsLetter, setAcceptNewsLetter] = useState(false)
    const groupRoomsByName = (rooms) => {
        return rooms.reduce((acc, room) => {
            const existingRoom = acc.find(r => r.name === room.name)
            if (existingRoom) {
                existingRoom.selectedQuantity += room.selectedQuantity
                existingRoom.totalPrice += room.totalPrice
            } else {
                acc.push({ ...room })
            }
            return acc
        }, [])
    }
    useEffect(() => {
        const bookingDataStr = localStorage.getItem("bookingData")
        const searchInfoStr = localStorage.getItem("searchInfor")

        if (!bookingDataStr) {
            navigate("/")
            return
        }

        const parsedBookingData = JSON.parse(bookingDataStr)
        const parsedSearchInfo = searchInfoStr ? JSON.parse(searchInfoStr) : null

        setBookingData(parsedBookingData)
        setSearchInfo(parsedSearchInfo)
    }, [navigate])

    const handleCustomerChange = (e) => {
        setCustomer({
            ...customer,
            [e.target.name]: e.target.value,
        })
    }

    const handleSubmit = async (e) => {
        e.preventDefault()

        try {
            if (bookingData.requireTT) {
                // Kiểm tra nếu có điều kiện trả trước thì chuyển hướng qua trang url của vnpay
                // Lưu thông tin đặt phòng vào localStorage để sử dụng sau khi thanh toán
                const bookingToValidate = {
                    booking: {
                        name: customer.name,
                        email: customer.email,
                        phone: customer.phone,
                        checkInDate: searchInfo?.checkInDate,
                        checkOutDate: searchInfo?.checkOutDate,
                        totalPrice: bookingData.totalPrice,
                        status: "PENDING",
                    },
                    rooms: bookingData.rooms.map((room) => ({
                        id: room.id,
                        roomName: room.name,
                    })),
                }

                const validateResponse = await axios.post("http://localhost:8080/api/customer/booking/validate", bookingToValidate)
                
                if (validateResponse.data.status === "success") {
                    console.log("Validated success")
                    const validatedBookingWithId = {
                        ...bookingToValidate,
                        booking: {
                            ...bookingToValidate.booking,
                            id: validateResponse.data.data.id
                        }
                    };
                    console.log("Booking with id",validatedBookingWithId)
                    localStorage.setItem("validatedBooking", JSON.stringify(validatedBookingWithId))
                } else {
                    alert(validateResponse.data.message)
                }
                console.log("bookingId",validateResponse.data)
                const response = await axios.post("http://localhost:8080/api/payment/create_payment_url", {
                    amount: bookingData.totalPrice,
                    bookingId:validateResponse.data.data.id
                })
                
                console.log("link",response)
               

                // Chuyển hướng đến URL thanh toán VNPAY
                window.location.href = response.data.data
                return
            }

            // Xử lý đặt phòng không cần thanh toán trước
            const bookingToValidate = {
                booking: {
                    name: customer.name,
                    email: customer.email,
                    phone: customer.phone,
                    checkInDate: searchInfo?.checkInDate,
                    checkOutDate: searchInfo?.checkOutDate,
                    totalPrice: bookingData.totalPrice,
                    status: "PENDING",
                },
                rooms: bookingData.rooms.map((room) => ({
                    id: room.id,
                    roomName: room.name,
                })),
            }
            console.log(bookingToValidate)

            const response = await axios.post("http://localhost:8080/api/customer/booking/validate", bookingToValidate)

            if (response.data.status === "success") {
                const validatedBookingWithId = {
                    ...bookingToValidate,
                    booking: {
                        ...bookingToValidate.booking,
                        id: response.data.data.id
                    }
                };
                console.log("Booking with id",validatedBookingWithId)
                localStorage.setItem("validatedBooking", JSON.stringify(validatedBookingWithId))
                navigate("/confirm")
            } else {
                alert(response.data.message)
            }
        } catch (error) {
            console.error("Error validating booking:", error)
            alert("An error occurred while validating your booking.")
        }
    }

    if (!bookingData || !searchInfo) return null

    return (
        <>
            <div className="header"><Header /></div>
            <div className="booking-page">
                <div className="booking-progress">
                    <div className="progress-step completed">
                        <span className="step-number">1</span>
                        <span className="step-text">You choose</span>
                    </div>
                    <div className="progress-step active">
                        <span className="step-number">2</span>
                        <span className="step-text">Your information</span>
                    </div>
                    <div className="progress-step">
                        <span className="step-number">3</span>
                        <span className="step-text">Complete booking</span>
                    </div>
                </div>

                <div className="booking-container">
                    <div className="booking-form">
                        <h2>Enter your information</h2>
                        <form onSubmit={handleSubmit}>
                            <div className="form-section">
                                <div className="form-group">
                                    <label htmlFor="name">Full name (English)</label>
                                    <input
                                        type="text"
                                        id="name"
                                        name="name"
                                        value={customer?.name || ""}
                                        onChange={handleCustomerChange}
                                        required
                                        placeholder="Enter full name as in the passport"
                                    />
                                </div>
                                <div className="form-group">
                                    <label htmlFor="email">Email address</label>
                                    <input
                                        type="email"
                                        id="email"
                                        name="email"
                                        value={customer?.email || ""}
                                        onChange={handleCustomerChange}
                                        required
                                    />
                                    <small>The booking confirmation email will be sent to this address</small>
                                </div>
                                <div className="form-group">
                                    <label htmlFor="phone">Phone number</label>
                                    <div className="phone-input">
                                        <select name="countryCode" defaultValue="+84">
                                            <option value="+84">VN +84</option>
                                        </select>
                                        <input
                                            type="tel"
                                            id="phone"
                                            name="phone"
                                            value={customer?.phone || ""}
                                            onChange={handleCustomerChange}
                                            required
                                        />
                                    </div>
                                </div>
                            </div>
                            <button type="submit" className="submit-button">
                                Continue
                            </button>
                        </form>
                    </div>

                    <div className="booking-summary">
                        <div className="hotel-info">
                            <h3>{bookingData.hotelName}</h3>
                            <div className="booking-dates">
                                <div>
                                    <strong>Check-in Date</strong>
                                    <p>{searchInfo.checkInDate}</p>
                                    <p>14:00 – 22:00</p>
                                </div>
                                <div>
                                    <strong>Check-out Date</strong>
                                    <p>{searchInfo.checkOutDate}</p>
                                    <p>12:00</p>
                                </div>
                            </div>
                            <div className="stay-duration">
                                <p>Total stay duration:</p>
                                <p>{calculateNights(searchInfo.checkInDate, searchInfo.checkOutDate)} đêm</p>
                            </div>
                        </div>

                        <div className="selected-rooms">
                            <h4>Selected room</h4>
                            {groupRoomsByName(bookingData.rooms).map((room, index) => (
                                <div key={index} className="room-summary">
                                    <h5>{room.name}</h5>
                                    <p>Quantity: {room.selectedQuantity}</p>
                                    <p className="room-price">
                                        {formatPrice(room.price)} x {room.selectedQuantity} = {formatPrice(room.totalPrice)}
                                    </p>
                                </div>
                            ))}
                        </div>

                        <div className="price-breakdown">
                            <div className="price-row">
                                <span>Original price</span>
                                <span>{formatPrice(bookingData.totalPrice)}</span>
                            </div>
                            <div className="price-row">
                                <span>Thuế và phí</span>
                                <span>{formatPrice(bookingData.totalPrice * 0.1)}</span>
                            </div>
                            <div className="total-price">
                                <strong>Taxes and fees</strong>
                                <strong>{formatPrice(bookingData.totalPrice * 1.1)}</strong>
                            </div>
                        </div>

                        <div className="cancellation-policy">
                            <h4>How much does it cost to cancel?</h4>
                            <p>Free cancellation before 18:00 {searchInfo.checkInDate}</p>
                        </div>
                    </div>
                </div>
            </div>
        </>
    )
}

const calculateNights = (checkIn, checkOut) => {
    const start = new Date(checkIn)
    const end = new Date(checkOut)
    return Math.ceil((end - start) / (1000 * 60 * 60 * 24))
}

const formatPrice = (price) => {
    return new Intl.NumberFormat("vi-VN", {
        style: "currency",
        currency: "VND",
    }).format(price)
}

export default Booking

