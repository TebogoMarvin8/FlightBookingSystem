package za.co.mie.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import za.co.mie.dao.FlightDAO;
import za.co.mie.dao.impl.FlightDAOImpl;
import za.co.mie.dao.impl.FrequentFlyerDAOImpl;
import za.co.mie.dao.impl.ReservationDAOImpl;
import za.co.mie.email.EmailService;
import za.co.mie.manager.DbManager;
import za.co.mie.model.Customer;
import za.co.mie.model.Reservation;
import za.co.mie.Exceptions.OutOfSeatsException;
import za.co.mie.model.Flight;
import za.co.mie.service.FlightService;
import za.co.mie.service.ReservationService;
import za.co.mie.service.impl.ReservationServiceImpl;

@WebServlet(name = "processReservation", urlPatterns = {"/processReservation"})
public class ReservationServlet extends HttpServlet {

    private FlightService fs;
    private ReservationService rs;
    private DbManager db;
    private FlightDAO fd;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Customer registeredCustomer = (Customer) session.getAttribute("registeredCustomer");
        db = DbManager.getInstance();
        rs = new ReservationServiceImpl(new FlightDAOImpl(db.getConnection()), new FrequentFlyerDAOImpl(db.getConnection()), new ReservationDAOImpl(db.getConnection()));

        if (registeredCustomer == null || registeredCustomer.getId() <= 0) {
            try {
                throw new NullPointerException("Customer is empty");
            } catch (NullPointerException e) {
                request.setAttribute("errorMessage", "No registered customer found!");
                request.getRequestDispatcher("customerNotFound.jsp").forward(request, response);
                System.out.println(e.getMessage());
            }
        } else {
            String seatGrade = request.getParameter("seatGrade");

//----------------------------------------------------------------------------------------------------------------------------------------------------
            String date = request.getParameter("date");
            int flightId = Integer.parseInt(request.getParameter("flightId"));
            FlightDAO flightDAO = new FlightDAOImpl(db.getConnection());

            List<Flight> allFlights = flightDAO.getFlightsByDate(date);

//------------------------------------------------------------------------------------------------------------------------------
            Reservation reservation = new Reservation(flightId, seatGrade);
            try {
                int id = registeredCustomer.getId();
                reservation.setCustomer_id(id);
                reservation.setTicketSerialNumber(rs.generateRandomTicketSerial());
                reservation.setPaymentStatus("Paid");
                Reservation isReservationInserted = rs.createReservation(reservation);
                if (isReservationInserted != null) {
                    String reservationStatus = "\nTicket Number is: " + reservation.getTicketSerialNumber();
                    String customerid = "Your Customer ID is: " + id + "<br><br>PLEASE KEEP IT SAFE FOR NEXT TIME";
                    request.setAttribute("status", reservationStatus);
                    request.setAttribute("customerid", customerid);

                    // After a successful reservation
                    String customerEmail = registeredCustomer.getAddress();
                    String msg = reservationStatus + customerid;

                    EmailService.sendReservationConfirmation(customerEmail,msg);

                    request.getRequestDispatcher("reservationConfirmation.jsp").forward(request, response);
                }
            } catch (OutOfSeatsException ex) {
                request.setAttribute("errorMessage", "No seats available in the requested class.");
                RequestDispatcher dispatcher = request.getRequestDispatcher("unavailableFlight.jsp");
                dispatcher.forward(request, response);
            }
        }
    }


}
