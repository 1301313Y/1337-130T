const CONFIG = require('./bot-app/config');
const PATH = require('path');
const WEBROOT = PATH.normalize(__dirname + '/');

var koa = require('koa');
var serve = require('koa-static');
var cors = require('koa-cors');
var bodyParser = require('koa-bodyparser');
var router = require('koa-router')();
var server = require('http').createServer();
var lodash = require('lodash');
var ws = require('ws');

const APP = koa();

var WebSocketServer = require('ws').Server;
const WSS = new WebSocketServer({ server: server });

const CACHE = require('./collections/cache');

CACHE.set('keymanager', require('./keymanager'));

APP
  .use(cors())
  .use(serve(WEBROOT + 'vue'))
  .use(bodyParser())
  .use(require('koa-logger')())
  .use(router.routes())
  .use(router.allowedMethods());
