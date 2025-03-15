"use client"

import { useEffect, useState ,useRef} from "react"
import { useNavigate, useLocation } from "react-router-dom"
import axios from "axios"
import { CheckCircle, XCircle } from "lucide-react"
import "./ReturnStyles.css"

export default function PaymentReturn() {
  const navigate = useNavigate()
  const location = useLocation()
  const [status, setStatus] = useState("loading") // "loading" | "success" | "failed"
  const [message, setMessage] = useState("")
  const hasVerified = useRef(false) 
  useEffect(() => {
    const verifyPayment = async () => {
      if (hasVerified.current) return
      console.log("the url is change ?")
      try {
        // Lấy tất cả các tham số từ URL
        const searchParams = new URLSearchParams(location.search)
       
        const params = {}
        for (const [key, value] of searchParams.entries()) {
          params[key] = value
        }
        // Kiểm tra xem có mã giao dịch không
        if (!params.vnp_TxnRef) {
          setStatus("failed")
          setMessage("Không tìm thấy thông tin giao dịch")
          return
        }

        // Gọi API để xác minh thanh toán
        const response = await axios.post("http://localhost:8080/api/payment/verify", params)

        if (response.data.status === "success") {
          
          
          // Lấy thông tin đặt phòng đã lưu trước đó
          const validatedBooking = JSON.parse(localStorage.getItem("validatedBooking") || "{}")

          // Cập nhật trạng thái thanh toán
          validatedBooking.booking.status = "CONFIRMED"

          // Gửi thông tin đặt phòng lên server
          await axios.post("http://localhost:8080/api/customer/booking/add", validatedBooking)
          setStatus("success")

          setMessage("Thanh toán thành công! Đơn đặt phòng của bạn đã được xác nhận.")
        }
        else if(response.data.status === "checked") {
          setStatus("checked")

          setMessage("Giao dịch đã được xử lý")
        }
        else {
          setStatus("failed")
          setMessage(response.data.message || "Thanh toán không thành công")
        }
      } catch (error) {
        console.error("Error verifying payment:", error)
        setStatus("failed")
        setMessage("Đã xảy ra lỗi khi xác minh thanh toán")
      }
    }

    verifyPayment()
    hasVerified.current = true 
  }, [])

  const handleContinue = () => {
    if (status === "success") {
      navigate("/bookingHistory")
    } else {
      navigate("/")
    }
  }

  return (
    <div className="container mx-auto py-12 px-4">
      <div className="max-w-md mx-auto bg-white rounded-lg shadow-md p-8">
        {status === "loading" && (
          <div className="text-center">
            <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-primary mx-auto"></div>
            <p className="mt-4 text-lg">Đang xác minh thanh toán...</p>
          </div>
        )}

        {status === "success" && (
          <div className="text-center">
            <CheckCircle className="h-16 w-16 text-green-500 mx-auto" />
            <h2 className="mt-4 text-2xl font-bold text-green-500">Thanh toán thành công!</h2>
            <p className="mt-2 text-gray-600">{message}</p>
            <button
              onClick={handleContinue}
              className="mt-6 w-full bg-primary text-white py-2 px-4 rounded-md hover:bg-primary/90 transition-colors"
            >
              Xem lịch sử đặt phòng
            </button>
          </div>
        )}

        {status === "failed" && (
          <div className="text-center">
            <XCircle className="h-16 w-16 text-red-500 mx-auto" />
            <h2 className="mt-4 text-2xl font-bold text-red-500">Thanh toán thất bại</h2>
            <p className="mt-2 text-gray-600">{message}</p>
            <button
              onClick={handleContinue}
              className="mt-6 w-full bg-primary text-white py-2 px-4 rounded-md hover:bg-primary/90 transition-colors"
            >
              Quay lại trang chủ
            </button>
          </div>
        )}
        {status === "checked" && (
          <div className="text-center">
            <XCircle className="h-16 w-16 text-gray-500 mx-auto" />
            <h2 className="mt-4 text-2xl font-bold text-red-500">Thanh toán không xác định</h2>
            <p className="mt-2 text-gray-600">{message}</p>
            <button
              onClick={handleContinue}
              className="mt-6 w-full bg-primary text-white py-2 px-4 rounded-md hover:bg-primary/90 transition-colors"
            >
              Quay lại trang chủ
            </button>
          </div>
        )}
      </div>
    </div>
  )
}

