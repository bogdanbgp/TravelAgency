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

  const updateFormRef = useRef(null);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const responses = await Promise.all([
          fetch('http://localhost:8080/api/admin/countries'),
          fetch('http://localhost:8080/api/admin/cities'),
          fetch('http://localhost:8080/api/admin/hotels'),
          fetch('http://localhost:8080/api/admin/airports'),
          fetch('http://localhost:8080/api/admin/tours')
        ]);
        const [
          countriesData,
          citiesData,
          hotelsData,
          airportsData,
          toursData
        ] = await Promise.all(responses.map((res) => res.json()));

        setCountries(countriesData);
        setCities(citiesData);
        setHotels(hotelsData);
        setAirports(airportsData);
        setTours(toursData);
      } catch (error) {
        console.error('Error fetching data:', error);
      }
    };
    fetchData();
  }, []);

  const handleNewTourChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const handleUpdateTourChange = (e) => {
    const { name, value } = e.target;
    setUpdateFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const handleSelectTour = (tour) => {
    setSelectedTour(tour);
    setUpdateFormData(tour);
    if (updateFormRef.current) {
      updateFormRef.current.scrollIntoView({ behavior: 'smooth', block: 'start' });
    }
  };

  const handleCreateTour = async (e) => {
    e.preventDefault();

    if (!formData.tourName || !formData.departureDate || !formData.price) {
      console.error('Please fill in all required fields');
      return;
    }

    try {
      const response = await fetch('http://localhost:8080/api/admin/tours', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(formData),
      });
      if (!response.ok) throw new Error('Failed to create tour');
      const newTour = await response.json();
      setTours([...tours, newTour]);
      setFormData({
        tourName: '',
        departureDate: '',
        returnDate: '',
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
    } catch (error) {
      console.error('Error creating tour:', error);
    }
  };

  const handleUpdateTour = async (e) => {
    e.preventDefault();

    if (!updateFormData.tourName || !updateFormData.departureDate || !updateFormData.price) {
      console.error('Please fill in all required fields for update');
      return;
    }

    try {
      const response = await fetch(`http://localhost:8080/api/admin/tours/${updateFormData.id}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(updateFormData),
      });
      if (!response.ok) throw new Error('Failed to update tour');
      const updatedTour = await response.json();
      setTours(tours.map((tour) => (tour.id === updatedTour.id ? updatedTour : tour)));
      setSelectedTour(null);
      setUpdateFormData({
        id: '',
        tourName: '',
        departureDate: '',
        returnDate: '',
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
    } catch (error) {
      console.error('Error updating tour:', error);
    }
  };

  const deleteTour = async (id) => {
    try {
      const response = await fetch(`http://localhost:8080/api/admin/tours/${id}`, {
        method: 'DELETE',
        headers: { 'Content-Type': 'application/json' },
      });
      if (!response.ok) throw new Error('Failed to delete tour');
      setTours((prevTours) => prevTours.filter((tour) => tour.id !== id));
      if (selectedTour && selectedTour.id === id) {
        setSelectedTour(null);
        setUpdateFormData({
          id: '',
          tourName: '',
          departureDate: '',
          returnDate: '',
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

  // Handle cancel operation for Update form
  const handleCancelUpdate = () => {
    setSelectedTour(null);
    setUpdateFormData({
      id: '',
      tourName: '',
      departureDate: '',
      returnDate: '',
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
  };

  return (
    <div className="tours-container">
      <div className="tours-header">
        <h1>Manage Tours</h1>
      </div>

      {/* Tours List */}
      <h2>Tour List</h2>
      <ul className="tour-list">
        {tours.map((tour) => (
          <li
            key={tour.id}
            onClick={() => handleSelectTour(tour)}
            className="tour-item"
          >
            <div className="tour-info">
              {tour.tourName} - {tour.departureDate} to {tour.returnDate}
            </div>
            <div className="tour-actions">
              <button className="update-button" onClick={() => handleSelectTour(tour)}>
                Update
              </button>
              <button className="delete-button" onClick={() => deleteTour(tour.id)}>
                Delete
              </button>
            </div>
          </li>
        ))}
      </ul>

      {/* Create Tour Form */}
      <h2>Create Tour</h2>
      <form onSubmit={handleCreateTour} className="add-tour-form">
        <input
          type="text"
          name="tourName"
          placeholder="Tour Name"
          value={formData.tourName}
          onChange={handleNewTourChange}
          required
          className="form-input"
        />
        <input
          type="date"
          name="departureDate"
          value={formData.departureDate}
          onChange={handleNewTourChange}
          className="form-input"
        />
        <input
          type="date"
          name="returnDate"
          value={formData.returnDate}
          onChange={handleNewTourChange}
          className="form-input"
        />
        <textarea
          name="description"
          placeholder="Description"
          value={formData.description}
          onChange={handleNewTourChange}
          className="form-textarea"
        />
        <input
          type="number"
          name="price"
          placeholder="Price($)"
          value={formData.price}
          onChange={handleNewTourChange}
          required
          className="form-input"
        />

        {/* Add Dropdowns for Countries, Cities, Airports, and Hotels */}
        <select
          name="fromCountryId"
          value={formData.fromCountryId}
          onChange={handleNewTourChange}
          className="form-input"
        >
          <option value="">From Country</option>
          {countries.map((country) => (
            <option key={country.id} value={country.id}>
              {country.countryName}
            </option>
          ))}
        </select>

        <select
          name="toCountryId"
          value={formData.toCountryId}
          onChange={handleNewTourChange}
          className="form-input"
        >
          <option value="">To Country</option>
          {countries.map((country) => (
            <option key={country.id} value={country.id}>
              {country.countryName}
            </option>
          ))}
        </select>

        <select
          name="fromCityId"
          value={formData.fromCityId}
          onChange={handleNewTourChange}
          className="form-input"
        >
          <option value="">From City</option>
          {cities.map((city) => (
            <option key={city.id} value={city.id}>
              {city.cityName}
            </option>
          ))}
        </select>

        <select
          name="toCityId"
          value={formData.toCityId}
          onChange={handleNewTourChange}
          className="form-input"
        >
          <option value="">To City</option>
          {cities.map((city) => (
            <option key={city.id} value={city.id}>
              {city.cityName}
            </option>
          ))}
        </select>

        <select
          name="fromAirportId"
          value={formData.fromAirportId}
          onChange={handleNewTourChange}
          className="form-input"
        >
          <option value="">From Airport</option>
          {airports.map((airport) => (
            <option key={airport.id} value={airport.id}>
              {airport.airportName}
            </option>
          ))}
        </select>

        <select
          name="toAirportId"
          value={formData.toAirportId}
          onChange={handleNewTourChange}
          className="form-input"
        >
          <option value="">To Airport</option>
          {airports.map((airport) => (
            <option key={airport.id} value={airport.id}>
              {airport.airportName}
            </option>
          ))}
        </select>

        <select
          name="hotelId"
          value={formData.hotelId}
          onChange={handleNewTourChange}
          className="form-input"
        >
          <option value="">Hotel</option>
          {hotels.map((hotel) => (
            <option key={hotel.id} value={hotel.id}>
              {hotel.hotelName}
            </option>
          ))}
        </select>

        <button type="submit" className="create-tour-button">Create Tour</button>
      </form>

      {/* Update Tour Form */}
      {selectedTour && (
        <div ref={updateFormRef}>
          <h2>Update Tour</h2>
          <form onSubmit={handleUpdateTour} className="update-tour-form">
            <input
              type="text"
              name="tourName"
              value={updateFormData.tourName}
              onChange={handleUpdateTourChange}
              className="form-input"
            />
            <input
              type="date"
              name="departureDate"
              value={updateFormData.departureDate}
              onChange={handleUpdateTourChange}
              className="form-input"
            />
            <input
              type="date"
              name="returnDate"
              value={updateFormData.returnDate}
              onChange={handleUpdateTourChange}
              className="form-input"
            />
            <textarea
              name="description"
              value={updateFormData.description}
              onChange={handleUpdateTourChange}
              className="form-textarea"
            />
            <input
              type="number"
              name="price"
              value={updateFormData.price}
              onChange={handleUpdateTourChange}
              className="form-input"
            />

            {/* Add dropdowns for countries, cities, airports, hotels */}
            <select
              name="fromCountryId"
              value={updateFormData.fromCountryId}
              onChange={handleUpdateTourChange}
              className="form-input"
            >
              <option value="">From Country</option>
              {countries.map((country) => (
                <option key={country.id} value={country.id}>
                  {country.countryName}
                </option>
              ))}
            </select>

            <select
              name="toCountryId"
              value={updateFormData.toCountryId}
              onChange={handleUpdateTourChange}
              className="form-input"
            >
              <option value="">To Country</option>
              {countries.map((country) => (
                <option key={country.id} value={country.id}>
                  {country.countryName}
                </option>
              ))}
            </select>

            <select
              name="fromCityId"
              value={updateFormData.fromCityId}
              onChange={handleUpdateTourChange}
              className="form-input"
            >
              <option value="">From City</option>
              {cities.map((city) => (
                <option key={city.id} value={city.id}>
                  {city.cityName}
                </option>
              ))}
            </select>

            <select
              name="toCityId"
              value={updateFormData.toCityId}
              onChange={handleUpdateTourChange}
              className="form-input"
            >
              <option value="">To City</option>
              {cities.map((city) => (
                <option key={city.id} value={city.id}>
                  {city.cityName}
                </option>
              ))}
            </select>

            <select
              name="fromAirportId"
              value={updateFormData.fromAirportId}
              onChange={handleUpdateTourChange}
              className="form-input"
            >
              <option value="">From Airport</option>
              {airports.map((airport) => (
                <option key={airport.id} value={airport.id}>
                  {airport.airportName}
                </option>
              ))}
            </select>

            <select
              name="toAirportId"
              value={updateFormData.toAirportId}
              onChange={handleUpdateTourChange}
              className="form-input"
            >
              <option value="">To Airport</option>
              {airports.map((airport) => (
                <option key={airport.id} value={airport.id}>
                  {airport.airportName}
                </option>
              ))}
            </select>

            <select
              name="hotelId"
              value={updateFormData.hotelId}
              onChange={handleUpdateTourChange}
              className="form-input"
            >
              <option value="">Hotel</option>
              {hotels.map((hotel) => (
                <option key={hotel.id} value={hotel.id}>
                  {hotel.hotelName}
                </option>
              ))}
            </select>

            <button type="submit" className="update-tour-button">Update Tour</button>
            <button
              type="button"
              className="cancel-update-button"
              onClick={handleCancelUpdate}
            >
              Cancel
            </button>
          </form>
        </div>
      )}
    </div>
  );
};

export default Tours;
