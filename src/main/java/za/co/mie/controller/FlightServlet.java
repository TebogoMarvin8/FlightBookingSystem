package za.co.mie.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import za.co.mie.dao.CityDAO;
import za.co.mie.dao.FlightDAO;
import za.co.mie.dao.impl.CityDAOImpl;
import za.co.mie.dao.impl.FlightDAOImpl;
import za.co.mie.manager.DbManager;
import za.co.mie.model.Flight;

@WebServlet("/getFlights")
public class FlightServlet extends HttpServlet {

    private FlightDAO fd;
    private DbManager db;
    private CityDAO cityDAO;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        db = DbManager.getInstance();
        fd = new FlightDAOImpl(db.getConnection());
        cityDAO = new CityDAOImpl(db.getConnection());

        if (db == null) {System.out.println("DB in getFlights IS Null");} 
        if (fd == null) {System.out.println("flightdao in  is null");}

        String date = request.getParameter("date");
        if (date != null && !date.isEmpty()) {
            List<Flight> flights = fd.getFlightsByDate(date);

            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.println("[");
            for (int i = 0; i < flights.size(); i++) {
                Flight flight = flights.get(i);
                out.println("{");
                out.println("\"id\": " + flight.getId() + ",");
                out.println("\"departureCity\": \"" + cityDAO.getCityNameById(flight.getDepartureCityId()) + "\",");
                out.println("\"destinationCity\": \"" + cityDAO.getCityNameById(flight.getDestinationCityId()) + "\",");
                out.println("\"departureTime\": \"" + flight.getDepartureTime() + "\",");
                out.println("\"arrivalTime\": \"" + flight.getArrivalTime() + "\"");
                if (i < flights.size() - 1) {
                    out.println("},");
                } else {
                    out.println("}");
                }
            }
            out.println("]");
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println("Date parameter is empty or null");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
