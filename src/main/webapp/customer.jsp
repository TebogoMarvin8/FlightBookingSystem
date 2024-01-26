<%@ page import="za.co.mie.dao.impl.CityDAOImpl" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="za.co.mie.model.Flight" %>
<%@ page import="java.util.List" %>
<%@ page import="za.co.mie.dao.CityDAO" %>
<%@ page import="za.co.mie.manager.DbManager" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>New Customer</title>

        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f4f4f4;
                margin: 0;
                padding: 20px;
                background-image: url('images/pexels-oleksandr-p-1004584.jpg');
                background-size: cover;
                background-position: center;
            }

            h2 {
                color: #333;
            }

            form {
                background-color: transparent;
                padding: 20px;
                border-radius: 8px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                margin-top: 20px;
                width: 400px;
                margin: 0 auto;
            }

            label {
                display: block;
                margin-bottom: 10px;
            }

            input[type="text"],
            select {
                width: 100%;
                padding: 10px;
                margin-bottom: 20px;
                border: 1px solid #ccc;
                border-radius: 4px;
                box-sizing: border-box;
            }

            input[type="submit"] {
                padding: 10px 20px;
                background-color: #007bff;
                color: #fff;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                transition: background-color 0.3s;
            }

            input[type="submit"]:hover {
                background-color: #0056b3;
            }
        </style>

    </head>
    <body>

        <%
            DbManager db = DbManager.getInstance();
            CityDAO cityDAO = new CityDAOImpl(db.getConnection());
            List<Flight> allFlights = (ArrayList<Flight>) request.getAttribute("flights");
        %>

        <h2>Welcome Back, Please Book A Seat</h2>

        <form method="post" action="NewCustomer">
            <label for="customerId">Enter Your Existing Customer ID:</label>
            <input type="text" id="customerId" name="customerId" required><br><br>

            <!-- Date selection -->
            <label for="date">Select Date (YYYY-MM-DD): </label>
            <input type="date" id="date" name="date" required><br><br>

            <!-- Flight selection based on the retrieved list -->
            <select id="flightId" name="flightId" required>
                <option value="" selected>No flights available</option>
            </select><br><br>

            <label for="seatGrade">Select Seat Grade:</label>
            <select id="seatGrade" name="seatGrade">
                <option value="First Class">First Class</option>
                <option value="Business Class">Business Class</option>
                <option value="Economy Class">Economy Class</option>
            </select><br><br>

            <label for="transactionType">Transaction Type:</label>
            <select id="transactionType" name="transactionType">
                <option value="reserveSeat">Reserve Seat</option>
                <option value="payWithMiles">Pay with Miles Balance</option>
            </select><br><br>

            <input type="submit" value="Proceed">
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

    </body>
</html>
