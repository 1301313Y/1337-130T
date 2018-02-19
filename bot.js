console.log('Starting 1337 130T v1.0.0...')
console.log('Author: @1301313Y')
console.log('Warning! Use At Your Own Risk! You Can Lose Money!');

const path = require('path');
const manager = require(path.normalize(__dirname + '/bot/manager'));

const config = manager.getConfig()
console.log(config);
