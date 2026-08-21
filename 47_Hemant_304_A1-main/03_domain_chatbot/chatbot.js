function getReply(message) {
  const text = message.toLowerCase();
  if (text.includes('assignment')) return 'Assignments must be submitted before the due date.';
  if (text.includes('exam')) return 'Check the examination timetable and prepare unit-wise.';
  if (text.includes('attendance')) return 'Maintain attendance according to institute rules.';
  if (text.includes('hello') || text.includes('hi')) return 'Hello! I am your Education Chatbot.';
  if (text.includes('bye')) return 'Goodbye! All the best.';
  return 'Sorry, I only answer education-related questions.';
}
module.exports = { getReply };
