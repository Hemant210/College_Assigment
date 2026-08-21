const http = require('http');
const fs = require('fs');
const path = require('path');

const server = http.createServer((req, res) => {
  const requestedPath = req.url === '/' ? '/index.html' : req.url;
  const filePath = path.join(__dirname, 'public', requestedPath);
  const ext = path.extname(filePath);
  const types = {'.html':'text/html', '.css':'text/css', '.js':'text/javascript'};

  fs.readFile(filePath, (err, data) => {
    if (err) {
      res.writeHead(404, {'Content-Type':'text/plain'});
      return res.end('404 - File Not Found');
    }
    res.writeHead(200, {'Content-Type': types[ext] || 'application/octet-stream'});
    res.end(data);
  });
});

server.listen(3000, () => console.log('Static server: http://localhost:3000'));
