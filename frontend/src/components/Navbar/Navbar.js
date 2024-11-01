import React, { useState, useEffect, useRef } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import './Navbar.css';

function Navbar({ handleLogout, isLoggedIn }) {
  const [showLogoutConfirm, setShowLogoutConfirm] = useState(false);
  const [notification, setNotification] = useState(null); // State for notifications
  const navigate = useNavigate();

  // Refs to manage outside clicks
  const logoutConfirmRef = useRef(null);

  // Retrieve user and superadmin from session storage
  const user = JSON.parse(sessionStorage.getItem('user'));
  const superAdmin = JSON.parse(sessionStorage.getItem('superadmin'));

  const isSuperAdminLoggedIn = superAdmin !== null;
  const isUserLoggedIn = user !== null;
  const username = isUserLoggedIn ? user.username : isSuperAdminLoggedIn ? superAdmin.username : '';

  // Handle outside clicks for logout confirmation
  useEffect(() => {
    const handleClickOutside = (event) => {
      if (logoutConfirmRef.current && !logoutConfirmRef.current.contains(event.target)) {
        setShowLogoutConfirm(false);
      }
    };

    // Add event listener for clicks
    document.addEventListener('mousedown', handleClickOutside);
    return () => {
      document.removeEventListener('mousedown', handleClickOutside);
    };
  }, []);

  const toggleLogoutConfirm = () => {
    setShowLogoutConfirm((prev) => !prev);
  };

  const confirmLogout = () => {
    handleLogout();
    setShowLogoutConfirm(false);
    navigate('/bye');
  };

  // Function to handle admin login/register attempts
  const handleAdminAction = (e) => {
    e.preventDefault();
    if (isUserLoggedIn || isSuperAdminLoggedIn) {
      setNotification('Please log out to access admin options.');
    } else {
      navigate('/login/superadmin'); // Redirect to admin login if logged out
    }
  };

  // Function to handle user login/register attempts
  const handleUserAction = (e) => {
    e.preventDefault();
    if (isSuperAdminLoggedIn) {
      setNotification('Please log out to access user options.');
    } else {
      navigate('/login'); // Redirect to user login if logged out
    }
  };

  // Function to handle SuperAdmin registration
  const handleSuperAdminRegister = (e) => {
    e.preventDefault();
    navigate('/register/superadmin'); // Redirect to SuperAdmin registration page
  };

  return (
    <nav className="navbar">
      <ul className="navbar-links">
        <li><Link to="/">Home</Link></li>
        <li><Link to="/about">About</Link></li>
        <li><Link to="/contact">Contact</Link></li>
        <li><Link to="/tours">Available Tours</Link></li>

        {/* Display links based on user type */}
        {!isUserLoggedIn && !isSuperAdminLoggedIn && (
          <>
            <li><Link to="/login" onClick={handleUserAction}>Login</Link></li>
            <li><Link to="/register">Register</Link></li>
            <li><Link to="/login/superadmin" onClick={handleAdminAction}>SuperAdmin Login</Link></li>
            <li><Link to="/register/superadmin" onClick={handleSuperAdminRegister}>SuperAdmin Register</Link></li>
            <span className="status-indicator" style={{ backgroundColor: 'red' }}></span>
          </>
        )}

        {isUserLoggedIn && (
          <>
            <li className="logout-container" ref={logoutConfirmRef}>
              <button className="button logout-button" onClick={toggleLogoutConfirm}>
                Logout
              </button>
              {showLogoutConfirm && (
                <div className="popup confirmation-dialog">
                  <p>Are you sure you want to log out?</p>
                  <div style={{ display: 'flex', justifyContent: 'center' }}>
                    <button className="button confirm-button" onClick={confirmLogout}>Yes</button>
                    <button className="button cancel-button" onClick={toggleLogoutConfirm}>No</button>
                  </div>
                </div>
              )}
            </li>
            <span className="status-indicator" style={{ backgroundColor: 'green' }}></span>
            <span className="username-display">{username}</span>
          </>
        )}

        {isSuperAdminLoggedIn && (
          <>
            <li className="logout-container" ref={logoutConfirmRef}>
              <button className="button logout-button" onClick={toggleLogoutConfirm}>
                Logout
              </button>
              {showLogoutConfirm && (
                <div className="popup confirmation-dialog">
                  <p>Are you sure you want to log out?</p>
                  <div style={{ display: 'flex', justifyContent: 'center' }}>
                    <button className="button confirm-button" onClick={confirmLogout}>Yes</button>
                    <button className="button cancel-button" onClick={toggleLogoutConfirm}>No</button>
                  </div>
                </div>
              )}
            </li>
            <li><Link to="/superadmin/dashboard">Admin Dashboard</Link></li>
            <span className="status-indicator" style={{ backgroundColor: 'green' }}></span>
            <span className="username-display">{username}</span>
          </>
        )}
      </ul>

      {/* Notification Display */}
      {notification && (
        <div className="notification">
          {notification}
          <button onClick={() => setNotification(null)} className="notification-close">✖</button>
        </div>
      )}
    </nav>
  );
}

export default Navbar;
