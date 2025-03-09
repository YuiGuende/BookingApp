import { useState, useEffect } from "react";
import axios from "axios";
import "./ReceptionStyles.css";
import CustomerDatePicker from "../../utils/CustomerDatePicker";
import Header from "../../components/header/Header";
import { Calendar, Clock, CheckCircle, XCircle, User, Phone, Mail, CreditCard, Hotel, DoorOpen, CalendarIcon, Search, CheckSquare, BedDouble, AlertCircle } from 'lucide-react';
import Checkin from "./Checkin";
import CheckedBooking from "./CheckedBooking";
import UncheckedBooking from "./UncheckedBooking";

export default function Hotel_Reception() {
  const [activeTab, setActiveTab] = useState("unchecked-bookings");
  const [uncheckedBookings, setUncheckedBookings] = useState([]);
  const [checkedBookings, setCheckedBookings] = useState([]);
  const [staffId, setStaffId] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [selectedBooking, setSelectedBooking] = useState(null);
  const [showModal, setShowModal] = useState(false);

  // Check-in form state
  const [checkinForm, setCheckinForm] = useState({
    name: "",
    phone: "",
    email: "",
    roomIds: "",
    checkInDate: new Date(),
    checkOutDate: new Date(new Date().setDate(new Date().getDate() + 1)),
  });
  const [validatedBooking, setValidatedBooking] = useState(null);

  // Room search state
  const [hotelId, setHotelId] = useState("");
  const [roomSearch, setRoomSearch] = useState({
    roomQuantity: 1,
    adultQuantity: 1,
    childrenQuantity: 0,
    checkInDate: new Date(),
    checkOutDate: new Date(new Date().setDate(new Date().getDate() + 1)),
  });
  const [availableRooms, setAvailableRooms] = useState([]);
  const [occupiedRooms, setOccupiedRooms] = useState([]);
  const [upcomingCheckins, setUpcomingCheckins] = useState([]);

  // Lấy staffId từ localStorage
  useEffect(() => {
    const userData = localStorage.getItem("customerInfor");
    if (userData) {
      const parsed = JSON.parse(userData);
      setStaffId(parsed.id);

      // Nếu có hotelId trong thông tin staff, set luôn
      if (parsed.hotelId) {
        setHotelId(parsed.hotelId);
      }
    }
  }, []);

  // Fetch data khi tab thay đổi
  useEffect(() => {
    if (!staffId) return;

    setLoading(true);
    setError(null);

    if (activeTab === "unchecked-bookings") {

    }

    if (activeTab === "checked-bookings") {

    }

    if (activeTab === "rooms" && hotelId) {
      // Fetch available rooms
      searchAvailableRooms();

      // Fetch occupied rooms (placeholder for now)
      axios.get(`http://localhost:8080/api/staff/getOccupiedRoom/${hotelId}`)
        .then((res) => {
          if (res.data && res.data.status === "success") {
            setOccupiedRooms(res.data.data || []);
          }
        })
        .catch((err) => {
          console.error("Error fetching occupied rooms:", err);
          // Placeholder data for now
          setOccupiedRooms([
            { id: 101, name: "Deluxe Room 101", type: "DELUXE", guestName: "Nguyễn Văn A", checkOutDate: "2025-03-15" },
            { id: 102, name: "Standard Room 102", type: "STANDARD", guestName: "Trần Thị B", checkOutDate: "2025-03-16" },
          ]);
        });

      // Placeholder for upcoming check-ins
      setUpcomingCheckins([
        { id: 103, name: "Suite 103", type: "SUITE", guestName: "Lê Văn C", checkInDate: "2025-03-10" },
        { id: 104, name: "Deluxe Room 104", type: "DELUXE", guestName: "Phạm Thị D", checkInDate: "2025-03-11" },
      ]);
    }
  }, [activeTab, staffId, hotelId]);

  // Đánh dấu booking đã kiểm tra
  // const markAsChecked = (bookingId) => {
  //   setLoading(true);
  //   axios.post("http://localhost:8080/api/staff/markBookingAsChecked", [bookingId])
  //     .then(() => {
  //       // Cập nhật UI
  //       const markedBooking = uncheckedBookings.find(item => item.booking.id === bookingId);
  //       setUncheckedBookings(prev => prev.filter(item => item.booking.id !== bookingId));

  //       if (markedBooking) {
  //         setCheckedBookings(prev => [...prev, markedBooking]);
  //       }

  //       // Hiển thị thông báo thành công
  //       alert("Đã đánh dấu đặt phòng là đã kiểm tra!");
  //     })
  //     .catch((err) => {
  //       console.error("Error marking booking as checked:", err);
  //       alert("Không thể đánh dấu đặt phòng. Vui lòng thử lại!");
  //     })
  //     .finally(() => {
  //       setLoading(false);
  //     });
  // };

  // Xem chi tiết booking
  const viewBookingDetails = (booking) => {
    setSelectedBooking(booking);
    setShowModal(true);
  };

  // Handle check-in form change
  const handleCheckinFormChange = (e) => {
    const { name, value } = e.target;
    setCheckinForm(prev => ({
      ...prev,
      [name]: value
    }));

    // Reset validated booking when form changes
    if (validatedBooking) {
      setValidatedBooking(null);
    }
  };

  // Handle date change for check-in form
  const handleCheckinDateChange = (dates) => {
    setCheckinForm(prev => ({
      ...prev,
      checkInDate: dates.startDate,
      checkOutDate: dates.endDate
    }));

    // Reset validated booking when dates change
    if (validatedBooking) {
      setValidatedBooking(null);
    }
  };

  // Validate booking
  const validateBooking = () => {
    // Check if all required fields are filled
    if (!checkinForm.name || !checkinForm.phone || !checkinForm.email || !checkinForm.roomIds) {
      alert("Vui lòng điền đầy đủ thông tin!");
      return;
    }

    setLoading(true);

    // Parse room IDs from comma-separated string to array of numbers
    const roomIds = checkinForm.roomIds.split(",").map(id => id.trim()).filter(id => id);

    // Create booking object
    const bookingToValidate = {
      booking: {
        name: checkinForm.name,
        email: checkinForm.email,
        phone: checkinForm.phone,
        checkInDate: checkinForm.checkInDate.toISOString().split('T')[0],
        checkOutDate: checkinForm.checkOutDate.toISOString().split('T')[0],
      },
      rooms: roomIds.map(id => ({ id: parseInt(id) }))
    };

    axios.post("http://localhost:8080/api/customer/booking/validate", bookingToValidate)
      .then((res) => {
        if (res.data && res.data.status === "success") {
          setValidatedBooking({
            ...bookingToValidate,
            booking: {
              ...bookingToValidate.booking,
              id: res.data.data.id,
              totalPrice: res.data.data.totalPrice,
              status: res.data.data.status,
              paid: res.data.data.paid
            }
          });
          alert("Đặt phòng hợp lệ! Vui lòng xác nhận để hoàn tất.");
        } else {
          alert(res.data.message || "Có lỗi xảy ra khi xác thực đặt phòng.");
        }
      })
      .catch((err) => {
        console.error("Error validating booking:", err);
        alert(err.response?.data?.message || "Không thể xác thực đặt phòng. Vui lòng thử lại!");
      })
      .finally(() => {
        setLoading(false);
      });
  };

  // Confirm booking
  const confirmBooking = () => {
    if (!validatedBooking) {
      alert("Vui lòng xác thực đặt phòng trước!");
      return;
    }

    setLoading(true);

    axios.post("http://localhost:8080/api/customer/booking/add", validatedBooking)
      .then((res) => {
        if (res.data && res.data.status === "success") {
          alert("Đặt phòng thành công!");
          // Reset form
          setCheckinForm({
            name: "",
            phone: "",
            email: "",
            roomIds: "",
            checkInDate: new Date(),
            checkOutDate: new Date(new Date().setDate(new Date().getDate() + 1)),
          });
          setValidatedBooking(null);
        } else {
          alert(res.data.message || "Có lỗi xảy ra khi đặt phòng.");
        }
      })
      .catch((err) => {
        console.error("Error confirming booking:", err);
        alert(err.response?.data?.message || "Không thể đặt phòng. Vui lòng thử lại!");
      })
      .finally(() => {
        setLoading(false);
      });
  };

  // Handle room search form change
  const handleRoomSearchChange = (e) => {
    const { name, value } = e.target;
    setRoomSearch(prev => ({
      ...prev,
      [name]: name === 'roomQuantity' || name === 'adultQuantity' || name === 'childrenQuantity'
        ? parseInt(value)
        : value
    }));
  };

  // Handle date change for room search
  const handleRoomSearchDateChange = (dates) => {
    setRoomSearch(prev => ({
      ...prev,
      checkInDate: dates.startDate,
      checkOutDate: dates.endDate
    }));
  };

  // Search available rooms
  const searchAvailableRooms = () => {
    if (!hotelId) {
      alert("Vui lòng nhập ID khách sạn!");
      return;
    }

    setLoading(true);

    const params = {
      roomQuantity: roomSearch.roomQuantity,
      adultQuantity: roomSearch.adultQuantity,
      childrenQuantity: roomSearch.childrenQuantity,
      checkInDate: roomSearch.checkInDate.toISOString().split('T')[0],
      checkOutDate: roomSearch.checkOutDate.toISOString().split('T')[0]
    };

    axios.get(`http://localhost:8080/api/customer/getHotel/${hotelId}`, { params })
      .then((res) => {
        if (res.data && res.data.status === "success") {
          setAvailableRooms(res.data.data.rooms || []);
        } else {
          setAvailableRooms([]);
          alert(res.data.message || "Không tìm thấy phòng phù hợp.");
        }
      })
      .catch((err) => {
        console.error("Error searching rooms:", err);
        setAvailableRooms([]);
        alert(err.response?.data?.message || "Không thể tìm kiếm phòng. Vui lòng thử lại!");
      })
      .finally(() => {
        setLoading(false);
      });
  };

  // Format date
  const formatDate = (dateString) => {
    const options = { day: '2-digit', month: '2-digit', year: 'numeric' };
    return new Date(dateString).toLocaleDateString('vi-VN', options);
  };

  // Tính số ngày lưu trú
  const calculateStayDuration = (checkIn, checkOut) => {
    const start = new Date(checkIn);
    const end = new Date(checkOut);
    const diffTime = Math.abs(end - start);
    const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24));
    return diffDays;
  };

  // Modal chi tiết booking
  const BookingDetailsModal = ({ booking, onClose }) => {
    if (!booking) return null;

    return (
      <div className="reception-modal-overlay">
        <div className="reception-modal-content">
          <div className="reception-modal-header">
            <h2>Chi tiết đặt phòng</h2>
            <button className="reception-close-button" onClick={onClose}>×</button>
          </div>
          <div className="reception-modal-body">
            <div className="reception-booking-detail-section">
              <h3><User size={18} /> Thông tin khách hàng</h3>
              <div className="reception-detail-row">
                <span className="reception-detail-label">Tên:</span>
                <span className="reception-detail-value">{booking.booking.name}</span>
              </div>
              <div className="reception-detail-row">
                <span className="reception-detail-label">Email:</span>
                <span className="reception-detail-value">{booking.booking.email}</span>
              </div>
              <div className="reception-detail-row">
                <span className="reception-detail-label">Điện thoại:</span>
                <span className="reception-detail-value">{booking.booking.phone}</span>
              </div>
            </div>

            <div className="reception-booking-detail-section">
              <h3><CalendarIcon size={18} /> Thông tin đặt phòng</h3>
              <div className="reception-detail-row">
                <span className="reception-detail-label">Ngày nhận phòng:</span>
                <span className="reception-detail-value">{formatDate(booking.booking.checkInDate)}</span>
              </div>
              <div className="reception-detail-row">
                <span className="reception-detail-label">Ngày trả phòng:</span>
                <span className="reception-detail-value">{formatDate(booking.booking.checkOutDate)}</span>
              </div>
              <div className="reception-detail-row">
                <span className="reception-detail-label">Số đêm:</span>
                <span className="reception-detail-value">{calculateStayDuration(booking.booking.checkInDate, booking.booking.checkOutDate)}</span>
              </div>
              <div className="reception-detail-row">
                <span className="reception-detail-label">Trạng thái:</span>
                <span className={`reception-status-badge reception-status-${booking.booking.status.toLowerCase()}`}>
                  {booking.booking.status}
                </span>
              </div>
              <div className="reception-detail-row">
                <span className="reception-detail-label">Thanh toán:</span>
                <span className={`reception-payment-status reception-payment-${booking.booking.paid ? 'paid' : 'unpaid'}`}>
                  {booking.booking.paid ? 'Đã thanh toán' : 'Chưa thanh toán'}
                </span>
              </div>
            </div>

            <div className="reception-booking-detail-section">
              <h3><Hotel size={18} /> Thông tin phòng</h3>
              <div className="reception-rooms-grid">
                {booking.rooms.map(room => (
                  <div key={room.id} className="reception-room-card">
                    <div className="reception-room-name">{room.name}</div>
                    <div className="reception-room-type">{room.type}</div>
                  </div>
                ))}
              </div>
            </div>

            <div className="reception-booking-detail-section">
              <h3><CreditCard size={18} /> Thông tin thanh toán</h3>
              <div className="reception-detail-row">
                <span className="reception-detail-label">Tổng tiền:</span>
                <span className="reception-detail-value reception-price">{booking.booking.totalPrice.toLocaleString('vi-VN')} VND</span>
              </div>
            </div>
          </div>
          <div className="reception-modal-footer">
            {activeTab === "unchecked-bookings" && (
              <button
                className="reception-check-button"
                onClick={() => {
                  markAsChecked(booking.booking.id);
                  onClose();
                }}
              >
                <CheckCircle size={16} /> Đánh dấu đã kiểm tra
              </button>
            )}
            <button className="reception-cancel-button" onClick={onClose}>Đóng</button>
          </div>
        </div>
      </div>
    );
  };

  return (
    <>
      <div className="reception-header-bar"><Header /></div>
      <div className="reception-container">
        <h1 className="reception-title">
          <Hotel className="reception-title-icon" />
          Quản lý lễ tân
        </h1>

        <div className="reception-tabs">
          <button
            className={activeTab === "unchecked-bookings" ? "reception-tab-active" : ""}
            onClick={() => setActiveTab("unchecked-bookings")}
          >
            <XCircle size={16} /> Đặt phòng chưa kiểm tra
          </button>
          <button
            className={activeTab === "checked-bookings" ? "reception-tab-active" : ""}
            onClick={() => setActiveTab("checked-bookings")}
          >
            <CheckCircle size={16} /> Đặt phòng đã kiểm tra
          </button>
          <button
            className={activeTab === "checkin" ? "reception-tab-active" : ""}
            onClick={() => setActiveTab("checkin")}
          >
            <Calendar size={16} /> Nhận phòng
          </button>
          <button
            className={activeTab === "checkout" ? "reception-tab-active" : ""}
            onClick={() => setActiveTab("checkout")}
          >
            <Clock size={16} /> Trả phòng
          </button>
          <button
            className={activeTab === "rooms" ? "reception-tab-active" : ""}
            onClick={() => setActiveTab("rooms")}
          >
            <DoorOpen size={16} /> Trạng thái phòng
          </button>
        </div>

        {/* {loading && (
          <div className="reception-loading-container">
            <div className="reception-loading-spinner"></div>
            <p>Đang tải dữ liệu...</p>
          </div>
        )} */}

        {error && (
          <div className="reception-error-message">
            <p>{error}</p>
          </div>
        )}

        {activeTab === "unchecked-bookings" && (
          <UncheckedBooking staffId={staffId} activeTab={activeTab} />
        )}

        {activeTab === "checked-bookings" && (
          <CheckedBooking staffId={staffId} activeTab={activeTab} />
        )}

        {activeTab === "checkin" && (
          <Checkin />
        )}

        {activeTab === "checkout" && (
          <div className="reception-content">
            <div className="reception-content-header">
              <h2>Trả phòng</h2>
              <p>Chức năng này sẽ được phát triển trong tương lai</p>
            </div>

            <div className="reception-coming-soon">
              <img src="/placeholder.svg?height=150&width=150" alt="Coming soon" />
              <p>Chức năng đang được phát triển</p>
            </div>
          </div>
        )}

        {activeTab === "rooms" && (
          <div className="reception-content">
            <div className="reception-content-header">
              <h2>Trạng thái phòng</h2>
              <p>Tìm kiếm và xem trạng thái hiện tại của các phòng</p>
            </div>

            <div className="reception-room-search">
              <div className="reception-search-form">
                <div className="reception-search-row">
                  <div className="reception-search-group">
                    <label>ID Khách sạn</label>
                    <input
                      type="text"
                      value={hotelId}
                      onChange={(e) => setHotelId(e.target.value)}
                      placeholder="Nhập ID khách sạn"
                      className="reception-input-line"
                    />
                  </div>

                  <div className="reception-search-group">
                    <label>Số lượng phòng</label>
                    <input
                      type="number"
                      name="roomQuantity"
                      value={roomSearch.roomQuantity}
                      onChange={handleRoomSearchChange}
                      min="1"
                      className="reception-input-line"
                    />
                  </div>

                  <div className="reception-search-group">
                    <label>Số người lớn</label>
                    <input
                      type="number"
                      name="adultQuantity"
                      value={roomSearch.adultQuantity}
                      onChange={handleRoomSearchChange}
                      min="1"
                      className="reception-input-line"
                    />
                  </div>

                  <div className="reception-search-group">
                    <label>Số trẻ em</label>
                    <input
                      type="number"
                      name="childrenQuantity"
                      value={roomSearch.childrenQuantity}
                      onChange={handleRoomSearchChange}
                      min="0"
                      className="reception-input-line"
                    />
                  </div>
                </div>

                <div className="reception-search-row">
                  <div className="reception-search-group reception-search-dates">
                    <label>Thời gian lưu trú</label>
                    <CustomerDatePicker
                      startDate={roomSearch.checkInDate}
                      endDate={roomSearch.checkOutDate}
                      onChange={handleRoomSearchDateChange}
                    />
                  </div>

                  <div className="reception-search-group reception-search-button-container">
                    <button
                      className="reception-search-button"
                      onClick={searchAvailableRooms}
                      disabled={loading}
                    >
                      <Search size={16} /> Tìm kiếm phòng
                    </button>
                  </div>
                </div>
              </div>

              <div className="reception-room-status-tabs">
                <button
                  className={`reception-room-tab ${!availableRooms.length ? 'reception-room-tab-disabled' : ''}`}
                  onClick={() => document.getElementById('reception-available-rooms').scrollIntoView({ behavior: 'smooth' })}
                >
                  Phòng trống ({availableRooms.length})
                </button>
                <button
                  className={`reception-room-tab ${!occupiedRooms.length ? 'reception-room-tab-disabled' : ''}`}
                  onClick={() => document.getElementById('reception-occupied-rooms').scrollIntoView({ behavior: 'smooth' })}
                >
                  Phòng đang sử dụng ({occupiedRooms.length})
                </button>
                <button
                  className={`reception-room-tab ${!upcomingCheckins.length ? 'reception-room-tab-disabled' : ''}`}
                  onClick={() => document.getElementById('reception-upcoming-checkins').scrollIntoView({ behavior: 'smooth' })}
                >
                  Phòng sắp nhận khách ({upcomingCheckins.length})
                </button>
              </div>

              <div id="reception-available-rooms" className="reception-room-section">
                <h3 className="reception-section-title">
                  <BedDouble size={18} /> Phòng trống
                </h3>

                {availableRooms.length > 0 ? (
                  <div className="reception-room-status-grid">
                    {availableRooms.map(room => (
                      <div key={room.id} className="reception-room-status-card reception-available">
                        <div className="reception-room-number">Phòng {room.id}</div>
                        <div className="reception-room-type">{room.type}</div>
                        <div className="reception-room-status">Trống</div>
                        <div className="reception-room-price">{room.price?.toLocaleString('vi-VN')} VND/đêm</div>
                      </div>
                    ))}
                  </div>
                ) : (
                  <div className="reception-no-rooms">
                    <AlertCircle size={24} />
                    <p>Không có phòng trống nào được tìm thấy</p>
                  </div>
                )}
              </div>

              <div id="reception-occupied-rooms" className="reception-room-section">
                <h3 className="reception-section-title">
                  <User size={18} /> Phòng đang sử dụng
                </h3>

                {occupiedRooms.length > 0 ? (
                  <div className="reception-room-status-grid">
                    {occupiedRooms.map(room => (
                      <div key={room.id} className="reception-room-status-card reception-occupied">
                        <div className="reception-room-number">Phòng {room.id}</div>
                        <div className="reception-room-type">{room.type}</div>
                        <div className="reception-room-status">Đang sử dụng</div>
                        <div className="reception-guest-info">{room.guestName}</div>
                        <div className="reception-checkout-date">Trả phòng: {formatDate(room.checkOutDate)}</div>
                      </div>
                    ))}
                  </div>
                ) : (
                  <div className="reception-no-rooms">
                    <AlertCircle size={24} />
                    <p>Không có phòng đang sử dụng</p>
                  </div>
                )}
              </div>

              <div id="reception-upcoming-checkins" className="reception-room-section">
                <h3 className="reception-section-title">
                  <Calendar size={18} /> Phòng sắp nhận khách
                </h3>

                {upcomingCheckins.length > 0 ? (
                  <div className="reception-room-status-grid">
                    {upcomingCheckins.map(room => (
                      <div key={room.id} className="reception-room-status-card reception-upcoming">
                        <div className="reception-room-number">Phòng {room.id}</div>
                        <div className="reception-room-type">{room.type}</div>
                        <div className="reception-room-status">Sắp nhận khách</div>
                        <div className="reception-guest-info">{room.guestName}</div>
                        <div className="reception-checkin-date">Nhận phòng: {formatDate(room.checkInDate)}</div>
                      </div>
                    ))}
                  </div>
                ) : (
                  <div className="reception-no-rooms">
                    <AlertCircle size={24} />
                    <p>Không có phòng nào sắp nhận khách</p>
                  </div>
                )}
              </div>
            </div>
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
