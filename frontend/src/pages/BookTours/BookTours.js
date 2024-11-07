import React, { useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';
import './BookTours.css';

const BookTours = () => {
    const [tours, setTours] = useState([]);
    const [selectedTour, setSelectedTour] = useState(null);
    const [bookingDetails, setBookingDetails] = useState({
        firstName: '',
        lastName: '',
        age: '',
        email: '',
        mobile: ''
    });
    const [error, setError] = useState('');
    const [successMessage, setSuccessMessage] = useState('');
    const [user, setUser] = useState(null);

    const location = useLocation();
    const params = new URLSearchParams(location.search);
    const selectedTourId = params.get('selectedTour');

    useEffect(() => {
        // Retrieve user data from session storage
        const userData = sessionStorage.getItem('user');
        if (userData) {
            const parsedUser = JSON.parse(userData);
            console.log('User data retrieved from session storage:', parsedUser);
            setUser(parsedUser);

            // Check if user ID is present
            if (!parsedUser.id) {
                setError('User ID is not defined. Please log in again.');
            }
        } else {
            console.log('No user data found in session storage.');
            setError('User is not defined. Please log in again.');
        }

        fetchTours();
    }, []);

    useEffect(() => {
        // If selectedTourId is present, set the tour based on it
        if (selectedTourId && tours.length > 0) {
            const tour = tours.find((t) => t.id === parseInt(selectedTourId));
            if (tour) setSelectedTour(tour);
        }
    }, [selectedTourId, tours]);

    const fetchTours = async () => {
        try {
            const response = await fetch('http://localhost:8080/api/admin/tours', {
                credentials: 'include'
            });
            if (!response.ok) {
                throw new Error('Failed to fetch tours');
            }
            const data = await response.json();
            setTours(data);
        } catch (error) {
            setError(error.message);
        }
    };

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setBookingDetails((prevDetails) => ({
            ...prevDetails,
            [name]: value
        }));
        setError('');
    };

    const validateEmail = (email) => {
        const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/; // Basic email format check
        return emailPattern.test(email);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (!selectedTour) {
            setError('Please select a tour to book.');
            return;
        }

        // Check if user ID is defined
        if (!user || !user.id) {
            setError('User is not defined. Please log in again.');
            return;
        }

        // Validate email and mobile number
        if (!validateEmail(bookingDetails.email)) {
            setError('Please enter a valid email address.');
            return;
        }

        if (bookingDetails.mobile.length > 10) {
            setError('Mobile number must be at most 10 characters long.');
            return;
        }

        try {
            const response = await fetch(`http://localhost:8080/api/admin/${user.id}/tours/${selectedTour.id}`, {
                method: 'POST',
                credentials: 'include',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(bookingDetails),
            });

            if (!response.ok) {
                const errorData = await response.json();
                throw new Error(errorData.message || 'Failed to book tour. Please try again.');
            }

            // Updated success message to indicate team will contact user
            setSuccessMessage("Tour booked successfully! Our team will contact you shortly.");
            setSelectedTour(null); // Reset selected tour
            setBookingDetails({
                firstName: '',
                lastName: '',
                age: '',
                email: '',
                mobile: ''
            });

        } catch (error) {
            setError(error.message);
        }
    };

    return (
        <div className="book-tours-container">
            <h2>Book Your Tour</h2>

            {error && <div className="error-message">{error}</div>}
            {successMessage && <div className="success-message">{successMessage}</div>}

            <section className="featured-tours-section">
                <div className="tour-list">
                    {tours.map((tour) => (
                        <div key={tour.id} className="tour-item">
                            <h3>{tour.tourName}</h3>
                            <p>{tour.description}</p>
                            <p>Price: ${tour.price}</p>
                            <button
                                className="select-tour-button"
                                onClick={() => setSelectedTour(tour)}
                            >
                                Select Tour
                            </button>
                        </div>
                    ))}
                </div>
            </section>

            {selectedTour && (
                <form className="booking-form" onSubmit={handleSubmit}>
                    <h3>Booking Details for {selectedTour.tourName}</h3>
                    <input
                        type="text"
                        name="firstName"
                        placeholder="First Name"
                        value={bookingDetails.firstName}
                        onChange={handleInputChange}
                        required
                    />
                    <input
                        type="text"
                        name="lastName"
                        placeholder="Last Name"
                        value={bookingDetails.lastName}
                        onChange={handleInputChange}
                        required
                    />
                    <input
                        type="number"
                        name="age"
                        placeholder="Age"
                        value={bookingDetails.age}
                        onChange={handleInputChange}
                        required
                    />
                    <input
                        type="email"
                        name="email"
                        placeholder="Email"
                        value={bookingDetails.email}
                        onChange={handleInputChange}
                        required
                    />
                    <input
                        type="text"
                        name="mobile"
                        placeholder="Mobile"
                        value={bookingDetails.mobile}
                        onChange={handleInputChange}
                        maxLength="10" // Limit to 10 characters
                        required
                    />
                    <button type="submit" className="submit-button">Submit</button>
                </form>
            )}
        </div>
    );
};

export default BookTours;
