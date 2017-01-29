/* NPM modules */
var util = require('util');
var pathModule = require("path");
var path = pathModule.join(__dirname, '../', 'views/');

/* Home class */
function Home() {

    // Home : Home page
    this.displayHome = function(req, res) {

        // Variable that we want to pass to our RSS home page
        var user_name = req.session.user_name;

        res.render(path + 'home_view', {
            username: user_name
        });
    };
}

module.exports = new Home();
