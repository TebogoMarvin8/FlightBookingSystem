package za.co.mie.dao;

import java.util.List;
import za.co.mie.model.Flight;
import za.co.mie.Exceptions.FlightNotFoundException;

public interface FlightDAO {

    List<Flight> getAllFlights();

    Flight getFlightById(int id) throws FlightNotFoundException;

    //void insertFlight(Flight flight);
    boolean insertFlight(Flight flight);

    //void updateFlight(Flight flight);
    boolean updateFlight(Flight flight) throws FlightNotFoundException;

    void deleteFlight(int id) throws FlightNotFoundException;

    Flight getFlightByCities(int departureCity_id, int arrivalCity_id) throws FlightNotFoundException;

    int getCityById(int cityId);

    void updateFirstClassSeats(int flightId, int updatedSeats);

    void updateBusinessClassSeats(int flightId, int updatedSeats);

    void updateEconomyClassSeats(int flightId, int updatedSeats);
    
    List<Flight> getFlightsByDate(String date);

}
