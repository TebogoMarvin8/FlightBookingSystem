<%-- 
    Document   : unavailableFlight
    Created on : Nov 29, 2023, 1:38:28 PM
    Author     : Train
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Unavailable Flight</title>
        <style>

            body {
                font-family: Arial, sans-serif;
                background-image: url('images/pexels-oleksandr-p-1004584.jpg');
                background-size: cover;
                background-position: center; 
                margin: 0;
                padding: 0;
            }

            h1 {
                text-align: center;
                color: red;
            }

            p {
                text-align: center;
                color: #555;
                line-height: 1.6;
            }

            a {
                display: block;
                text-align: center;
                margin-top: 20px;
                text-decoration: none;
                color: #3498db;
                transition: color 0.3s ease;
            }

            a:hover {
                color: #2980b9;
            }
        </style>

    </head>
    <body>
        <h1>Sorry, the requested flight is unavailable.</h1>
        <p>The requested flight is currently unavailable. Please choose another flight or try again later.</p>
    </body>
</html>

