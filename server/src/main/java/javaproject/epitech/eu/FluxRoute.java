package javaproject.epitech.eu;

import com.fasterxml.jackson.databind.node.ObjectNode;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import java.net.URL;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import io.ebean.Ebean;
import javaproject.epitech.eu.models.FeedItem;
import javaproject.epitech.eu.models.Flux;
import javaproject.epitech.eu.models.User;

@Path("/feeds")
public class FluxRoute {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Object list(@Context HttpServletResponse r, @HeaderParam("authorization") String auth) {
		User currentUser = BasicAuth.getUser(auth);
		if (currentUser == null)
			return new HttpError(r, "Wrong credentials", HttpServletResponse.SC_FORBIDDEN);
		List<Flux> list = Ebean.find(Flux.class).where().eq("user", currentUser).findList();
		return list;
	}

    @GET
    @Path("/sync")
    @Produces(MediaType.APPLICATION_JSON)
    public Object sync(@Context HttpServletResponse r, @HeaderParam("authorization") String auth) {
        User currentUser = BasicAuth.getUser(auth);
        if (currentUser == null)
            return new HttpError(r, "Wrong credentials", HttpServletResponse.SC_FORBIDDEN);
        List<Flux> fluxs = Ebean.find(Flux.class).where().eq("user", currentUser).findList();
        SyndFeed feed;
        Timestamp now = new Timestamp(new Date().getTime());
        List<FeedItem> items = new ArrayList<>();
        for (Flux f : fluxs) {
            try {
                URL feedUrl = new URL(f.url);
                SyndFeedInput input = new SyndFeedInput();
                feed = input.build(new XmlReader(feedUrl));
            }
            catch (Exception ex) {
                return new HttpError(r, "Unexpected error fetching feeds", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
            for (SyndEntry e : feed.getEntries()) {
                Timestamp pub = new Timestamp(e.getPublishedDate().getTime());
                if (pub.getTime() > f.lastsync.getTime() && pub.getTime() < now.getTime()) {
                    FeedItem item = new FeedItem();
                    item.flux = f;
                    item.description = e.getDescription().getValue();
                    item.link = e.getLink();
                    item.pub_date = pub;
                    item.title = e.getTitle();
                    item.save();
                }
            }
            items.addAll(Ebean.find(FeedItem.class).where().eq("flux", f).gt("pub_date", f.lastsync).findList());
        }
        Ebean.update(Flux.class).set("lastsync", now).where().eq("user", currentUser).update();
        return items;
    }

    @GET
    @Path("/{feedId: [0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public Object get(@Context HttpServletResponse r, @HeaderParam("authorization") String auth, @PathParam("feedId") String id) {
        User currentUser = BasicAuth.getUser(auth);
        if (currentUser == null)
            return new HttpError(r, "Wrong credentials", HttpServletResponse.SC_FORBIDDEN);
        Flux y = Ebean.find(Flux.class).where().idEq(id).eq("user", currentUser).findUnique();
        if (y == null)
            return new HttpError(r, "not found", HttpServletResponse.SC_NOT_FOUND);
        return y;
    }

	@DELETE
	@Path("/{feedId: [0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public Object delete(@Context HttpServletResponse r, @HeaderParam("authorization") String auth, @PathParam("feedId") String id) {
		User currentUser = BasicAuth.getUser(auth);
		if (currentUser == null)
			return new HttpError(r, "Wrong credentials", HttpServletResponse.SC_FORBIDDEN);
		Flux y = Ebean.find(Flux.class).where().idEq(id).eq("user", currentUser).findUnique();
		if (y == null)
			return new HttpError(r, "not found", HttpServletResponse.SC_NOT_FOUND);
        List<FeedItem> items = Ebean.find(FeedItem.class).where().eq("flux", y).findList();
        for (FeedItem i : items) {
            i.delete();
        }
        Ebean.delete(Flux.class, id);
		return null;
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Object create(ObjectNode body, @HeaderParam("authorization") String auth, @Context HttpServletResponse r) {
		User currentUser = BasicAuth.getUser(auth);
		if (currentUser == null)
			return new HttpError(r, "Wrong credentials", HttpServletResponse.SC_FORBIDDEN);
        SyndFeed feed;
        try {
            URL feedUrl = new URL(body.findValue("url").asText());
            SyndFeedInput input = new SyndFeedInput();
            feed = input.build(new XmlReader(feedUrl));
        }
        catch (Exception ex) {
            return new HttpError(r, "Invalid flux", HttpServletResponse.SC_BAD_REQUEST);
        }
        Flux x = new Flux();
		x.name = body.findValue("name").asText();
		x.url = body.findValue("url").asText();
		x.tag = body.findValue("tag").asText();
        x.title = feed.getTitle();
        x.description = feed.getDescription();
        x.language = feed.getLanguage();
        x.copyright = feed.getCopyright();
        if (feed.getImage() != null)
            x.image = feed.getImage().getUrl();
		x.user = currentUser;
        x.lastsync = new Timestamp(0);
        x.save();
		return x;
	}
}