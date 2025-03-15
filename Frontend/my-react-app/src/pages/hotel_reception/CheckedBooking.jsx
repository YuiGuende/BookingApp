import { useState, useEffect} from "react";
import axios from "axios";
import { Calendar, Clock, CheckCircle, XCircle, User, Phone, Mail, CreditCard, Hotel, DoorOpen, CalendarIcon, Search, CheckSquare, BedDouble, AlertCircle } from 'lucide-react';
import BookingDetailsModal from "../../components/BookingDetailsModel";

export default function CheckedBooking(prop) {
  const [selectedBooking, setSelectedBooking] = useState(null);
  const [error, setError] = useState(null);
  const [showModal, setShowModal] = useState(false);

    useEffect(() => {
        axios.get(`http://localhost:8080/api/staff/checkedBooking/${prop.staffId}`)
        .then((res) => {
          if (res.data && res.data.status === "success" && Array.isArray(res.data.data)) {
            setCheckedBookings(res.data.data);
          } else {
            setCheckedBookings([]);
          }
        })
        .catch((err) => {
          console.error("Error fetching checked bookings:", err);
          setError("Không thể tải danh sách đặt phòng đã kiểm tra");
          setCheckedBookings([]);
        })
    },[prop.activeTab])
    
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
    return (
        <>
        <div className="reception-content">
            <div className="reception-content-header">
              <h2>Đặt phòng đã kiểm tra</h2>
              <p>Danh sách các đặt phòng đã được kiểm tra và sẵn sàng cho khách nhận phòng</p>
            </div>
            
            {Array.isArray(checkedBookings) && checkedBookings.length > 0 ? (
              <div className="reception-bookings-grid">
                {checkedBookings.map((bookingItem) => (
                  <div key={bookingItem.booking.id} className="reception-booking-card reception-checked">
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
                      </div>
                      
                      <div className="reception-rooms-info">
                        <h4>Phòng đã đặt:</h4>
                        <div className="reception-room-tags">
                          {bookingItem.rooms.map(room => (
                            <span key={room.id} className="reception-room-tag">{room.name}</span>
                          ))}
                        </div>
                      </div>
                    </div>
                    
                    <div className="reception-booking-card-actions">
                      <button 
                        className="reception-view-details-button"
                        onClick={() => viewBookingDetails(bookingItem)}
                      >
                        Xem chi tiết
                      </button>
                    </div>
                  </div>
                ))}
              </div>
            ) : (
              <div className="reception-no-bookings">
                <img src="/placeholder.svg?height=100&width=100" alt="No bookings" />
                <p>Không có đặt phòng nào đã kiểm tra</p>
              </div>
            )}
          </div>
          {showModal && selectedBooking && (
        <BookingDetailsModal
          booking={selectedBooking}
          onClose={() => {
            setShowModal(false);
            setSelectedBooking(null);
          }}
        />
      )}
        </>
    );
}