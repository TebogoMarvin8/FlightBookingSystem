package za.co.mie.dao.impl;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import za.co.mie.dao.FlightDAO;
import za.co.mie.model.Flight;
import za.co.mie.Exceptions.FlightNotFoundException;
import za.co.mie.manager.DbManager;

public class FlightDAOImpl implements FlightDAO {
    private Connection connection;
    PreparedStatement statement;
    
    public FlightDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean insertFlight(Flight flight) {
        boolean isInserted = false;
        try {
            statement = connection.prepareStatement("INSERT INTO flights (departure_city_id, destination_city_id, departure_time, arrival_time, first_class_seats, business_class_seats, economy_class_seats) VALUES (?, ?, ?, ?, ?, ?, ?)");
            statement.setInt(1, flight.getDepartureCityId());
            statement.setInt(2, flight.getDestinationCityId());
            statement.setString(3, flight.getDepartureTime());
            statement.setString(4, flight.getArrivalTime());
            statement.setInt(5, flight.getFirstClassSeats());
            statement.setInt(6, flight.getBusinessClassSeats());
            statement.setInt(7, flight.getEconomyClassSeats());
            
            int rowsAffected = statement.executeUpdate();
            isInserted = rowsAffected > 0;
        } catch (SQLException e) {
        }
        return isInserted;
    }

  private Flight createFlightFromResultSet(ResultSet resultSet) throws SQLException {
    int id = resultSet.getInt("id");
    int departureCityId = resultSet.getInt("departure_city_id");
    int destinationCityId = resultSet.getInt("destination_city_id");
    String departureTime = resultSet.getString("departure_time");
    String arrivalTime = resultSet.getString("arrival_time");
    int firstClassSeats = resultSet.getInt("first_class_seats");
    int businessClassSeats = resultSet.getInt("business_class_seats");
    int economyClassSeats = resultSet.getInt("economy_class_seats");

    // Parse departureDate and arrivalDate from respective database fields
        java.util.Date departureDate = parseStringToDate(resultSet.getString("departure_date"));
        java.util.Date arrivalDate = parseStringToDate(resultSet.getString("arrival_date"));

    return new Flight(
            id,
            departureCityId,
            destinationCityId,
            departureDate, // Set the parsed departure date
            arrivalDate,   // Set the parsed arrival date
            departureTime,
            arrivalTime,
            firstClassSeats,
            businessClassSeats,
            economyClassSeats
    );
}

    /**
     *
     * @return
     */
    @Override
    public List<Flight> getAllFlights() {
        List<Flight> flights = new ArrayList<Flight>();
        try {
            Statement statementt = connection.createStatement();
            ResultSet resultSet = statementt.executeQuery("SELECT * FROM flights");
            while (resultSet.next()) {
                Flight flight = createFlightFromResultSet(resultSet);
                flights.add(flight);
            }
        } catch (SQLException e) {
            System.out.println("Error: Could not get all flights"+e.getMessage());
        }
        return flights;
    }

