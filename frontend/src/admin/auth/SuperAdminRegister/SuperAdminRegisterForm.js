import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom'; // Import useNavigate for redirection
import './SuperAdminRegisterForm.css'; // Ensure this path is correct

function SuperAdminRegisterForm() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const [successMessage, setSuccessMessage] = useState('');
  const navigate = useNavigate(); // Initialize useNavigate

  const handleRegister = async (e) => {
    e.preventDefault();

    const registerRequest = {
      username,
      password,
    };

    try {
      const response = await fetch('http://localhost:8080/api/auth/register/superadmin', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(registerRequest),
      });

      if (response.ok) {
        const contentType = response.headers.get('Content-Type');
        let data;

        if (contentType && contentType.includes('application/json')) {
          data = await response.json();
        } else {
          data = await response.text(); // Handle plain text response
        }

        setError(''); // Clear any previous errors
        setSuccessMessage(data.message || 'Registration successful! Redirecting to login page...'); // Set success message

        // Redirect to superadmin login after a brief delay
        setTimeout(() => {
          navigate('/login/superadmin'); // Redirect to Super Admin login
        }, 1000); // Redirect after 1 second

        // Optionally clear the form
        setUsername('');
        setPassword('');
      } else {
        const contentType = response.headers.get('Content-Type');
        let errorData;

        if (contentType && contentType.includes('application/json')) {
          errorData = await response.json();
        } else {
          errorData = await response.text(); // Handle plain text error
        }

        setError(errorData.message || 'Registration failed'); // Specific error from the server
        setSuccessMessage(''); // Clear success message when there's an error
      }
    } catch (error) {
      setError(error.message); // Handle fetch errors
      setSuccessMessage(''); // Clear success message when there's an error
    }
  };

  return (
    <div className="register-container">
      <div className="register-form-box">
        <h2>Create Super Admin Account</h2>
        <form onSubmit={handleRegister}>
          <div className="input-group">
            <label>Username:</label>
            <input
              type="text"
              placeholder="e.g. super_admin"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              required
            />
          </div>

          <div className="input-group">
            <label>Password:</label>
            <input
              type="password"
              placeholder="Choose a strong password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
          </div>

          <button type="submit" className="submit-button">Register</button>
          {error && <p className="error-message">{error}</p>}
          {successMessage && <p className="success-message">{successMessage}</p>}
        </form>
      </div>
    </div>
  );
}

export default SuperAdminRegisterForm;
