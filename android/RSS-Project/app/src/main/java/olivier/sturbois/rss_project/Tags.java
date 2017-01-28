package olivier.sturbois.rss_project;

import android.util.Log;
import com.anupcowkur.reservoir.Reservoir;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class Tags {

    private static final String currentTAG = "Tags";

    private ArrayList<String> tagList;

    public ArrayList<String> GetTagList() {
        try {
            Type res = new TypeToken<ArrayList<String>>(){}.getType();
            return Reservoir.get("tagsList", res);
        } catch (Exception e) {
            Log.v(currentTAG, e.toString());
            return null;
        }
    }

    public void SetTagList(ArrayList<String> l) {
        try {
            Reservoir.put("tagsList", l);
        }
        catch (Exception e) {
            Log.v(currentTAG, e.toString());
        }
    }
}
