package za.co.mie.model;

import java.util.Date;





public class Flight {

    private int id;
    private int departureCityId;
    private int destinationCityId;
    private Date departureDate; // Include date for departure
    private Date arrivalDate;   // Include date for arrival
    private String departureTime;
    private String arrivalTime;
    private int firstClassSeats;
    private int businessClassSeats;
    private int economyClassSeats;

    // Constructor with date attributes
    public Flight(int id, int departureCityId, int destinationCityId, Date departureDate, Date arrivalDate, String departureTime, String arrivalTime, int firstClassSeats, int businessClassSeats, int economyClassSeats) {
        this.id = id;
        this.departureCityId = departureCityId;
        this.destinationCityId = destinationCityId;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.firstClassSeats = firstClassSeats;
        this.businessClassSeats = businessClassSeats;
        this.economyClassSeats = economyClassSeats;
    }

    // Getters and setters for new date attributes
    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public Flight() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDepartureCityId() {
        return departureCityId;
    }

    public void setDepartureCityId(int departureCityId) {
        this.departureCityId = departureCityId;
    }

    public int getDestinationCityId() {
        return destinationCityId;
    }

    public void setDestinationCityId(int destinationCityId) {
        this.destinationCityId = destinationCityId;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getFirstClassSeats() {
        return firstClassSeats;
    }

    public void setFirstClassSeats(int firstClassSeats) {
        this.firstClassSeats = firstClassSeats;
    }

    public int getBusinessClassSeats() {
        return businessClassSeats;
    }

    public void setBusinessClassSeats(int businessClassSeats) {
        this.businessClassSeats = businessClassSeats;
    }

    public int getEconomyClassSeats() {
        return economyClassSeats;
    }

    public void setEconomyClassSeats(int economyClassSeats) {
        this.economyClassSeats = economyClassSeats;
    }

    @Override
    public String toString() {
        return "Flight{" + "id=" + id + ", departureCityId=" + departureCityId + ", destinationCityId=" + destinationCityId + ", departureDate=" + departureDate + ", arrivalDate=" + arrivalDate + ", departureTime=" + departureTime + ", arrivalTime=" + arrivalTime + ", firstClassSeats=" + firstClassSeats + ", businessClassSeats=" + businessClassSeats + ", economyClassSeats=" + economyClassSeats + '}';
    }


}
