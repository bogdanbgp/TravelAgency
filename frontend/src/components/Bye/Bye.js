import React from 'react';
import { useNavigate } from 'react-router-dom';
import './Bye.css'; // Import the CSS for Bye component

const Bye = () => {
  const navigate = useNavigate();

  const handleReturnHome = () => {
    navigate('/'); // Redirect to home page
  };

  return (
    <div className="bye-container">
      <h1>Goodbye!</h1>
      <p>We're sad to see you go, but feel free to return anytime.</p>
      <button className="return-home-button" onClick={handleReturnHome}>
        Return to Home
      </button>
    </div>
  );
};

export default Bye;
