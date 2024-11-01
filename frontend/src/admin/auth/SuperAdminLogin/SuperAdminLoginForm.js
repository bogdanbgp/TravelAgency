import React, { useState } from 'react';
import './SuperAdminLoginForm.css'; // Import specific CSS for SuperAdmin Login
import { useNavigate } from 'react-router-dom'; // Import useNavigate for navigation

function SuperAdminLoginForm({ onLogin }) { // Accept onLogin as a prop to update parent state
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const [successMessage, setSuccessMessage] = useState(''); // State for success message
  const [isLoading, setIsLoading] = useState(false); // Loading state
  const navigate = useNavigate(); // For navigation after login

  const handleLogin = async (e) => {
    e.preventDefault(); // Prevent default form submission
    setIsLoading(true); // Set loading to true
    setError(''); // Clear previous errors
    setSuccessMessage(''); // Clear previous success messages

    const endpoint = 'http://localhost:8080/api/auth/login/superadmin';

    try {
      // Log the username and password for debugging
      console.log('Logging in with:', { username, password });

      const response = await fetch(endpoint, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json', // Set content type to JSON
        },
        body: JSON.stringify({ username, password }), // Send username and password as JSON
      });

      // Log the complete response for debugging
      console.log('Response:', response);

      if (!response.ok) {
        const errorData = await response.json(); // Get error data from response
        console.error('Login Error:', errorData); // Log error for debugging
        throw new Error(errorData.message || 'Invalid credentials'); // Throw error with message
      }

      const data = await response.json(); // Parse the response data
      sessionStorage.setItem('superadmin', JSON.stringify(data)); // Store superadmin info in session storage
      setSuccessMessage('Login successful! Redirecting to the dashboard...'); // Set success message
      onLogin(); // Notify parent component about successful login

      // Automatically redirect to the dashboard after 1 second (1000 milliseconds)
      setTimeout(() => {
        navigate('/superadmin/dashboard'); // Use navigate to redirect
      }, 1000); // Reduced from 3000 to 1000 milliseconds

    } catch (error) {
      console.error('Error during login:', error); // Log any caught errors
      setError(error.message); // Update the error state with the error message
    } finally {
      setIsLoading(false); // Set loading to false after operation
    }
  };

  return (
    <div className="login-container">
      <div className="login-form-box">
        <h2>Superadmin Login</h2>
        <form onSubmit={handleLogin}>
          <div className="input-group">
            <label>Username:</label>
            <input
              type="text"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              required
              autoFocus // Automatically focus on the username field
            />
          </div>
          <div className="input-group">
            <label>Password:</label>
            <input
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
          </div>
          <button type="submit" className="submit-button" disabled={isLoading}>
            {isLoading ? 'Logging in...' : 'Login'} {/* Loading state */}
          </button>
          {error && <p className="error-message">{error}</p>} {/* Display error message */}
          {successMessage && <p className="success-message" style={{ color: 'green' }}>{successMessage}</p>} {/* Display success message */}
        </form>
      </div>
    </div>
  );
}

export default SuperAdminLoginForm;
