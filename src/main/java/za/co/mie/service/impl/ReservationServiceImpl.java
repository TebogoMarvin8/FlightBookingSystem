package za.co.mie.service.impl;

import za.co.mie.Exceptions.OutOfSeatsException;
import java.util.List;
import java.util.Random;
import za.co.mie.dao.FlightDAO;
import za.co.mie.dao.FrequentFlyerDAO;
import za.co.mie.dao.ReservationDAO;
import za.co.mie.model.Flight;
import za.co.mie.model.Reservation;
import za.co.mie.Exceptions.FlightNotFoundException;
import za.co.mie.service.ReservationService;

public class ReservationServiceImpl implements ReservationService { //implements ProcessRequest

    private FlightDAO flightDAO;
    private FrequentFlyerDAO frequentFlyerDAO;
    private ReservationDAO reservationDAO;

    public ReservationServiceImpl(FlightDAO flightDAO, FrequentFlyerDAO frequentFlyerDAO,ReservationDAO reservationDAO) {
        this.flightDAO = flightDAO;
        this.frequentFlyerDAO = frequentFlyerDAO;
        this.reservationDAO=reservationDAO;
    }

    @Override
    public Reservation createReservation(Reservation reservation) throws OutOfSeatsException, FlightNotFoundException {

        Flight flight = flightDAO.getFlightById(reservation.getFlight_id());
        String seatGrade = reservation.getSeatGrade();
        
        if (!checkSeatAvailability(flight, seatGrade)) {
            throw new OutOfSeatsException("No seats available in the requested class.");
        }
        
        updateSeatCount(flight, seatGrade);
        return reservationDAO.insertReservation(reservation);
    }

    private boolean checkSeatAvailability(Flight flight, String seatGrade) throws OutOfSeatsException {
        if ("First Class".equals(seatGrade)) {
            return flight.getFirstClassSeats() > 0;
        } else if ("Business Class".equals(seatGrade)) {
            return flight.getBusinessClassSeats() > 0;
        } else if ("Economy Class".equals(seatGrade)) {
            return flight.getEconomyClassSeats() > 0;
        }
        throw new OutOfSeatsException("Out Of Seats");
    }

    private void updateSeatCount(Flight flight, String seatGrade) {

        if ("First Class".equals(seatGrade)) {
            int currentFirstClassSeats = flight.getFirstClassSeats();
            if (currentFirstClassSeats > 0) {
                flight.setFirstClassSeats(currentFirstClassSeats - 1);
                flightDAO.updateFirstClassSeats(flight.getId(), currentFirstClassSeats - 1);
            }
        } else if ("Business Class".equals(seatGrade)) {
            int currentBusinessClassSeats = flight.getBusinessClassSeats();
            if (currentBusinessClassSeats > 0) {
                flight.setBusinessClassSeats(currentBusinessClassSeats - 1);
                flightDAO.updateBusinessClassSeats(flight.getId(), currentBusinessClassSeats - 1);
            }
        } else if ("Economy Class".equals(seatGrade)) {
            int currentEconomyClassSeats = flight.getEconomyClassSeats();
            if (currentEconomyClassSeats > 0) {
                flight.setEconomyClassSeats(currentEconomyClassSeats - 1);
                flightDAO.updateEconomyClassSeats(flight.getId(), currentEconomyClassSeats - 1);
            }
        } else {
            
        }
    }

    @Override

    public String generateRandomTicketSerial() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder ticketSerial = new StringBuilder();
        Random random = new Random();
        int length = 10;

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(chars.length());
            ticketSerial.append(chars.charAt(index));
        }

        return ticketSerial.toString();
    }

    @Override
    public List<Reservation> getAllReservations() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Reservation getReservationById(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean deleteReservationById(int id) {
        boolean isDeleted = reservationDAO.deleteReservation(id);

        if (isDeleted) {
            System.out.println("Reservation with ID " + id + " has been deleted.");
        } else {
            System.out.println("Failed to delete reservation with ID " + id);
        }

        return isDeleted;
    }

//    @Override
//    public void processTheRequest(HttpServletRequest request, HttpServletResponse response) {
//        try {
//            String name = request.getParameter("name");
//            String address = request.getParameter("address");
//            int flightId = Integer.parseInt(request.getParameter("flight_id"));
//            String seatGrade = request.getParameter("seatGrade");
//
//            Reservation reservation = new Reservation(name, address, flightId, seatGrade);
//
//            if (createReservation(reservation) != null) {
//                response.getWriter().write("Reservation created successfully!");
//                response.setStatus(HttpServletResponse.SC_OK);
//            } else {
//                response.getWriter().write("Failed to create reservation.");
//                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//            }
//        } catch (IOException e) {
//            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//        } catch (NumberFormatException e) {
//            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//        } catch (OutOfSeatsException ex) {
//            Logger.getLogger(ReservationServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (FlightNotFoundException ex) {
//            Logger.getLogger(ReservationServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//    @Override
//    public void processTheResponse(HttpServletRequest request, HttpServletResponse response) {
//        try {
//            String responseData = "This is a response message.";
//            response.setContentType("text/plain");
//            response.setCharacterEncoding("UTF-8");
//            PrintWriter out = response.getWriter();
//            out.print(responseData);
//            out.flush();
//        } catch (IOException e) {
//            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//        }
//    }

}
