"use client"

import { useState, useEffect } from "react"

export default function useHotel(searchParams) {//hồi nãy m để ở đây là id chứ ko phải searchParams
  // const [hotel, setHotel] = useState(null)
  // const [loading, setLoading] = useState(true)
  // const [error, setError] = useState(null)

  // useEffect(() => {
  //   const fetchHotel = async () => {
  //     try {
  //       console.log(`Fetching hotel with id: ${id}`)
  //       const response = await fetch(`http://localhost:8080/api/customer/getHotel/${id}`)
  //       console.log("Response status:", response.status)

  //       const data = await response.json()
  //       console.log("Response data:", data)

  //       if (response.ok && data.status === "success") {
  //         setHotel(data.data)
  //       } else {
  //         setError(data.message || "An error occurred")
  //       }
  //     } catch (err) {
  //       console.error("Fetch error:", err)
  //       setError("An error occurred while fetching the hotel data.")
  //     } finally {
  //       setLoading(false)
  //     }
  //   }

  //   fetchHotel()
  // }, [id])

  // return { hotel, loading, error }
  const [hotels, setHotels] = useState([])
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState(null)

  useEffect(() => {
    const fetchHotels = async () => {
      setLoading(true)
      setError(null)
      try {
        const queryParams = new URLSearchParams({
          fullAddress: searchParams.fullAddress,
          checkInDate: searchParams.checkInDate ? searchParams.checkInDate.toISOString() : "",
          checkOutDate: searchParams.checkOutDate ? searchParams.checkOutDate.toISOString() : "",
          capacity: searchParams.capacity.toString(),
        }).toString()
  
        console.log(queryParams);
        const response = await fetch(`/api/customer/getHotels?${queryParams}`)
        if (!response.ok) {
          throw new Error(`HTTP error! status: ${response.status}`)
        }
        const data = await response.json()
        setHotels(data.data)
      } catch (e) {
        setError(e.message)
      } finally {
        setLoading(false)
      }
    }

    fetchHotels()
  }, [searchParams])

  return { hotels, loading, error }
}

