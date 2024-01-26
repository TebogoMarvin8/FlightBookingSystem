<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Reservation Error</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-image: url('images/pexels-oleksandr-p-1004584.jpg');
                background-size: cover;
                background-position: center;
                margin: 0;
                padding: 0;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
            }

            .container {
                text-align: center;
                background-color: #fff;
                padding: 20px;
                border-radius: 5px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            }

            h1 {
                color: red;
            }

            p {
                font-size: 18px;
                margin: 20px 0;
            }

            a {
                text-decoration: none;
                color: #fff;
                background-color: #007bff;
                padding: 10px 20px;
                border-radius: 5px;
                transition: background-color 0.3s ease;
            }

            a:hover {
                background-color: #0056b3;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h1>Reservation Error</h1>
            <p><%= request.getAttribute("errorMessage")%></p>
            <p><a href="http://localhost:8080/FlightBookingSystem/NewCustomer">Go Back</a></p>
        </div>
    </body>
</html>
