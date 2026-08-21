const express = require('express');
const path = require('path');
const app = express();

app.use(express.static(path.join(__dirname, 'public')));

app.get('/gethello', (req, res) => {
  res.send('Hello NodeJS!!');
});

app.listen(3000, () => console.log('Server running at http://localhost:3000'));
