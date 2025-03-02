import React, { useState } from 'react';
import './LoginStyles.css'; // Import file CSS
import axios from 'axios'; // Cần cài đặt axios bằng npm install axios
import { useNavigate } from "react-router-dom"
const Login = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const [userType, setUserType] = useState('CUSTOMER');
  const navigate = useNavigate()
  const handleSubmit = async (e) => {
    e.preventDefault();

    const accountRequest = {
      username: username,
      password: password,
      userType: 'CUSTOMER'
    };

    try {
      const response = await axios.post('http://localhost:8080/api/auth/login', accountRequest);

      if (response.data.status === 'success') {
        localStorage.removeItem("customerInfor")
        localStorage.setItem("customerInfor",JSON.stringify(response.data.data))
        alert('Log in successfully!');
        navigate("/") 
        // Có thể chuyển hướng trang sau khi đăng nhập thành công
        // window.location.href = '/'; // Chuyển hướng đến trang dashboard hoặc trang khác
      } else {
        setError(response.data.message || 'Login failed');
      }
    } catch (error) {
      setError('An error occurred during the login process');
    }
  };

  return (
    <div className="login-page">
      <h2>Log In</h2>
      <form onSubmit={handleSubmit} className="login-form">
        <div className="input-container">
          <label>Username</label>
          <input 
            type="text" 
            value={username} 
            onChange={(e) => setUsername(e.target.value)} 
            className="input" 
            required 
          />
        </div>
        <div className="input-container">
          <label>Password</label>
          <input 
            type="password" 
            value={password} 
            onChange={(e) => setPassword(e.target.value)} 
            className="input" 
            required 
          />
        </div>
        {error && <div className="error">{error}</div>}
        <button type="submit" className="button">Log in</button>
      </form>
    </div>
  );
};

export default Login;
