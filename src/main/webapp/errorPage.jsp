
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Error Page</title>
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
                flex-direction: column;
                color: #333;
            }

            h1 {
                font-size: 2em;
                margin-bottom: 10px;
            }

            p {
                font-size: 1.2em;
                text-align: center;
            }

            /* You can add more styling as needed */
        </style>

    </head>
    <body>
        <h1>Oops! Something went wrong.</h1>
        <p>We apologize for the inconvenience. Please try again later.</p>
        <p><%= request.getAttribute("errorMessage")%></p>
    </body>
</html>

