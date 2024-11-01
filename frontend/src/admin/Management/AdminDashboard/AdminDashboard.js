import React, { useEffect, useState } from 'react'; // Import necessary hooks from React
import { Link } from 'react-router-dom'; // Import Link for routing
import './AdminDashboard.css'; // Import the CSS for the Admin Dashboard

const AdminDashboard = () => {
  const [error, setError] = useState(''); // State for error message
  const [isLoading, setIsLoading] = useState(true); // State for loading

  useEffect(() => {
    const superadmin = JSON.parse(sessionStorage.getItem('superadmin')); // Retrieve superadmin data

    if (!superadmin) {
      setError('Log in as superadmin to access the admin dashboard.'); // Set error message if not logged in
    }
    setIsLoading(false); // Set loading to false after checking login status
  }, []);

  return (
    <div className="admin-dashboard">
      <div className="admin-dashboard-box"> {/* Wrapper for the white box */}
        <h1>Admin Dashboard</h1>

        {isLoading ? (
          <p>Loading...</p> // Display loading message while checking login
        ) : error ? (
          <div className="error-message">{error}</div> // Display error message if no superadmin
        ) : (
          <ul>
            <li><Link to="/superadmin/users">Manage Users</Link></li>
            <li><Link to="/superadmin/hotels">Manage Hotels</Link></li>
            <li><Link to="/superadmin/countries">Manage Countries</Link></li>
            <li><Link to="/superadmin/cities">Manage Cities</Link></li>
            <li><Link to="/superadmin/tours">Manage Tours</Link></li>
            <li><Link to="/superadmin/airports">Manage Airports</Link></li>
            <li><Link to="/superadmin/roles">Manage Roles</Link></li>
          </ul>
        )}
      </div>
    </div>
  );
}

export default AdminDashboard;
