import { memo, useEffect, useState } from "react";
import "./flightInfo.scss";

const FlightInfoPage = () => {
  const [flights, setFlights] = useState([]);

  // useEffect(() => {
  //   const fetchFlights = async () => {
  //     const response = await fetch("/api/flights");
  //     const data = await response.json();
  //     setFlights(data);
  //   };
  //   fetchFlights();
  // }, []);

  return (
    <div id="flight-info-section">
      <h2>Thông tin các chuyến bay</h2>
      <table className="flight-table">
        <thead>
          <tr>
            <th>Số hiệu chuyến bay</th>
            <th>Điểm đi</th>
            <th>Điểm đến</th>
            <th>Thời gian khởi hành</th>
            <th>Thời gian đến</th>
            <th>Trạng thái</th>
          </tr>
        </thead>
        {/* <tbody>
          {flights.map((flight) => (
            <tr key={flight.flight_id}>
              <td>{flight.flight_number}</td>
              <td>{flight.departure_airport}</td>
              <td>{flight.arrival_airport}</td>
              <td>{new Date(flight.departure_time).toLocaleString()}</td>
              <td>{new Date(flight.arrival_time).toLocaleString()}</td>
              <td>{flight.status}</td>
            </tr>
          ))}
        </tbody> */}
      </table>
    </div>
  );
};

export default memo(FlightInfoPage);