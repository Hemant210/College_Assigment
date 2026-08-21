const fs = require('fs');
const util = require('util');

const unlinkPromise = util.promisify(fs.unlink);

async function deleteFile() {
  try {
    await unlinkPromise('delete-me.txt');
    console.log('File deleted successfully.');
  } catch (err) {
    console.error('Error:', err.message);
  }
}
deleteFile();
