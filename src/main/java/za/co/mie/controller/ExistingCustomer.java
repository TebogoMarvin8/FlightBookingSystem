package za.co.mie.controller;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import za.co.mie.dao.CustomerDAO;
import za.co.mie.dao.FlightDAO;
import za.co.mie.dao.FrequentFlyerDAO;
import za.co.mie.dao.ReservationDAO;
import za.co.mie.dao.impl.CustomerDAOImpl;
import za.co.mie.dao.impl.FlightDAOImpl;
import za.co.mie.dao.impl.FrequentFlyerDAOImpl;
import za.co.mie.dao.impl.ReservationDAOImpl;
import za.co.mie.email.EmailService;
import za.co.mie.manager.DbManager;
import za.co.mie.model.Customer;
import za.co.mie.model.Flight;
import za.co.mie.model.FrequentFlyer;
import za.co.mie.model.Reservation;
import za.co.mie.Exceptions.OutOfSeatsException;
import za.co.mie.service.ReservationService;
import za.co.mie.service.impl.ReservationServiceImpl;

@WebServlet(name = "NewCustomer", urlPatterns = {"/NewCustomer"})
public class ExistingCustomer extends HttpServlet {

    private FlightDAO fd;
    private FrequentFlyerDAO ff;
    private ReservationDAO rd;
    private DbManager db;
    private FrequentFlyerDAO frequentFlyerDAO;
    private ReservationDAO reservationDAO;
    private FrequentFlyer frequentflyer;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        db = DbManager.getInstance();
        fd = new FlightDAOImpl(db.getConnection());
        ff = new FrequentFlyerDAOImpl(db.getConnection());
        rd = new ReservationDAOImpl(db.getConnection());
        if (db == null) {
            System.out.println("DB in processRequest IS Null");
        }
        if (fd == null) {
            System.out.println("flightdao in processRequest is null");
        }
        List<Flight> flights = fd.getAllFlights();
        request.setAttribute("flights", flights);
        RequestDispatcher dispatcher = request.getRequestDispatcher("customer.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int customerId = Integer.parseInt(request.getParameter("customerId"));

        CustomerDAO customerDAO = new CustomerDAOImpl(db.getConnection());
        Customer existingCustomer = customerDAO.getCustomerById(customerId);
        frequentFlyerDAO = new FrequentFlyerDAOImpl(db.getConnection());
        reservationDAO = new ReservationDAOImpl(db.getConnection());

        String transactionType = request.getParameter("transactionType");

        if ("reserveSeat".equals(transactionType)) {
            if (existingCustomer != null) {
                request.setAttribute("existingCustomer", existingCustomer);

                int flightId = Integer.parseInt(request.getParameter("flightId"));
                String seatGrade = request.getParameter("seatGrade");

                ReservationService rs = new ReservationServiceImpl(fd, ff, rd);
                Reservation reservation = new Reservation(existingCustomer.getName(), existingCustomer.getAddress(), flightId, seatGrade);
                reservation.setCustomer_id(existingCustomer.getId());
                reservation.setTicketSerialNumber(rs.generateRandomTicketSerial());
                reservation.setPaymentStatus("Paid");
                try {
                    Reservation isReservationInserted = rs.createReservation(reservation);
                    if (isReservationInserted != null) {
                        String reservationStatus = "\nTicket Number is: " + reservation.getTicketSerialNumber();

                        //----------------------------------------------------------------------------------------------------------------------------------
                        String membershipNumber = UUID.randomUUID().toString().toUpperCase();

                        int reservationCount = reservationDAO.getReservationCountForCustomer(reservation.getCustomer_id());
                        if (reservationCount >= 10 && !frequentFlyerDAO.customerIdExists(customerId)) {
                            frequentflyer = new FrequentFlyer();
                            frequentflyer.setCustomerid(customerId);
                            frequentflyer.setMembershipNumber(membershipNumber);
                            frequentflyer.setMilesBalance(10000);
                            boolean insertionSuccess = frequentFlyerDAO.insertFrequentFlyer(frequentflyer);

                            if (insertionSuccess) {

                                String congratulationsMessage = "Congratulations! You're now a frequent flyer.";
                                String message2 = "Your Membership number is: " + frequentflyer.getMembershipNumber();
                                String message3 = "You have " + frequentflyer.getMilesBalance() + " Miles left in the Balance";
                                request.setAttribute("congratulationsMessage", congratulationsMessage);
                                request.setAttribute("message2", message2);
                                request.setAttribute("message3", message3);
                                String customerEmail = existingCustomer.getAddress();

                                String msg = reservationStatus + " " + congratulationsMessage + " " + message2 + " " + message3;
                                EmailService.sendReservationConfirmation(customerEmail, msg);

                            } else {
                                System.out.println("Error: Frequent Flyer insertion failed.");

                            }
                        }
                        //-----------------------------------------------------------------------------------------------------------------------------------------
                        if (frequentFlyerDAO.customerIdExists(customerId)) {

                            String message2 = "Your Membership number is: " + frequentFlyerDAO.getFrequentFlyerByCustomerId(customerId).getMembershipNumber();
                            String message3 = "You have " + frequentFlyerDAO.getFrequentFlyerByCustomerId(customerId).getMilesBalance() + " Miles left in the Balance";
                            request.setAttribute("message2", message2);
                            request.setAttribute("message3", message3);
                            request.setAttribute("status", reservationStatus);
                            String customerEmail = existingCustomer.getAddress();
                            String msg = reservationStatus + message2 + " " + message3;

                            EmailService.sendReservationConfirmation(customerEmail, msg);

                            request.getRequestDispatcher("reservationConfirmation.jsp").forward(request, response);
                        }

                        //-------------------------------------------------------------------------------------------------------------------------------------------
                        String customerEmail = existingCustomer.getAddress();

                        EmailService.sendReservationConfirmation(customerEmail, reservationStatus);

                        request.setAttribute("status", reservationStatus);
                        request.getRequestDispatcher("reservationConfirmation.jsp").forward(request, response);
                    } else {
                        response.sendRedirect("errorPage.jsp");
                    }
                } catch (OutOfSeatsException e) {
                    request.setAttribute("errorMessage", "No seats available in the requested class.");
                    RequestDispatcher dispatcher = request.getRequestDispatcher("reservationError.jsp");
                    dispatcher.forward(request, response);
                }
            } else {
                response.sendRedirect("customerNotFound.jsp");
            }
            //-----------------------------------------------------------------------------------------------------------
            //-----------------------------------------------------------------------------------------------------------
            //-----------------------------------------------------------------------------------------------------------
        } else if ("payWithMiles".equals(transactionType)) {
            int milesToDeduct = 1000;

            if (frequentFlyerDAO.customerIdExists(customerId)) {
                FrequentFlyer frequentFlyer = frequentFlyerDAO.getFrequentFlyerByCustomerId(customerId);
                int currentMiles = frequentFlyer.getMilesBalance();
                if (currentMiles >= milesToDeduct) {
                    frequentFlyer.setMilesBalance(currentMiles - milesToDeduct);
                    frequentFlyerDAO.updateFrequentFlyer(frequentFlyer);

                    ReservationService rs = new ReservationServiceImpl(fd, ff, rd);
                    if (existingCustomer != null) {
                        request.setAttribute("existingCustomer", existingCustomer);

                        int flightId = Integer.parseInt(request.getParameter("flightId"));
                        String seatGrade = request.getParameter("seatGrade");

                        Reservation reservation = new Reservation(flightId, seatGrade);
                        reservation.setCustomer_id(existingCustomer.getId());
                        reservation.setTicketSerialNumber(rs.generateRandomTicketSerial());
                        reservation.setPaymentStatus("Paid with Miles Account");

                        try {
                            Reservation isReservationInserted = rs.createReservation(reservation);
                            if (isReservationInserted != null) {
                                String reservationStatus = "\nTicket Number is: " + reservation.getTicketSerialNumber();

                                String message2 = "Your Membership number is: " + frequentFlyerDAO.getFrequentFlyerByCustomerId(customerId).getMembershipNumber();
                                String message3 = "You have " + frequentFlyerDAO.getFrequentFlyerByCustomerId(customerId).getMilesBalance() + " Miles left in the Balance";
                                request.setAttribute("message2", message2);
                                request.setAttribute("message3", message3);
                                request.setAttribute("status", reservationStatus);

                                String customerEmail = existingCustomer.getAddress();
                                String msg = reservationStatus + message2 + " " + message3;

                                EmailService.sendReservationConfirmation(customerEmail, msg);

                                request.setAttribute("status", reservationStatus);
                                request.getRequestDispatcher("reservationConfirmation.jsp").forward(request, response);
                            } else {
                                response.sendRedirect("errorPage.jsp");
                            }
                        } catch (OutOfSeatsException e) {
                            request.setAttribute("errorMessage", "No seats available in the requested class.");
                            RequestDispatcher dispatcher = request.getRequestDispatcher("reservationError.jsp");
                            dispatcher.forward(request, response);
                        }
                    }
                } else {
                    request.setAttribute("errorMessage", "Insufficient miles balance.");
                    RequestDispatcher dispatcher = request.getRequestDispatcher("milePaymentError.jsp");
                    dispatcher.forward(request, response);
                }
            } else {
                request.setAttribute("errorMessage", "Customer is not a frequent flyer.");
                RequestDispatcher dispatcher = request.getRequestDispatcher("milePaymentError.jsp");
                dispatcher.forward(request, response);
            }

        }

    }
}
