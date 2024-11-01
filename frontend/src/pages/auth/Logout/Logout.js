// File: Logout.js

import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './Logout.css'; // Import the CSS file for styles

const Logout = ({ setUser }) => {
  const [showConfirmation, setShowConfirmation] = useState(false);
  const navigate = useNavigate();

  const handleLogout = async () => {
    const endpoint = 'http://localhost:8080/api/auth/logout'; // User logout endpoint

    try {
      await fetch(endpoint, {
        method: 'POST',
        credentials: 'include', // Include credentials for the session
      });

      // Clear session storage
      sessionStorage.clear();
      setUser(null); // Reset user state
      navigate('/'); // Redirect to home page
    } catch (error) {
      console.error('Error during logout:', error);
    }
  };

  const handleConfirmLogout = () => {
    handleLogout(); // Call the logout function
  };

  const handleCancelLogout = () => {
    setShowConfirmation(false); // Hide the confirmation dialog if the user cancels
  };

  return (
    <div className="logout-container">
      <button
        onClick={() => setShowConfirmation(true)} // Show confirmation dialog when clicked
        style={{ cursor: 'pointer' }} // Change cursor to pointer on hover
      >
        Logout
      </button>

      {showConfirmation && (
        <div className="confirmation-dialog">
          <p>Are you sure you want to log out?</p>
          <button onClick={handleConfirmLogout}>Yes</button>
          <button onClick={handleCancelLogout}>No</button>
        </div>
      )}
    </div>
  );
};

export default Logout;
