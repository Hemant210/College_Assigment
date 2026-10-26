<?php
$dir = "Admin/uploads";

$files = scandir($dir);

echo "<h2>Files in Directory</h2>";

foreach ($files as $file) {
  if ($file != "." && $file != "..") {
    echo $file . "<br>";
  }
}
