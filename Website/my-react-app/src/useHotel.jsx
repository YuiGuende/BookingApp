"use client"

import { useState, useEffect } from "react"

export default function useHotel(id) {
  const [hotel, setHotel] = useState(null)
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)

  useEffect(() => {
    const fetchHotel = async () => {
      try {
        console.log(`Fetching hotel with id: ${id}`)
        const response = await fetch(`http://localhost:8080/api/customer/getHotel/${id}`)
        console.log("Response status:", response.status)

        const data = await response.json()
        console.log("Response data:", data)

        if (response.ok && data.status === "success") {
          setHotel(data.data)
        } else {
          setError(data.message || "An error occurred")
        }
      } catch (err) {
        console.error("Fetch error:", err)
        setError("An error occurred while fetching the hotel data.")
      } finally {
        setLoading(false)
      }
    }

    fetchHotel()
  }, [id])

  return { hotel, loading, error }
}

