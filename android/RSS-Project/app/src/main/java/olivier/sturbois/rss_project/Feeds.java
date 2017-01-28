package olivier.sturbois.rss_project;

import android.util.Log;

import com.anupcowkur.reservoir.Reservoir;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Feeds {

    private static final String currentTAG = "Feeds";

    ArrayList<Feed> feeds;

    public void setFeeds(ArrayList<Feed> feeds) {
        try {
            Reservoir.put("feedsList", feeds);
        }
        catch (Exception e) {
            Log.v(currentTAG, e.toString());
        }
    }

    public ArrayList<Feed> getFeeds() {
        try {
            Type res = new TypeToken<ArrayList<Feed>>(){}.getType();
            return Reservoir.get("feedsList", res);
        } catch (Exception e) {
            Log.v(currentTAG, e.toString());
            return null;
        }
    }
}
