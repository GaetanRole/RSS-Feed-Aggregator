/* NPM modules */
var querystring = require('querystring');

var callbackMod = require('./callbackAPI');

/* Variables simulations */
var dataLogs = querystring.stringify({
    username: 'Gaetan',
    password: 'secret'
});

callbackMod.postUserLoginCallback(function (response) {
    console.log("Response POST /login/ : " + response + '\n');
}, dataLogs);


callbackMod.postUserRegisterCallback(function (response) {
    console.log("Response POST /register/ : " + response + '\n');
}, dataLogs);

callbackMod.getAllFluxListedCallback(function (response) {
    console.log("Response GET /feeds/ : " + response + '\n');

    /* HOW USE A JSON OBJECT
     var JSONObj = JSON.parse(response);
     for (var i = 0, len = JSONObj.length; i < len; i++) {
     console.log(JSONObj[i]['tags']);
     }*/
});

var dataFlux = querystring.stringify(
    {
        "name": "BFM news",
        "tags": "cool",
        "url": "http://www.bfmtv.com/rss/info/flux-rss/flux-toutes-les-actualites/"
    }
);
callbackMod.postFluxCallback(function (response) {
    console.log("Response POST /feeds/ : " + response + '\n');
}, dataFlux);

callbackMod.getAllFluxCallback(function (response) {
    console.log("Response GET /feeds/sync/ : " + response + '\n');
});

callbackMod.getAllTagsCallback(function (response) {
    console.log("Response GET /tags/ : " + response + '\n');
});

/* Variables simulations */
var dataID = querystring.stringify({
    id: 'toto'
});
callbackMod.deleteFluxCallback(function (response) {
    console.log("Response DELETE /feeds/{id} : " + response + '\n');
}, dataID);