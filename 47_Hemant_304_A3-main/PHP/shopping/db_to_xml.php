<?php
$conn = mysqli_connect("localhost", "root", "root", "college");

$q = mysqli_query($conn, "SELECT * FROM students");

$xml = new SimpleXMLElement("<students/>");

while ($r = mysqli_fetch_assoc($q)) {
  $s = $xml->addChild("student");
  $s->addChild("name", htmlspecialchars($r['name']));
  $s->addChild("age", $r['age']);
  $s->addChild("city", htmlspecialchars($r['city']));
}

$xml->asXML("students.xml");

echo "MySQL data stored in students.xml";
