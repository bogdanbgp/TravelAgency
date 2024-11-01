import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import './Home.css';

function Home() {
    const [tours, setTours] = useState([]);
    const [error, setError] = useState('');

    useEffect(() => {
        fetchTours();
    }, []);

    const fetchTours = async () => {
        try {
            const response = await fetch('http://localhost:8080/api/admin/tours', {
                credentials: 'include' // Ensures cookies are included for session validation
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

    return (
        <div className="home-container">
            <header className="home-header">
                <h1>Welcome to Dream Travel Agency</h1>
                <p>Explore breathtaking destinations and unforgettable experiences tailored just for you.</p>
                <Link to="/tours">
                    <button className="explore-tours-button">Explore Our Tours</button>
                </Link>
            </header>

            {/* Section for featured tours */}
            <section className="home-featured-tours">
                <h2>Featured Tours</h2>
                {error && <div className="error-message">{error}</div>}
                <div className="tour-list">
                    {tours.length > 0 ? (
                        tours.map((tour) => (
                            <div key={tour.id} className="tour-item">
                                <h3>{tour.tourName}</h3>
                                <p>{tour.description}</p>
                                <p>Price: ${tour.price}</p>
                                <Link to={`/tours?selectedTour=${tour.id}`}>
                                    <button className="view-tour-button">Book Tour</button> {/* Updated button text */}
                                </Link>
                            </div>
                        ))
                    ) : (
                        <p>No tours available at the moment.</p>
                    )}
                </div>
            </section>

            {/* Section for testimonials */}
            <section className="home-testimonials">
                <h2>What Our Clients Say</h2>
                <div className="testimonial-item">
                    <p>"The trip to Japan was life-changing! Every detail was taken care of, and the experiences were unforgettable!"</p>
                    <span>- Sarah J.</span>
                </div>
                <div className="testimonial-item">
                    <p>"We had an amazing family vacation in Paris. Thank you for making it so special for us!"</p>
                    <span>- The Anderson Family</span>
                </div>
                <div className="testimonial-item">
                    <p>"Our safari in Kenya was a dream come true. Highly recommend this agency!"</p>
                    <span>- Mark T.</span>
                </div>
            </section>

            {/* Section for booking a consultation */}
            <section className="home-consultation">
                <h2>Start Your Journey Today!</h2>
                <p>If you're ready to explore amazing destinations, reach out to us for more information!</p>
                <Link to="/contact">
                    <button className="contact-button">Contact Us</button>
                </Link>
            </section>
        </div>
    );
}

export default Home;
