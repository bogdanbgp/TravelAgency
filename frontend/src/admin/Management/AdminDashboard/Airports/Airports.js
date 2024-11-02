import React, { useState, useEffect } from 'react';
import './Airports.css';

const Airports = () => {
    const [airports, setAirports] = useState([]);
    const [newAirportName, setNewAirportName] = useState("");
    const [cityId, setCityId] = useState("");
    const [editingAirportId, setEditingAirportId] = useState(null);
    const [errorMessage, setErrorMessage] = useState("");
    const [successMessage, setSuccessMessage] = useState("");

    useEffect(() => {
        fetchAirports();
    }, []);

    const fetchAirports = async () => {
        try {
            const response = await fetch('http://localhost:8080/api/admin/airports', { method: 'GET' });
            if (!response.ok) {
                throw new Error('Failed to fetch airports');
            }
            const data = await response.json();
            setAirports(data);
        } catch (error) {
            console.error('Error fetching airports:', error);
        }
    };

    const addOrUpdateAirport = async (event) => {
        event.preventDefault();
        const airportData = { airportName: newAirportName, cityId };

        try {
            const response = editingAirportId
                ? await fetch(`http://localhost:8080/api/admin/airports/${editingAirportId}`, {
                    method: 'PUT',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(airportData),
                })
                : await fetch('http://localhost:8080/api/admin/airports', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(airportData),
                });

            if (!response.ok) {
                if (response.status === 404) {
                    setErrorMessage("City not found. Please enter a valid city ID.");
                } else {
                    throw new Error(editingAirportId ? 'Failed to update airport' : 'Failed to create airport');
                }
                setSuccessMessage("");
                return;
            }

            const newAirport = await response.json();
            setAirports(editingAirportId
                ? airports.map(airport => (airport.id === editingAirportId ? newAirport : airport))
                : [...airports, newAirport]);
            resetForm();
            setErrorMessage("");
            setSuccessMessage(editingAirportId ? 'Airport updated successfully!' : 'Airport added successfully!');
        } catch (error) {
            console.error('Error adding/updating airport:', error);
        }
    };

    const handleEdit = (airport) => {
        setNewAirportName(airport.airportName);
        setCityId(airport.cityId);
        setEditingAirportId(airport.id);
        setErrorMessage("");
        setSuccessMessage("");
    };

    const handleDelete = async (airportId) => {
        try {
            const response = await fetch(`http://localhost:8080/api/admin/airports/${airportId}`, { method: 'DELETE' });
            if (!response.ok) {
                throw new Error('Failed to delete airport');
            }
            setAirports(airports.filter((airport) => airport.id !== airportId));
        } catch (error) {
            console.error('Error deleting airport:', error);
        }
    };

    const resetForm = () => {
        setNewAirportName("");
        setCityId("");
        setEditingAirportId(null);
        setSuccessMessage("");
        setErrorMessage("");
    };

    return (
        <div className="airports-container">
            <div className="airports-header">
                <h1>Manage Airports</h1>
            </div>
            <form className="add-airport-form" onSubmit={addOrUpdateAirport}>
                <input
                    type="text"
                    className="form-input"
                    value={newAirportName}
                    onChange={(e) => setNewAirportName(e.target.value)}
                    placeholder="Enter airport name"
                    required
                />
                <input
                    type="text"
                    className="form-input"
                    value={cityId}
                    onChange={(e) => setCityId(e.target.value)}
                    placeholder="Enter city ID"
                    required
                />
                <button type="submit" className="submit-button">
                    {editingAirportId ? 'Update Airport' : 'Add Airport'}
                </button>
            </form>
            {errorMessage && <div className="error-message">{errorMessage}</div>}
            {successMessage && <div className="success-message" style={{ color: 'green' }}>{successMessage}</div>}
            <h2>Airport List</h2>
            <ul className="airport-list">
                {airports.map((airport) => (
                    <li key={airport.id} className="airport-item">
                        <span className="airport-info">{airport.airportName} (City: {airport.cityName || 'N/A'})</span>
                        <div>
                            <button className="update-button" onClick={() => handleEdit(airport)}>Edit</button>
                            <button className="delete-button" onClick={() => handleDelete(airport.id)}>Delete</button>
                        </div>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default Airports;
