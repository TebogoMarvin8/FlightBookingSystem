
package za.co.mie.dao;

import java.util.List;
import za.co.mie.model.Reservation;


public interface ReservationDAO {
    int getReservationCountForCustomer(int customerId);
    List<Reservation> getAllReservations();
    Reservation getReservationById(int id);
    Reservation insertReservation(Reservation reservation);
    void updateReservation(Reservation reservation);
    boolean deleteReservation(int id);
}
