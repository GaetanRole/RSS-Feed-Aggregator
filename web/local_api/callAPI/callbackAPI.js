/* NPM modules */
var http = require('http');
var querystring = require('querystring');

/* API CALLBACK */
module.exports.postUserLoginCallback = function (callback, data) {
    var str = '';
    var options = {
        host: '127.0.0.1',
        port: 8000,
        path: '/test/user/',
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            'Content-Length': Buffer.byteLength(data)
        }
    };

    var req = http.request(options, function (res) {
        res.setEncoding('utf8');
        res.on('data', function (body) {
            str += body;
        });
        res.on('end', function () {
            return callback(str);
        });
    });
    req.write(data);
    req.end();
};

module.exports.postUserRegisterCallback = function (callback, data) {
    var str = '';
    var options = {
        host: '127.0.0.1',
        port: 8000,
        path: '/test/register',
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            'Content-Length': Buffer.byteLength(data)
        }
    };

    var req = http.request(options, function (res) {
        res.setEncoding('utf8');
        res.on('data', function (body) {
            str += body;
        });
        res.on('end', function () {
            return callback(str);
        });
    });
    req.write(data);
    req.end();
};

module.exports.getAllFluxListedCallback = function (callback) {
    var str = '';
    var options = {
        host: '127.0.0.1',
        port: 8000,
        path: '/test/feeds/',
        method: 'GET'
    };

    var req = http.request(options, function (res) {
        res.setEncoding('utf8');
        res.on('data', function (body) {
            str += body;
        });
        res.on('end', function () {
            return callback(str);
        });
    });
    req.end();
};

module.exports.postFluxCallback = function (callback, data) {
    var str = '';
    var options = {
        host: '127.0.0.1',
        port: 8000,
        path: '/test/feeds/',
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            'Content-Length': Buffer.byteLength(data)
        }
    };

    var req = http.request(options, function (res) {
        res.setEncoding('utf8');
        res.on('data', function (body) {
            str += body;
        });
        res.on('end', function () {
            return callback(str);
        });
    });
    req.write(data);
    req.end();
};

module.exports.getAllFluxCallback = function (callback) {
    var str = '';
    var options = {
        host: '127.0.0.1',
        port: 8000,
        path: '/test/feeds/sync/',
        method: 'GET'
    };

    var req = http.request(options, function (res) {
        res.setEncoding('utf8');
        res.on('data', function (body) {
            str += body;
        });
        res.on('end', function () {
            return callback(str);
        });
    });
    req.end();
};

module.exports.getAllTagsCallback = function (callback) {
    var str = '';
    var options = {
        host: '127.0.0.1',
        port: 8000,
        path: '/test/tags/',
        method: 'GET'
    };

    var req = http.request(options, function (res) {
        res.setEncoding('utf8');
        res.on('data', function (body) {
            str += body;
        });
        res.on('end', function () {
            return callback(str);
        });
    });
    req.end();
};

module.exports.deleteFluxCallback = function (callback, data) {
    var str = '';
    var options = {
        host: '127.0.0.1',
        port: 8000,
        path: '/test/feeds/',
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            'Content-Length': Buffer.byteLength(data)
        }
    };

    var req = http.request(options, function (res) {
        res.setEncoding('utf8');
        res.on('data', function (body) {
            str += body;
        });
        res.on('end', function () {
            return callback(str);
        });
    });
    req.write(data);
    req.end();
};