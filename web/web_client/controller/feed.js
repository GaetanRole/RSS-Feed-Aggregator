/* NPM modules */
var util = require('util');
var querystring = require('querystring');
var http = require('http');
var pathModule = require("path");

/* Local modules */
var path = pathModule.join(__dirname, '../', 'views/');
var callbackMod = require('./callback');

/* Feed class */
function Feed() {

    // Feed : Manage page
    this.displayManageFeeds = function(req, res) {
        callbackMod.getAllFluxListedCallback(function (response) {
            console.log("Response GET /feeds/ : " + response + '\n');

            // Variable that we want to pass to our RSS home page
            var user_name = req.session.user_name;

            var JSONObj = JSON.parse(response);
            /* Think that for is always faster than a foreach
             for (var i = 0, len = JSONObj.length; i < len; i++) {
             console.log(JSONObj[i]['tags']);
             }*/

            res.render(path + 'manage_view', {
                username: user_name,
                jsonObj: JSONObj
            });
        }, req.session.auth);
    };

    // Feed : Delete feed with ID
    this.deleteFeed = function(dataID, req, res) {
        callbackMod.deleteFluxCallback(function (response) {
            console.log("Response DELETE /feeds/{id} : " + response + '\n');
            res.redirect('/manage');
        }, dataID, req.session.auth);
    };

    // Feed : Display add feed page.
    this.displayAddFeed = function(req, res, errorHandler) {
        var user_name = req.session.user_name;

        res.render(path + 'addFeed_view', {
            username: user_name,
            errorHandler: errorHandler
        });
    };

    // Feed : Add a feed with 3 params.
    this.addFeed = function(req, res) {
        if (!req.body.name || !req.body.tag || !req.body.url) {
            this.displayAddFeed(req, res, "Please complete all fields.");
            return;
        }

        // Form fields
        var dataFlux = JSON.stringify(
            {
                "name": req.body.name,
                "tag": req.body.tag,
                "url": req.body.url
            }
        );

        callbackMod.postFluxCallback(function (response) {
            console.log("Response POST /feeds/ : " + response + '\n');
        }, dataFlux, req.session.auth);

        res.redirect('/manage');
    };

    // Feed : View page
    this.displayAllFeeds = function(req, res) {
        callbackMod.getAllFluxCallback(function (response) {
            console.log("Response GET /feeds/sync/ : " + response + '\n');

            // Variable that we want to pass to our RSS home page
            var user_name = req.session.user_name;

            var feedFiltered = [];

            if (req.query.tag != null) {
                console.log("!= null");
                for (var i = 0, len = feeds.length; i < len; i++) {
                    if (req.query.tag === feeds[i].tag)
                        feedFiltered.push(feeds[i]);
                }
            }
            else
            {
                console.log("null");
                feedFiltered = feeds;
            }

            console.log("Feeds filtered with tag <" + req.query.tag + "> : " + feedFiltered + '\n');

             for (var j = 0, lol = feedFiltered.length; j < lol; j++) {
             console.log(feedFiltered[j]['name']);
             }

            callbackMod.getAllTagsCallback(function (response) {
                console.log("Response GET /tags/ : " + response + '\n');
                var tags = JSON.parse(response);

                res.render(path + 'feeds_view', {
                    username: user_name,
                    tags: tags,
                    feedFiltered: feedFiltered
                });
            }, req.session.auth);
        }, req.session.auth);
    };
}

module.exports = new Feed();