import { useState} from "react";
import "./ReceptionStyles.css";
import Capacity from "../../utils/capacity/Capacity";
import { LocalizationProvider } from "@mui/x-date-pickers-pro/LocalizationProvider"
import { DateRangePicker } from "@mui/x-date-pickers-pro/DateRangePicker"
import dayjs from "dayjs"
import { AdapterDayjs } from "@mui/x-date-pickers-pro/AdapterDayjs"


export default function Checkin() {
  const [activeTab, setActiveTab] = useState("booking");
  const [loading, setLoading] = useState(false);
  const [checkinForm, setCheckinForm] = useState({
    name: "",
    phone: "",
    email: "",
    roomIds: "",
    checkInDate: new Date(),
    checkOutDate: new Date(new Date().setDate(new Date().getDate() + 1)),
  });
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
  const [validatedBooking, setValidatedBooking] = useState(null);
    const handleDateRangeChange = (dateRange) => {
        const [checkIn, checkOut] = dateRange
        setBooking((prev) => ({
          ...prev,
          checkInDate: checkIn ? dayjs(checkIn).format("YYYY-MM-DD") : null,
          checkOutDate: checkOut ? dayjs(checkOut).format("YYYY-MM-DD") : null,
        }))
      }
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
    
    return (
        <>
            <div className="mini-tab">
                <button className={activeTab === "booking" ? "active" : ""} onClick={() => setActiveTab("booking")}>Create Booking</button>
                <button className={activeTab === "checkin" ? "active" : ""} onClick={() => setActiveTab("checkin")}>Check-in</button>
            </div>
            {activeTab === "booking" && (
                <div className="reception-content">
                  <div className="reception-content-header">
                    <h2>Nhận phòng</h2>
                    <p>Tạo đặt phòng mới trực tiếp tại quầy lễ tân</p>
                  </div>
                  <div className="reception-checkin-form">
                  <div className="reception-form-group">
                    <label>Tên khách hàng <span className="reception-required">*</span></label>
                    <input 
                      type="text" 
                      name="name"
                      value={checkinForm.name}
                      onChange={handleCheckinFormChange}
                      placeholder="Nhập tên khách hàng" 
                      className="reception-input-line" 
                    />
                  </div>
              
                  <div className="reception-form-group">
                    <label>Số điện thoại <span className="reception-required">*</span></label>
                    <div className="reception-phone-input">
                      <select name="countryCode" defaultValue="+84" className="reception-country-code">
                      <option value="+84">VN +84</option>
                    </select>
                    <input 
                      type="tel" 
                      name="phone"
                      value={checkinForm.phone}
                      onChange={handleCheckinFormChange}
                      placeholder="Nhập số điện thoại" 
                      className="reception-input-line" 
                    />
                    </div>
                  </div>
              
                <div className="reception-form-group">
                  <label>Email <span className="reception-required">*</span></label>
                  <input 
                    type="email" 
                    name="email"
                    value={checkinForm.email}
                    onChange={handleCheckinFormChange}
                    placeholder="Nhập địa chỉ email" 
                    className="reception-input-line" 
                  />
                </div>
              
                <div className="reception-form-group">
                  <label>ID Phòng <span className="reception-required">*</span></label>
                  <input
                    type="text"
                    name="roomIds"
                    value={checkinForm.roomIds}
                    onChange={handleCheckinFormChange}
                    placeholder="Nhập ID phòng (phân cách bằng dấu phẩy, ví dụ: 1,2,3)"
                    className="reception-input-line"
                  />
                  <small className="reception-form-hint">Nhập ID của các phòng, cách nhau bằng dấu phẩy</small>
                </div>
                <div className="reception-form-group">
                  <label>Thời gian lưu trú <span className="reception-required">*</span></label>
                  <LocalizationProvider dateAdapter={AdapterDayjs}>
                            <DateRangePicker
                                startDate={checkinForm.checkInDate}
                                endDate={checkinForm.checkOutDate}
                                onChange={handleDateRangeChange}
                                disablePast
                            />
                        </LocalizationProvider>
                    </div>
                    {validatedBooking ? (
                <div className="reception-validated-booking">
                  <div className="reception-validation-success">
                    <CheckSquare size={20} />
                    <span>Đặt phòng hợp lệ! Vui lòng xác nhận để hoàn tất.</span>
                  </div>
                  
                  <div className="reception-validation-details">
                    <div className="reception-validation-item">
                      <span className="reception-validation-label">Tổng tiền:</span>
                      <span className="reception-validation-value">{validatedBooking.booking.totalPrice?.toLocaleString('vi-VN')} VND</span>
                    </div>
                  </div>
                  
                  <button 
                    className="reception-confirm-button" 
                    onClick={confirmBooking}
                    disabled={loading}
                  >
                    Xác nhận đặt phòng
                  </button>
                </div>
              ) : (
                <button 
                  className="reception-validate-button" 
                  onClick={validateBooking}
                  disabled={loading}
                >
                  Kiểm tra đặt phòng
                </button>
              )}
            </div>
            </div>
            )}
            {activeTab === "checkin" && (
              <label></label>
            )}
        </>
    );
}