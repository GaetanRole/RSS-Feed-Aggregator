/* NPM modules */
var fs = require('fs');
var util = require('util');
var pathModule = require("path");
var path = pathModule.join(__dirname, '../', 'views/');

function Form() {

    // Form : Login page
    this.displayLogin = function(res) {
        res.sendFile(path + 'connection_view.html');
    };

    // Form : Login POST
    this.processLogin = function(req, res) {
        if (!req.body.name || !req.body.password)
            res.redirect('/error');
        console.log("USERNAME :" + req.body.name);
        console.log("PASSWORD :" + req.body.password);
    };

    // Form : Signup page
    this.displaySignup = function(res) {
        res.sendFile(path + 'inscription_view.html');
    };

    // Form : Signup POST
    this.processSignup = function(req, res) {
        if (!req.body.name || !req.body.password || !req.body.email)
            res.redirect('/error');
        console.log("USERNAME :" + req.body.name);
        console.log("PASSWORD :" + req.body.password);
        console.log("EMAIL :" + req.body.email);
    };
}

module.exports = new Form();

