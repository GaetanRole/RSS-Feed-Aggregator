var querystring = require('querystring');
var http = require('http');

var data = querystring.stringify({
    url: 'toto'
});

var options = {
    host: '127.0.0.1',
    port: 8000,
    path: '/test/flux/delete/',
    method: 'DELETE',
    headers: {
        'Content-Type': 'application/x-www-form-urlencoded',
        'Content-Length': Buffer.byteLength(data)
    }
};

var req = http.request(options, function(res) {
    res.setEncoding('utf8');
    res.on('data', function (chunk) {
        console.log("body: " + chunk);
    });
});

req.write(data);
req.end();