<%@page import="za.co.mie.dao.impl.FlightDAOImpl"%>
<%@page import="za.co.mie.dao.FlightDAO"%>
<%@page import="za.co.mie.manager.DbManager"%>
<%@page import="java.util.ArrayList"%>
<%@page import="za.co.mie.model.Flight"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Flight Reservation</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                margin: 0;
                padding: 0;
                background-image: url('images/pexels-oleksandr-p-1004584.jpg');
                background-size: cover;
                background-position: center; 
                background-size: cover;
                background-position: center;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
            }


            .container {
                text-align: center;
                background-color: transparent;
                padding: 40px;
                border-radius: 10px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            }

            h1 {
                color: #007bff;
                margin-bottom: 20px;
            }

            p {
                font-size: 18px;
                margin-bottom: 20px;
            }

            .btn {
                text-decoration: none;
                color: #fff;
                background-color: #007bff;
                padding: 10px 20px;
                border-radius: 5px;
                transition: background-color 0.3s ease;
                display: inline-block;
                margin: 0 10px;
            }

            .btn:hover {
                background-color: #0056b3;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h1>Welcome to Blue Skies Airline</h1>
            <p>Explore our flights and book your next adventure!</p>
            <p>Please Click Register if you are a new Customer</p>

            Click Login if you are an existing customer. Thanks!</p>
        <a href="http://localhost:8080/FlightBookingSystem/registerCustomer" class="btn">Register</a>
        <a href="http://localhost:8080/FlightBookingSystem/NewCustomer" class="btn">Login</a>
    </div>
</body>
</html>
