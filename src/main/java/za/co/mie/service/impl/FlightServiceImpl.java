package za.co.mie.service.impl;

import za.co.mie.dao.FlightDAO;
import za.co.mie.model.Flight;

import java.util.List;
import za.co.mie.dao.impl.FlightDAOImpl;
import za.co.mie.manager.DbManager;
import za.co.mie.service.FlightService;

public class FlightServiceImpl implements FlightService {

    private FlightDAO flightDAO;

    public FlightServiceImpl(FlightDAO flightDAO) {
        this.flightDAO = flightDAO;
    }

    @Override
    public List<Flight> getAllFlights() {
        return flightDAO.getAllFlights();
    }

    @Override
    public Flight getFlightById(int id) {
        return flightDAO.getFlightById(id);
    }

    @Override
    public Flight getFlightByCities(int departureCity, int arrivalCity) {
        return flightDAO.getFlightByCities(departureCity, arrivalCity);
    }

    @Override
    public boolean saveFlight(Flight flight) {
        if (flight.getId() == 0) {
            return flightDAO.insertFlight(flight);
        } else {
            return flightDAO.updateFlight(flight);
        }
    }

    @Override
    public void deleteFlight(int id) {
        flightDAO.deleteFlight(id);
    }


//    public static void main(String[] args) {
//      DbManager db = new DbManager();
//
//
//          FlightDAO flightDAO = new FlightDAOImpl(db.getConnection());
//          FlightService flightService = new FlightServiceImpl(flightDAO);
//        //Testing getAllFlights
//        List<Flight> allFlights = flightService.getAllFlights();
//        //List<Flight> allFlights = flightDAO.getAllFlights();
//        System.out.println("All Flights: " + allFlights);
//        
//        //SAVE FLIGHT
//         Flight flight = new Flight(11,2,1,"09:00:00","09:00:00",50,100,200);
//        boolean newFlight = flightService.saveFlight(flight);
//        // Testing getFlightById
//        int flightId = 1;
//        Flight flightById = flightService.getFlightById(flightId);
//        System.out.println("Flight by ID: " + flightById);
//
//        // Testing getFlightByCities
//        String departureCity = "Johannesburg";
//        String arrivalCity = "Durban";
//        Flight flightByCities = flightService.getFlightByCities(departureCity, arrivalCity);
//        System.out.println("Flight by Cities: " + flightByCities);
//
//        // Testing deleteFlight
//        int flightToDeleteId = 2;
//        flightService.deleteFlight(flightToDeleteId);
//        System.out.println("Flight Deleted.");
  //  }
}
