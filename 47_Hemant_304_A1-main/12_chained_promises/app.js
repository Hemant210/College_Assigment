function task1() {
  return new Promise(resolve => setTimeout(() => { console.log('Task 1 completed'); resolve(10); }, 500));
}
function task2(value) {
  return new Promise(resolve => setTimeout(() => { console.log('Task 2 completed'); resolve(value * 2); }, 500));
}
function task3(value) {
  return new Promise(resolve => setTimeout(() => { console.log('Task 3 completed'); resolve(value + 5); }, 500));
}

task1()
  .then(result => task2(result))
  .then(result => task3(result))
  .then(finalResult => console.log('Final Result:', finalResult))
  .catch(err => console.error(err));
