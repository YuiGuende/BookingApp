// "use client"

// import { useState, useEffect } from "react"

// export default function useHotel(searchParams) {
//   const [hotels, setHotels] = useState([])
//   const [loading, setLoading] = useState(false)
//   const [error, setError] = useState(null)

//   useEffect(() => {
//     const fetchHotels = async () => {
//       setLoading(true)
//       setError(null)
//      // http://localhost:8080/api/customer/getHotel/${id}
//       try {
//         const requestBody = {
//           fullAddress: searchParams.fullAddress,
//           checkInDate: searchParams.checkInDate ? searchParams.checkInDate : null,
//           checkOuDate: searchParams.checkOutDate ? searchParams.checkOutDate : null,
//           maxAdults: searchParams.maxAdults,
//           maxChildren: searchParams.maxChildren,
//           rooms: searchParams.rooms,
//         }

//         console.log(requestBody);

//         const response = await fetch("http://localhost:8080/api/customer/hotel/search", {
//           method: "POST",
//           headers: {
//             "Content-Type": "application/json",
//           },
//           body: JSON.stringify(requestBody),
//         })
        
//         if (!response.ok) {
//           throw new Error(`HTTP error! status: ${response.status}`)
//         }
        
//         const data = await response.json()

//         if (data.status === "success") {
//           setHotels(data.data)
//         } else {
//           throw new Error(data.message || "An error occurred while fetching hotels")
//         }
//       } catch (e) {
//         setError(e.message)
//       } finally {
//         setLoading(false)
//       }
//     }

//     if (searchParams.fullAddress) {
//       fetchHotels()
//     }
//   }, [searchParams])

//   return { hotels, loading, error }
// }