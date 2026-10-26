const express = require("express");
const app = express();

app.get("/students", (req, res) => {
  res.json([
    { id: 1, name: "Rahul" },
    { id: 2, name: "Priya" }
  ]);
});

app.listen(3000, () => {
  console.log("Server running on port 3000");
});