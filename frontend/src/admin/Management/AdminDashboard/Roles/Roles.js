import React, { useState, useEffect } from 'react';
import './Roles.css'; // Optional: for styling

const Roles = () => {
    const [roles, setRoles] = useState([]);
    const [newRoleName, setNewRoleName] = useState("");
    const [editingRoleId, setEditingRoleId] = useState(null);
    const [errorMessage, setErrorMessage] = useState("");
    const [successMessage, setSuccessMessage] = useState("");

    // Fetch all roles on component mount
    useEffect(() => {
        fetchRoles();
    }, []);

    // Fetch roles from backend (GET)
    const fetchRoles = async () => {
        try {
            const response = await fetch('http://localhost:8080/api/admin/roles', {
                method: 'GET'
            });
            if (!response.ok) throw new Error('Failed to fetch roles');
            const data = await response.json();
            setRoles(data);
            setErrorMessage("");
        } catch (error) {
            setErrorMessage(`Error fetching roles: ${error.message}`);
            setRoles([]);
        }
    };

    // Add or update role (POST/PUT)
    const addOrUpdateRole = async (event) => {
        event.preventDefault();
        const roleData = { roleName: newRoleName };
        const url = editingRoleId
            ? `http://localhost:8080/api/admin/roles/${editingRoleId}`
            : 'http://localhost:8080/api/admin/roles';
        const method = editingRoleId ? 'PUT' : 'POST';

        try {
            const response = await fetch(url, {
                method,
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(roleData),
            });
            if (!response.ok) {
                const errorData = await response.json();
                if (errorData.message) {
                    throw new Error(errorData.message); // Show specific error from backend
                }
                throw new Error('Failed to save role');
            }

            const newRole = await response.json();
            if (editingRoleId) {
                setRoles(roles.map(role => (role.id === editingRoleId ? newRole : role)));
            } else {
                setRoles([...roles, newRole]);
            }
            resetForm();
            setSuccessMessage(editingRoleId ? 'Role updated successfully!' : 'Role added successfully!');
            setErrorMessage("");
        } catch (error) {
            if (error.message.includes("already exists")) {
                setErrorMessage("Role with this name already exists. Please choose a different name.");
            } else {
                setErrorMessage(`Error: ${error.message}`);
            }
            setSuccessMessage("");
        }
    };

    // Edit role
    const handleEdit = (role) => {
        setNewRoleName(role.roleName);
        setEditingRoleId(role.id);
        setErrorMessage("");
        setSuccessMessage("");
    };

    // Delete role (DELETE)
    const handleDelete = async (roleId) => {
        try {
            const response = await fetch(`http://localhost:8080/api/admin/roles/${roleId}`, {
                method: 'DELETE'
            });
            if (!response.ok) {
                const errorData = await response.json();
                throw new Error(errorData.message || 'Failed to delete role');
            }
            setRoles(roles.filter((role) => role.id !== roleId));
            setSuccessMessage('Role deleted successfully!');
            setErrorMessage("");
        } catch (error) {
            if (error.message.includes("not found")) {
                setErrorMessage("Role not found. It may have already been deleted.");
            } else {
                setErrorMessage(`Error deleting role: ${error.message}`);
            }
            setSuccessMessage("");
        }
    };

    // Reset form fields
    const resetForm = () => {
        setNewRoleName("");
        setEditingRoleId(null);
        setErrorMessage("");
        setSuccessMessage("");
    };

    return (
        <div className="roles-container">
            <div className="roles-header">
                <h1>Manage Roles</h1>
            </div>

            <h2>Role List</h2>
            <ul className="role-list">
                {roles.map((role) => (
                    <li key={role.id} className="role-item">
                        <span className="role-info">{role.roleName}</span>
                        <button onClick={() => handleEdit(role)} className="update-button">Edit</button>
                        <button onClick={() => handleDelete(role.id)} className="delete-button">Delete</button>
                    </li>
                ))}
            </ul>

            <form onSubmit={addOrUpdateRole} className="update-form">
                <input
                    type="text"
                    value={newRoleName}
                    onChange={(e) => setNewRoleName(e.target.value)}
                    placeholder="Enter role name"
                    required
                    className="form-input"
                />
                <button type="submit" className="submit-button">
                    {editingRoleId ? 'Update Role' : 'Add Role'}
                </button>
            </form>

            {errorMessage && <div className="error-message" style={{ color: 'red' }}>{errorMessage}</div>}
            {successMessage && <div className="success-message" style={{ color: 'green' }}>{successMessage}</div>}
        </div>
    );
};

export default Roles;
