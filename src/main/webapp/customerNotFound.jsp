<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Customer Not Found</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                margin: 0;
                padding: 0;
                background-image: url('images/pexels-oleksandr-p-1004584.jpg');
                background-size: cover;
                background-position: center;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
            }

            .container {
                text-align: center;
                background-color: #fff;
                padding: 40px;
                border-radius: 8px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            }

            h1 {
                color: #333;
            }

            p {
                color: #666;
                margin-bottom: 20px;
            }

            a {
                display: inline-block;
                padding: 10px 20px;
                background-color: #007bff;
                color: #fff;
                text-decoration: none;
                border-radius: 4px;
                transition: background-color 0.3s;
            }

            a:hover {
                background-color: #0056b3;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h1>Customer Not Found</h1>
            <p>The customer ID you entered was not found in our records.</p>
            <p><a href="http://localhost:8080/FlightBookingSystem/NewCustomer">Try Again</a></p>
        </div>
    </body>
</html>
