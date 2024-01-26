<%@page import="za.co.mie.dao.impl.FlightDAOImpl"%>
<%@page import="za.co.mie.dao.FlightDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="za.co.mie.dao.impl.CityDAOImpl"%>
<%@page import="za.co.mie.dao.CityDAO"%>
<%@page import="za.co.mie.manager.DbManager"%>
<%@page import="za.co.mie.model.Flight"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="za.co.mie.model.Customer" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Process Reservation</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                margin: 20px;
                background-image: url('images/pexels-oleksandr-p-1004584.jpg');
                background-size: cover;
                background-position: center;
            }

            h1 {
                color: #007bff;
            }

            form {
                max-width: 400px;
                margin: 0 auto;
                padding: 20px;
                background-color: transparent;
                border-radius: 5px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            }

            label {
                display: block;
                margin-bottom: 8px;
            }

            input[type="text"],
            select {
                width: 100%;
                padding: 8px;
                margin-bottom: 10px;
                border: 1px solid #ccc;
                border-radius: 4px;
                box-sizing: border-box;
            }

            input[type="submit"] {
                background-color: #007bff;
                color: #fff;
                padding: 10px 20px;
                border: none;
                border-radius: 4px;
                cursor: pointer;
            }

            input[type="submit"]:hover {
                background-color: #0056b3;
            }

            p {
                color: green;
                font-style: italic;
            }

        </style>
    </head>
    <body>

        <%
            DbManager db = DbManager.getInstance();
            CityDAO cityDAO = new CityDAOImpl(db.getConnection());

            String date = request.getParameter("date");
            List<Flight> allFlights = null;

            if (date != null && !date.isEmpty()) {
                FlightDAO flightDAO = new FlightDAOImpl(db.getConnection());

                allFlights = flightDAO.getFlightsByDate(date);
            }
        %>


        <h1>Process Reservation</h1>
        <%
            Customer registeredCustomer = (Customer) session.getAttribute("registeredCustomer");
            if (registeredCustomer != null) {
        %>

        <p>Your Customer ID: <%= registeredCustomer.getId()%></p>
        <p>PLEASE KEEP IT SAFE FOR LOGIN NEXT TIME</p>
        <p> You have Successfully registered! </p>

        <hr>

        <form method="post" action="processReservation">
            <label for="name">Name:</label>
            <input type="text" id="name" name="name" value="<%= registeredCustomer.getName()%>" required><br><br>

            <label for="address">Address:</label>
            <input type="text" id="address" name="address" value="<%= registeredCustomer.getAddress()%>" required><br><br>

            <label for="creditCard">Credit Card:</label>
            <input type="text" id="creditCard" name="creditCard" value="<%= registeredCustomer.getCreditCardNumber()%>" required><br><br>

            <label for="seatGrade">Select Seat Grade:</label>
            <select id="seatGrade" name="seatGrade">
                <option value="First Class">First Class</option>
                <option value="Business Class">Business Class</option>
                <option value="Economy Class">Economy Class</option>
            </select><br><br>

            <!-- Date selection -->
            <label for="date">Select Date: </label>
            <input type="date" id="date" name="date" required><br><br>

            <!-- Flight selection based on the retrieved list -->
            <select id="flightId" name="flightId" required>
                <option value="" selected>No flights available</option>
            </select>

            <input type="submit" value="Confirm Reservation">
        </form>

        <script>
            document.getElementById('date').addEventListener('change', function () {
                var selectedDate = this.value;
                var flightDropdown = document.getElementById('flightId');

                // Clear existing options
                flightDropdown.innerHTML = '';

                if (selectedDate === '') {
                    // If no date is selected, show default option
                    var option = document.createElement('option');
                    option.value = '';
                    option.textContent = 'No flights available for the selected date';
                    flightDropdown.appendChild(option);
                } else {
                    // AJAX request to fetch flights based on the selected date
                    var xhr = new XMLHttpRequest();
                    xhr.onreadystatechange = function () {
                        if (xhr.readyState === XMLHttpRequest.DONE) {
                            if (xhr.status === 200) {
                                var flights = JSON.parse(xhr.responseText);
                                if (flights.length > 0) {
                                    flights.forEach(function (flight) {
                                        var option = document.createElement('option');
                                        option.value = flight.id;
                                        option.textContent = 'Flight No: ' + flight.id + ' - ' + flight.departureCity + ' to ' + flight.destinationCity + ', Departure time: ' + flight.departureTime + ', Arrival time: ' + flight.arrivalTime;
                                        flightDropdown.appendChild(option);
                                    });
                                } else {
                                    var option = document.createElement('option');
                                    option.value = '';
                                    option.textContent = 'No flights available for the selected date';
                                    flightDropdown.appendChild(option);
                                }
                            } else {
                                console.error('Request failed: ' + xhr.status);
                            }
                        }
                    };

                    xhr.open('GET', 'getFlights?date=' + selectedDate, true);
                    xhr.send();
                }
            });
        </script>
        <%}else {
        %>
        <p>No registered customer found!</p>
        <%
            }
        %>
        <!-- Any additional content or script tags can go here -->
    </body>
</html>
