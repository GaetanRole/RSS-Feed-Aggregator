/* NPM modules */
var util = require('util');
var querystring = require('querystring');
var http = require('http');
var pathModule = require("path");

/* Local modules */
var path = pathModule.join(__dirname, '../', 'views/');
var callbackMod = require('./callback');

/* Home class */
function Home() {

    // Home : Home page
    this.displayHome = function(req, res) {
        callbackMod.getAllFluxListedCallback(function (response) {

            console.log("Response GET /feeds/ : " + response + '\n');

            // Variable that we want to pass to our RSS home page
            var user_name = req.session.user_name;

            // Take last feed posted
            var JSONObj = JSON.parse(response);
            var lastFeed = JSONObj[JSONObj.length - 1];

            res.render(path + 'home_view', {
                username: user_name,
                lastFeed: lastFeed,
                numberFeeds : JSONObj.length
            });
        }, req.session.auth);
    };
}

module.exports = new Home();
