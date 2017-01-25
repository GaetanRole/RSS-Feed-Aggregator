package javaproject.epitech.eu;


import com.fasterxml.jackson.databind.node.ObjectNode;


import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import javaproject.epitech.eu.models.User;

@Path("/register")
public class RegisterRoute {
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Object register(ObjectNode body, @Context HttpServletResponse r) {
        User x = new User();
        x.username = body.findValue("username").asText();
        x.password = body.findValue("password").asText();
        try {
            x.save();
        } catch (javax.persistence.PersistenceException e) {
            return new HttpError(r, "Username already taken", HttpServletResponse.SC_BAD_REQUEST);
        }
        r.setStatus(HttpServletResponse.SC_CREATED);
        try {r.flushBuffer();}catch(Exception e){}
        return x;
    }
}