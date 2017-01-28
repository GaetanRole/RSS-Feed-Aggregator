/* NPM modules */
var express = require('express');
var bodyparser = require('body-parser');

/* Local modules */
var connection = require('./connection');
var routes = require('./routes');

/* Init express module */
var app = express();
app.use(bodyparser.urlencoded({extended: true}));
app.use(bodyparser.json());

// connection.init();
routes.configure(app);

var server = app.listen(8000, function() {
  console.log('Server listening on port : ' + server.address().port);
});
