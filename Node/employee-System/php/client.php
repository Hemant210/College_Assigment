<?php

$url = "http://localhost:3000/api/employees";

$message = "";

// ================= POST =================

if ($_SERVER["REQUEST_METHOD"] == "POST") {

    $data = [

        "name" => $_POST["name"],

        "email" => $_POST["email"],

        "password" => $_POST["password"],

        "department" => $_POST["department"]

    ];

    $ch = curl_init($url);

    curl_setopt($ch, CURLOPT_POST, true);

    curl_setopt(
        $ch,
        CURLOPT_POSTFIELDS,
        json_encode($data)
    );

    curl_setopt(
        $ch,
        CURLOPT_HTTPHEADER,
        [
            "Content-Type: application/json"
        ]
    );

    curl_setopt(
        $ch,
        CURLOPT_RETURNTRANSFER,
        true
    );

    $response = curl_exec($ch);

    if ($response === false) {

        $message = curl_error($ch);

    } else {

        $result = json_decode($response, true);

        $message = $result["message"] ?? "Error";

    }

    curl_close($ch);
}


// ================= GET =================

$ch = curl_init($url);

curl_setopt(
    $ch,
    CURLOPT_RETURNTRANSFER,
    true
);

$response = curl_exec($ch);

if ($response === false) {

    $employees = [];

    $getError = curl_error($ch);

} else {

    $result = json_decode($response, true);

    $employees = $result["data"] ?? [];

}

curl_close($ch);

?>

<!DOCTYPE html>

<html>

<head>

    <title>PHP Client</title>

</head>

<body>

<h2>Send Employee to Node.js</h2>

<?php if ($message) { ?>

    <p>
        <?php echo htmlspecialchars($message); ?>
    </p>

<?php } ?>


<form method="POST">

    Name:

    <input
        type="text"
        name="name"
        required
    >

    <br><br>


    Email:

    <input
        type="email"
        name="email"
        required
    >

    <br><br>


    Password:

    <input
        type="password"
        name="password"
        required
    >

    <br><br>


    Department:

    <select
        name="department"
        required
    >

        <option value="HR">HR</option>

        <option value="IT">IT</option>

        <option value="Finance">Finance</option>

        <option value="Sales">Sales</option>

    </select>

    <br><br>


    <button type="submit">
        Send
    </button>

</form>


<h2>Employees</h2>


<table border="1">

<tr>

    <th>Name</th>

    <th>Email</th>

    <th>Department</th>

</tr>


<?php foreach ($employees as $emp) { ?>

<tr>

    <td>
        <?php
        echo htmlspecialchars(
            $emp["name"] ?? ""
        );
        ?>
    </td>


    <td>
        <?php
        echo htmlspecialchars(
            $emp["email"] ?? ""
        );
        ?>
    </td>


    <td>
        <?php
        echo htmlspecialchars(
            $emp["department"] ?? ""
        );
        ?>
    </td>

</tr>

<?php } ?>


</table>

</body>

</html>
