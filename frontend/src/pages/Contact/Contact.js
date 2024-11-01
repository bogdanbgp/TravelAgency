import React from 'react';
import './Contact.css';

function Contact() {
  return (
    <div className="contact-container">
      <div className="contact-box"> {/* Added contact-box div */}
        <h1>Contact Us</h1>
        <address>
          Dream Travel Agency<br />
          456 Wanderlust Avenue<br />
          Adventure City, TX 75001<br />
          Email: info@dreamtravelagency.com<br />
          Phone: (555) 987-6543
        </address>
            <p>
            Ready to embark on your next adventure? We're here to guide you every step of the way!
            Contact us today, and let’s make your dream vacation a reality!
            </p>
        </div>
    </div>
  );
}

export default Contact;
