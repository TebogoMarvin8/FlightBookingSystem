<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Reservation Status</title>
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

            #reservationStatus {
                text-align: center;
                padding: 20px;
                border-radius: 5px;
                box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
                background-color: #fff;
            }

            h2 {
                color: #007bff;
                margin-bottom: 10px;
            }

            p {
                font-size: 1.2em;
                margin-bottom: 20px;
            }

            /* Styles for different status messages */
            .success-message {
                color: #28a745;
            }

            .failure-message {
                color: #dc3545;
            }

            .not-found-message {
                color: #ff8800;
            }

            /* You can add more styles based on your requirements */
        </style>
    </head>
    <body>
        <div id="reservationStatus">
            <h2>Reservation Status</h2>
            <p>Status: ${status}</p>
            <p>${congratulationsMessage}</p>
            <p>${customerid}</p>
            <p>PLEASE KEEP IT SAFE FOR NEXT TIME</p>

            <%
                String status = (String) request.getAttribute("status");
                if (status != null) {
                    if (status.equals("success")) {
            %>
            <p class="success-message">Reservation successful!</p>
            <%
            } else if (status.equals("failure")) {
            %>
            <p class="failure-message">Reservation failed. No seats available in the requested class for the selected flight.</p>
            <%
            } else if (status.equals("notFound")) {
            %>
            <p class="not-found-message">Flight not found.</p>
            <%
            } else {
            %>
            <p>Failed to reserve seat. Please try again.</p>
            <%
                }
            } else {
            %>
            <p>Reservation status not available.</p>
            <%
                }
            %>
        </div>
    </body>
</html>
