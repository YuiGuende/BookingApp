import React, { useState } from 'react';
import "./HotelResult.css";

const Filter = ({ filter, onFilterChange }) => {
    const [selectedOptions, setSelectedOptions] = useState([]);
  
    const handleChange = (option) => {
      let updatedOptions = [...selectedOptions];
      if (updatedOptions.includes(option)) {
        updatedOptions = updatedOptions.filter(item => item !== option);
      } else {
        updatedOptions.push(option);
      }
      setSelectedOptions(updatedOptions);
      onFilterChange(filter.id, updatedOptions);
    };
  
    const renderOptions = () => {
      switch (filter.type) {
        case 'checkbox':
          return filter.options.map(option => (
            <label key={option.id}>
              <input
                type="checkbox"
                checked={selectedOptions.includes(option.id)}
                onChange={() => handleChange(option.id)}
              />
              {option.name}
            </label>
          ));
        case 'radio':
          return filter.options.map(option => (
            <label key={option}>
              <input
                type="radio"
                checked={selectedOptions.includes(option)}
                onChange={() => handleChange(option)}
              />
              {option}
            </label>
          ));
        default:
          return null;
      }
    };
  
    return (
      <div className="filter-category">
        <h3>{filter.name}</h3>
        {renderOptions()}
      </div>
    );
  };
  
  // Filter Box Component
  const AmenityDetails = ({ filters, onFilterChange }) => {
    return (
      <div className="filter-box">
        {filters.map(filter => (
          <Filter
            key={filter.id}
            filter={filter}
            onFilterChange={onFilterChange}
          />
        ))}
      </div>
    );
  };
  
  export default AmenityDetails;