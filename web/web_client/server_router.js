/* NPM modules */
var express         = require('express');
var favicon         = require('serve-favicon');
var logger          = require('morgan');
var methodOverride  = require('method-override');
var session         = require('express-session');
var bodyParser      = require('body-parser');
var multer          = require('multer');
var errorHandler    = require('errorhandler');

/* Local modules */
var form = require('./controller/form');
var home = require('./controller/home');

/* Init express module */
var app = express();

/* Init static directories */
app.use('/css',  express.static(__dirname + '/views/css'));
app.use('/img',  express.static(__dirname + '/views/img'));
app.use('/font',  express.static(__dirname + '/views/font-awesome'));
app.use('/js',  express.static(__dirname + '/views/js'));
app.use(favicon(__dirname + '/views/img/rss.ico'));

/* Init other variables like session, web view engine or parser_app in JSON */
app.use(logger('dev'));
app.use(methodOverride());
app.use(session({ resave: true, saveUninitialized: true,
    secret: 'uwotm8' }));
app.set('view engine', 'ejs');
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));
app.use(multer());

/* Init port */
var port = process.env.PORT || 8080;

/* Get an instance of router */
var router = express.Router();

/* Middleware */
router.use(function(req, res, next) {

    console.log('checkAuth : ' + req.method + " - URL : " + req.url);

    // Signup Page
    if (req.url == '/create')
        res.redirect('/create');

    // Login Page
    !req.session.user_name ? res.redirect('/login') : next();
});

/* Home page route (http://localhost:8080/home) */
router.get('/home', function(req, res) {
    home.displayHome(req, res);
});

/* About page route (http://localhost:8080/about) */
router.get('/about', function(req, res) {
    home.displayHome(req, res);
});

app.route('/login')
    // Show the form (GET http://localhost:8080/login)
    .get(function(req, res) {
        form.displayLogin(res);
    })
    // Process the form (POST http://localhost:8080/login)
    .post(function(req, res) {
        console.log('Processing POST.');
        form.processLogin(req, res);
    });

app.route('/signup')
    // Show the form (GET http://localhost:8080/signup)
    .get(function(req, res) {
        form.displaySignup(res);
    })
    // Process the form (POST http://localhost:8080/signup)
    .post(function(req, res) {
        console.log('Processing POST.');
        form.processSignup(req, res);
    });

app.get('/error', function (req, res) {
    res.send(500, 'Bad move dude !');
});

app.get('/logout', function (req, res) {
    delete req.session.user_name;
    res.redirect('/login');
});

app.use('/', router);

/* START THE SERVER */
var server = app.listen(port, function() {
    console.log('Server listening on port : ' + port);
});