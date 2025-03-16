import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { format } from 'date-fns';
import { vi } from 'date-fns/locale';
import './BookingHistory.css';
import Header from '../../components/header/Header';

const BookingHistory = () => {
  const [bookings, setBookings] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchBookings = async () => {
      try {
        const customerInfo = JSON.parse(localStorage.getItem('customerInfor') || '{}');
        
        if (!customerInfo.email || !customerInfo.phone) {
          setError('Không tìm thấy thông tin khách hàng');
          setLoading(false);
          return;
        }

        const response = await axios.post('http://localhost:8080/api/customer/BookingList', {
          email: customerInfo.email,
          phone: customerInfo.phone
        });

        if (response.data.status === 'error') {
          throw new Error(response.data.message);
        }

        setBookings(response.data.data);
        setLoading(false);
      } catch (err) {
        setError(err.message || 'Không thể tải lịch sử đặt phòng');
        setLoading(false);
      }
    };

    fetchBookings();
  }, []);

  const formatDate = (date) => {
    return format(new Date(date), 'dd MMMM, yyyy', { locale: vi });
  };

  const getStatusText = (status) => {
    switch (status) {
      case 'COMPLETED':
        return 'Đã hoàn thành';
      case 'PENDING':
        return 'Đang chờ xác nhận';
      case 'CONFIRMED':
        return 'Đã xác nhận';
      case 'CANCELLED':
        return 'Đã hủy';
      default:
        return status;
    }
  };

  if (loading) return <div className="loading">Đang tải...</div>;
  if (error) return <div className="error">{error}</div>;
  if (bookings.length === 0) return <div className="empty">Bạn chưa có đặt phòng nào</div>;

  return (
    <>
    <div className='header'><Header/></div>
    <div className="booking-history">
      <h1>Lịch sử đặt phòng</h1>
      <div className="booking-list">
        {bookings.map((booking) => (
          <div key={`booking-${booking.id}`} className="booking-card">
            <div className="booking-history-image">
              <img 
                src={booking.image || "/placeholder.svg"} 
                alt={booking.hotelName} 
                onError={(e) => {
                  e.target.src = "/placeholder.svg";
                }}
              />
            </div>
            <div className="bookinghistory-details">
              <div className="booking-header">
                <h3>{booking.hotelName}</h3>
                <div className="booking-menu">
                  <button className="menu-button">⋮</button>
                  <div className="menu-dropdown">
                    <a href={`/booking-detail/${booking.id}`}>Xem chi tiết</a>
                    {booking.status === 'PENDING' && (
                      <button 
                        className="cancel-booking"
                        onClick={() => handleCancelBooking(booking.id)}
                      >
                        Hủy đặt phòng
                      </button>
                    )}
                  </div>
                </div>
              </div>
              <div className="booking-info">
                <p className="booking-dates">
                  {formatDate(booking.checkInDate)} - {formatDate(booking.checkOutDate)}
                </p>
                <p className="booking-address">{booking.fullAddress}</p>
                <p className={`booking-status status-${booking.status.toLowerCase()}`}>
                  {getStatusText(booking.status)}
                </p>
              </div>
            </div>
            <div className="booking-price">
              VND {booking.totalPrice.toLocaleString()}
            </div>
          </div>
        ))}
      </div>
    </div>
    </>
  );
};

export default BookingHistory;