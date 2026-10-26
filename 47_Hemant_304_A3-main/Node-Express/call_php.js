const express = require("express");
const app = express();

app.get("/students", async (req, res) => {
  try {
    const response = await fetch(
      "http://localhost/Assignment-3/PHP/api.php"
    );

    const data = await response.json();

    res.json(data);
  } catch (error) {
    res.status(500).json({ error: "PHP API failed" });
  }
});

app.listen(3001, () => {
  console.log("Express running on port 3001");
});