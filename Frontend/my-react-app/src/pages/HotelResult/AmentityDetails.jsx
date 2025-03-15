// import React, { useState } from 'react';
// import "./HotelResult.css";

// const Filter = ({ filter, onFilterChange }) => {
//   const [selectedOptions, setSelectedOptions] = useState([]);

//   const handleChange = (option) => {
//     let updatedOptions = [...selectedOptions];
//     if (filter.type === 'checkbox') {
//       // Handle checkboxes: toggle selection
//       if (updatedOptions.includes(option)) {
//         updatedOptions = updatedOptions.filter(item => item !== option);
//       } else {
//         updatedOptions.push(option);
//       }
//     } else if (filter.type === 'radio') {
//       // Handle radio buttons: only one option can be selected at a time
//       updatedOptions = [option];
//     }
    
//     setSelectedOptions(updatedOptions);
//     onFilterChange(filter.id, updatedOptions);
//   };

//   const renderOptions = () => {
//     switch (filter.type) {
//       case 'checkbox':
//         return (
//           <ul>
//             {filter.options.map(option => (
//               <li key={option.id}>
//                 <label>
//                   <input
//                     type="checkbox"
//                     checked={selectedOptions.includes(option.id)}
//                     onChange={() => handleChange(option.id)}
//                   />
//                   <span id='option-name'>{option.name}</span>
//                 </label>
//               </li>
//             ))}
//           </ul>
//         );
//       case 'radio':
//         return (
//           <ul>
//             {filter.options.map(option => (
//               <li key={option}>
//                 <label>
//                   <input
//                     type="radio"
//                     name={filter.id} // Ensure that radio buttons group together
//                     checked={selectedOptions.includes(option)}
//                     onChange={() => handleChange(option)}
//                   />
//                   {option}
//                 </label>
//               </li>
//             ))}
//           </ul>
//         );
//       default:
//         return null;
//     }
//   };

//   return (
//     <div className="filter-category">
//       <h3>{filter.name}</h3>
//       {renderOptions()}
//     </div>
//   );
// };

// const AmenityDetails = ({ filters, onFilterChange }) => {
//   return (
//     <div className="filter-box">
//       {filters.map(filter => (
//         <Filter
//           key={filter.id}
//           filter={filter}
//           onFilterChange={onFilterChange}
//         />
//       ))}
//     </div>
//   );
// };

// export default AmenityDetails;
// import React, { useState, useEffect } from 'react';
// import "./HotelResult.css";
// import SearchBar from "../../components/searchbar/SearchBar";
// import { useNavigate } from "react-router-dom";
// import { useDispatch } from "react-redux";
// import { fetchHotels } from "../../store/hotelSlice";
// import Header from "../../components/header/Header";

// export default function AmenityDetails() {
// const Filter = ({ options, selectedOptions, onFilterChange }) => {
//   const handleChange = (option) => {
//     let updatedOptions = [...selectedOptions];
//     if (updatedOptions.includes(option)) {
//       updatedOptions = updatedOptions.filter(item => item !== option);
//     } else {
//       updatedOptions.push(option);
//     }
//     onFilterChange(updatedOptions);
//   };

//   return (
//     <div className="filter-category">
//       <h3>Filter by Type</h3>
//       <ul>
//         {options.map(option => (
//           <li key={option}>
//             <label>
//               <input
//                 type="checkbox"
//                 checked={selectedOptions.includes(option)}
//                 onChange={() => handleChange(option)}
//               />
//               {option}
//             </label>
//           </li>
//         ))}
//       </ul>
//     </div>
//   );
// }
// const AmennityDetails = () => {
//   const [amenities, setAmenities] = useState([]);
//   const [filteredAmenities, setFilteredAmenities] = useState([]);
//   const [selectedTypes, setSelectedTypes] = useState([]);

//   useEffect(() => {
//     fetch("/amenity")
//       .then(response => response.json())
//       .then(data => {
//         setAmenities(data);
//         setFilteredAmenities(data);
//       });
//   }, []);

//   useEffect(() => {
//     if (selectedTypes.length === 0) {
//       setFilteredAmenities(amenities);
//     } else {
//       setFilteredAmenities(amenities.filter(a => selectedTypes.includes(a.type)));
//     }
//   }, [selectedTypes, amenities]);

//   const uniqueTypes = [...new Set(amenities.map(a => a.type))];

//   return (
//     <div className="filter-box">
//       <Filter options={uniqueTypes} selectedOptions={selectedTypes} onFilterChange={setSelectedTypes} />
//       <div className="amenity-list">
//         {filteredAmenities.map(a => (
//           <div key={a.id} className="amenity-item">
//             {a.name} ({a.type})
//           </div>
//         ))}
//       </div>
//     </div>
//   );
// }; 

// }
import React, { useState, useEffect } from 'react';
import "./HotelResult.css";

const Filter = ({ type, options, selectedOptions, onFilterChange }) => {
  const handleChange = (code) => {
    let updatedOptions = [...selectedOptions];
    if (updatedOptions.includes(code)) {
      updatedOptions = updatedOptions.filter(item => item !== code);
    } else {
      updatedOptions.push(code);
    }
    onFilterChange(updatedOptions);
  };

  return (
    <div className="filter-category">
      <h3>{type}</h3>
      <div className="filter-options">
        {options.map(option => (
          <div key={option.code} className="filter-option">
            <input
              type="checkbox"
              id={`amenity-${option.code}`}
              checked={selectedOptions.includes(option.code)}
              onChange={() => handleChange(option.code)}
            />
            <label htmlFor={`amenity-${option.code}`}>{option.name}</label>
          </div>
        ))}
      </div>
    </div>
  );
};

const AmenityDetails = ({ groupedAmenities }) => {
  const [selectedOptions, setSelectedOptions] = useState([]); // ✅ Khai báo state bên trong component
  const [filteredAmenities, setFilteredAmenities] = useState([]);

  const handleFilterChange = (updatedOptions) => {
    setSelectedOptions(updatedOptions);
  };

  useEffect(() => {
    if (selectedOptions.length === 0) {
      setFilteredAmenities(Object.values(groupedAmenities).flat());
    } else {
      setFilteredAmenities(
        Object.values(groupedAmenities)
          .flat()
          .filter(a => selectedOptions.every(option => a.amenities.includes(option))) // ✅ Lọc đúng điều kiện
      );
    }
  }, [selectedOptions, groupedAmenities]); // ✅ Đưa `selectedOptions` vào dependencies

  return (
    <div className="filter-box">
      {Object.entries(groupedAmenities).map(([type, amenities]) => (
        <Filter 
          key={type} 
          type={type} 
          options={amenities} 
          selectedOptions={selectedOptions} 
          onFilterChange={handleFilterChange} 
        />
      ))}
    </div>
  );
};

export default AmenityDetails;
