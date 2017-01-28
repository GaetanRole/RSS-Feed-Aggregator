package olivier.sturbois.rss_project;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import java.util.ArrayList;


public class FeedActivity extends AppCompatActivity {

    private static final String currentTAG = "FeedActivity";


    private static int IDFeed;

    private TextView name;
    private TextView url;
    private TextView tag;
    private TextView title;
    private TextView desc;
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        Intent intent = getIntent();
        IDFeed =  intent.getIntExtra("IDFEED", 0);

        Log.v(currentTAG, "id is " + IDFeed);

        name = (TextView) findViewById(R.id.feed_name);
        url = (TextView) findViewById(R.id.feed_url);
        tag = (TextView) findViewById(R.id.feed_tag);
        title = (TextView) findViewById(R.id.feed_title);
        desc = (TextView) findViewById(R.id.feed_desc);
        img = (ImageView) findViewById(R.id.feed_img);

        displayFeedInfo(IDFeed);
    }

    protected void displayFeedInfo(int id) {
        User user = User.getUser();

        Ion.with(getApplicationContext())
                .load(APIRoutes.GetFeedByIdRoute(String.valueOf(id)))
                .setHeader("Authorization", APIRoutes.GetAuthorizationHeader(user))
                .asJsonObject()
                .withResponse()
                .then(new FutureCallback<Response<JsonObject>>() {
                    @Override
                    public void onCompleted(Exception e, Response<JsonObject> result) {
                        if (e != null) {
                            Log.v(currentTAG, e.toString());
                        }
                        else if (result != null) {
                            Log.v(currentTAG, "result not null");
                            Log.v(currentTAG, String.valueOf(result.getHeaders().code()));
                            if (result.getHeaders().code() == 200) {
                                Log.v(currentTAG, "RESULT :");
                                Log.v(currentTAG, result.getResult().toString());
                                JsonObject obj = result.getResult();
                                name.setText(obj.get("name").getAsString());
                                url.setText(obj.get("url").getAsString());
                                tag.setText(obj.get("tag").getAsString());
                                title.setText(obj.get("title").getAsString());
                                desc.setText(obj.get("description").getAsString());
                                if (obj.get("image").getAsString() != null) {
                                    String imgUrl = obj.get("image").getAsString();
                                    Ion.with(img).load(imgUrl);
                                }
                            }
                        }
                        else if (result == null) {
                            Log.v(currentTAG, "result null");
                        }
                    }
                });
    }
}
