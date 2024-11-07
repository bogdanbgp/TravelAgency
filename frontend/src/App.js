import React, { useState } from 'react';
import { BrowserRouter as Router, Route, Routes, Navigate } from 'react-router-dom'; // Import Navigate

import Navbar from './components/Navbar/Navbar';
import Bye from './components/Bye/Bye';
import NotFound from './components/NotFound/NotFound';
import Profile from './components/Profile/Profile';  // Import the Profile component
import Review from './pages/Review/Review'; // Import Review component
import ReadReviews from './pages/Review/ReadReviews'; // Import ReadReviews component

import Home from './pages/Home/Home';
import About from './pages/About/About';
import Contact from './pages/Contact/Contact';
import BookTours from './pages/BookTours/BookTours';

import SuperAdminLoginForm from './admin/auth/SuperAdminLogin/SuperAdminLoginForm';
import SuperAdminRegisterForm from './admin/auth/SuperAdminRegister/SuperAdminRegisterForm';
import AdminDashboard from './admin/Management/AdminDashboard/AdminDashboard';
import Users from './admin/Management/AdminDashboard/Users/Users';
import Tours from './admin/Management/AdminDashboard/Tours/Tours';
import Roles from './admin/Management/AdminDashboard/Roles/Roles';
import Countries from './admin/Management/AdminDashboard/Countries/Countries';
import Cities from './admin/Management/AdminDashboard/Cities/Cities';
import Airports from './admin/Management/AdminDashboard/Airports/Airports';
import Hotels from './admin/Management/AdminDashboard/Hotels/Hotels';

import Register from './pages/auth/Register/Register';
import Login from './pages/auth/Login/Login';
import Logout from './pages/auth/Logout/Logout';

function App() {
  const [user, setUser] = useState(JSON.parse(sessionStorage.getItem('user')));
  const [superAdmin, setSuperAdmin] = useState(JSON.parse(sessionStorage.getItem('superadmin'))); // State for superadmin

  // Updates the user state after a successful login
  const handleUserLogin = () => {
    setUser(JSON.parse(sessionStorage.getItem('user')));
  };

  // Updates the super admin state after a successful login
  const handleSuperAdminLogin = () => {
    setSuperAdmin(JSON.parse(sessionStorage.getItem('superadmin')));
  };

  // Clears the user session and state on logout
  const handleLogout = () => {
    sessionStorage.removeItem('user');
    setUser(null);
    sessionStorage.removeItem('superadmin'); // Clear superadmin session
    setSuperAdmin(null); // Clear superadmin state
  };

  return (
    <Router>
      <Navbar handleLogout={handleLogout} isLoggedIn={!!user || !!superAdmin} /> {/* Updated to reflect superadmin status */}
      <div className="app-container">
        <Routes>
          {/* Public Routes */}
          <Route path="/" element={<Home />} />
          <Route path="/contact" element={<Contact />} />
          <Route path="/about" element={<About />} />
          <Route path="/register" element={<Register />} />
          <Route path="/login" element={<Login onLogin={handleUserLogin} />} />
          <Route path="/tours" element={<BookTours user={user} />} />
          <Route path="/logout" element={<Logout setUser={setUser} />} />

          {/* Admin Routes */}
          <Route path="/login/superadmin" element={<SuperAdminLoginForm onLogin={handleSuperAdminLogin} />} /> {/* Pass onLogin */}
          <Route path="/register/superadmin" element={<SuperAdminRegisterForm />} />
          <Route path="/superadmin/dashboard" element={<AdminDashboard />} />
          <Route path="/superadmin/users" element={<Users />} />
          <Route path="/superadmin/tours" element={<Tours />} />
          <Route path="/superadmin/roles" element={<Roles />} />
          <Route path="/superadmin/countries" element={<Countries />} />
          <Route path="/superadmin/cities" element={<Cities />} />
          <Route path="/superadmin/airports" element={<Airports />} />
          <Route path="/superadmin/hotels" element={<Hotels />} />

          {/* SuperAdmin Route to Read Reviews */}
          <Route path="/superadmin/reviews" element={superAdmin ? <ReadReviews /> : <Navigate to="/login/superadmin" />} />

          {/* Review Route - Only for Logged-in Users */}
          <Route path="/review" element={user ? <Review /> : <Navigate to="/login" />} /> {/* Redirect to login if not authenticated */}

          {/* User Profile Route - Protected */}
          <Route path="/profile" element={user ? <Profile /> : <Navigate to="/login" />} />  {/* Redirect to login if not authenticated */}

          {/* Miscellaneous Routes */}
          <Route path="/bye" element={<Bye />} />
          <Route path="*" element={<NotFound />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
