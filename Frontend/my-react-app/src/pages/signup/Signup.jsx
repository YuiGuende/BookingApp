import React, { useState } from 'react';
import './SignupStyles.css'; // Import file CSS
import axios from 'axios'; // Cần cài đặt axios bằng npm install axios
import { useNavigate } from "react-router-dom"
import Header from '../../components/header/Header';
const Signup = () => {
  // Các state lưu trữ thông tin đăng ký
  const [name, setName] = useState('');
  const [email, setEmail] = useState('');
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [phone, setPhone] = useState('');
  const [error, setError] = useState('');
  const [successMessage, setSuccessMessage] = useState('');
  const navigate = useNavigate()
  // Hàm xử lý khi người dùng gửi form
  const handleSubmit = async (e) => {
    e.preventDefault();
   
    const signupData = {
      name,
      email,
      username,
      password,
      phone
    };

    try {
      const response = await axios.post('http://localhost:8080/api/account/signUp', signupData, { withCredentials: true });

      if (response.data.status === 'success') {
        setSuccessMessage('Registration successful! You can log in now.');
        setError('');
        navigate("/login")
      } else {
        setError(response.data.message || 'Registration failed');
      }
    } catch (error) {
      setError('An error occurred during the registration process');
    }
  };

  return (
    <>
    <div className='header'><Header/></div>
    <div className="signup-page">
      <h2 id='signupStnce'>Sign Up</h2>
      <form onSubmit={handleSubmit} className="signup-form">
        <div className="input-container">
          <label>Full name</label>
          <input 
            type="text" 
            value={name} 
            onChange={(e) => setName(e.target.value)} 
            className="input-line" 
            required 
          />
        </div>
        <div className="input-container">
          <label>Email address</label>
          <input 
            type="email" 
            value={email} 
            onChange={(e) => setEmail(e.target.value)} 
            className="input-line" 
            required 
          />
        </div>
        <div className="input-container">
          <label>Username</label>
          <input 
            type="text" 
            value={username} 
            onChange={(e) => setUsername(e.target.value)} 
            className="input-line" 
            required 
          />
        </div>
        <div className="input-container">
          <label>Password</label>
          <input 
            type="password" 
            value={password} 
            onChange={(e) => setPassword(e.target.value)} 
            className="input-line" 
            required 
          />
        </div>
        <div className="input-container">
          <label>Phone number</label>
          <input 
            type="text" 
            value={phone} 
            onChange={(e) => setPhone(e.target.value)} 
            className="input-line" 
            required 
          />
        </div>
        {error && <div className="error">{error}</div>}
        {successMessage && <div className="success">{successMessage}</div>}
        <button type="submit" className="signup-button">Sign up</button>
      </form>
    </div>
    </>
  );
};

export default Signup;
