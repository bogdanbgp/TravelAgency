import React, { useEffect, useState, useRef } from 'react';
import './Tours.css'; // Ensure this path matches where you save your CSS file

const Tours = () => {
  const [countries, setCountries] = useState([]);
  const [cities, setCities] = useState([]);
  const [hotels, setHotels] = useState([]);
  const [airports, setAirports] = useState([]);
  const [tours, setTours] = useState([]);

  const [selectedTour, setSelectedTour] = useState(null);
  const [formData, setFormData] = useState({
    tourName: '',
    departureDate: '',
    returnDate: '',
    numberOfDays: '',
    description: '',
    price: '',
    fromCountryId: '',
    toCountryId: '',
    fromCityId: '',
    toCityId: '',
    fromAirportId: '',
    toAirportId: '',
    hotelId: ''
  });

  const [updateFormData, setUpdateFormData] = useState({
    id: '',
    tourName: '',
    departureDate: '',
    returnDate: '',
    numberOfDays: '',
    description: '',
    price: '',
    fromCountryId: '',
    toCountryId: '',
    fromCityId: '',
    toCityId: '',
    fromAirportId: '',
    toAirportId: '',
    hotelId: ''
  });

  // Reference to the update form for scrolling
  const updateFormRef = useRef(null);

  // Fetch all required data
  useEffect(() => {
    const fetchData = async () => {
      try {
        const countriesResponse = await fetch('http://localhost:8080/api/admin/countries');
        const countriesData = await countriesResponse.json();
        setCountries(countriesData);

        const citiesResponse = await fetch('http://localhost:8080/api/admin/cities');
        const citiesData = await citiesResponse.json();
        setCities(citiesData);

        const hotelsResponse = await fetch('http://localhost:8080/api/admin/hotels');
        const hotelsData = await hotelsResponse.json();
        setHotels(hotelsData);

        const airportsResponse = await fetch('http://localhost:8080/api/admin/airports');
        const airportsData = await airportsResponse.json();
        setAirports(airportsData);

        const toursResponse = await fetch('http://localhost:8080/api/admin/tours');
        const toursData = await toursResponse.json();
        setTours(toursData);
      } catch (error) {
        console.error('Error fetching data:', error);
      }
    };

    fetchData();
  }, []);

  // Handle input changes for the new tour form
  const handleNewTourChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  // Handle input changes for the update tour form
  const handleUpdateTourChange = (e) => {
    const { name, value } = e.target;
    setUpdateFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  // Handle tour selection for updating
  const handleSelectTour = (tour) => {
    setSelectedTour(tour);
    setUpdateFormData(tour); // Auto-fill the update form with the selected tour's data
    // Check if updateFormRef.current is not null before scrolling
    if (updateFormRef.current) {
      updateFormRef.current.scrollIntoView({ behavior: 'smooth', block: 'start' });
    }
  };

  // Create a new tour
  const handleCreateTour = async (e) => {
    e.preventDefault();
    const response = await fetch('http://localhost:8080/api/admin/tours', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(formData),
    });
    if (response.ok) {
      const newTour = await response.json();
      setTours([...tours, newTour]);
      setFormData({
        tourName: '',
        departureDate: '',
        returnDate: '',
        numberOfDays: '',
        description: '',
        price: '',
        fromCountryId: '',
        toCountryId: '',
        fromCityId: '',
        toCityId: '',
        fromAirportId: '',
        toAirportId: '',
        hotelId: ''
      });
    }
  };

  // Update an existing tour
  const handleUpdateTour = async (e) => {
    e.preventDefault();
    const response = await fetch(`http://localhost:8080/api/admin/tours/${updateFormData.id}`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(updateFormData),
    });
    if (response.ok) {
      const updatedTour = await response.json();
      setTours(tours.map(tour => (tour.id === updatedTour.id ? updatedTour : tour)));
      setSelectedTour(null);
      setUpdateFormData({
        id: '',
        tourName: '',
        departureDate: '',
        returnDate: '',
        numberOfDays: '',
        description: '',
        price: '',
        fromCountryId: '',
        toCountryId: '',
        fromCityId: '',
        toCityId: '',
        fromAirportId: '',
        toAirportId: '',
        hotelId: ''
      });
    }
  };

  // Handle tour deletion
  const deleteTour = async (id) => {
    try {
      const response = await fetch(`http://localhost:8080/api/admin/tours/${id}`, {
        method: 'DELETE',
        headers: { 'Content-Type': 'application/json' },
      });
      if (!response.ok) throw new Error('Failed to delete tour');
      setTours((prevTours) => prevTours.filter((tour) => tour.id !== id)); // Remove the deleted tour from the state
      // Hide the update form if the deleted tour was selected
      if (selectedTour && selectedTour.id === id) {
        setSelectedTour(null);
        setUpdateFormData({
          id: '',
          tourName: '',
          departureDate: '',
          returnDate: '',
          numberOfDays: '',
          description: '',
          price: '',
          fromCountryId: '',
          toCountryId: '',
          fromCityId: '',
          toCityId: '',
          fromAirportId: '',
          toAirportId: '',
          hotelId: ''
        });
      }
    } catch (error) {
      console.error('Error deleting tour:', error);
    }
  };

  return (
    <div className="tours-container">
      <div className="tours-header">
        <h1>Manage Tours</h1>
      </div>
      {/* Tours List */}
      <h2>Tour List</h2>
      <ul className="tour-list">
        {tours.map(tour => (
          <li key={tour.id} onClick={() => handleSelectTour(tour)} className="tour-item">
            <div className="tour-info">
              {tour.tourName} - {tour.departureDate} to {tour.returnDate}
            </div>
            <div className="tour-actions">
              <button className="update-button" onClick={() => handleSelectTour(tour)}>Update</button>
              <button className="delete-button" onClick={() => deleteTour(tour.id)}>Delete</button>
            </div>
          </li>
        ))}
      </ul>

      {/* Create Tour Form */}
      <h2>Create Tour</h2>
      <form onSubmit={handleCreateTour} className="add-tour-form">
        <input type="text" name="tourName" placeholder="Tour Name" value={formData.tourName} onChange={handleNewTourChange} required className="form-input"/>
        <input type="date" name="departureDate" value={formData.departureDate} onChange={handleNewTourChange} required className="form-input"/>
        <input type="date" name="returnDate" value={formData.returnDate} onChange={handleNewTourChange} required className="form-input"/>
        <input type="number" name="numberOfDays" placeholder="Number of Days" value={formData.numberOfDays} onChange={handleNewTourChange} required className="form-input"/>
        <textarea name="description" placeholder="Description" value={formData.description} onChange={handleNewTourChange} required className="form-input"></textarea>
        <input type="number" name="price" placeholder="Price" value={formData.price} onChange={handleNewTourChange} required className="form-input"/>

        <select name="fromCountryId" value={formData.fromCountryId} onChange={handleNewTourChange} required className="form-input">
          <option value="">From Country</option>
          {countries.map(country => (
            <option key={country.id} value={country.id}>{country.countryName}</option>
          ))}
        </select>

        <select name="toCountryId" value={formData.toCountryId} onChange={handleNewTourChange} required className="form-input">
          <option value="">To Country</option>
          {countries.map(country => (
            <option key={country.id} value={country.id}>{country.countryName}</option>
          ))}
        </select>

        <select name="fromCityId" value={formData.fromCityId} onChange={handleNewTourChange} required className="form-input">
          <option value="">From City</option>
          {cities.map(city => (
            <option key={city.id} value={city.id}>{city.cityName}</option>
          ))}
        </select>

        <select name="toCityId" value={formData.toCityId} onChange={handleNewTourChange} required className="form-input">
          <option value="">To City</option>
          {cities.map(city => (
            <option key={city.id} value={city.id}>{city.cityName}</option>
          ))}
        </select>

        <select name="fromAirportId" value={formData.fromAirportId} onChange={handleNewTourChange} required className="form-input">
          <option value="">From Airport</option>
          {airports.map(airport => (
            <option key={airport.id} value={airport.id}>{airport.airportName}</option>
          ))}
        </select>

        <select name="toAirportId" value={formData.toAirportId} onChange={handleNewTourChange} required className="form-input">
          <option value="">To Airport</option>
          {airports.map(airport => (
            <option key={airport.id} value={airport.id}>{airport.airportName}</option>
          ))}
        </select>

        <select name="hotelId" value={formData.hotelId} onChange={handleNewTourChange} required className="form-input">
          <option value="">Hotel</option>
          {hotels.map(hotel => (
            <option key={hotel.id} value={hotel.id}>{hotel.hotelName}</option>
          ))}
        </select>

        <button type="submit" className="submit-button">Create Tour</button>
      </form>

      {/* Update Tour Form */}
      {selectedTour && (
        <div ref={updateFormRef}>
          <h2>Update Tour</h2>
          <form onSubmit={handleUpdateTour} className="update-tour-form">
            <input type="hidden" name="id" value={updateFormData.id} />
            <input type="text" name="tourName" placeholder="Tour Name" value={updateFormData.tourName} onChange={handleUpdateTourChange} required className="form-input"/>
            <input type="date" name="departureDate" value={updateFormData.departureDate} onChange={handleUpdateTourChange} required className="form-input"/>
            <input type="date" name="returnDate" value={updateFormData.returnDate} onChange={handleUpdateTourChange} required className="form-input"/>
            <input type="number" name="numberOfDays" placeholder="Number of Days" value={updateFormData.numberOfDays} onChange={handleUpdateTourChange} required className="form-input"/>
            <textarea name="description" placeholder="Description" value={updateFormData.description} onChange={handleUpdateTourChange} required className="form-input"></textarea>
            <input type="number" name="price" placeholder="Price" value={updateFormData.price} onChange={handleUpdateTourChange} required className="form-input"/>

            <select name="fromCountryId" value={updateFormData.fromCountryId} onChange={handleUpdateTourChange} required className="form-input">
              <option value="">From Country</option>
              {countries.map(country => (
                <option key={country.id} value={country.id}>{country.countryName}</option>
              ))}
            </select>

            <select name="toCountryId" value={updateFormData.toCountryId} onChange={handleUpdateTourChange} required className="form-input">
              <option value="">To Country</option>
              {countries.map(country => (
                <option key={country.id} value={country.id}>{country.countryName}</option>
              ))}
            </select>

            <select name="fromCityId" value={updateFormData.fromCityId} onChange={handleUpdateTourChange} required className="form-input">
              <option value="">From City</option>
              {cities.map(city => (
                <option key={city.id} value={city.id}>{city.cityName}</option>
              ))}
            </select>

            <select name="toCityId" value={updateFormData.toCityId} onChange={handleUpdateTourChange} required className="form-input">
              <option value="">To City</option>
              {cities.map(city => (
                <option key={city.id} value={city.id}>{city.cityName}</option>
              ))}
            </select>

            <select name="fromAirportId" value={updateFormData.fromAirportId} onChange={handleUpdateTourChange} required className="form-input">
              <option value="">From Airport</option>
              {airports.map(airport => (
                <option key={airport.id} value={airport.id}>{airport.airportName}</option>
              ))}
            </select>

            <select name="toAirportId" value={updateFormData.toAirportId} onChange={handleUpdateTourChange} required className="form-input">
              <option value="">To Airport</option>
              {airports.map(airport => (
                <option key={airport.id} value={airport.id}>{airport.airportName}</option>
              ))}
            </select>

            <select name="hotelId" value={updateFormData.hotelId} onChange={handleUpdateTourChange} required className="form-input">
              <option value="">Hotel</option>
              {hotels.map(hotel => (
                <option key={hotel.id} value={hotel.id}>{hotel.hotelName}</option>
              ))}
            </select>

            <button type="submit" className="submit-button">Update Tour</button>
            <button type="button" onClick={() => setSelectedTour(null)} className="cancel-button">Cancel</button>
          </form>
        </div>
      )}
    </div>
  );
};

export default Tours;
