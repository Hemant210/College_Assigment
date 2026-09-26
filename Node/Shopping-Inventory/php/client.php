<?php

// 1. API
$api = "http://localhost:3000/api/products";


// 2. POST
               // curl_init
               //    ↓
               // POST
               //    ↓
               // JSON
               //    ↓
               // json_encode
               //    ↓
               // exec
               //    ↓
               // close
if ($_SERVER['REQUEST_METHOD'] == 'POST') {

    $data = [
        "name" => $_POST['name'],
        "price" => $_POST['price'],
        "quantity" => $_POST['quantity']
    ];

    $ch = curl_init($api);

    curl_setopt($ch, CURLOPT_POST, true);

    curl_setopt($ch, CURLOPT_HTTPHEADER, [
        'Content-Type: application/json'
    ]);

    curl_setopt($ch, CURLOPT_POSTFIELDS,
        json_encode($data)
    );

    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);

    curl_exec($ch);

    curl_close($ch);
}


// 3. GET
$ch = curl_init($api);

curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);

$response = curl_exec($ch);

curl_close($ch);


// 4. DECODE
$result = json_decode($response, true);

$products = $result['data'] ?? [];


// 5. CALCULATE
$total = 0;

foreach ($products as $p) {

    $total += $p['price'] * $p['quantity'];

}

?>

<!DOCTYPE html>
<html>

<body>

<!-- 6. FORM -->

<form method="POST">

    <input name="name">
    <input name="price">
    <input name="quantity">

    <button type="submit">
        Add
    </button>

</form>


<!-- 7. DISPLAY -->

<table border="1">

<tr>
    <th>Name</th>
    <th>Price</th>
    <th>Quantity</th>
</tr>

<?php foreach ($products as $p) { ?>

<tr>

    <td><?= $p['name'] ?></td>
    <td><?= $p['price'] ?></td>
    <td><?= $p['quantity'] ?></td>

</tr>

<?php } ?>

</table>


<!-- 8. TOTAL -->

<h3>Total: <?= $total ?></h3>

</body>
</html>