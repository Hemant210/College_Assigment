<?php

$api = "http://localhost:5000/api/doctors";


// GET DOCTORS
function getDoctors($api)
{
    $response = file_get_contents($api);

    $data = json_decode($response, true);

    return $data;
}


// ADD DOCTOR
function addDoctor($api, $name, $email, $password)
{
    $data = [
        "name" => $name,
        "email" => $email,
        "password" => $password
    ];

    $options = [
        "http" => [
            "method" => "POST",
            "header" => "Content-Type: application/json",
            "content" => json_encode($data)
        ]
    ];

    $context = stream_context_create($options);

    $response = file_get_contents($api, false, $context);

    return json_decode($response, true);
}


// FORM SUBMITTED
if ($_SERVER["REQUEST_METHOD"] == "POST") {

    $name = $_POST["name"];
    $email = $_POST["email"];
    $password = $_POST["password"];

    $result = addDoctor($api, $name, $email, $password);

    echo "<p>Doctor Added Successfully.</p>";
}


// GET ALL DOCTORS
$result = getDoctors($api);

?>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>Doctor Client</title>
</head>

<body>

    <h2>Add Doctor</h2>

    <form method="post">

        Name:
        <input type="text" name="name" required>
        <br><br>

        Email:
        <input type="email" name="email" required>
        <br><br>

        Password:
        <input type="password" name="password" required>
        <br><br>

        <button type="submit">Add Doctor</button>

    </form>


    <h2>Doctors List</h2>

    <?php

    if (isset($result["data"])) {

        foreach ($result["data"] as $doctor) {

            echo "Name: " . $doctor["name"] . "<br>";
            echo "Email: " . $doctor["email"] . "<br>";
            echo "Password: " . $doctor["password"] . "<br>";

            echo "<hr>";
        }
    }

    ?>

</body>

</html>