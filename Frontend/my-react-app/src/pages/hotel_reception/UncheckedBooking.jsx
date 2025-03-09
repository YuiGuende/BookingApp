import { useState} from "react";
import axios from "axios";
import { Calendar, Clock, CheckCircle, XCircle, User, Phone, Mail, CreditCard, Hotel, DoorOpen, CalendarIcon, Search, CheckSquare, BedDouble, AlertCircle } from 'lucide-react';

export default function UncheckedBooking(prop) {
    const [uncheckedBookings, setUncheckedBookings] = useState([]);
    axios.get(`http://localhost:8080/api/staff/unCheckedBooking/${prop.staffId}`)
        .then((res) => {
          console.log("Unchecked bookings:", res.data);
          if (res.data && res.data.status === "success" && Array.isArray(res.data.data)) {
            setUncheckedBookings(res.data.data);
          } else {
            setUncheckedBookings([]);
          }
        })
        .catch((err) => {
          console.error("Error fetching unchecked bookings:", err);
          setError("Không thể tải danh sách đặt phòng chưa kiểm tra");
          setUncheckedBookings([]);
        })
        .finally(() => {
          setLoading(false);
        });
    // Format date
    const formatDate = (dateString) => {
        const options = { day: '2-digit', month: '2-digit', year: 'numeric' };
        return new Date(dateString).toLocaleDateString('vi-VN', options);
    };

    // Xem chi tiết booking
    const viewBookingDetails = (booking) => {
        setSelectedBooking(booking);
        setShowModal(true);
    };

    // Đánh dấu booking đã kiểm tra
    const markAsChecked = (bookingId) => {
        setLoading(true);
        axios.post("http://localhost:8080/api/staff/markBookingAsChecked", [bookingId])
        .then(() => {
        // Cập nhật UI
        const markedBooking = uncheckedBookings.find(item => item.booking.id === bookingId);
        setUncheckedBookings(prev => prev.filter(item => item.booking.id !== bookingId));

        if (markedBooking) {
          setCheckedBookings(prev => [...prev, markedBooking]);
        }

        // Hiển thị thông báo thành công
        alert("Đã đánh dấu đặt phòng là đã kiểm tra!");
      })
      .catch((err) => {
        console.error("Error marking booking as checked:", err);
        alert("Không thể đánh dấu đặt phòng. Vui lòng thử lại!");
      })
      .finally(() => {
        setLoading(false);
      });
    };
    return (
        <>
            <div className="reception-content">
            <div className="reception-content-header">
              <h2>Đặt phòng chưa kiểm tra</h2>
              <p>Danh sách các đặt phòng cần được kiểm tra trước khi khách nhận phòng</p>
            </div>

            {Array.isArray(uncheckedBookings) && uncheckedBookings.length > 0 ? (
              <div className="reception-bookings-grid">
                {uncheckedBookings.map((bookingItem) => (
                  <div key={bookingItem.booking.id} className="reception-booking-card">
                    <div className="reception-booking-card-header">
                      <h3>{bookingItem.booking.name}</h3>
                      <span className={`reception-status-badge reception-status-${bookingItem.booking.status.toLowerCase()}`}>
                        {bookingItem.booking.status}
                      </span>
                    </div>

                    <div className="reception-booking-card-content">
                      <div className="reception-booking-info">
                        <div className="reception-info-item">
                          <Calendar size={16} />
                          <span>{formatDate(bookingItem.booking.checkInDate)} - {formatDate(bookingItem.booking.checkOutDate)}</span>
                        </div>
                        <div className="reception-info-item">
                          <Phone size={16} />
                          <span>{bookingItem.booking.phone}</span>
                        </div>
                        <div className="reception-info-item">
                          <Mail size={16} />
                          <span>{bookingItem.booking.email}</span>
                        </div>
                      </div>

                      <div className="reception-rooms-info">
                        <h4>Phòng đã đặt:</h4>
                        <div className="reception-room-tags">
                          {bookingItem.rooms.map(room => (
                            <span key={room.id} className="reception-room-tag">{room.name}</span>
                          ))}
                        </div>
                      </div>

                      <div className="reception-price-info">
                        <span className="reception-price-label">Tổng tiền:</span>
                        <span className="reception-price-value">{bookingItem.booking.totalPrice.toLocaleString('vi-VN')} VND</span>
                      </div>

                      <div className="reception-payment-info">
                        <span className={`reception-payment-status reception-payment-${bookingItem.booking.paid ? 'paid' : 'unpaid'}`}>
                          {bookingItem.booking.paid ? 'Đã thanh toán' : 'Chưa thanh toán'}
                        </span>
                      </div>
                    </div>

                    <div className="reception-booking-card-actions">
                      <button
                        className="reception-view-details-button"
                        onClick={() => viewBookingDetails(bookingItem)}
                      >
                        Xem chi tiết
                      </button>
                      <button
                        className="reception-check-button"
                        onClick={() => markAsChecked(bookingItem.booking.id)}
                      >
                        Đánh dấu đã kiểm tra
                      </button>
                    </div>
                  </div>
                ))}
              </div>
            ) : (
              <div className="reception-no-bookings">
                <img src="/placeholder.svg?height=100&width=100" alt="No bookings" />
                <p>Không có đặt phòng nào cần kiểm tra</p>
              </div>
            )}
          </div>
        </>
    );
}