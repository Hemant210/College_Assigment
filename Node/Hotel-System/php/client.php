<?php

$url = "http://localhost:3000/api/guests";

//POST
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $data = [
        "name" => $_POST['name'],
        "email" => $_POST['email'],
        "password" => $_POST['password']
    ];

    $ch = curl_init($url);

    curl_setopt($ch, CURLOPT_POST, true);


    curl_setopt($ch, CURLOPT_HTTPHEADER, ['Content-Type: application/json']);

    curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($data));

    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);

    $response = curl_exec($ch);
    curl_close($ch);

    $result = json_decode($response, true);

    echo "<p>";
    echo $result['message'];
    echo "</p>";
}

//GET
$ch = curl_init($url);

curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
$response = curl_exec($ch);
curl_close($ch);

$result = json_decode($response, true);

$guests = $result['data'] ?? [];
?>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register</title>
</head>

<body>
    <h2>Register</h2>
    <form method="post">
        <input type="text" name="name" placeholder="name" />
        <input type="text" name="email" placeholder="email" />
        <input type="password" name="password" placeholder="password" />

        <button type="submit">Send data to Node</button>

        <table>
            <tr>
                <th>
                    Name
                </th>
                <th>email </th>

            </tr>
            <?php foreach ($guests as $guest) { ?>
                <tr>
                    <td> <?php echo $guest['name'] ?></td>

                    <td> <?php echo $guest['email'] ?></td>

                </tr>
            <?php } ?>
        </table>
    </form>
</body>

</html>