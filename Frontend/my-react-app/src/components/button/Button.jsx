// export default Button
// export default function Button({ children, type = "button", ...props }) {
//     return (
//       <button type={type} className="search-button" {...props}>
//         {children}<i className="fa fa-search"></i>
//       </button>
//     )
//   }
import React from 'react';
import { Search } from 'lucide-react';
import './ButtonStyles.css';

export default function Button({ children, type = "button", iconOnly = false, ...props }) {
  return (
    <button 
      type={type} 
      className={`search-button ${iconOnly ? 'icon-only' : ''}`} 
      {...props}
    >
      {iconOnly ? <Search size={24} /> : children}
    </button>
  );
}
