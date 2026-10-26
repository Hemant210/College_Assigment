<?php
$url = "http://localhost:3000/students";

$data = file_get_contents($url);

$students = json_decode($data, true);

echo "<h2>Student List</h2>";

foreach ($students as $s) {
  echo $s['id'] . " - " . $s['name'] . "<br>";
}
