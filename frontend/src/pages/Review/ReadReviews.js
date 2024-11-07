import React, { useState, useEffect } from 'react';
import './ReadReviews.css'; // Import the CSS file for styling

function ReadReviews() {
  const [reviews, setReviews] = useState([]);
  const [error, setError] = useState(null);
  const [successMessage, setSuccessMessage] = useState(null); // New state for success message

  // Fetch reviews from the backend when the component is mounted
  useEffect(() => {
    const fetchReviews = async () => {
      try {
        const response = await fetch('http://localhost:8080/api/user/reviews', {
          method: 'GET', // Explicitly specifying the GET method
        });
        if (response.ok) {
          const data = await response.json();
          setReviews(data);
        } else {
          setError('Failed to load reviews.');
        }
      } catch (error) {
        setError('Error fetching reviews.');
      }
    };

    fetchReviews();
  }, []);

  // Method to delete a review
  const deleteReview = async (id) => {
    try {
      const response = await fetch(`http://localhost:8080/api/user/reviews/${id}`, {
        method: 'DELETE', // DELETE method to remove the review
      });

      if (response.ok) {
        setReviews(prevReviews => prevReviews.filter(review => review.id !== id)); // Remove the review from state
        setSuccessMessage('Review deleted successfully!'); // Set the success message
        setError(null); // Reset any previous errors
      } else {
        const data = await response.json();
        setError(data.error || 'Failed to delete review.');
        setSuccessMessage(null); // Reset success message if there was an error
      }
    } catch (error) {
      setError('Error deleting review.');
      setSuccessMessage(null); // Reset success message if there was an error
    }
  };

  return (
    <div className="review-container">
      <h2>Submitted Reviews</h2>
      {error && <p className="error">{error}</p>} {/* Error message styling */}
      {successMessage && <p className="deleteSuccess">{successMessage}</p>} {/* Success message styling */}
      <ul className="review-list">
        {reviews.length > 0 ? (
          reviews.map((review) => (
            <li key={review.id} className="review-item">
              <strong>{review.name}</strong>: {review.message}
              <button onClick={() => deleteReview(review.id)} className="delete-button">
                Delete Review
              </button>
            </li>
          ))
        ) : (
          <p>No reviews submitted yet.</p>
        )}
      </ul>
    </div>
  );
}

export default ReadReviews;
