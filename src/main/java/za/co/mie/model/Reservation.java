
package za.co.mie.model;


public class Reservation {
    private int id;
    private int customer_id;
    private int flight_id;
    private String seatGrade;
    private String reservationTime;
    private String paymentStatus;
    private String ticketSerialNumber;
    private boolean frequentFlyer;

    public Reservation(int id, int customer_id, int flight_id, String seatGrade, String reservationTime, String paymentStatus, String ticketSerialNumber) {
        this.id = id;
        this.customer_id = customer_id;
        this.flight_id = flight_id;
        this.seatGrade = seatGrade;
        this.reservationTime = reservationTime;
        this.paymentStatus = paymentStatus;
        this.ticketSerialNumber = ticketSerialNumber;
    }

      public Reservation(String name,String address, int flight_id, String seatGrade) {
        this.flight_id = flight_id;
        this.seatGrade = seatGrade;
     
    }

    public Reservation(int flight_id, String seatGrade) {
        this.flight_id = flight_id;
        this.seatGrade = seatGrade;
    }

      
    public Reservation() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getFlight_id() {
        return flight_id;
    }

    public void setFlight_id(int flight_id) {
        this.flight_id = flight_id;
    }

    public String getSeatGrade() {
        return seatGrade;
    }

    public void setSeatGrade(String seatGrade) {
        this.seatGrade = seatGrade;
    }

    public String getReservationTime() {
        return reservationTime;
    }

    public void setReservationTime(String reservationTime) {
        this.reservationTime = reservationTime;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getTicketSerialNumber() {
        return ticketSerialNumber;
    }

    public void setTicketSerialNumber(String ticketSerialNumber) {
        this.ticketSerialNumber = ticketSerialNumber;
    }

    public void setFrequentFlyer(boolean frequentFlyer) {
        this.frequentFlyer=frequentFlyer;
    }

    @Override
    public String toString() {
        return "Reservation{" + "id=" + id + ", customer_id=" + customer_id + ", flight_id=" + flight_id + ", seatGrade=" + seatGrade + ", reservationTime=" + reservationTime + ", paymentStatus=" + paymentStatus + ", ticketSerialNumber=" + ticketSerialNumber + ", frequentFlyer=" + frequentFlyer + '}';
    }

    
    
    
}
