
package za.co.mie.dao.impl;

import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import za.co.mie.dao.ReservationDAO;
import za.co.mie.model.Reservation;

public class ReservationDAOImpl implements ReservationDAO {
    private Connection connection;

    public ReservationDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public int getReservationCountForCustomer(int customerId) {
        int reservationCount = 0;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            String query = "SELECT COUNT(*) AS count FROM reservations WHERE customer_id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, customerId);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                reservationCount = resultSet.getInt("count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return reservationCount;
    }
    @Override
    public List<Reservation> getAllReservations() {
        List<Reservation> reservations = new ArrayList<Reservation>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM reservations");
            while (resultSet.next()) {
                Reservation reservation = new Reservation();
                reservation.setId(resultSet.getInt("id"));
                reservations.add(reservation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }

    @Override
    public Reservation getReservationById(int id) {
        Reservation reservation = null;
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM reservations WHERE id = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                reservation = new Reservation();
                reservation.setId(resultSet.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservation;
    }

    @Override
    public Reservation insertReservation(Reservation reservation) {
    Reservation insertedReservation = null;
    if (connection == null) {
        System.out.println("Connection is null inside insertReservation");
    }
    try {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO reservations (customer_id, flight_id, seat_grade, reservation_time, payment_status, ticket_serial_number) VALUES (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1,reservation.getCustomer_id());
        statement.setInt(2, reservation.getFlight_id());
        statement.setString(3, reservation.getSeatGrade());
        statement.setString(4, LocalTime.now().toString());
        statement.setString(5, reservation.getPaymentStatus());
        statement.setString(6, reservation.getTicketSerialNumber());

        int rowsAffected = statement.executeUpdate();
        if (rowsAffected > 0) {
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int generatedId = generatedKeys.getInt(rowsAffected);
                reservation.setId(generatedId);
                insertedReservation = reservation;
           }
        }
    } catch (SQLException e) {
        System.out.println("Error: couldn't insert data " + e.getMessage());
    }
    return insertedReservation;
}

    @Override
    public void updateReservation(Reservation reservation) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE reservations SET customer_id = ?, flight_id = ? WHERE id = ?");
            statement.setInt(1, reservation.getCustomer_id());
            statement.setInt(2, reservation.getFlight_id());
            statement.setInt(3, reservation.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error: could not insert data: "+e.getMessage());
        }
    }

   @Override
public boolean deleteReservation(int id) {
    try {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM reservations WHERE id = ?");
        statement.setInt(1, id);
        int rowsAffected = statement.executeUpdate();

        if (rowsAffected > 0) {
            return true; 
        } else {
            return false; 
        }
    } catch (SQLException e) {
        System.out.println("Error: could not delete reservation: " + e.getMessage());
        return false;
    }
}

//    public static void main(String[] args) {
//        DbManager db = new DbManager();
//        
//        ReservationDAO res = new ReservationDAOImpl(db.getConnection());
       // Reservation reservation = new Reservation(1,1,1,"g","d","d","d");
//        if(res.insertReservation(reservation))
//        {
//            System.out.println("Succesfully inserted");
//        }
//        else
//        {
//          System.out.println("Failed");
//        }
        //-------------------------------------------------
       // System.out.println((Reservation)res.getReservationById(24));
//        System.out.println(res.getAllReservations());
//       
//    }
}

