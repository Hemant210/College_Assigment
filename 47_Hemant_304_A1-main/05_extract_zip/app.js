const extract = require('extract-zip');
const path = require('path');

async function main() {
  try {
    await extract(path.join(__dirname, 'sample.zip'), { dir: path.join(__dirname, 'extracted') });
    console.log('ZIP extracted successfully.');
  } catch (err) {
    console.error('Extraction failed:', err.message);
  }
}
main();
