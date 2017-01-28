/* NPM modules */
var http = require('http');
var querystring = require('querystring');

/* API CALLBACK */

module.exports.LoginCallback = function (callback, auth) {
    var str = '';
    console.log("logs : " + auth);
    var options = {
        host: '149.202.169.91',
        path: '/feeds',
        method: 'GET',
        headers: {
            "Authorization" : auth
        }
    };

    var req = http.request(options, function (res) {
        res.setEncoding('utf8');
        res.on('data', function (body) {
            str += body;
        });
        res.on('end', function () {
            console.log(str);
            return callback(str);
        });
    });
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

module.exports.getAllFluxListedCallback = function (callback, auth) {
    var str = '';
    var options = {
        host: '149.202.169.91',
        path: '/feeds',
        method: 'GET',
        headers: {
            "Authorization" : auth
        }
    };

    var req = http.request(options, function (res) {
        res.setEncoding('utf8');
        res.on('data', function (body) {
            str += body;
        });
        res.on('end', function () {
            console.log(str);
            return callback(str);
        });
    });
    req.end();
};

module.exports.postFluxCallback = function (callback, data, auth) {
    var str = '';
    var options = {
        host: '149.202.169.91',
        //port: 8000,
        path: '/feeds',
        method: 'POST',
        headers: {
            "Authorization" : auth,
            'Content-Type': 'application/json',
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

module.exports.getAllFluxCallback = function (callback, auth) {
    var str = '';
    var options = {
        host: '149.202.169.91',
        path: '/feeds/sync',
        method: 'GET',
        headers: {
            "Authorization" : auth
        }
    };

    var req = http.request(options, function (res) {
        res.setEncoding('utf8');
        res.on('data', function (body) {
            str += body;
        });
        res.on('end', function () {
            console.log(str);
            return callback(str);
        });
    });
    req.end();
};

module.exports.getAllTagsCallback = function (callback, auth) {
    var str = '';
    var options = {
        host: '149.202.169.91',
        path: '/tags',
        method: 'GET',
        headers: {
            "Authorization" : auth
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
    req.end();
};

module.exports.deleteFluxCallback = function (callback, data, auth) {
    var str = '';
    console.log("DATA" + data);
    var options = {
        host: '149.202.169.91',
        path: '/feeds/'+ data,
        method: 'DELETE',
        headers: {
            "Authorization" : auth
        }
    };

    var req = http.request(options, function (res) {
        res.setEncoding('utf8');
        res.on('data', function (body) {
            str += body;
            console.log("RETURN DE PHILIP : " + body);
        });
        res.on('end', function () {
            return callback(str);
        });
    });
    req.write(data);
    req.end();
};