const path = require('path');
const manager = require(path.normalize(__dirname + '/bot/manager'));

console.log(manager.getTitleString());

if(!manager.isCommandLineMode()) {
  return require(manager.getSpecificPackage('server').http);
}

const config = manager.getConfig()
console.log(config);
