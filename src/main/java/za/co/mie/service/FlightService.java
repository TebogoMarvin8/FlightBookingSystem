
package za.co.mie.service;

import java.util.List;
import za.co.mie.model.Flight;


public interface FlightService {
     List<Flight> getAllFlights();
    Flight getFlightById(int id);
    //void saveFlight(Flight flight);
    boolean saveFlight(Flight flight);
    void deleteFlight(int id);
    Flight getFlightByCities(int departureCity, int arrivalCity);
}
