/* Import local test module */
var test = require('./models/test');

module.exports = {
    configure: function(app) {
        app.post('/test/user/', function(req, res) {
            test.postUserLogin(req.body.username, req.body.password, res);
        });

        app.post('/test/register/', function(req, res) {
            test.postUserRegister(req.body.username, req.body.password, res);
        });

        app.get('/test/feeds/', function(req, res) {
            test.getAllFluxListed(res);
        });

        app.post('/test/feeds/', function(req, res) {
            test.postFlux(req.body, res);
        });

        app.get('/test/feeds/sync/', function(req, res) {
            test.getAllFlux(res);
        });

        app.get('/test/tags/', function(req, res) {
            test.getAllTags(res);
        });

        app.delete('/test/feeds/', function(req, res) {
            test.deleteFlux(req.body.id, res);
        });
    }
};
