// function Button() {    

//     return(
//         <button type="submit" className="search-button">
//             <i className="fa fa-search"></i>
//         </button>
//     );
// }
// export default Button
export default function Button({ children, type = "button", ...props }) {
    return (
      <button type={type} className="search-button" {...props}>
        {children}
      </button>
    )
  }