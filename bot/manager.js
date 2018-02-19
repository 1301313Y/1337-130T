var program = require('commander');
var fs = require('fs');

var _config = false;
var _package = false

var manager = {
  getConfig: function() {
    // cache
    if(_config)
      return _config;
    if(!program.config)
        manager.stop('Please specify a configuration file.');
    var path = manager.packages().root + program.config;
    if(!fs.existsSync(path))
        manager.stop('Cannot find the specified configuration file!');
    _config =  _config = require(path);
    return _config;
  },
  packages: function() {
    var ROOT = __dirname + '/../';
    return {
      root: ROOT,
      bot: ROOT + 'bot/'
    }
  },
  stop: function(message) {
    console.warn(message);
    console.warn("Shutting Down!");
    process.exit(1);
  },
  printVersion: function() {
    return  `Version: v${manager.getVersion()}`
    + `\nNodeJS version: ${process.version}`;
  },
  getVersion: function() {
    return manager.getPackage().version;
  },
  getPackage: function() {
    if(_package)
      return _package;
    _package = JSON.parse( fs.readFileSync(__dirname + '/../package.json', 'utf8') );
    return _package;
  }
}

// initialize commander for options
program
  .version(manager.printVersion())
  .option('-c, --config <file>', 'Configuration file to use.' +
                                 ' (Default: \'config.js\')', 'config.js')
  .parse(process.argv);

module.exports = manager;
