import React, { useState, useEffect } from 'react';
import './Countries.css'; // Ensure this CSS file is created or modified accordingly

const Countries = () => {
    const [countries, setCountries] = useState([]);
    const [newCountryName, setNewCountryName] = useState("");
    const [editingCountryId, setEditingCountryId] = useState(null);
    const [errorMessage, setErrorMessage] = useState(""); // State for error message
    const [successMessage, setSuccessMessage] = useState(""); // State for success message

    // Fetch all countries when the component mounts
    useEffect(() => {
        fetchCountries(); // Call to fetch countries on component mount
    }, []);

    // Function to fetch countries from the backend (GET)
    const fetchCountries = async () => {
        try {
            const response = await fetch('http://localhost:8080/api/admin/countries', {
                method: 'GET' // Method to fetch the countries
            });
            if (!response.ok) {
                throw new Error('Failed to fetch countries');
            }
            const data = await response.json();
            setCountries(data); // Update state with fetched data
        } catch (error) {
            console.error('Error fetching countries:', error);
        }
    };

    // Function to add or update a country (POST/PUT)
    const addOrUpdateCountry = async (event) => {
        event.preventDefault();
        const countryData = { countryName: newCountryName };

        try {
            const response = editingCountryId
                ? await fetch(`http://localhost:8080/api/admin/countries/${editingCountryId}`, {
                    method: 'PUT',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(countryData),
                })
                : await fetch('http://localhost:8080/api/admin/countries', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(countryData),
                });

            if (!response.ok) {
                const errorData = await response.json();
                throw new Error(errorData.message); // Display error message from server
            }

            const newCountry = await response.json();
            if (editingCountryId) {
                setCountries(countries.map(country => (country.id === editingCountryId ? newCountry : country)));
            } else {
                setCountries([...countries, newCountry]);
            }
            resetForm();
            setSuccessMessage(editingCountryId ? 'Country updated successfully!' : 'Country added successfully!'); // Set success message
            setErrorMessage(""); // Clear error message on success
        } catch (error) {
            setErrorMessage(`${error.message}`); // Set error message
            setSuccessMessage(""); // Clear success message on error
            console.error('Error adding/updating country:', error);
        }
    };

    // Function to handle editing a country
    const handleEdit = (country) => {
        setNewCountryName(country.countryName);
        setEditingCountryId(country.id);
        setErrorMessage(""); // Clear any previous error message
        setSuccessMessage(""); // Clear any previous success message
    };

    // Function to delete a country (DELETE)
    const handleDelete = async (countryId) => {
        try {
            const response = await fetch(`http://localhost:8080/api/admin/countries/${countryId}`, {
                method: 'DELETE' // Method to delete the country
            });
            if (!response.ok) {
                throw new Error('Failed to delete country');
            }
            setCountries(countries.filter((country) => country.id !== countryId)); // Update the list after deletion
            setSuccessMessage('Country deleted successfully!'); // Set success message
            setErrorMessage(""); // Clear error message on successful deletion
        } catch (error) {
            setErrorMessage(`Error: ${error.message}`); // Set error message
            console.error('Error deleting country:', error);
        }
    };

    // Function to reset the form
    const resetForm = () => {
        setNewCountryName("");
        setEditingCountryId(null);
        setSuccessMessage(""); // Clear success message
        setErrorMessage(""); // Clear error message on reset
    };

    return (
        <div className="countries-container">
            <div className="countries-header">
                <h1>Manage Countries</h1>
            </div>

            <form onSubmit={addOrUpdateCountry} className="update-form">
                <input
                    type="text"
                    value={newCountryName}
                    onChange={(e) => setNewCountryName(e.target.value)}
                    placeholder="Enter country name"
                    required
                    className="form-input"
                />
                <button type="submit" className="submit-button">
                    {editingCountryId ? 'Update Country' : 'Add Country'}
                </button>
            </form>

            {errorMessage && <div className="error-message" style={{ color: 'red' }}>{errorMessage}</div>} {/* Centered error message */}
            {successMessage && <div className="success-message" style={{ color: 'green' }}>{successMessage}</div>} {/* Success message */}

            <h2>Country List</h2>
            <ul className="user-list">
                {countries.map((country) => (
                    <li key={country.id} className="user-item">
                        <span className="user-info">{country.countryName}</span>
                        <div>
                            <button onClick={() => handleEdit(country)} className="update-button">Edit</button>
                            <button onClick={() => handleDelete(country.id)} className="delete-button">Delete</button>
                        </div>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default Countries;
