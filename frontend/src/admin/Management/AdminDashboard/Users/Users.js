import React, { useState, useEffect, useRef } from 'react';
import './Users.css';

const Users = () => {
    const [users, setUsers] = useState([]);
    const [formData, setFormData] = useState({
        id: '',
        username: '',
        firstName: '',
        lastName: '',
        age: '',
        email: '',
        mobile: '',
        password: '',
    });
    const [newUserData, setNewUserData] = useState({
        username: '',
        firstName: '',
        lastName: '',
        age: '',
        email: '',
        mobile: '',
        password: '',
    });
    const [showUpdateForm, setShowUpdateForm] = useState(false);
    const [userTours, setUserTours] = useState([]);
    const [availableTours, setAvailableTours] = useState([]);
    const [selectedTourId, setSelectedTourId] = useState('');
    const [errorMessage, setErrorMessage] = useState('');
    const [successMessage, setSuccessMessage] = useState('');
    const updateFormRef = useRef(null);

    // Fetch Data Function (Generic for all fetches)
    const fetchData = async (url, method = 'GET', body = null) => {
        try {
            const response = await fetch(url, {
                method,
                headers: { 'Content-Type': 'application/json' },
                body: body ? JSON.stringify(body) : null,
            });
            if (!response.ok) throw new Error('API request failed');

            const responseData = await response.text();
            if (!responseData) {
                return {};
            }
            return JSON.parse(responseData);
        } catch (error) {
            console.error('Error fetching data:', error);
            throw error;
        }
    };

    // Fetch all data (users, tours)
    useEffect(() => {
        const fetchAllData = async () => {
            try {
                const usersData = await fetchData('http://localhost:8080/api/admin/users');
                setUsers(usersData);
                const toursData = await fetchData('http://localhost:8080/api/admin/tours');
                setAvailableTours(toursData);
            } catch (error) {
                setErrorMessage('Failed to fetch users or tours.');
            }
        };
        fetchAllData();
    }, []);

    // Handle form data changes
    const handleChange = (e, dataSetter) => {
        const { name, value } = e.target;
        dataSetter((prevData) => ({
            ...prevData,
            [name]: value,
        }));
    };

    // Handle select user for update
    const handleSelectUser = (user) => {
        setFormData({
            id: user.id,
            username: user.username,
            firstName: user.firstName,
            lastName: user.lastName,
            age: user.age,
            email: user.email,
            mobile: user.mobile,
            password: '',
        });
        setShowUpdateForm(true);
        fetchUserTours(user.id);
        if (updateFormRef.current) updateFormRef.current.scrollIntoView({ behavior: 'smooth' });
    };


    // Fetch user tours
    const fetchUserTours = async (userId) => {
        try {
            const tours = await fetchData(`http://localhost:8080/api/admin/users/${userId}/tours`);
            setUserTours(tours);
        } catch (error) {
            setErrorMessage('Failed to fetch user tours.');
        }
    };

    // Add or remove user tour
    const modifyUserTour = async (userId, tourId, action) => {
        const url = `http://localhost:8080/api/admin/users/${userId}/tours/${tourId}`;
        const method = action === 'add' ? 'POST' : 'DELETE';
        try {
            await fetchData(url, method);
            fetchUserTours(userId); // Refresh user tours after adding/removing
            setSuccessMessage(action === 'add' ? 'Tour added successfully' : 'Tour removed successfully');
        } catch (error) {
            setErrorMessage(action === 'add' ? 'Failed to add tour to user.' : 'Failed to remove tour from user.');
        }
    };

    // Add new user
    const addUser = async (e) => {
        e.preventDefault();
        try {
            const addedUser = await fetchData('http://localhost:8080/api/admin/users', 'POST', newUserData);
            setUsers((prevUsers) => [...prevUsers, addedUser]);
            setNewUserData({
                username: '',
                firstName: '',
                lastName: '',
                age: '',
                email: '',
                mobile: '',
                password: '',
            });
            setSuccessMessage('User added successfully!');
        } catch (error) {
            setErrorMessage('Failed to add user.');
        }
    };

    // Update user
    const updateUser = async (e) => {
        e.preventDefault();
        try {
            const updatedUser = await fetchData(`http://localhost:8080/api/admin/users/${formData.id}`, 'PUT', formData);
            setUsers((prevUsers) =>
                prevUsers.map((user) => (user.id === updatedUser.id ? updatedUser : user))
            );
            setShowUpdateForm(false);
            setFormData({
                id: '',
                username: '',
                firstName: '',
                lastName: '',
                age: '',
                email: '',
                mobile: '',
                password: '',
            });
            setSuccessMessage('User updated successfully!');
        } catch (error) {
            setErrorMessage('Failed to update user.');
        }
    };

    // Delete user
    const deleteUser = async (id) => {
        try {
            await fetchData(`http://localhost:8080/api/admin/users/${id}`, 'DELETE');
            setUsers((prevUsers) => prevUsers.filter((user) => user.id !== id));
            setSuccessMessage('User deleted successfully!');
            setTimeout(() => {
                setSuccessMessage('');
            }, 2000);
        } catch (error) {
            setErrorMessage('Failed to delete user.');
        }
    };

    // Cancel update form
    const cancelUpdate = () => {
        setShowUpdateForm(false);
        setFormData({
            id: '',
            username: '',
            firstName: '',
            lastName: '',
            age: '',
            email: '',
            mobile: '',
            password: '',
        });
        setUserTours([]);
    };

    // Render component
    return (
        <div className="users-management-container">
            <div className="users-header">
                <h1>Manage Users</h1>
            </div>

            {/* Error and Success Messages */}
            {errorMessage && <div className="error-message">{errorMessage}</div>}
            {successMessage && <div className="success-message">{successMessage}</div>}

            <h2 className="section-title">Users List</h2>
            <ul className="users-list">
                {users.map((user) => (
                    <li key={user.id} className="user-list-item">
                        <span className="user-details">{user.username} ({user.email})</span>
                        <div className="user-actions">
                            <button onClick={() => handleSelectUser(user)} className="user-list-item__update-button">Update</button>
                            <button onClick={() => deleteUser(user.id)} className="user-list-item__delete-button">Delete</button>
                        </div>
                    </li>
                ))}
            </ul>

            <h3 className="section-title">Add New User</h3>
            <form onSubmit={addUser} className="user-form add-user-form">
                {['username', 'firstName', 'lastName', 'age', 'email', 'mobile', 'password'].map((field) => (
                    <input
                        key={field}
                        type={field === 'age' ? 'number' : field === 'email' ? 'email' : field === 'password' ? 'password' : 'text'}
                        name={field}
                        placeholder={field.charAt(0).toUpperCase() + field.slice(1)}
                        value={newUserData[field]}
                        onChange={(e) => handleChange(e, setNewUserData)}
                        required
                        className="form-input"
                    />
                ))}
                <button type="submit" className="user-form__submit-button add-user-button">Add User</button>
            </form>

            {showUpdateForm && (
                <div ref={updateFormRef} className="update-user-container">
                    <h3 className="section-title">Update User</h3>
                    <form onSubmit={updateUser} className="user-form update-user-form">
                        {['username', 'firstName', 'lastName', 'age', 'email', 'mobile', 'password'].map((field) => (
                            <input
                                key={field}
                                type={field === 'age' ? 'number' : field === 'email' ? 'email' : field === 'password' ? 'password' : 'text'}
                                name={field}
                                placeholder={field.charAt(0).toUpperCase() + field.slice(1)}
                                value={formData[field]}
                                onChange={(e) => handleChange(e, setFormData)}
                                required
                                className="form-input"
                            />
                        ))}
                        <button type="submit" className="user-form__submit-button update-user-button">Update User</button>
                        <button type="button" onClick={cancelUpdate} className="cancel-update-button">Cancel</button>
                    </form>

                    <h4 className="section-title">User's Tours</h4>
                    <ul className="user-tours-list">
                        {userTours.map((tour) => (
                            <li key={tour.id} className="tour-list-item">
                                <span>{tour.tourName || 'Unnamed Tour'}</span>
                                <div className="tour-actions">
                                    <button
                                        onClick={() => modifyUserTour(formData.id, tour.id, 'remove')}
                                        className="tour-actions__remove-button"
                                    >
                                        Remove Tour
                                    </button>
                                </div>
                            </li>
                        ))}
                    </ul>

                    <h5 className="section-title">Add Tour to User:</h5>
                    <div className="tour-selection-container">
                        <select
                            value={selectedTourId}
                            onChange={(e) => setSelectedTourId(e.target.value)}
                            className="tour-select-dropdown"
                        >
                            <option value="">Select a Tour</option>
                            {availableTours.map((tour) => (
                                <option key={tour.id} value={tour.id}>
                                    {tour.tourName || 'Unnamed Tour'}
                                </option>
                            ))}
                        </select>
                        <button
                            onClick={() => modifyUserTour(formData.id, selectedTourId, 'add')}
                            className="tour-actions__add-button"
                            disabled={!selectedTourId}
                        >
                            Add Tour
                        </button>
                    </div>
                </div>
            )}
        </div>
    );
};

export default Users;
