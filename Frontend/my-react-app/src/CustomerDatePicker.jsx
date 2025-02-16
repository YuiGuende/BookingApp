// import React, { useState } from 'react';
// import DatePicker from 'react-datepicker';
// import 'react-datepicker/dist/react-datepicker.css';

// function CustomerDatePicker(){
//   const [startDate, setStartDate] = useState(new Date());

//   return (
//     <DatePicker
//       selected={startDate}
//       onChange={(date) => setStartDate(date)}
//       dateFormat="dd/MM/yyyy"
//     />
//   );
// };

// export default CustomerDatePicker;

import * as React from 'react';
import { DemoContainer } from '@mui/x-date-pickers/internals/demo';
import { LocalizationProvider } from '@mui/x-date-pickers-pro/LocalizationProvider';
import { AdapterDayjs } from '@mui/x-date-pickers-pro/AdapterDayjs';
import { DateRangePicker } from '@mui/x-date-pickers-pro/DateRangePicker';

export default function CustomerDatePicker() {
  const handleDateChange = (dates) => {
    console.log('Selected dates:', dates);
    if (Array.isArray(dates) && dates.length === 2) {
      onDateChange(dates);
    }
  };

  return (
    <LocalizationProvider dateAdapter={AdapterDayjs}>
      <DemoContainer components={['DateRangePicker']}>
        <DateRangePicker localeText={{ start: 'Check-in', end: 'Check-out' }} onChange={handleDateChange}/>
      </DemoContainer>
    </LocalizationProvider>
  );
}