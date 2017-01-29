/* NPM modules */
var util = require('util');
var pathModule = require("path");
var path = pathModule.join(__dirname, '../', 'views/');

/* Form class */
function Form() {

    // Form : Login page
    this.displayLogin = function(res) {
        res.sendFile(path + 'connection_view.html');
    };

    // Form : Login POST
    this.processLogin = function(req, res) {

        if (!req.body.name || !req.body.password)
            res.redirect('/error');

        console.log("User_name : " + req.body.name);
        console.log("User_password : " + req.body.password);

        req.session.user_name = req.body.name;
        res.redirect('/home')
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

