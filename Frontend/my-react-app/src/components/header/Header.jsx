import "./HeaderStyles.css";
function Header(){
    return(
        <header>
            <a className="return-home" href="Frontend/my-react-app/index.html">Booking</a>
            <div className="startBtn">
                <button id="loginBtn">Log In</button>
                <button id="signupBtn">Sign Up</button>
            </div>
        </header>
    );

}
export default Header