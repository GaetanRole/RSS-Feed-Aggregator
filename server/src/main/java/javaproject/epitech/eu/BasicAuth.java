package javaproject.epitech.eu;

import javax.xml.bind.DatatypeConverter;

import io.ebean.Ebean;
import javaproject.epitech.eu.models.User;

public class BasicAuth {
    /**
     * Decode the basic auth and convert it to array login/password
     * @param auth The string encoded authentification
     * @return The login (case 0), the password (case 1)
     */
    public static String[] decode(String auth) {
        //Replacing "Basic THE_BASE_64" to "THE_BASE_64" directly
        auth = auth.replaceFirst("[B|b]asic ", "");

        //Decode the Base64 into byte[]
        byte[] decodedBytes = DatatypeConverter.parseBase64Binary(auth);

        //If the decode fails in any case
        if (decodedBytes == null || decodedBytes.length == 0) {
            return null;
        }

        //Now we can convert the byte[] into a splitted array :
        //  - the first one is login,
        //  - the second one password
        return new String(decodedBytes).split(":", 2);
    }

    public static User getUser(String auth) {
        String[] decoded = BasicAuth.decode(auth);
        if (decoded == null)
            return null;
        String username = decoded[0];
        String password = decoded[1];
        return Ebean.find(User.class).where().eq("username", username).eq("password", password).findUnique();
    }
}