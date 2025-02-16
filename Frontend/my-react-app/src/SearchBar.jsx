// "use client"

// import { useState } from "react"
// import Button from "./Button"
// // import DatePicker from "react-datepicker"
// // import "react-datepicker/dist/react-datepicker.css"
// import CustomerDatePicker from "./CustomerDatePicker"
// function SearchBar({ onSearch, initialParams }) {
//   const [searchParams, setSearchParams] = useState({
//     fullAddress: "",
//     checkInDate: null,
//     checkOutDate: null,
//     capacity: 1,
//     ...initialParams,
//   })

//   const handleChange = (name, value) => {
//     setSearchParams((prev) => ({ ...prev, [name]: value }))
//   }

//   const handleSubmit = (e) => {
//     e.preventDefault()
//     onSearch(searchParams)
//   }

//   return (
//     <form className="search-bar" onSubmit={handleSubmit}>
//       <input
//         type="text"
//         value={searchParams.fullAddress}
//         onChange={(e) => handleChange("fullAddress", e.target.value)}
//         placeholder="Enter full address"
//         className="input-field"
//       />
//       {/* <div className="date-input">
//         <DatePicker
//           selected={searchParams.checkInDate}
//           onChange={(date) => handleChange("checkInDate", date)}
//           placeholderText="Check-in Date"
//           className="input-field"
//         />
//         <DatePicker
//           selected={searchParams.checkOutDate}
//           onChange={(date) => handleChange("checkOutDate", date)}
//           placeholderText="Check-out Date"
//           className="input-field"
//         />
//       </div> */}
//       <CustomerDatePicker onDateChange={handleDateChange} />
//       <div className="capacity-input">
//           <label htmlFor="capacity">Capacity:</label>
//           <div className="capacity-controls">
//             <button type="button" onClick={() => handleCapacityChange(-1)} className="capacity-button">
//               -
//             </button>
//             <input
//               type="number"
//               id="capacity"
//               value={searchParams.capacity}
//               onChange={(e) => handleChange("capacity", Number.parseInt(e.target.value) || 1)}
//               className="capacity-value"
//               min="1"
//             />
//             <button type="button" onClick={() => handleCapacityChange(1)} className="capacity-button">
//               +
//             </button>
//           </div>
//         </div>
//       <Button type="submit">Search</Button>
//     </form>
//   )
// }

// export default SearchBar

"use client"

import { useState } from "react"
import { LocalizationProvider } from "@mui/x-date-pickers-pro/LocalizationProvider"
import { AdapterDayjs } from "@mui/x-date-pickers-pro/AdapterDayjs"
import { DateRangePicker } from "@mui/x-date-pickers-pro/DateRangePicker"
import { DemoContainer } from "@mui/x-date-pickers/internals/demo"
import dayjs from "dayjs"
import Button from "./Button"
import { LicenseInfo } from '@mui/x-license';

LicenseInfo.setLicenseKey('e0d9bb8070ce0054c9d9ecb6e82cb58fTz0wLEU9MzI0NzIxNDQwMDAwMDAsUz1wcmVtaXVtLExNPXBlcnBldHVhbCxLVj0y');

function SearchBar({ onSearch, initialParams }) {
  const [searchParams, setSearchParams] = useState({
    fullAddress: "",
    checkInDate: null,
    checkOutDate: null,
    capacity: 1,
    ...initialParams,
  })

  const handleChange = (name, value) => {
    setSearchParams((prev) => ({ ...prev, [name]: value }))
  }

  const handleDateRangeChange = (dateRange) => {
    const [checkIn, checkOut] = dateRange
    setSearchParams((prev) => ({
      ...prev,
      checkInDate: checkIn ? dayjs(checkIn).format("YYYY-MM-DD") : null,
      checkOutDate: checkOut ? dayjs(checkOut).format("YYYY-MM-DD") : null,
    }))
  }

  const handleCapacityChange = (change) => {
    const newCapacity = Math.max(1, searchParams.capacity + change)
    handleChange("capacity", newCapacity)
  }

  const handleSubmit = (e) => {
    e.preventDefault()
    onSearch(searchParams)
  }

  return (
    <form className="flex flex-col md:flex-row gap-4 p-4 bg-white rounded-lg shadow-md" onSubmit={handleSubmit}>
      <div className="flex-1">
        <input
          type="text"
          value={searchParams.fullAddress}
          onChange={(e) => handleChange("fullAddress", e.target.value)}
          placeholder="Enter location"
          className="w-full px-4 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent"
        />
      </div>

      <div className="flex-1">
        <LocalizationProvider dateAdapter={AdapterDayjs}>
          <DemoContainer
            components={["DateRangePicker"]}
            sx={{
              "& .MuiTextField-root": { width: "100%" },
              "& .MuiStack-root": { width: "100%" },
              "& .MuiInputBase-root": {
                borderRadius: "0.5rem",
                height: "42px",
              },
            }}
          >
            <DateRangePicker
              localeText={{ start: "Check-in", end: "Check-out" }}
              value={[
                searchParams.checkInDate ? dayjs(searchParams.checkInDate) : null,
                searchParams.checkOutDate ? dayjs(searchParams.checkOutDate) : null,
              ]}
              onChange={handleDateRangeChange}
              disablePast
            />
          </DemoContainer>
        </LocalizationProvider>
      </div>

      <div className="flex items-center gap-4">
        {/* <div className="flex items-center gap-2">
          <label htmlFor="capacity" className="text-sm font-medium">
            Guests:
          </label>
          <div className="flex items-center border rounded-lg">
            <button
              type="button"
              onClick={() => handleCapacityChange(-1)}
              className="px-3 py-2 hover:bg-gray-100 rounded-l-lg"
            >
              -
            </button>
            <input
              type="number"
              id="capacity"
              value={searchParams.capacity}
              onChange={(e) => handleChange("capacity", Number.parseInt(e.target.value) || 1)}
              className="w-12 text-center border-x"
              min="1"
            />
            <button
              type="button"
              onClick={() => handleCapacityChange(1)}
              className="px-3 py-2 hover:bg-gray-100 rounded-r-lg"
            >
              +
            </button>
          </div>
        </div> */}

        <div class="dropdown">
          <button class="dropbtn">Guests And Rooms
            <i class="fa fa-caret-down"></i>
          </button>
          <div class="dropdown-content">
            <div className="adult-input">
                <label htmlFor="adult">Adult</label>
                
            </div>
          </div>
        </div>
        <Button type="submit" className="px-6 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700">
          Search
        </Button>
      </div>
    </form>
  )
}

export default SearchBar

