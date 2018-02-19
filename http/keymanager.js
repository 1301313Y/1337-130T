/*
  The MIT License (MIT)

  Copyright (c) 2014 Mike van Rossum mike@mvr.me

  Permission is hereby granted, free of charge, to any person obtaining a
  copy of this software and associated documentation files (the "Software"),
  to deal in the Software without restriction, including without
  limitation the rights to use, copy, modify, merge, publish, distribute,
  sublicense, and/or sell copies of the Software, and to permit persons
  to whom the Software is furnished to do so, subject to the following
  conditions:

  The above copyright notice and this permission notice shall be included in all
  copies or substantial portions of the Software.

  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
  THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
  FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
  DEALINGS IN THE SOFTWARE.

*/
const fs = require('fs');
const _ = require('lodash');
const cache = require('./collections/cache');
const broadcast = cache.get('broadcast');

const apiKeysFile = __dirname + '/../SECRET-api-keys.json';

// on init:
const noApiKeysFile = !fs.existsSync(apiKeysFile);

if(noApiKeysFile)
  fs.writeFileSync(
    apiKeysFile,
    JSON.stringify({})
  );

const apiKeys = JSON.parse( fs.readFileSync(apiKeysFile, 'utf8') );

module.exports = {
  get: () => _.keys(apiKeys),

  // note: overwrites if exists
  add: (exchange, props) => {
    apiKeys[exchange] = props;
    fs.writeFileSync(apiKeysFile, JSON.stringify(apiKeys));

    broadcast({
      type: 'apiKeys',
      exchanges: _.keys(apiKeys)
    });
  },
  remove: exchange => {
    if(!apiKeys[exchange])
      return;

    delete apiKeys[exchange];
    fs.writeFileSync(apiKeysFile, JSON.stringify(apiKeys));

    broadcast({
      type: 'apiKeys',
      exchanges: _.keys(apiKeys)
    });
  },

  // retrieve api keys
  // this cannot touch the frontend for security reaons.
  _getApiKeyPair: key => apiKeys[key]
}
