const fs = require('fs');

fs.writeFileSync('demo.txt', 'Hello Node.js File System');
console.log('1. File created and written.');

fs.appendFileSync('demo.txt', '\nThis line is appended.');
console.log('2. Data appended.');

const data = fs.readFileSync('demo.txt', 'utf8');
console.log('3. File content:\n' + data);

fs.copyFileSync('demo.txt', 'copy.txt');
console.log('4. File copied.');

fs.renameSync('copy.txt', 'renamed.txt');
console.log('5. File renamed.');

console.log('6. Files:', fs.readdirSync(__dirname));
console.log('7. demo.txt exists:', fs.existsSync('demo.txt'));
