"use client"

import { useState } from "react"
import useHotel from "./useHotel"
import HotelDetails from "./HotelDetails"
import "./index.css"
import Header from "./Header"
// import Button from "./button"
// import CustomerDatePicker from "./CustomerDatePicker"
// import ChooseCapacity from "./chooseCapacity"
import SearchBar from "./SearchBar"







export default function Home() {
  // const [hotelId, setHotelId] = useState(1)
  // const { hotel, loading, error } = useHotel(hotelId)

  // const handleIdChange = (e) => {
  //   setHotelId(Number(e.target.value))
  // }

  // return (
  //   <>
  //     <div className="container">
  //       <Header></Header>
  //       <form className="search-bar">
  //         <div className="filter-box">
  //           <label htmlFor="hotelId" className="myLabel">City, location or hotel name</label>
  //           <input
  //           type="number"
  //           id="hotelId"
  //           value={hotelId}
  //           onChange={handleIdChange}
  //           placeholder="Enter Hotel ID"
  //           className="input-field"
  //           />
  //         </div>
  //         <div className="filter-box">
  //           <label htmlFor="checkInDate" className="myLabel">Check-in date</label>
  //           <CustomerDatePicker/>
  //         </div>
  //         <div className="filter-box">
  //           <label htmlFor="checkOutDate" className="myLabel">Check-out date</label>
  //           <CustomerDatePicker/>
  //         </div>
  //         <div className="filter-box">
  //           <label htmlFor="capacity" className="myLabel">Rooms</label>
  //           <ChooseCapacity/>
  //         </div>
  //         <Button></Button>
  //       </form>
  //       {/* <SearchBar onSearch={handleSearch} initialParams={searchParams} />        {loading && <p>Loading...</p>} */}
  //       {error && <p className="error-msg">{error}</p>}
  //       {hotel && <HotelDetails hotel={hotel} />}
  //     </div>
  //   </>
  // )
  const [searchParams, setSearchParams] = useState({//chỗ ni không thì do bên searchBar thôi
    fullAddress: "",
    checkInDate: null,
    checkOutDate: null,
    capacity: 1,//chạy thử nếu sai thì set nó lại thành 1
  })
  const { hotels, loading, error } = useHotel(searchParams)

  const handleSearch = (params) => {
    setSearchParams(params)
  }

  return (
    <div className="container">
      <Header />
      <SearchBar onSearch={handleSearch} initialParams={searchParams} />
      {loading && <p className="loading-msg">Loading...</p>}
      {error && <p className="error-msg">Error: {error}</p>}
      {hotels && hotels.length > 0 ? (
        hotels.map((hotel) => <HotelDetails key={hotel.id} hotel={hotel} />)
      ) : (
        <p>No hotels found. Try adjusting your search criteria.</p>
      )}
    </div>
  )
}

