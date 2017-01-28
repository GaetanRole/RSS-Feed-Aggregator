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

        app.post('/test/flux/add/:url/:tag/', function(req, res) {
            test.postFlux(req.params.url, req.params.tag, res);
        });

        app.delete('/test/flux/delete/:url/', function(req, res) {
            test.deleteFlux(req.params.url, res);
        });
    }
};
