```php
<?php
// Node.js API URL
$apiUrl = "http://localhost:3000/api/students";

$message = "";


/* =========================
   POST - Send Student
   ========================= */

if ($_SERVER["REQUEST_METHOD"] == "POST") {

    $name = $_POST["name"];
    $email = $_POST["email"];
    $password = $_POST["password"];

    // Student data
    $data = [
        "name" => $name,
        "email" => $email,
        "password" => $password
    ];

    // Convert data to JSON
    $jsonData = json_encode($data);

    // Send data to Node.js
    $ch = curl_init($apiUrl);

    curl_setopt($ch, CURLOPT_POST, true);
    curl_setopt($ch, CURLOPT_POSTFIELDS, $jsonData);
    curl_setopt($ch, CURLOPT_HTTPHEADER, [
        "Content-Type: application/json"
    ]);
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);

    $response = curl_exec($ch);

    // Check error
    if ($response === false) {
        $message = "Error: " . curl_error($ch);
    } else {

        $result = json_decode($response, true);

        $message = $result["message"] ?? "Student sent successfully";
    }

    curl_close($ch);
}


/* =========================
   GET - Get Students
   ========================= */

$ch = curl_init($apiUrl);

curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);

$response = curl_exec($ch);

$students = [];

if ($response !== false) {

    $result = json_decode($response, true);

    $students = $result["data"] ?? [];
}

curl_close($ch);

?>


<!DOCTYPE html>
<html>

<head>

    <title>PHP Client</title>

    <style>

        body {
            font-family: Arial;
            margin: 40px;
        }

        input {
            padding: 8px;
            margin: 5px;
        }

        button {
            padding: 8px 15px;
        }

        table {
            border-collapse: collapse;
            margin-top: 20px;
            width: 60%;
        }

        th, td {
            border: 1px solid black;
            padding: 8px;
        }

        th {
            background-color: #eee;
        }

    </style>

</head>


<body>


<h2>Send Student to Node.js</h2>


<form method="POST">

    <input
        type="text"
        name="name"
        placeholder="Name"
        required
    >

    <input
        type="email"
        name="email"
        placeholder="Email"
        required
    >

    <input
        type="password"
        name="password"
        placeholder="Password"
        required
    >

    <button type="submit">
        Send Student
    </button>

</form>


<?php

if ($message != "") {

    echo "<p><b>API Response:</b> "
         . htmlspecialchars($message)
         . "</p>";
}

?>


<h2>Students from Node.js</h2>


<table>

    <tr>
        <th>No.</th>
        <th>Name</th>
        <th>Email</th>
    </tr>


    <?php

    if (empty($students)) {

        echo "<tr>";
        echo "<td colspan='3'>No students found</td>";
        echo "</tr>";

    } else {

        foreach ($students as $index => $student) {

            echo "<tr>";

            echo "<td>" . ($index + 1) . "</td>";

            echo "<td>"
                . htmlspecialchars($student["name"])
                . "</td>";

            echo "<td>"
                . htmlspecialchars($student["email"])
                . "</td>";

            echo "</tr>";
        }
    }

    ?>

</table>


</body>

</html>
```
