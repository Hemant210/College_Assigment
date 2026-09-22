const fs = require('fs');
const archiver = require('archiver');

const output = fs.createWriteStream('output.zip');
const archive = archiver('zip', { zlib: { level: 9 } });

output.on('close', () => console.log(`ZIP created. ${archive.pointer()} bytes written.`));
archive.on('error', err => { throw err; });

archive.pipe(output);
archive.directory('myfolder/', false);
archive.finalize();
