const http = require('http');

const server = http.createServer(async (req, res) => {
  if (req.url === '/google') {
    try {
      const response = await fetch('https://www.google.com');
      const data = await response.text();
      res.writeHead(200, {'Content-Type':'text/html'});
      res.end(data);
    } catch (err) {
      res.writeHead(500, {'Content-Type':'text/plain'});
      res.end('Error fetching Google: ' + err.message);
    }
  } else {
    res.writeHead(200, {'Content-Type':'text/plain'});
    res.end('Open /google route');
  }
});
server.listen(3000, () => console.log('Open http://localhost:3000/google'));
