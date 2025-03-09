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
import React, { useState, useEffect } from 'react';
import "./HotelResult.css";

const Filter = ({ options, selectedOptions, onFilterChange }) => {
  const handleChange = (option) => {
    let updatedOptions = [...selectedOptions];
    if (updatedOptions.includes(option)) {
      updatedOptions = updatedOptions.filter(item => item !== option);
    } else {
      updatedOptions.push(option);
    }
    onFilterChange(updatedOptions);
  };

  return (
    <div className="filter-category">
      <h3>Filter by Type</h3>
      <ul>
        {options.map(option => (
          <li key={option}>
            <label>
              <input
                type="checkbox"
                checked={selectedOptions.includes(option)}
                onChange={() => handleChange(option)}
              />
              {option}
            </label>
          </li>
        ))}
      </ul>
    </div>
  );
};

const AmenityList = () => {
  const [amenities, setAmenities] = useState([]);
  const [filteredAmenities, setFilteredAmenities] = useState([]);
  const [selectedTypes, setSelectedTypes] = useState([]);

  useEffect(() => {
    fetch("/amenity")
      .then(response => response.json())
      .then(data => {
        setAmenities(data);
        setFilteredAmenities(data);
      });
  }, []);

  useEffect(() => {
    if (selectedTypes.length === 0) {
      setFilteredAmenities(amenities);
    } else {
      setFilteredAmenities(amenities.filter(a => selectedTypes.includes(a.type)));
    }
  }, [selectedTypes, amenities]);

  const uniqueTypes = [...new Set(amenities.map(a => a.type))];

  return (
    <div className="filter-box">
      <Filter options={uniqueTypes} selectedOptions={selectedTypes} onFilterChange={setSelectedTypes} />
      <div className="amenity-list">
        {filteredAmenities.map(a => (
          <div key={a.id} className="amenity-item">
            {a.name} ({a.type})
          </div>
        ))}
      </div>
    </div>
  );
};

export default AmenityList;
