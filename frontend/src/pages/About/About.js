import React from 'react';
import './About.css';

function About() {
  return (
    <div className="about-container">
      <div className="about-box"> {/* Added about-box div */}
        <h1>About Us</h1>
        <p><strong>Welcome to Dream Travel Agency!</strong></p>
        <p>
          At Dream Travel Agency, we are passionate about making your travel dreams come true. Since our founding in 2024,
          we’ve been dedicated to helping adventurers discover breathtaking destinations around the world.
        </p>
        <p>
          Whether you're looking for a luxury getaway, a thrilling adventure, or a peaceful retreat, our experienced team
          is here to craft the perfect travel experience just for you. With personalized service, insider knowledge, and a
          commitment to excellence, we take the stress out of planning and deliver unforgettable vacations.
        </p>
        <p>
          We offer tailor-made trips to stunning locations across the globe, from the romantic streets of Paris to the
          exotic landscapes of Bali. Our curated tours are designed to suit all tastes and preferences, whether you're
          traveling solo, as a couple, or with family and friends.
        </p>
        <p>
          Let Dream Travel Agency be your trusted guide to the world. Contact us today, and let’s embark on an unforgettable journey together!
        </p>
        <address>
          Dream Travel Agency<br />
          456 Wanderlust Avenue<br />
          Adventure City, TX 75001<br />
          Email: info@dreamtravelagency.com<br />
          Phone: (555) 987-6543
        </address>
      </div>
    </div>
  );
}

export default About;
