
function Header(){
    return(
        <>
        <header>
            <a className="return-home" href="index.html">Booking</a>
            <div className="startBtn">
                <button id="loginBtn">Log In</button>
                <button id="signupBtn">Sign Up</button>
            </div>
        </header>
        <div className="welcome-sentence">
            <h1>Find your next place to stay</h1>
            <h3>Find hotel deals, home stays and more...</h3>
        </div>
        </>  
    );

}
export default Header