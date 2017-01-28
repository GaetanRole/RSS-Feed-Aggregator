/* Import local test module */
var test = require('./models/test');

module.exports = {
    configure: function(app) {
        app.get('/test/user/:name/:password/', function(req, res) {
            test.getUser(res);
        });

        app.get('/test/flux/', function(req, res) {
            test.getFlux(res);
        });

        app.post('/test/flux/add/', function(req, res) {
            test.postFlux(req.body.url, req.body.tag, res);
        });

        app.delete('/test/flux/delete/', function(req, res) {
            test.deleteFlux(req.body.url, res);
        });
    }
};
