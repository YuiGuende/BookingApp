import "./HeaderStyles.css";
import { useNavigate } from "react-router-dom"

function Header(){
    const navigate = useNavigate()
    return(
        <header>
            <a className="return-home" href="/">Booking</a>
            <div className="startBtn">
                <button id="loginBtn" onClick={() => navigate("/Login")}>Log In</button>
                <button id="signupBtn" onClick={() => navigate("/Signup")}>Sign Up</button>
            </div>
        </header>
    );

}
export default Header