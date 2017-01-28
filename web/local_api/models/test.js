function Test() {

	// Test : Getting user connection
	this.getUser = function(res) {
			res.json({
				"user": [
					{
						"name": {
							"first": "TestUser_first",
							"last": "TestUser_last"
						},
						"email": "romain.hoogmoed@example.com",
						"login": {
							"username": "TestUser",
							"password": "rssfeed",
							"salt": "UGtRFz4N",
							"md5": "6d83a8c084731ee73eb5f9398b923183",
							"sha1": "cb21097d8c430f2716538e365447910d90476f6e",
							"sha256": "5a9b09c86195b8d8b01ee219d7d9794e2abb6641a2351850c49c309f1fc204a0"
						}
					}
				]
			});
	};

	// Test : Getting all flux
	this.getFlux = function(res) {
		res.json({
			"status": "ok",
			"feed": {
				"url": "http://www.theverge.com/rss/full.xml",
				"title": "The Verge - All Posts",
				"link": "http://www.theverge.com/",
				"author": "",
				"description": "",
				"image": "https://cdn3.vox-cdn.com/community_logos/34086/verge-fv.png"
			},
			"items": [
				{
					"title": "Zuckerberg to Trump: ‘Keep our doors open to refugees’",
					"pubDate": "2017-01-27 23:09:14",
					"link": "http://www.theverge.com/2017/1/27/14420662/zuckerberg-immigration-refugees-trump-executive-orders",
					"guid": "http://www.theverge.com/2017/1/27/14420662/zuckerberg-immigration-refugees-trump-executive-orders",
					"author": "Casey Newton",
					"thumbnail": "",
					"description": "Mark Zuckerberg pushed back on President Donald Trump’s immigration initiatives today, protesting his call for local law enforcement to enforce immigration laws and his produced reduction in the number of refugees. “Like many of you, I’m concerned about the impact of the recent executive orders signed by President Trump,”",
					"content": "",
					"enclosure": [
					],
					"categories": [
					]
				}]
		});
	};

	// Test : Posting one flux with a tag
	this.postFlux = function(url, tag, res) {
		res.status(200).send("Flux : " + (url).toString()+ " added with tag <" + (tag).toString() + ">.\n");
	};

	// Test : Deleting one flux with a tag
	this.deleteFlux = function(url, res) {
		res.send({status: 200, message: 'Flux : ' + (url).toString() + ' is deleted.'});
	};
}

module.exports = new Test();
