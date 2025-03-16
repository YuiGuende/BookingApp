"use client"

import { useState, useEffect } from "react"
import axios from "axios"
import "./ReviewStyles.css"
import { MoreVertical } from "lucide-react"
import Header from "../../components/header/Header"

export default function Review() {
  const [reviewedBookings, setReviewedBookings] = useState([])
  const [unreviewedBookings, setUnreviewedBookings] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)
  const [activeReviewForm, setActiveReviewForm] = useState(null)
  const [newReview, setNewReview] = useState({
    rating: "",
    comment: "",
  })

  const customerId = JSON.parse(localStorage.getItem("customerInfor"))?.id

  useEffect(() => {
    if (!customerId) {
      setError("Vui lòng đăng nhập để xem đánh giá")
      setLoading(false)
      return
    }

    Promise.all([
      axios.get(`http://localhost:8080/api/customer/reviewed/${customerId}`),
      axios.get(`http://localhost:8080/api/customer/unreviewed/${customerId}`),
    ])
      .then(([reviewedRes, unreviewedRes]) => {
        if (reviewedRes.data.status === "success") {
          setReviewedBookings(reviewedRes.data.data)
        }
        if (unreviewedRes.data.status === "success") {
          setUnreviewedBookings(unreviewedRes.data.data)
        }
      })
      .catch((err) => {
        console.error("Error fetching reviews:", err)
        setError("Không thể tải dữ liệu đánh giá")
      })
      .finally(() => {
        setLoading(false)
      })
  }, [customerId])

  // Cập nhật hàm handleReviewSubmit để thêm reviewId vào reviewData
  const handleReviewSubmit = async (booking) => {
    if (!newReview.rating || !newReview.comment) {
      alert("Vui lòng nhập đầy đủ đánh giá và rating!")
      return
    }

    const rating = Number.parseFloat(newReview.rating)
    if (rating < 1 || rating > 10 || isNaN(rating)) {
      alert("Rating phải từ 1 đến 10!")
      return
    }

    try {
      const reviewData = {
        reviewId: booking.reviewId, // Server sẽ tạo reviewId mới
        rating: rating,
        comment: newReview.comment,
        hotelName: booking.hotelName,
        hotelId: booking.hotelId,
        hotelURL: booking.hotelURL,
        hotelImgURL: booking.hotelImgURL,
        bookingId: booking.bookingId,
      }

      const response = await axios.post(`http://localhost:8080/api/customer/review/add/${customerId}`, reviewData)

      if (response.data.status === "success") {
        // Update the lists
        setUnreviewedBookings((prev) => prev.filter((b) => b.bookingId !== booking.bookingId))

        // Thêm review mới vào danh sách đã đánh giá
        // Giả định response.data.data chứa reviewId mới được tạo
        const newReviewWithId = {
          ...reviewData,
          reviewId: response.data.data?.reviewId || null,
          reviewDate: new Date().toISOString(),
        }

        setReviewedBookings((prev) => [...prev, newReviewWithId])
        setActiveReviewForm(null)
        setNewReview({ rating: "", comment: "" })
        alert("Đánh giá của bạn đã được lưu!")
      }
    } catch (err) {
      console.error("Error submitting review:", err)
      alert(err.response?.data?.message || "Không thể lưu đánh giá. Vui lòng thử lại!")
    }
  }

  const formatDate = (dateString) => {
    const date = new Date(dateString)
    return date.toLocaleDateString("vi-VN", {
      day: "numeric",
      month: "numeric",
      year: "numeric",
    })
  }

  const getRatingText = (rating) => {
    if (rating >= 9) return "Tuyệt hảo"
    if (rating >= 8) return "Rất tốt"
    if (rating >= 7) return "Tốt"
    if (rating >= 6) return "Hài lòng"
    return "Trung bình"
  }

  if (loading) return <div className="review-loading">Đang tải...</div>
  if (error) return <div className="review-error">{error}</div>

  return (
    <>
   <div className="header">
        <Header />
      </div>
    <div className="review-container">
      {unreviewedBookings.length > 0 && (
        <section className="review-section">
          <h2>Đánh giá đang chờ</h2>
          <div className="review-list">
            {unreviewedBookings.map((booking) => (
              <div key={booking.bookingId} className="review-card unreviewed">
                <div className="review-card-content">
                  <img
                    src={booking.hotelImgURL || "/placeholder.svg?height=100&width=100"}
                    alt={booking.hotelName}
                    className="hotel-image"
                  />
                  <div className="review-info">
                    <div className="review-status pending">Chưa đánh giá</div>
                    <h3>Bạn đã ở {booking.hotelName}</h3>
                  </div>
                  <button
                    className="more-button"
                    onClick={() =>
                      setActiveReviewForm(activeReviewForm === booking.bookingId ? null : booking.bookingId)
                    }
                  >
                    <MoreVertical size={20} />
                  </button>
                </div>

                {activeReviewForm === booking.bookingId && (
                  <div className="review-form">
                    <div className="form-group">
                      <label>Đánh giá của bạn:</label>
                      <textarea
                        value={newReview.comment}
                        onChange={(e) =>
                          setNewReview((prev) => ({
                            ...prev,
                            comment: e.target.value,
                          }))
                        }
                        placeholder="Chia sẻ trải nghiệm của bạn..."
                        rows={4}
                      />
                    </div>
                    <div className="form-group">
                      <label>Rating (1-10):</label>
                      <input
                        type="number"
                        min="1"
                        max="10"
                        step="0.1"
                        value={newReview.rating}
                        onChange={(e) =>
                          setNewReview((prev) => ({
                            ...prev,
                            rating: e.target.value,
                          }))
                        }
                      />
                    </div>
                    <button className="submit-button" onClick={() => handleReviewSubmit(booking)}>
                      Gửi đánh giá
                    </button>
                  </div>
                )}
              </div>
            ))}
          </div>
        </section>
      )}

      {reviewedBookings.length > 0 && (
        <section className="review-section">
          <h2>Đánh giá của bạn</h2>
          <div className="review-list">
            {reviewedBookings.map((review) => (
              <div key={review.bookingId} className="review-card">
                <div className="review-card-content">
                  <img
                    src={review.hotelImgURL || "/placeholder.svg?height=100&width=100"}
                    alt={review.hotelName}
                    className="hotel-image"
                  />
                  <div className="review-info">
                    <div className="review-status">Đánh giá đã đăng</div>
                    <h3>Bạn đã đánh giá {review.hotelName}</h3>
                    <div className="review-date">{formatDate(review.reviewDate)}</div>
                    <div className="rating-box">
                      <div className="rating-score">{review.rating.toFixed(1)}</div>
                      <div className="rating-text">{getRatingText(review.rating)}</div>
                    </div>
                    <div className="review-comment">{review.comment}</div>
                  </div>
                </div>
              </div>
            ))}
          </div>
        </section>
      )}

      {reviewedBookings.length === 0 && unreviewedBookings.length === 0 && (
        <div className="no-reviews">Bạn chưa có đánh giá nào.</div>
      )}
    </div>
    </>
    
  )
}

