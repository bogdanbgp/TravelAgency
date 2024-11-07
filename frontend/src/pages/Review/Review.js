import React, { useState } from 'react';
import './Review.css';

function Review() {
  const [review, setReview] = useState({
    name: '',
    message: ''
  });
  const [feedback, setFeedback] = useState(null);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setReview({ ...review, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (review.name && review.message) {
      // Make API call to submit the review
      try {
        const response = await fetch('http://localhost:8080/api/user/review', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(review)
        });

        if (response.ok) {
          setFeedback('Thank you for your review!');
          setReview({ name: '', message: '' }); // Reset the form
        } else {
          const error = await response.text();
          setFeedback(error);
        }
      } catch (error) {
        setFeedback('Error submitting review.');
      }
    } else {
      setFeedback('Please fill out both fields.');
    }
  };

  return (
    <div className="review-container">
      <h2>Submit a Review</h2>
      {feedback && <p className="feedback">{feedback}</p>}
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          name="name"
          placeholder="Your Name"
          value={review.name}
          onChange={handleChange}
          required
        />
        <textarea
          name="message"
          placeholder="Your Review"
          value={review.message}
          onChange={handleChange}
          required
        />
        <button type="submit">Submit</button>
      </form>
    </div>
  );
}

export default Review;
