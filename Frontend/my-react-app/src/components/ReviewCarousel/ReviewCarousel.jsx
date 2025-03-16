"use client"

import { useState } from "react"
import { ChevronLeft, ChevronRight } from "lucide-react"
import "./ReviewCarousel.css"

const ReviewCarousel = ({ reviews }) => {
  const [currentIndex, setCurrentIndex] = useState(0)

  const handlePrevious = () => {
    setCurrentIndex((prevIndex) => (prevIndex === 0 ? reviews.length - 1 : prevIndex - 1))
  }

  const handleNext = () => {
    setCurrentIndex((prevIndex) => (prevIndex === reviews.length - 1 ? 0 : prevIndex + 1))
  }

  if (!reviews || reviews.length === 0) return null

  return (
    <div className="review-carousel">
      <button className="carousel-button previous" onClick={handlePrevious} aria-label="Previous review">
        <ChevronLeft />
      </button>

      <div className="review-container">
        {reviews.slice(currentIndex, currentIndex + 3).map((review, index) => (
          <div key={index} className="review-card">
            <div className="reviewer-info">
              {review.customerImgURL ? (
                <img
                  src={review.customerImgURL || "/placeholder.svg"}
                  alt={review.customerName}
                  className="reviewer-avatar"
                />
              ) : (
                <div className="reviewer-avatar-placeholder">{review.customerName.charAt(0)}</div>
              )}
              <div className="reviewer-details">
                <div className="reviewer-name">
                  {review.customerName}
                  <span className="country-flag">🇻🇳 Việt Nam</span>
                </div>
              </div>
            </div>
            <div className="review-content">
              <p className="review-text">"{review.comment}"</p>
              <button className="learn-more-button">Tìm hiểu thêm</button>
            </div>
          </div>
        ))}
      </div>

      <button className="carousel-button next" onClick={handleNext} aria-label="Next review">
        <ChevronRight />
      </button>
    </div>
  )
}

export default ReviewCarousel

