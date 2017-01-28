package olivier.sturbois.rss_project;

import android.util.Log;

import com.anupcowkur.reservoir.Reservoir;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.ion.Ion;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class User implements Serializable {
    private String login;
    private String password;
    private static final String currentTAG = "User";

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public static User getUser() {
        try {
            return Reservoir.get("user", User.class);
        } catch (Exception e) {
            Log.v(currentTAG, e.toString());
            return null;
        }
    }

    public static void setUser(User u) {
        try {
            Reservoir.put("user", u);
        }
        catch (Exception e) {
            Log.v(currentTAG, e.toString());
        }
    }
}
