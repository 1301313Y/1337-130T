const path = require('path');

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
    var path = manager.getPackages().root + program.config;
    if(!fs.existsSync(path))
        manager.stop('Cannot find the specified configuration file!');
    _config =  _config = require(path);
    return _config;
  },
  getPackages: function() {
      var ROOT = __dirname + '/../';
    return {
      root: path.normalize(ROOT),
      bot:  path.normalize(ROOT + 'bot/'),
      http: path.normalize(ROOT + 'http/')
    }
  },
  getSpecificPackage: function(location) {
    var ROOT = __dirname + '/../';
    return {
      root: path.normalize(ROOT + location),
      bot:  path.normalize(ROOT + 'bot/' + location),
      http: path.normalize(ROOT + 'http/' + location)
    }
  },
  stop: function(message) {
    console.warn(message);
    console.warn("Shutting Down!");
    process.exit(1);
  },
  getVersionString: function() {
    return  `Version: v${manager.getVersion()}`
    + `\nNodeJS version: ${process.version}`;
  },
  getTitleString: function() {
    return  `Welcome to 1337 130T v${manager.getVersion()}`
    + `\nAuthor: @${manager.getAuthor()}`
    + `\nRunning NodeJS Version: ${process.version}`;
  },
  getVersion: function() {
    return manager.getPackage().version;
  },
  getAuthor: function() {
    return manager.getPackage().author;
  },
  getPackage: function() {
    if(_package)
      return _package;
    _package = JSON.parse( fs.readFileSync(__dirname + '/../package.json', 'utf8') );
    return _package;
  },
  isCommandLineMode: function() {
    return program['cmd'];
  }
}
//Add proper string.format
String.prototype.format = function() {
    var formatted = this;
    for (var i = 0; i < arguments.length; i++) {
        var regexp = new RegExp('\\{'+i+'\\}', 'gi');
        formatted = formatted.replace(regexp, arguments[i]);
    }
    return formatted;
};

// initialize commander for options
program
  .version(manager.getVersionString())
  .option('-c, --config <file>', 'Configuration file to use.' +
                                 ' (Default: \'config.js\')', 'config.js')
  .option('-cmd, --command', 'Use Command Line Mode (No UI) (Defalt: false)')
  .parse(process.argv);

module.exports = manager;
