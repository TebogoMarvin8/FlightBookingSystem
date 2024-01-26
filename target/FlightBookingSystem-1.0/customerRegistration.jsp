<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Customer Registration</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f4f4f4;
                margin: 0;
                padding: 0;
                background-image: url('images/pexels-oleksandr-p-1004584.jpg');
                background-size: cover;
                background-position: center;
            }

            h1 {
                text-align: center;
                color: #333;
            }

            form {
                max-width: 400px;
                margin: 20px auto;
                background: transparent;
                padding: 20px;
                border-radius: 8px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            }

            label {
                display: block;
                margin-bottom: 8px;
            }

            input[type="text"] {
                width: calc(100% - 12px);
                padding: 8px;
                margin-bottom: 10px;
                border: 1px solid #ccc;
                border-radius: 4px;
            }

            input[type="submit"] {
                width: 100%;
                padding: 10px;
                border: none;
                border-radius: 4px;
                background-color: #3498db;
                color: #fff;
                cursor: pointer;
                transition: background-color 0.3s ease;
            }

            input[type="submit"]:hover {
                background-color: #2980b9;
            }
        </style>
    <script>
        function validateForm() {
            var name = document.forms["registrationForm"]["name"].value;
            var address = document.forms["registrationForm"]["address"].value;
            var creditCard = document.forms["registrationForm"]["creditCard"].value;

            var isValidName = /^[a-zA-Z ]+$/.test(name);
            var isValidEmail = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/.test(address);
            var isValidCreditCard = /^\d{16}$/.test(creditCard);

            if (!isValidName) {
                alert("Please enter a valid name with letters only.");
                return false;
            }
            if (!isValidEmail) {
                alert("Please enter a valid email address.");
                return false;
            }
            if (!isValidCreditCard) {
                alert("Please enter a valid Credit Card number.");
                return false;
            }
            return true;
        }
    </script>
</head>
<body>
    <h1>Customer Registration</h1>
    <form name="registrationForm" action="registerCustomer" method="post" onsubmit="return validateForm()">

        <label for="name">Name:</label>
        <input type="text" name="name" placeholder="Name" required><br><br>

        <label for="address">Email Address:</label>
        <input type="text" name="address" placeholder="Email Address" required><br><br>

        <label for="creditCard">Credit Card Number:</label>
        <input type="text" name="creditCard" placeholder="Credit Card" required><br><br>

        <input type="submit" name="action" value="cancel"><br><br>
        <input type="submit" name="action" value="proceed">
    </form>
</body>

</html>
