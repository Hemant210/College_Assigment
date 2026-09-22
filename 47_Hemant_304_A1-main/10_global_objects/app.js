console.log('Current directory (__dirname):', __dirname);
console.log('Current file (__filename):', __filename);
console.log('Node version:', process.version);
console.log('Platform:', process.platform);

console.log('\nCommand line arguments:');
const args = process.argv.slice(2);
args.forEach((arg, index) => console.log(`Argument ${index + 1}: ${arg}`));

setTimeout(() => console.log('setTimeout global function executed.'), 1000);
