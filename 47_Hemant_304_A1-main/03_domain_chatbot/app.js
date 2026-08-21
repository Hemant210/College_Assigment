const readline = require('readline');
const { getReply } = require('./chatbot');

const rl = readline.createInterface({ input: process.stdin, output: process.stdout });
console.log('Education Chatbot started. Type "bye" to exit.');

function ask() {
  rl.question('You: ', (message) => {
    console.log('Bot:', getReply(message));
    if (message.toLowerCase().includes('bye')) return rl.close();
    ask();
  });
}
ask();
