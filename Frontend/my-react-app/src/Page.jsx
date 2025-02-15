"use client"

import { useState } from "react"
import useHotel from "./useHotel"
import HotelDetails from "./HotelDetails"
import "./index.css"
import Header from "./Header"
import Button from "./Button"
import SearchBar from "./SearchBar"

// export default function Home() {
//   const [searchParams, setSearchParams] = useState({
//     fullAddress: "",
//     checkInDate: null,
//     checkOutDate: null,
//     capacity: 1,
//   })
//   const { hotels, loading, error } = useHotel(searchParams)

//   const handleSearch = (params) => {
//     setSearchParams(params)
//   }

//   return (
//     <div className="container">
//       <Header />
//       <SearchBar onSearch={handleSearch} initialParams={searchParams} />
//       {loading && <p className="loading-msg">Loading...</p>}
//       {error && <p className="error-msg">Error: {error}</p>}
//       {hotels && hotels.length > 0 ? (
//         hotels.map((hotel) => <HotelDetails key={hotel.hotelId} hotel={hotel} />)
//       ) : (
//         <p>No hotels found. Try adjusting your search criteria.</p>
//       )}
//     </div>
//   )
// }

export default function Home() {
  const [searchParams, setSearchParams] = useState({
        fullAddress: "",
        checkInDate: null,
        checkOutDate: null,
        capacity: 1,
      })
      const { hotels, loading, error } = useHotel(searchParams)
    
      const handleSearch = (params) => {
        setSearchParams(params)
      }

  return (
    <div className="container">
      <Header />
      <SearchBar
        onSearch={handleSearch}
        initialParams={{
          fullAddress: "",
          checkInDate: null,
          checkOutDate: null,
          capacity: 1,
        }}
      />
      {loading && <p className="loading-msg">Loading...</p>}
      {error && <p className="error-msg">Error: {error}</p>}
      {hotels && hotels.length > 0 ? (
        hotels.map((hotel) => <HotelDetails key={hotel.hotelId} hotel={hotel} />)
      ) : (
        <p>No hotels found. Try adjusting your search criteria.</p>
      )}
    </div>
  )
}

