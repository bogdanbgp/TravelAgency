import React, { useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';
import './BookTours.css';

const BookTours = ({ user }) => {
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

    // Parse the URL to retrieve the selectedTour parameter
    const location = useLocation();
    const params = new URLSearchParams(location.search);
    const selectedTourId = params.get('selectedTour');

    useEffect(() => {
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

    const handleSubmit = (e) => {
        e.preventDefault();
        // Ensure all fields are completed
        if (!bookingDetails.firstName || !bookingDetails.lastName || !bookingDetails.email || !bookingDetails.mobile) {
            setError('Please fill in all required fields.');
            return;
        }

        setSuccessMessage("Our team will contact you for details and payment information.");
        setBookingDetails({
            firstName: '',
            lastName: '',
            age: '',
            email: '',
            mobile: ''
        });
        setSelectedTour(null);
    };

    return (
        <div className="book-tours-container">
            <h2>Book Your Tour</h2>

            {error && <div className="error-message">{error}</div>}
            {successMessage && <div className="success-message">{successMessage}</div>}

            {/* Section for available tours */}
            <section className="featured-tours-section">
                <div className="tour-list">
                    {tours.map((tour) => (
                        <div
                            key={tour.id}
                            className="tour-item"
                            onClick={() => setSelectedTour(tour)}
                        >
                            <h3>{tour.tourName}</h3>
                            <p>{tour.description}</p>
                            <p>Price: ${tour.price}</p>
                            <button className="select-tour-button">Select Tour</button>
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
                        required
                    />
                    <button type="submit" className="submit-button">Submit</button>
                </form>
            )}
        </div>
    );
};

export default BookTours;
