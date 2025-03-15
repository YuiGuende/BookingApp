// import { useState, useEffect } from "react";
// import axios from "axios";
// import { Calendar, Clock, CheckCircle, XCircle, User, Phone, Mail, CreditCard, Hotel, DoorOpen, CalendarIcon, Search, CheckSquare, BedDouble, AlertCircle } from 'lucide-react';

// const BookingDetailsModal = ({ booking, onClose }) => {
//     const [activeTab, setActiveTab] = useState("unchecked-bookings");
//     const [staffId, setStaffId] = useState(null);
//     useEffect(() => {
//         const userData = localStorage.getItem("customerInfor");
//         if (userData) {
//             const parsed = JSON.parse(userData);
//             setStaffId(parsed.id);
//         }
//     }, []);
//     const formatDate = (dateString) => {
//         const options = { day: '2-digit', month: '2-digit', year: 'numeric' };
//         return new Date(dateString).toLocaleDateString('vi-VN', options);
//     };
//     // Tính số ngày lưu trú
//     const calculateStayDuration = (checkIn, checkOut) => {
//         const start = new Date(checkIn);
//         const end = new Date(checkOut);
//         const diffTime = Math.abs(end - start);
//         const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24));
//         return diffDays;
//     };
//     const markAsChecked = (bookingId) => {
//         console.log("booking idddddd",bookingId)
//         axios.post(`http://localhost:8080/api/staff/markBookingAsChecked/${staffId}`, [bookingId])
//             .then(() => {
//                 // Cập nhật UI
//                 const markedBooking = uncheckedBookings.find(item => item.booking.id === bookingId);
//                 setUncheckedBookings(prev => prev.filter(item => item.booking.id !== bookingId));

//                 if (markedBooking) {
//                     setCheckedBookings(prev => [...prev, markedBooking]);
//                 }

//                 // Hiển thị thông báo thành công
//                 alert("Đã đánh dấu đặt phòng là đã kiểm tra!");
//             })
//             .catch((err) => {
//                 console.error("Error marking booking as checked:", err);
//                 alert("Không thể đánh dấu đặt phòng. Vui lòng thử lại!");
//             })
//     };
//     if (!booking) return null;

//     return (
//         <div className="reception-modal-overlay">
//             <div className="reception-modal-content">
//                 <div className="reception-modal-header">
//                     <h2>Chi tiết đặt phòng</h2>
//                     <button className="reception-close-button" onClick={onClose}>×</button>
//                 </div>
//                 <div className="reception-modal-body">
//                     <div className="reception-booking-detail-section">
//                         <h3><User size={18} /> Thông tin khách hàng</h3>
//                         <div className="reception-detail-row">
//                             <span className="reception-detail-label">Tên:</span>
//                             <span className="reception-detail-value">{booking.booking.name}</span>
//                         </div>
//                         <div className="reception-detail-row">
//                             <span className="reception-detail-label">Email:</span>
//                             <span className="reception-detail-value">{booking.booking.email}</span>
//                         </div>
//                         <div className="reception-detail-row">
//                             <span className="reception-detail-label">Điện thoại:</span>
//                             <span className="reception-detail-value">{booking.booking.phone}</span>
//                         </div>
//                     </div>

//                     <div className="reception-booking-detail-section">
//                         <h3><CalendarIcon size={18} /> Thông tin đặt phòng</h3>
//                         <div className="reception-detail-row">
//                             <span className="reception-detail-label">Ngày nhận phòng:</span>
//                             <span className="reception-detail-value">{formatDate(booking.booking.checkInDate)}</span>
//                         </div>
//                         <div className="reception-detail-row">
//                             <span className="reception-detail-label">Ngày trả phòng:</span>
//                             <span className="reception-detail-value">{formatDate(booking.booking.checkOutDate)}</span>
//                         </div>
//                         <div className="reception-detail-row">
//                             <span className="reception-detail-label">Số đêm:</span>
//                             <span className="reception-detail-value">{calculateStayDuration(booking.booking.checkInDate, booking.booking.checkOutDate)}</span>
//                         </div>
//                         <div className="reception-detail-row">
//                             <span className="reception-detail-label">Trạng thái:</span>
//                             <span className={`reception-status-badge reception-status-${booking.booking.status.toLowerCase()}`}>
//                                 {booking.booking.status}
//                             </span>
//                         </div>
//                         <div className="reception-detail-row">
//                             <span className="reception-detail-label">Thanh toán:</span>
//                             <span className={`reception-payment-status reception-payment-${booking.booking.paid ? 'paid' : 'unpaid'}`}>
//                                 {booking.booking.paid ? 'Đã thanh toán' : 'Chưa thanh toán'}
//                             </span>
//                         </div>
//                     </div>

//                     <div className="reception-booking-detail-section">
//                         <h3><Hotel size={18} /> Thông tin phòng</h3>
//                         <div className="reception-rooms-grid">
//                             {booking.rooms.map(room => (
//                                 <div key={room.id} className="reception-room-card">
//                                     <div className="reception-room-name">{room.name}</div>
//                                     <div className="reception-room-type">{room.type}</div>
//                                 </div>
//                             ))}
//                         </div>
//                     </div>

//                     <div className="reception-booking-detail-section">
//                         <h3><CreditCard size={18} /> Thông tin thanh toán</h3>
//                         <div className="reception-detail-row">
//                             <span className="reception-detail-label">Tổng tiền:</span>
//                             <span className="reception-detail-value reception-price">{booking.booking.totalPrice.toLocaleString('vi-VN')} VND</span>
//                         </div>
//                     </div>
//                 </div>
//                 <div className="reception-modal-footer">
//                     {activeTab === "unchecked-bookings" && (
//                         <button
//                             className="reception-check-button"
//                             onClick={() => {
                                
//                                 markAsChecked(booking.booking.id);
//                                 onClose();
//                             }}
//                         >
//                             <CheckCircle size={16} /> Đánh dấu đã kiểm tra
//                         </button>
//                     )}
//                     <button className="reception-cancel-button" onClick={onClose}>Đóng</button>
//                 </div>
//             </div>
//         </div>
//     );
// }
// export default BookingDetailsModal