    @Override
    public Flight getFlightById(int id) throws FlightNotFoundException {
        Flight flight = null;
        try {
            statement = connection.prepareStatement("SELECT * FROM flights WHERE id = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet==null) {
                 throw new FlightNotFoundException("No available flight for the selected route");
            }
            if (resultSet.next()) {
                flight = createFlightFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            System.out.println("Error: Could not get flight by ID"+e.getMessage());
        }
        return flight;
    }

    @Override
    public void updateFirstClassSeats(int flightId, int updatedSeats) {
        try {
            statement = connection.prepareStatement(
                    "UPDATE flights SET first_class_seats = ? WHERE id = ?");
            statement.setInt(1, updatedSeats);
            statement.setInt(2, flightId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateBusinessClassSeats(int flightId, int updatedSeats) {
        try {
            statement = connection.prepareStatement(
                    "UPDATE flights SET business_class_seats = ? WHERE id = ?");
            statement.setInt(1, updatedSeats);
            statement.setInt(2, flightId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateEconomyClassSeats(int flightId, int updatedSeats) {
        try {
            statement = connection.prepareStatement(
                    "UPDATE flights SET economy_class_seats = ? WHERE id = ?");
            statement.setInt(1, updatedSeats);
            statement.setInt(2, flightId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public boolean updateFlight(Flight flight) {
        return false;
    }

    @Override
    public void deleteFlight(int id) {
    }

    @Override
    public Flight getFlightByCities(int departureCity_id, int destinationCity_id) {
        Flight flight = null;
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM flights WHERE departure_city_id = ? AND destination_city_id = ?");
            statement.setInt(1, getCityById(departureCity_id));
            statement.setInt(2, getCityById(destinationCity_id));

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                flight = new Flight();
                flight.setId(resultSet.getInt("id"));
                flight.setDepartureCityId(resultSet.getInt("departure_city_id"));
                flight.setDestinationCityId(resultSet.getInt("destination_city_id"));
                flight.setDepartureTime(resultSet.getString("departure_time"));
                flight.setArrivalTime(resultSet.getString("arrival_time"));
                flight.setFirstClassSeats(resultSet.getInt("first_class_seats"));
                flight.setBusinessClassSeats(resultSet.getInt("business_class_seats"));
                flight.setEconomyClassSeats(resultSet.getInt("economy_class_seats"));
            }
        } catch (SQLException e) {
            System.out.println("Error: Could not get flight by cities"+e.getMessage());
        }

        return flight;
    }

    @Override
    public int getCityById(int cityId) {
        int retrievedCityId = 0;
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT id FROM cities WHERE id = ?");
            statement.setInt(1, cityId);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                retrievedCityId = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println("Error: Could not get city by id"+e.getMessage());
        }
        return retrievedCityId;
    }
    
    

   @Override
public List<Flight> getFlightsByDate(String date) {
    List<Flight> flights = new ArrayList<Flight>();
    String query = "SELECT * FROM flights WHERE departure_date = ?";
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
        pstmt = connection.prepareStatement(query);
        pstmt.setString(1, date);
        rs = pstmt.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("id");
            int departureCityId = rs.getInt("departure_city_id");
            int destinationCityId = rs.getInt("destination_city_id");
            String departureTime = rs.getString("departure_time");
            String arrivalTime = rs.getString("arrival_time");
            int firstClassSeats = rs.getInt("first_class_seats");
            int businessClassSeats = rs.getInt("business_class_seats");
            int economyClassSeats = rs.getInt("economy_class_seats");

            java.util.Date departureDate = parseStringToDate(rs.getString("departure_date"));
            java.util.Date arrivalDate = parseStringToDate(rs.getString("arrival_date"));

            Flight flight = new Flight(id, departureCityId, destinationCityId, departureDate, arrivalDate, departureTime, arrivalTime, firstClassSeats, businessClassSeats, economyClassSeats);
            flights.add(flight);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        try {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    return flights;
}

    // Method to parse string to Date
 private java.util.Date parseStringToDate(String dateString) {
    if (dateString != null && !dateString.isEmpty()) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    return null;
}



      //  public static void main(String[] args) {
      //  DbManager db = new DbManager();
//
      //   FlightDAO flightDAO = new FlightDAOImpl(db.getConnection());
//        //Testing getAllFlights
      //  List<Flight> flight = flightDAO.getFlightsByDate("2023-01-07");
      //  System.out.println("All Flights\n\n " + flight);
//        
//        //SAVE FLIGHT
//         Flight flight = new Flight(11,2,1,"09:00:00","09:00:00",50,100,200);
//        boolean newFlight = flightService.saveFlight(flight);
//
//        // Testing getFlightById
//        int flightId = 6; // Provide an existing flight ID for testing
//        Flight flightById = flightDAO.getFlightById(flightId);
//        System.out.println("Flight by ID\n\n" + flightById);
//
//        // Testing getFlightByCities
//        int departureCityid = 3;
//        int arrivalCityid = 5; 
//      Flight flightByCities = flightDAO.getFlightByCities(departureCityid, arrivalCityid);
//        System.out.println("Flight by Cities: " + flightByCities);
////
////        // Testing deleteFlight
//        int flightToDeleteId = 2;
//        flightService.deleteFlight(flightToDeleteId);
//        System.out.println("Flight Deleted.");
 //   }

    
//}
}
