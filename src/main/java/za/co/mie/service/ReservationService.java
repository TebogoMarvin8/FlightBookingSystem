package za.co.mie.service;

import java.util.List;
import za.co.mie.model.Reservation;
import za.co.mie.Exceptions.OutOfSeatsException;

public interface ReservationService {

    List<Reservation> getAllReservations();

    Reservation getReservationById(int id);

    boolean deleteReservationById(int id);

    String generateRandomTicketSerial();

    Reservation createReservation(Reservation reservation)throws OutOfSeatsException;
    
}
