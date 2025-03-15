
import { useState, useEffect } from "react";
import axios from "axios";
import "./ReceptionStyles.css";
import Header from "../../components/header/Header";
import { Calendar, Clock, CheckCircle, XCircle, User, Phone, Mail, CreditCard, Hotel, DoorOpen, CalendarIcon, Search, CheckSquare, BedDouble, AlertCircle, KeyRound } from 'lucide-react';
import Checkin from "./Checkin";
import { LocalizationProvider } from "@mui/x-date-pickers-pro/LocalizationProvider"
import { DateRangePicker } from "@mui/x-date-pickers-pro/DateRangePicker"
import dayjs from "dayjs"
import { AdapterDayjs } from "@mui/x-date-pickers-pro/AdapterDayjs"

export default function Hotel_Reception() {
  const [activeTab, setActiveTab] = useState("unchecked-bookings");
  const [uncheckedBookings, setUncheckedBookings] = useState([]);
  const [checkedBookings, setCheckedBookings] = useState([]);
  const [staffId, setStaffId] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [selectedBooking, setSelectedBooking] = useState(null);
  const [showModal, setShowModal] = useState(false);
  const [order, setOrder] = useState(null);
  const [services, setServices] = useState({});
  const [bookingId, setBookingId] = useState("");
  const [selectedServices, setSelectedServices] = useState({});
  const [paymentMethod, setPaymentMethod] = useState("");
  const [bookingDetails, setBookingDetails] = useState(null);
  const [searchResults, setSearchResults] = useState([])

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
    const userData = localStorage.getItem("staffInfor");
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
      axios.get(`http://localhost:8080/api/staff/unCheckedBooking/${staffId}`)
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
    }

    if (activeTab === "checked-bookings") {
      axios.get(`http://localhost:8080/api/staff/checkedBooking/${staffId}`)
        .then((res) => {
          console.log("Checked bookings:", res.data);
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
        .finally(() => {
          setLoading(false);
        });
    }
    if (activeTab === "checkin" || activeTab === "checkout") {
      setLoading(false);
    }

    if (activeTab === "rooms" && hotelId && staffId) {
      // Fetch available rooms
      axios
        .get(`http://localhost:8080/api/staff/getAvailableRoom/${staffId}`)
        .then((res) => {
          if (res.data && res.data.status === "success") {
            setAvailableRooms(res.data.data || [])
          } else {
            setAvailableRooms([])
          }
        })
        .catch((err) => {
          console.error("Error fetching available rooms:", err)
          setAvailableRooms([])
        })
        .finally(() => {
          setLoading(false);
        });

      // Fetch occupied rooms
      axios
        .get(`http://localhost:8080/api/staff/getOccupiedRoom/${staffId}`)
        .then((res) => {
          if (res.data && res.data.status === "success") {
            setOccupiedRooms(res.data.data || [])
          } else {
            setOccupiedRooms([])
          }
        })
        .catch((err) => {
          console.error("Error fetching occupied rooms:", err)
          setOccupiedRooms([])
        })
        .finally(() => {
          setLoading(false);
        });

      // Placeholder for upcoming check-ins
      setUpcomingCheckins([
        { id: 103, name: "Suite 103", type: "SUITE", guestName: "Lê Văn C", checkInDate: "2025-03-10" },
        { id: 104, name: "Deluxe Room 104", type: "DELUXE", guestName: "Phạm Thị D", checkInDate: "2025-03-11" },
      ])
    }
  }, [activeTab, staffId, hotelId]);

  // Đánh dấu booking đã kiểm tra
  const markAsChecked = (bookingId) => {
    setLoading(true);
    axios.post(`http://localhost:8080/api/staff/markBookingAsChecked/${staffId}`, [bookingId])
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
  const handleDateChange = (dates) => {
    if (!dates || dates.length !== 2) return;

    setRoomSearch(prev => ({
      ...prev,
      checkInDate: dates[0],  // Lấy ngày check-in từ phần tử đầu tiên của mảng
      checkOutDate: dates[1]   // Lấy ngày check-out từ phần tử thứ hai của mảng
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
      alert("Vui lòng nhập ID khách sạn!")
      return
    }

    setLoading(true)

    const params = {
      roomQuantity: roomSearch.roomQuantity,
      adultQuantity: roomSearch.adultQuantity,
      childrenQuantity: roomSearch.childrenQuantity,
      checkInDate: roomSearch.checkInDate.toISOString().split("T")[0],
      checkOutDate: roomSearch.checkOutDate.toISOString().split("T")[0],
    }

    axios
      .get(`http://localhost:8080/api/customer/getHotel/${hotelId}`, { params })
      .then((res) => {
        if (res.data && res.data.status === "success") {
          setSearchResults(res.data.data.rooms || [])
          console.log("ressss",res.data.data.rooms )
        } else {
          setSearchResults([])
          alert(res.data.message || "Không tìm thấy phòng phù hợp.")
        }
      })
      .catch((err) => {
        console.error("Error searching rooms:", err)
        setSearchResults([])
        alert(err.response?.data?.message || "Không thể tìm kiếm phòng. Vui lòng thử lại!")
      })
      .finally(() => {
        setLoading(false)
      })
  }


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
  const fetchBookingDetails = async () => {
    if (!bookingId) {
      setError("Vui lòng nhập Booking ID");
      return;
    }
    try {
      const response = await fetch(`http://localhost:8080/api/staff/bookingDetails/${bookingId}`);
      const data = await response.json();
      if (response.ok) {
        setBookingDetails(data);
      } else {
        setError("Không tìm thấy thông tin đặt phòng");
      }
    } catch (error) {
      setError("Lỗi hệ thống");
    }
  };

  useEffect(() => {
    const fetchServices = async () => {
      try {
        const response = await fetch(`http://localhost:8080/api/staff/serviceList/${hotelId}`);
        const data = await response.json();
        console.log("services", data)
        if (response.ok) {

          setServices(data);
          const initialSelection = data.reduce((acc, service) => {
            acc[service.code] = 0;
            return acc;
          }, {});
          setSelectedServices(initialSelection);
        } else {
          setError("Không thể tải danh sách dịch vụ");
        }
      } catch (error) {
        setError("Lỗi khi lấy danh sách dịch vụ");
      }
    };
    if (hotelId) fetchServices();
  }, [hotelId]);
  const handleServiceQuantityChange = (code, quantity) => {
    setSelectedServices((prev) => ({
      ...prev,
      [code]: quantity,
    }));
  };
  // const handleCheckout = async () => {
  //   if (!bookingId) {
  //     setError("Vui lòng nhập Booking ID");
  //     return;
  //   }
  //   setLoading(true);
  //   setError(null);
  //   try {
  //     const response = await fetch(`http://localhost:8080/api/staff/checkout/${bookingId}`, {
  //       method: "POST",
  //       headers: { "Content-Type": "application/json" },
  //       body: JSON.stringify({
  //         services: Object.fromEntries(
  //           Object.entries(selectedServices).filter(([_, quantity]) => quantity > 0)
  //         ),
  //         payment: { paymentMethod },
  //       }),
  //     });
  //     const data = await response.json();
  //     if (response.ok) {
  //       setOrder(data.data);
  //     } else {
  //       setError(data.message || "Lỗi không xác định");
  //     }
  //   } catch (error) {
  //     setError("Lỗi hệ thống");
  //   }
  //   setLoading(false);
  // };
  const handleCheckout = async () => {
    if (!bookingId) {
      setError("Vui lòng nhập Booking ID");
      return;
    }
    setLoading(true);
    setError(null);
  
    // Tạo danh sách dịch vụ đúng format `{ serviceId: quantity }`
    const serviceData = Object.fromEntries(
      Object.entries(selectedServices)
        .filter(([_, quantity]) => quantity > 0)
        .map(([code, quantity]) => {
          const service = services.find((s) => s.code === code);
          return [service.id, quantity]; // Đúng format API
        })
    );
  
    // Tính tổng tiền dịch vụ
    const serviceAmount = Object.entries(serviceData).reduce((sum, [id, quantity]) => {
      const service = services.find((s) => s.id === Number(id));
      return sum + (service ? service.price * quantity : 0);
    }, 0);
  
    // Tổng tiền thanh toán = tiền phòng + tiền dịch vụ
    const totalAmount = (bookingDetails?.totalPrice || 0) + serviceAmount;
  
    try {
      const response = await fetch(`http://localhost:8080/api/staff/checkout/${bookingId}`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          services: serviceData,
          payment: {
            paymentMethod,
            paymentDate: new Date().toISOString().split("T")[0], // Ngày hiện tại
            amount: totalAmount,
            serviceAmount,
          },
        }),
      });
  
      const data = await response.json();
      if (!response.ok) throw new Error(data.message || "Lỗi không xác định");
  
      setOrder(data.data);
      if (data.data) {
        alert("Check-out thành công!");
      }
    } catch (error) {
      setError(error.message || "Lỗi hệ thống");
    }
  
    setLoading(false);
  };
  
  const calculateTotalAmount = () => {
    const serviceTotal = Object.entries(selectedServices).reduce(
      (sum, [code, quantity]) => {
        const service = services.find((s) => s.code === code);
        return sum + (service ? service.price * quantity : 0);
      },
      0
    );
    return (bookingDetails?.totalPrice || 0) + serviceTotal;
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
                <span className="reception-detail-label">Booking ID:</span>
                <span className="reception-detail-value">{booking.booking.id}</span>
              </div>
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

        {loading && (
          <div className="reception-loading-container">
            <div className="reception-loading-spinner"></div>
            <p>Đang tải dữ liệu...</p>
          </div>
        )}

        {error && (
          <div className="reception-error-message">
            <p>{error}</p>
          </div>
        )}

        {activeTab === "unchecked-bookings" && !loading && (
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
        )}

        {activeTab === "checked-bookings" && !loading && (
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
        )}

        {activeTab === "checkin" && (
          <Checkin />
        )}

        {activeTab === "checkout" && (
          <div className="checkout-container">
            <input
              type="text"
              className="reception-input-line"
              value={bookingId}
              onChange={(e) => setBookingId(e.target.value)}
              placeholder="Nhập Booking ID"
            />
            <button className="checkout-button" onClick={fetchBookingDetails}>Tìm kiếm</button>
            {bookingDetails && (
        <div className="booking-details">
          <h3>Thông tin đặt phòng</h3>
          <p><strong>Booking ID:</strong> {bookingDetails.id}</p>
          <p><strong>Tên khách hàng:</strong> {bookingDetails.name}</p>
          <p><strong>Email:</strong> {bookingDetails.email}</p>
          <p><strong>Điện thoại:</strong> {bookingDetails.phone}</p>
          <p><strong>Ngày nhận phòng:</strong> {bookingDetails.checkInDate}</p>
          <p><strong>Ngày trả phòng:</strong> {bookingDetails.checkOutDate}</p>
          <p><strong>Tổng tiền phòng:</strong> {bookingDetails.totalPrice} VND</p>
        </div>
      )}            <div className="service-container">
              <div className="service-selected">
                <h3>Dịch vụ đã chọn</h3>
                {Object.entries(selectedServices).map(([code, quantity]) => (
                  quantity > 0 && (
                    <div key={code} className="selected-item">
                      <span>{services.find(s => s.code === code)?.name}</span>
                      <input
                        type="number"
                        className="quantity-input"
                        min="0"
                        value={quantity}
                        onChange={(e) => handleServiceQuantityChange(code, Number(e.target.value))}
                      />
                    </div>
                  )
                ))}
              </div>
              <div className="service-list">
                <h3>Chọn dịch vụ</h3>
                <div className="service-grid">
                {services.length > 0 ? (
            services.map((service) => (
              <div
                key={service.code}
                className="service-item"
                onClick={() => handleServiceQuantityChange(service.code, (selectedServices[service.code] || 0) + 1)}
              >
                <p className="service-name">{service.name}</p>
                <p className="service-price">{service.price} VND</p>
              </div>
            ))
          ) : (
            <p>Không có dịch vụ nào khả dụng</p>
          )}
                </div>
              </div>
            </div>
            <div className="payment-method">
              <h3>Chọn phương thức thanh toán</h3>
              <select value={paymentMethod} onChange={(e) => setPaymentMethod(e.target.value)}>
                <option value="">Chọn phương thức</option>
                <option value="Card">Thẻ</option>
                <option value="Cash">Tiền mặt</option>
                <option value="Online">Trực tuyến</option>
              </select>
            </div>
            <p><strong>Tổng tiền phải thanh toán:</strong> {calculateTotalAmount()} VND</p>
            {error && <p className="error-message">{error}</p>}
              <button className="checkout-button" onClick={handleCheckout} disabled={loading}>
                {loading ? "Đang xử lý..." : "Xác nhận trả phòng"}
              </button>
          </div>
        )}

        {activeTab === "rooms" && (
          <div className="reception-tab-content">
            <h2 className="reception-section-title">Tình trạng phòng</h2>
            {/* Room Search Form */}
            <div className="reception-search-form">
              <h3 className="reception-section-title">
                <Search size={18} /> Tìm kiếm phòng
              </h3>
              <div className="reception-form-group">
                <label htmlFor="roomQuantity">Số lượng phòng:</label>
                <input
                  type="number"
                  id="roomQuantity"
                  name="roomQuantity"
                  value={roomSearch.roomQuantity}
                  onChange={handleRoomSearchChange}
                  className="reception-input-line"
                />
              </div>
              <div className="reception-form-group">
                <label htmlFor="adultQuantity">Số lượng người lớn:</label>
                <input
                  type="number"
                  id="adultQuantity"
                  name="adultQuantity"
                  value={roomSearch.adultQuantity}
                  onChange={handleRoomSearchChange}
                  className="reception-input-line"
                />
              </div>
              <div className="reception-form-group">
                <label htmlFor="childrenQuantity">Số lượng trẻ em:</label>
                <input
                  type="number"
                  id="childrenQuantity"
                  name="childrenQuantity"
                  value={roomSearch.childrenQuantity}
                  onChange={handleRoomSearchChange}
                  className="reception-input-line"
                />
              </div>
              <div className="reception-form-group">
                <LocalizationProvider dateAdapter={AdapterDayjs}>
                  <DateRangePicker
                    localeText={{ start: "Check-in", end: "Check-out" }}
                    value={[
                      roomSearch.checkInDate ? dayjs(roomSearch.checkInDate) : null,
                      roomSearch.checkOutDate ? dayjs(roomSearch.checkOutDate) : null,
                    ]}
                    onChange={handleDateChange}
                  />
                </LocalizationProvider>
                {/* <label>Ngày nhận phòng:</label>
                <DatePicker
                  selected={roomSearch.checkInDate}
                  onChange={(date) => handleDateChange(date, "checkInDate")}
                  dateFormat="dd/MM/yyyy"
                />
              </div>
              <div className="form-group">
                <label>Ngày trả phòng:</label>
                
                <DatePicker
                  selected={roomSearch.checkOutDate}
                  onChange={(date) => handleDateChange(date, "checkOutDate")}
                  dateFormat="dd/MM/yyyy"
                /> */}
              </div>
              <button className="search-button" onClick={searchAvailableRooms} disabled={loading}>
                {loading ? "Đang tìm kiếm..." : "Tìm kiếm"}
              </button>
            </div>

            {searchResults.length > 0 && (
              <div className="reception-search-results">
                <h3 className="reception-section-title">
                  <Search size={18} /> Kết quả tìm kiếm
                </h3>
                <div className="reception-room-status-grid">
                  {searchResults.map((room) => (
                    <div key={room.room.id} className="reception-room-status-card reception-search-result">
                      <div className="reception-room-number">Phòng {room.room.id}</div>
                      <div className="reception-room-type">{room.room.type}</div>
                      <div className="reception-room-status">Có thể đặt</div>
                      <div className="reception-room-price">{room.room.price?.toLocaleString("vi-VN")} VND/đêm</div>
                    </div>
                  ))}
                </div>
              </div>
            )}

            {/* Available Rooms */}
            <h3 className="reception-section-title">
              <DoorOpen size={18} /> Phòng trống
            </h3>
            <div className="reception-room-status-grid">
              {availableRooms.map((room) => (
                <div key={room.id} className="reception-room-status-card">
                  <div className="reception-room-number">Phòng {room.id}</div>
                  <div className="reception-room-type">{room.type}</div>
                  <div className="reception-room-status">Trống</div>
                </div>
              ))}
            </div>

            {/* Occupied Rooms */}
            <h3 className="reception-section-title">
              <KeyRound size={18} /> Phòng đang sử dụng
            </h3>
            <div className="reception-room-status-grid">
              {occupiedRooms.map((room) => (
                <div key={room.id} className="reception-room-status-card occupied">
                  <div className="reception-room-number">Phòng {room.id}</div>
                  <div className="reception-room-type">{room.type}</div>
                  <div className="reception-room-status">Đang sử dụng</div>
                </div>
              ))}
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