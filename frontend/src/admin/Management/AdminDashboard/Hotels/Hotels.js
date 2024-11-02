import React, { useState, useEffect } from 'react';
import './Hotels.css';

const Hotels = () => {
    const [hotels, setHotels] = useState([]);
    const [newHotelName, setNewHotelName] = useState("");
    const [editingHotelId, setEditingHotelId] = useState(null);
    const [errorMessage, setErrorMessage] = useState("");
    const [successMessage, setSuccessMessage] = useState("");

    // Fetch all hotels when the component mounts
    useEffect(() => {
        fetchHotels();
    }, []);

    // Function to fetch hotels from the backend (GET)
    const fetchHotels = async () => {
        try {
            const response = await fetch('http://localhost:8080/api/admin/hotels', {
                method: 'GET',
            });
            if (!response.ok) {
                throw new Error('Failed to fetch hotels');
            }
            const data = await response.json();
            setHotels(data);
        } catch (error) {
            console.error('Error fetching hotels:', error);
        }
    };

    // Function to add or update a hotel (POST/PUT)
    const addOrUpdateHotel = async (event) => {
        event.preventDefault();
        const hotelData = { hotelName: newHotelName };

        // Clear any existing error message when attempting a new action
        setErrorMessage("");

        try {
            const response = editingHotelId
                ? await fetch(`http://localhost:8080/api/admin/hotels/${editingHotelId}`, {
                    method: 'PUT',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(hotelData),
                })
                : await fetch('http://localhost:8080/api/admin/hotels', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(hotelData),
                });

            if (!response.ok) {
                const errorData = await response.json();
                throw new Error(errorData.message);
            }

            const newHotel = await response.json();
            if (editingHotelId) {
                setHotels(hotels.map(hotel => (hotel.id === editingHotelId ? newHotel : hotel)));
            } else {
                setHotels([...hotels, newHotel]);
            }
            resetForm();
            setSuccessMessage(editingHotelId ? 'Hotel updated successfully!' : 'Hotel added successfully!');
        } catch (error) {
            setErrorMessage(`Error: ${error.message}`);
            console.error('Error adding/updating hotel:', error);
        }
    };

    // Function to handle editing a hotel
    const handleEdit = (hotel) => {
        setNewHotelName(hotel.hotelName);
        setEditingHotelId(hotel.id);
        // Clear messages
        setErrorMessage("");
        setSuccessMessage("");
    };

    // Function to delete a hotel (DELETE)
    const handleDelete = async (hotelId) => {
        // Clear success message before deletion attempt
        setSuccessMessage("");

        // Clear error message before making a deletion request
        setErrorMessage("");

        try {
            const response = await fetch(`http://localhost:8080/api/admin/hotels/${hotelId}`, {
                method: 'DELETE',
            });
            if (!response.ok) {
                const errorData = await response.json();
                throw new Error(errorData.message);
            }
            setHotels(hotels.filter((hotel) => hotel.id !== hotelId));
            setSuccessMessage('Hotel deleted successfully!');
        } catch (error) {
            setErrorMessage(`Error: ${error.message}`);
            console.error('Error deleting hotel:', error);
        }
    };

    // Function to reset the form
    const resetForm = () => {
        setNewHotelName("");
        setEditingHotelId(null);
        setSuccessMessage("");
        setErrorMessage("");
    };

    return (
        <div className="hotels-container">
            <div className="hotels-header">
                <h1>Manage Hotels</h1>
            </div>

            <form onSubmit={addOrUpdateHotel} className="update-form">
                <input
                    type="text"
                    value={newHotelName}
                    onChange={(e) => setNewHotelName(e.target.value)}
                    placeholder="Enter hotel name"
                    required
                    className="form-input"
                />
                <button type="submit" className="submit-button">
                    {editingHotelId ? 'Update Hotel' : 'Add Hotel'}
                </button>
                {editingHotelId && (
                    <button
                        type="button"
                        onClick={resetForm}
                        className="cancel-button"
                    >
                        Cancel
                    </button>
                )}
            </form>

            {errorMessage && <div className="error-message" style={{ color: 'red' }}>{errorMessage}</div>}
            {successMessage && <div className="success-message" style={{ color: 'green' }}>{successMessage}</div>}

            <h2>Hotel List</h2>
            <ul className="user-list">
                {hotels.map((hotel) => (
                    <li key={hotel.id} className="user-item">
                        <span className="user-info">{hotel.hotelName}</span>
                        <div>
                            <button onClick={() => handleEdit(hotel)} className="update-button">Edit</button>
                            <button onClick={() => handleDelete(hotel.id)} className="delete-button">Delete</button>
                        </div>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default Hotels;
