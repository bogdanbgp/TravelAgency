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
    const [errorMessage, setErrorMessage] = useState("");
    const [successMessage, setSuccessMessage] = useState("");
    const updateFormRef = useRef(null);

    // Fetch all users from the API
    useEffect(() => {
        const fetchUsers = async () => {
            try {
                const response = await fetch('http://localhost:8080/api/admin/users', {
                    method: 'GET',
                    headers: { 'Content-Type': 'application/json' },
                });
                if (!response.ok) throw new Error('Failed to fetch users');
                const data = await response.json();
                setUsers(data);
            } catch (error) {
                console.error('Error fetching users:', error);
                setErrorMessage('Failed to fetch users.');
            }
        };
        fetchUsers();
    }, []);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData((prevData) => ({
            ...prevData,
            [name]: value,
        }));
    };

    const handleNewUserChange = (e) => {
        const { name, value } = e.target;
        setNewUserData((prevData) => ({
            ...prevData,
            [name]: value,
        }));
    };

    const deleteUser = async (id) => {
        try {
            const response = await fetch(`http://localhost:8080/api/admin/users/${id}`, {
                method: 'DELETE',
                headers: { 'Content-Type': 'application/json' },
            });
            if (!response.ok) throw new Error('Failed to delete user');
            setUsers((prevUsers) => prevUsers.filter((user) => user.id !== id));
            setSuccessMessage('User deleted successfully!');
            setErrorMessage("");
        } catch (error) {
            console.error('Error deleting user:', error);
            setErrorMessage('Failed to delete user.');
            setSuccessMessage("");
        }
    };

    const updateUser = async (e) => {
        e.preventDefault();
        try {
            const response = await fetch(`http://localhost:8080/api/admin/users/${formData.id}`, {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({
                    username: formData.username,
                    firstName: formData.firstName,
                    lastName: formData.lastName,
                    age: parseInt(formData.age, 10),
                    email: formData.email,
                    mobile: formData.mobile,
                    password: formData.password,
                }),
            });
            if (!response.ok) throw new Error('Failed to update user');
            const updatedUser = await response.json();
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
            setErrorMessage("");
        } catch (error) {
            console.error('Error updating user:', error);
            setErrorMessage('Failed to update user.');
            setSuccessMessage("");
        }
    };

    const addUser = async (e) => {
        e.preventDefault();
        try {
            const response = await fetch('http://localhost:8080/api/admin/users', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({
                    username: newUserData.username,
                    firstName: newUserData.firstName,
                    lastName: newUserData.lastName,
                    age: parseInt(newUserData.age, 10),
                    email: newUserData.email,
                    mobile: newUserData.mobile,
                    password: newUserData.password,
                }),
            });
            if (!response.ok) {
                if (response.status === 409) {
                    throw new Error('A user with this email or username already exists');
                }
                throw new Error('Failed to add user');
            }
            const addedUser = await response.json();
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
            setErrorMessage("");
        } catch (error) {
            console.error('Error adding user:', error);
            setErrorMessage('Failed to add user.');
            setSuccessMessage("");
        }
    };

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
        if (updateFormRef.current) {
            updateFormRef.current.scrollIntoView({ behavior: 'smooth' });
        }
    };

    const cancelUpdate = () => {
        setShowUpdateForm(false);
    };

    return (
        <div className="users-container">
            <div className="users-header">
                <h1>Manage Users</h1>
            </div>

            {errorMessage && <div className="error-message" style={{ color: 'red' }}>{errorMessage}</div>}
            {successMessage && <div className="success-message" style={{ color: 'green' }}>{successMessage}</div>}

            <h2>User List</h2>
            <ul className="user-list">
                {users.map((user) => (
                    <li key={user.id} className="user-item">
                        <span className="user-info">{user.username} ({user.email})</span>
                        <div className="user-actions">
                            <button
                                onClick={() => handleSelectUser(user)}
                                className="update-button"
                            >
                                Update
                            </button>
                            <button onClick={() => deleteUser(user.id)} className="delete-button">
                                Delete
                            </button>
                        </div>
                    </li>
                ))}
            </ul>

            <h3>Add New User</h3>
            <form onSubmit={addUser} className="add-user-form">
                <input
                    type="text"
                    name="username"
                    placeholder="Username"
                    value={newUserData.username}
                    onChange={handleNewUserChange}
                    required
                    className="form-input"
                />
                <input
                    type="text"
                    name="firstName"
                    placeholder="First Name"
                    value={newUserData.firstName}
                    onChange={handleNewUserChange}
                    required
                    className="form-input"
                />
                <input
                    type="text"
                    name="lastName"
                    placeholder="Last Name"
                    value={newUserData.lastName}
                    onChange={handleNewUserChange}
                    required
                    className="form-input"
                />
                <input
                    type="number"
                    name="age"
                    placeholder="Age"
                    value={newUserData.age}
                    onChange={handleNewUserChange}
                    required
                    className="form-input"
                />
                <input
                    type="email"
                    name="email"
                    placeholder="Email"
                    value={newUserData.email}
                    onChange={handleNewUserChange}
                    required
                    className="form-input"
                />
                <input
                    type="text"
                    name="mobile"
                    placeholder="Mobile"
                    value={newUserData.mobile}
                    onChange={handleNewUserChange}
                    required
                    className="form-input"
                />
                <input
                    type="password"
                    name="password"
                    placeholder="Password"
                    value={newUserData.password}
                    onChange={handleNewUserChange}
                    required
                    className="form-input"
                />
                <button type="submit" className="submit-button">Add User</button>
            </form>

            {showUpdateForm && (
                <div ref={updateFormRef}>
                    <h3>Update User</h3>
                    <form onSubmit={updateUser} className="update-user-form">
                        <input
                            type="hidden"
                            name="id"
                            value={formData.id}
                        />
                        <input
                            type="text"
                            name="username"
                            placeholder="Username"
                            value={formData.username}
                            onChange={handleChange}
                            required
                            className="form-input"
                        />
                        <input
                            type="text"
                            name="firstName"
                            placeholder="First Name"
                            value={formData.firstName}
                            onChange={handleChange}
                            required
                            className="form-input"
                        />
                        <input
                            type="text"
                            name="lastName"
                            placeholder="Last Name"
                            value={formData.lastName}
                            onChange={handleChange}
                            required
                            className="form-input"
                        />
                        <input
                            type="number"
                            name="age"
                            placeholder="Age"
                            value={formData.age}
                            onChange={handleChange}
                            required
                            className="form-input"
                        />
                        <input
                            type="email"
                            name="email"
                            placeholder="Email"
                            value={formData.email}
                            onChange={handleChange}
                            required
                            className="form-input"
                        />
                        <input
                            type="text"
                            name="mobile"
                            placeholder="Mobile"
                            value={formData.mobile}
                            onChange={handleChange}
                            required
                            className="form-input"
                        />
                        <input
                            type="password"
                            name="password"
                            placeholder="Password"
                            value={formData.password}
                            onChange={handleChange}
                            required
                            className="form-input"
                        />
                        <button type="submit" className="submit-button">Update User</button>
                        <button type="button" onClick={cancelUpdate} className="cancel-button">Cancel</button>
                    </form>
                </div>
            )}
        </div>
    );
};

export default Users;
