var http = require('http');
var client = http.createClient(80, '149.202.169.91');

var username = 'toto';
var password = 'cool';
var auth = 'Basic ' + new Buffer(username + ':' + password).toString('base64');
console.log(auth);
// auth is: 'Basic VGVzdDoxMjM='

var header = {'Host': '149.202.169.91', 'Authorization': auth};
var request = client.request('GET', '/feeds', header);

request.end();
request.on('response', function (response) {
    console.log('STATUS: ' + response.statusCode);
    console.log('HEADERS: ' + JSON.stringify(response.headers));
    response.setEncoding('utf8');
    response.on('data', function (chunk) {
        console.log('BODY: ' + chunk);
    });
});