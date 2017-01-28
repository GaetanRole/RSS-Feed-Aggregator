function Test() {

	// Test : Getting user connection
	this.postUserLogin = function(username, password, res) {
		res.status(201).json(
			{
				"username": username,
				"password": password
			}
		);
	};

	// Test : Post user inscription
	this.postUserRegister = function(username, password, res) {
		res.status(201).json(
			{
				"username": username,
				"password": password
			}
		);
	};

	// Test : Getting all flux (feeds) listed
	this.getAllFluxListed = function(res) {
		res.status(201).json(
			[
				{
					"id": 1,
					"name": "BFM news",
					"tags": "cool",
					"url": "http://www.bfmtv.com/rss/info/flux-rss/flux-toutes-les-actualites/"
				},
				{
					"id": 2,
					"name": "BFM news",
					"tags": "pas cool",
					"url": "http://www.bfmtv.com/rss/info/flux-rss/flux-toutes-les-actualites/"
				}
			]
		);
	};

	// Test : Getting all flux (feeds)
	this.getAllFlux = function(res) {
		res.status(201).json(
			[
				{
					"flux_title": "Flux toutes les actualités - actualites",
					"flux_tag" : "cool",
					"flux_link": "http://www.bfmtv.com/info/flux-rss/flux-toutes-les-actualites/",
					"flux_description": "BFMTV.COM",
					"flux_language": "fr",
					"flux_copyright": "Copyright BFMTV",
					"flux_image": {
						"title": "Flux toutes les actualités - actualites",
						"url": "http://static.bfmtv.com/ressources/img/logo/logoportail.png",
						"link": "http://www.bfmtv.com/info/flux-rss/flux-toutes-les-actualites/"
					},
					"title": "Le décret de Trump sur l'immigration peut-il être appliqué?",
					"description": "Donald Trump a suspendu, vendredi, un programme d'accueil des réfugiés et a interdit l'entrée de ressortissants en provenance de sept pays du Moyen-Orient. Mais selon un spécialiste cité par le New York Times, ce décret ne respecte pas les lois migratoires du pays.",
					"link": "http://www.bfmtv.com/international/le-decret-de-trump-sur-l-immigration-peut-il-etre-applique-1091696.html",
					"pub_date": "Sat, 28 Jan 2017 01:30:33 +0100",
					"guid": {
						"@isPermaLink": "false",
						"#text": "http://www.bfmtv.com/international/le-decret-de-trump-sur-l-immigration-peut-il-etre-applique-1091696.html"
					},
					"enclosure": {
						"@url": "http://img.bfmtv.com/i/0/0/bda/58d8f0739e0d69c017af876c8ba86.jpeg",
						"@length": "0"
					}
				},
				{
					"flux_title": "Flux toutes les actualités - actualites",
					"flux_tag" : "pas cool",
					"flux_link": "http://www.bfmtv.com/info/flux-rss/flux-toutes-les-actualites/",
					"flux_description": "BFMTV.COM",
					"flux_language": "fr",
					"flux_copyright": "Copyright BFMTV",
					"flux_image": {
						"title": "Flux toutes les actualités - actualites",
						"url": "http://static.bfmtv.com/ressources/img/logo/logoportail.png",
						"link": "http://www.bfmtv.com/info/flux-rss/flux-toutes-les-actualites/"
					},
					"title": "Jean michel musique mort",
					"description": "Jean michel musique est mort après avoir mangé une compte andros, quel con.",
					"link": "http://www.bfmtv.com/international/le-decret-de-trump-sur-l-immigration-peut-il-etre-applique-1091696.html",
					"pub_date": "Sat, 28 Jan 2017 01:30:33 +0100",
					"guid": {
						"@isPermaLink": "false",
						"#text": "http://www.bfmtv.com/international/le-decret-de-trump-sur-l-immigration-peut-il-etre-applique-1091696.html"
					},
					"enclosure": {
						"@url": "http://img.bfmtv.com/i/0/0/bda/58d8f0739e0d69c017af876c8ba86.jpeg",
						"@length": "0"
					}
				},
				{
					"flux_title": "Flux toutes les actualités - actualites",
					"flux_tag" : "cool",
					"flux_link": "http://www.bfmtv.com/info/flux-rss/flux-toutes-les-actualites/",
					"flux_description": "BFMTV.COM",
					"flux_language": "fr",
					"flux_copyright": "Copyright BFMTV",
					"flux_image": {
						"title": "Flux toutes les actualités - actualites",
						"url": "http://static.bfmtv.com/ressources/img/logo/logoportail.png",
						"link": "http://www.bfmtv.com/info/flux-rss/flux-toutes-les-actualites/"
					},
					"title": "Le décret de Trump sur l'immigration peut-il être appliqué?",
					"description": "Donald Trump a suspendu, vendredi, un programme d'accueil des réfugiés et a interdit l'entrée de ressortissants en provenance de sept pays du Moyen-Orient. Mais selon un spécialiste cité par le New York Times, ce décret ne respecte pas les lois migratoires du pays.",
					"link": "http://www.bfmtv.com/international/le-decret-de-trump-sur-l-immigration-peut-il-etre-applique-1091696.html",
					"pub_date": "Sat, 28 Jan 2017 01:30:33 +0100",
					"guid": {
						"@isPermaLink": "false",
						"#text": "http://www.bfmtv.com/international/le-decret-de-trump-sur-l-immigration-peut-il-etre-applique-1091696.html"
					},
					"enclosure": {
						"@url": "http://img.bfmtv.com/i/0/0/bda/58d8f0739e0d69c017af876c8ba86.jpeg",
						"@length": "0"
					}
				},
				{
					"flux_title": "Flux toutes les actualités - actualites",
					"flux_tag" : "pas cool",
					"flux_link": "http://www.bfmtv.com/info/flux-rss/flux-toutes-les-actualites/",
					"flux_description": "BFMTV.COM",
					"flux_language": "fr",
					"flux_copyright": "Copyright BFMTV",
					"flux_image": {
						"title": "Flux toutes les actualités - actualites",
						"url": "http://static.bfmtv.com/ressources/img/logo/logoportail.png",
						"link": "http://www.bfmtv.com/info/flux-rss/flux-toutes-les-actualites/"
					},
					"title": "Jean michel musique mort",
					"description": "Jean michel musique est mort après avoir mangé une compte andros, quel con.",
					"link": "http://www.bfmtv.com/international/le-decret-de-trump-sur-l-immigration-peut-il-etre-applique-1091696.html",
					"pub_date": "Sat, 28 Jan 2017 01:30:33 +0100",
					"guid": {
						"@isPermaLink": "false",
						"#text": "http://www.bfmtv.com/international/le-decret-de-trump-sur-l-immigration-peut-il-etre-applique-1091696.html"
					},
					"enclosure": {
						"@url": "http://img.bfmtv.com/i/0/0/bda/58d8f0739e0d69c017af876c8ba86.jpeg",
						"@length": "0"
					}
				},
				{
					"flux_title": "Flux toutes les actualités - actualites",
					"flux_tag" : "cool",
					"flux_link": "http://www.bfmtv.com/info/flux-rss/flux-toutes-les-actualites/",
					"flux_description": "BFMTV.COM",
					"flux_language": "fr",
					"flux_copyright": "Copyright BFMTV",
					"flux_image": {
						"title": "Flux toutes les actualités - actualites",
						"url": "http://static.bfmtv.com/ressources/img/logo/logoportail.png",
						"link": "http://www.bfmtv.com/info/flux-rss/flux-toutes-les-actualites/"
					},
					"title": "Le décret de Trump sur l'immigration peut-il être appliqué?",
					"description": "Donald Trump a suspendu, vendredi, un programme d'accueil des réfugiés et a interdit l'entrée de ressortissants en provenance de sept pays du Moyen-Orient. Mais selon un spécialiste cité par le New York Times, ce décret ne respecte pas les lois migratoires du pays.",
					"link": "http://www.bfmtv.com/international/le-decret-de-trump-sur-l-immigration-peut-il-etre-applique-1091696.html",
					"pub_date": "Sat, 28 Jan 2017 01:30:33 +0100",
					"guid": {
						"@isPermaLink": "false",
						"#text": "http://www.bfmtv.com/international/le-decret-de-trump-sur-l-immigration-peut-il-etre-applique-1091696.html"
					},
					"enclosure": {
						"@url": "http://img.bfmtv.com/i/0/0/bda/58d8f0739e0d69c017af876c8ba86.jpeg",
						"@length": "0"
					}
				},
				{
					"flux_title": "Flux toutes les actualités - actualites",
					"flux_tag" : "pas cool",
					"flux_link": "http://www.bfmtv.com/info/flux-rss/flux-toutes-les-actualites/",
					"flux_description": "BFMTV.COM",
					"flux_language": "fr",
					"flux_copyright": "Copyright BFMTV",
					"flux_image": {
						"title": "Flux toutes les actualités - actualites",
						"url": "http://static.bfmtv.com/ressources/img/logo/logoportail.png",
						"link": "http://www.bfmtv.com/info/flux-rss/flux-toutes-les-actualites/"
					},
					"title": "Jean michel musique mort",
					"description": "Jean michel musique est mort après avoir mangé une compte andros, quel con.",
					"link": "http://www.bfmtv.com/international/le-decret-de-trump-sur-l-immigration-peut-il-etre-applique-1091696.html",
					"pub_date": "Sat, 28 Jan 2017 01:30:33 +0100",
					"guid": {
						"@isPermaLink": "false",
						"#text": "http://www.bfmtv.com/international/le-decret-de-trump-sur-l-immigration-peut-il-etre-applique-1091696.html"
					},
					"enclosure": {
						"@url": "http://img.bfmtv.com/i/0/0/bda/58d8f0739e0d69c017af876c8ba86.jpeg",
						"@length": "0"
					}
				}
			]
		);
	};

	// Test : Getting all tags
	this.getAllTags = function(res) {
		res.status(201).json(
			[
				"cool",
				"pas cool",
				"oklm"
			]
		);
	};

	// Test : Posting one flux
	this.postFlux = function(data, res) {
		res.status(201).json(
			data
		);
	};

	// Test : Deleting one flux with a id
	this.deleteFlux = function(id, res) {
		res.send({status: 204, message: 'Flux_id : ' + (id).toString() + ' is deleted.'});
	};
}

module.exports = new Test();
