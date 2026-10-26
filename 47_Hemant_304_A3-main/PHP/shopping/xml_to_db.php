<?php
$conn = mysqli_connect("localhost", "root", "root", "college");

$xml = simplexml_load_file("students.xml");

foreach ($xml->student as $s) {
  $name = (string)$s->name;
  $age = (int)$s->age;
  $city = (string)$s->city;

  $q = mysqli_prepare(
    $conn,
    "INSERT INTO students(name,age,city) VALUES(?,?,?)"
  );

  mysqli_stmt_bind_param($q, "sis", $name, $age, $city);
  mysqli_stmt_execute($q);
}

echo "XML data stored in MySQL";
