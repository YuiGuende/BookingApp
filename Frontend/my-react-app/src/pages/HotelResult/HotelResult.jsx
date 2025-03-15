// "use client";

// import { useSelector } from "react-redux";
// import HotelDetails from "./HotelDetails";
// import "./HotelResult.css";
// import SearchBar from "../../components/searchbar/SearchBar";
// import { useState } from "react";
// import { useNavigate } from "react-router-dom";
// import { useDispatch } from "react-redux";
// import { fetchHotels } from "../../store/hotelSlice";
// import Header from "../../components/header/Header";
// import AmenityDetails from "./AmentityDetails";

// export default function HotelResult() {
//   const { hotels, loading, error } = useSelector((state) => state.hotel);
//   const navigate = useNavigate();
//   const dispatch = useDispatch();
//   const [searchParams, setSearchParams] = useState({
//     fullAddress: "",
//     checkInDate: null,
//     checkOutDate: null,
//     maxAdults: 1,
//     maxChildren: 0,
//     rooms: 1,
//   });

//   const handleSearch = async (params) => {
//     setSearchParams(params);
//     // Gọi fetchHotels để fetch dữ liệu 1 lần
//     await dispatch(fetchHotels(params));
//     navigate("/HotelResult");
//   };
//   {loading && <p className="loading-msg">Loading...</p>}
//   {error && <p className="error-msg">Error: {error}</p>}
//   const filters = [
//     {
//       id: 'popular-filters',
//       type: 'checkbox',
//       name: 'Popular Filters',
//       options: [
//         { id: 1, name: 'Free Wi-Fi' },
//         { id: 2, name: 'Swimming Pool' },
//         { id: 3, name: 'Free Breakfast' },
//       ],
//     },
//     {
//       id: 'amenities',
//       type: 'checkbox',
//       name: 'Amenities',
//       options: [
//         { id: 1, name: 'Air Conditioning' },
//         { id: 2, name: 'Heating' },
//         { id: 3, name: 'Parking' },
//       ],
//     },
//     {
//       id: 'property-rating',
//       type: 'checkbox',
//       name: 'Property Rating',
//       options: [
//         { id: 1, name: '1 star' },
//         { id: 2, name: '2 starts' },
//         { id: 3, name: '3 starts' },
//         { id: 4, name: '4 starts' },
//         { id: 5, name: '5 starts' },
//       ],
//     },
//   ];

//   const handleFilterChange = (filterId, selectedOptions) => {
//     setFiltersApplied(prevState => ({
//       ...prevState,
//       [filterId]: selectedOptions,
//     }));
//   };
//   return (
//     <>
//       <div className="header-container">
//       < Header/>
//       </div>
//       <div className="searchbar-container">
//         <SearchBar onSearch={handleSearch} initialParams={searchParams}/>
//       </div>
//       <div className="result">
//         <AmenityDetails filters={filters} onFilterChange={handleFilterChange} />
//         <div className="hotel-container">
//           <div className="hotel-list">
//             {hotels && hotels.length > 0 ? (
//               hotels.map((hotel) => <HotelDetails key={hotel.hotelId} hotel={hotel} />)
//             ) : (
//               <p>No hotels found. Try adjusting your search criteria.</p>
//             )}
//           </div>
//         </div>
//       </div>

//     </>
//   );
// }
import { useSelector } from "react-redux";
import HotelDetails from "./HotelDetails";
import "./HotelResult.css";
import SearchBar from "../../components/searchbar/SearchBar";
import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { useDispatch } from "react-redux";
import { fetchHotels } from "../../store/hotelSlice";
import Header from "../../components/header/Header";
import AmenityDetails from "./AmentityDetails";

export default function HotelResult() {
  const { hotels, loading, error } = useSelector((state) => state.hotel);
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const [searchParams, setSearchParams] = useState({
    fullAddress: "",
    checkInDate: null,
    checkOutDate: null,
    maxAdults: 1,
    maxChildren: 0,
    rooms: 1,
  });

  const [amenities, setAmenities] = useState([]);
  const [selectedCodes, setSelectedCodes] = useState([]);

  useEffect(() => {
    fetch("http://localhost:8080/api/customer/amenity")
      .then(response => response.json())
      .then(data => setAmenities(data));
  }, []);

  const handleSearch = async (params) => {
    setSearchParams(params);
    await dispatch(fetchHotels(params));
    navigate("/HotelResult");
  };

  const handleFilterChange = (selectedOptions) => {
    setSelectedCodes(selectedOptions);
  };

  const groupedAmenities = amenities.reduce((acc, amenity) => {
    if (!acc[amenity.type]) {
      acc[amenity.type] = [];
    }
    acc[amenity.type].push(amenity);
    return acc;
  }, {});

  const filteredHotels = hotels.filter(hotel =>
    selectedCodes.length === 0 || 
    (hotel.hotelWithRoomsDTO.amenities && hotel.hotelWithRoomsDTO.amenities.some(a => selectedCodes.includes(a.code)))
  );
  useEffect(() => {
    console.log("Hotels data:", hotels);
    console.log("Selected codes:", selectedCodes);
    
    // Log chi tiết về việc lọc
    hotels.forEach(hotel => {
      console.log("Hotel:", hotel.hotelName);
      console.log("Hotel amenities:", hotel.amenities);
      console.log("Matches:", hotel.amenities?.every(a => selectedCodes.includes(a.code)));
    });
  }, [hotels, selectedCodes]);
  return (
    <>
      <div className="header-container">
        <Header />
      </div>
      <div className="searchbar-container">
        <SearchBar onSearch={handleSearch} initialParams={searchParams} />
      </div>
      <div className="result">
        <AmenityDetails
          groupedAmenities={groupedAmenities}
          selectedOptions={selectedCodes}
          onFilterChange={handleFilterChange}
        />
        <div className="hotel-container">
          <div className="hotel-list">
            {loading && <p className="loading-msg">Loading...</p>}
            {error && <p className="error-msg">Error: {error}</p>}
            {filteredHotels.length > 0 ? (
              filteredHotels.map((hotel) => <HotelDetails key={hotel.hotelId} hotel={hotel} />)
            ) : (
              <p>No hotels found. Try adjusting your search criteria.</p>
            )}
          </div>
        </div>
      </div>
    </>
  );
}
