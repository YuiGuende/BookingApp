"use client"

import { useState } from "react"
import { LocalizationProvider } from "@mui/x-date-pickers-pro/LocalizationProvider"
import { AdapterDayjs } from "@mui/x-date-pickers-pro/AdapterDayjs"
import { DateRangePicker } from "@mui/x-date-pickers-pro/DateRangePicker"
// import { DemoContainer } from "@mui/x-date-pickers/internals/demo"
import dayjs from "dayjs"
import Button from "../button/Button"
import Capacity from "../../utils/Capacity"
import { LicenseInfo } from '@mui/x-license';
import "./SearchbarStyles.css";

LicenseInfo.setLicenseKey('e0d9bb8070ce0054c9d9ecb6e82cb58fTz0wLEU9MzI0NzIxNDQwMDAwMDAsUz1wcmVtaXVtLExNPXBlcnBldHVhbCxLVj0y');

function SearchBar({ onSearch, initialParams }) {
  const [searchParams, setSearchParams] = useState({
     roomQuantity: 1,
    fullAddress: "",
    checkInDate: null,
    checkOutDate: null,
    adultQuantity: 1,
    childrenQuantity: 0,
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
  const handleCapacityChange = (type, value) => {
    setSearchParams((prev) => ({
      ...prev,
      [type]: value,
    }))
  }

  const handleSubmit = (e) => {
    e.preventDefault()
    onSearch(searchParams)
  }

  return (
    <form className="search-bar" onSubmit={handleSubmit}>
      <div className="input-field">
        <input
          type="text"
          value={searchParams.fullAddress}
          onChange={(e) => handleChange("fullAddress", e.target.value)}
          placeholder="Enter location"
          autoComplete="off" action="/action_page.php"
        />
      </div>

      <div className="date-input">
        <LocalizationProvider dateAdapter={AdapterDayjs}>
          {/* <DemoContainer
            components={["DateRangePicker"]}
            sx={{
              "& .MuiTextField-root": { width: "100%" },
              // "& .MuiStack-root": { width: "100%" },
              "& .MuiInputBase-root": {
                borderRadius: "0.5rem",
                height: "42px",
              },
            }}
          > */}
            <DateRangePicker
              localeText={{ start: "Check-in", end: "Check-out" }}
              value={[
                searchParams.checkInDate ? dayjs(searchParams.checkInDate) : null,
                searchParams.checkOutDate ? dayjs(searchParams.checkOutDate) : null,
              ]}
              onChange={handleDateRangeChange}
              disablePast
            />
          {/* </DemoContainer> */}
        </LocalizationProvider>
      </div>
      <Capacity
          adultQuantity={searchParams.adultQuantity}
          childrenQuantity={searchParams.childrenQuantity}
          roomQuantity={searchParams.roomQuantity}
          onChange={handleCapacityChange}
        />      {/* </div> */}
      <Button type="submit" iconOnly={true} aria-label="Search" />
    </form>
  )
}
export default SearchBar