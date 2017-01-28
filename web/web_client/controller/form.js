/* NPM modules */
var util = require('util');
var pathModule = require("path");
var auth = require('basic-auth');
var querystring = require('querystring');

/* Local modules */
var callbackMod = require('./callback');
var path = pathModule.join(__dirname, '../', 'views/');

/* Form class */
function Form() {

    // Form : Login page
    this.displayLogin = function(res, errorHandler) {
        res.render(path + 'connection_view', {
            errorHandler: errorHandler
        });
    };

    // Form : Login POST
    this.processLogin = function(req, res) {

        if (!req.body.name || !req.body.password) {
            this.displayLogin(res, "Please complete all fields.");
            return;
        }

        var username, password = null;

        !username && (username = req.body.name);
        !password && (password = req.body.password);

        var auth = 'Basic ' + new Buffer(username + ':' + password).toString('base64');
        req.session.auth = auth;
        // Verify login and password are set and correct
        callbackMod.LoginCallback(function (response) {
            console.log("Response GET /login/ : " + response + '\n');
            if (!response)
                res.redirect('/login');
            if (response) {
                username && (req.session.user_name = username);
                res.redirect('/home');
            }
        }, auth);
    };

    // Form : Signup page
    this.displaySignup = function(res, errorHandler) {
        res.render(path + 'inscription_view', {
            errorHandler: errorHandler
        });
    };

    // Form : Signup POST
    this.processSignup = function(req, res) {

        if (!req.body.name || !req.body.password || !req.body.email) {
            this.displaySignup(res, "Please complete all fields.");
            return;
        }

        console.log("USERNAME :" + req.body.name);
        console.log("PASSWORD :" + req.body.password);
        console.log("EMAIL :" + req.body.email);

        // Email not posted
        var dataLogs = querystring.stringify({
            username: req.body.name,
            password: req.body.password
        });

        callbackMod.postUserRegisterCallback(function (response) {
            console.log("Response POST /register/ : " + response + '\n');
            res.redirect('/login');
        }, dataLogs);
    };
}

module.exports = new Form();

