<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Reservation Confirmation</title>
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
                flex-direction: column;
                color: #333;
            }

            h1 {
                font-size: 2em;
                margin-bottom: 10px;
                color: green;
            }

            p {
                font-size: 1.2em;
                margin-bottom: 20px;
                font-size: 30px;
            }

            .ticket-serial {
                font-weight: bold;
                font-size: 1.5em;
                background-color: #f1f1f1;
                padding: 10px;
                border-radius: 5px;
                text-align: center;
                width: 300px;
                margin: 20px auto;
            }
        </style>
    </head>
    <body>
        <h1>Reservation Confirmed!</h1>
        <p>Your reservation has been confirmed. Thank you for choosing our services!</p>
        <div class="ticket-serial">
            <p>${congratulationsMessage}</p>
            <p>${status}</p>
            <p>${customerid}</p>
            <p>${message2}</p>
            <p>${message3}</p>
        </div>
    </body>

</html>
