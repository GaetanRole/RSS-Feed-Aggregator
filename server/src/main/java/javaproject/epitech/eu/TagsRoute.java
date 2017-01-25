package javaproject.epitech.eu;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import io.ebean.Ebean;
import javaproject.epitech.eu.models.Flux;
import javaproject.epitech.eu.models.User;

@Path("/tags")
public class TagsRoute {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Object list(@Context HttpServletResponse r, @HeaderParam("authorization") String auth) {
		User currentUser = BasicAuth.getUser(auth);
		if (currentUser == null)
			return new HttpError(r, "Wrong credentials", HttpServletResponse.SC_FORBIDDEN);
		List<Flux> fluxs = Ebean.find(Flux.class).select("tag").where().eq("user", currentUser).findList();
		List<String> tags = new ArrayList<>();
		for (Flux f : fluxs) {
			if (!tags.contains(f.tag))
				tags.add(f.tag);
		}
		return tags;
	}
}