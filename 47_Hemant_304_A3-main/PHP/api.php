<?php
header("Content-Type: application/json");

$data = [
  ["id" => 1, "name" => "Rahul"],
  ["id" => 2, "name" => "Priya"]
];

echo json_encode($data);
