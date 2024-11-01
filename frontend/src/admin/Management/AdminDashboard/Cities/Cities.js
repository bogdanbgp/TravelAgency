import React, { useState, useEffect } from 'react';
import './Cities.css'; // Import the CSS file

const Cities = () => {
    const [cities, setCities] = useState([]);
    const [newCityName, setNewCityName] = useState("");
    const [countryId, setCountryId] = useState("");
    const [editingCityId, setEditingCityId] = useState(null);
    const [errorMessage, setErrorMessage] = useState(""); // State for error message
    const [successMessage, setSuccessMessage] = useState(""); // State for success message

    // Fetch all cities when the component mounts
    useEffect(() => {
        fetchCities();
    }, []);

    // Function to fetch cities from the backend (GET)
    const fetchCities = async () => {
        try {
            const response = await fetch('http://localhost:8080/api/admin/cities', { method: 'GET' });
            if (!response.ok) {
                throw new Error('Failed to fetch cities');
            }
            const data = await response.json();
            setCities(data);
        } catch (error) {
            console.error('Error fetching cities:', error);
        }
    };

    // Function to add or update a city (POST/PUT)
    const addOrUpdateCity = async (event) => {
        event.preventDefault();
        const cityData = { cityName: newCityName, countryId };

        try {
            const response = editingCityId
                ? await fetch(`http://localhost:8080/api/admin/cities/${editingCityId}`, {
                    method: 'PUT',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(cityData),
                })
                : await fetch('http://localhost:8080/api/admin/cities', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(cityData),
                });

            if (!response.ok) {
                // Check for specific error response
                if (response.status === 404) {
                    setErrorMessage("Country not found. Please enter a valid country ID.");
                } else {
                    throw new Error(editingCityId ? 'Failed to update city' : 'Failed to create city');
                }
                setSuccessMessage(""); // Clear success message on error
                return; // Exit the function if there was an error
            }

            const newCity = await response.json();
            setCities(editingCityId
                ? cities.map(city => (city.id === editingCityId ? newCity : city))
                : [...cities, newCity]);
            resetForm();
            setErrorMessage(""); // Clear error message on success
            setSuccessMessage(editingCityId ? 'City updated successfully!' : 'City added successfully!'); // Set success message
        } catch (error) {
            console.error('Error adding/updating city:', error);
        }
    };

    // Function to handle editing a city
    const handleEdit = (city) => {
        setNewCityName(city.cityName);
        setCountryId(city.countryId);
        setEditingCityId(city.id);
        setErrorMessage(""); // Clear any previous error message
        setSuccessMessage(""); // Clear any previous success message
    };

    // Function to delete a city (DELETE)
    const handleDelete = async (cityId) => {
        try {
            const response = await fetch(`http://localhost:8080/api/admin/cities/${cityId}`, { method: 'DELETE' });
            if (!response.ok) {
                throw new Error('Failed to delete city');
            }
            setCities(cities.filter((city) => city.id !== cityId));
        } catch (error) {
            console.error('Error deleting city:', error);
        }
    };

    // Function to reset the form
    const resetForm = () => {
        setNewCityName("");
        setCountryId("");
        setEditingCityId(null);
        setSuccessMessage(""); // Clear success message
        setErrorMessage(""); // Clear error message on reset
    };

    return (
        <div className="cities-container">
            <h1>Manage Cities</h1>
            <form className="add-city-form" onSubmit={addOrUpdateCity}>
                <input
                    type="text"
                    className="form-input"
                    value={newCityName}
                    onChange={(e) => setNewCityName(e.target.value)}
                    placeholder="Enter city name"
                    required
                />
                <input
                    type="text"
                    className="form-input"
                    value={countryId}
                    onChange={(e) => setCountryId(e.target.value)}
                    placeholder="Enter country ID"
                    required
                />
                <button type="submit" className="submit-button">
                    {editingCityId ? 'Update City' : 'Add City'}
                </button>
            </form>
            {errorMessage && <div className="error-message">{errorMessage}</div>} {/* Centered error message */}
            {successMessage && <div className="success-message" style={{ color: 'green' }}>{successMessage}</div>} {/* Success message */}
            <h2>City List</h2>
            <ul className="city-list">
                {cities.map((city) => (
                    <li key={city.id} className="city-item">
                        <span className="city-info">{city.cityName} (Country: {city.countryName || 'N/A'})</span>
                        <div>
                            <button className="update-button" onClick={() => handleEdit(city)}>Edit</button>
                            <button className="delete-button" onClick={() => handleDelete(city.id)}>Delete</button>
                        </div>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default Cities;
