package olivier.sturbois.rss_project;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.ListView;

import com.google.android.gms.common.api.Api;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import java.util.ArrayList;


public class MenuActivity extends AppCompatActivity {

    private static final String currentTAG = "MenuActivity";

    private Spinner mTagsView;
    private ListView mFeedsView;
    private Button mAddFeed;
    private Button mLogout;
    private ArrayList<String> feedNames;
    private ArrayList<Integer> feedIDs;
    private ArrayList<String> tagsList;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        this.setTitle("Menu");

        Intent intent = getIntent();

        user = User.getUser();

        feedNames = new ArrayList<String>();
        feedIDs = new ArrayList<Integer>();
        tagsList = new ArrayList<String>();
        tagsList.add("All");

        mTagsView = (Spinner) findViewById(R.id.tag_spinner);
        mFeedsView = (ListView) findViewById(R.id.rss_listView);
        mAddFeed = (Button) findViewById(R.id.add_rss_button);
        mLogout = (Button) findViewById(R.id.logout_button);

        mAddFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddFeedActivity.class);
                intent.putExtra("USER", user);
                startActivity(intent);
                finish();
            }
        });

        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        boolean isConnected = intent.getBooleanExtra("ISCONNECTED", false);

        if (user != null) {
            getTags();
            getFeeds();
        } else {
            displayFeeds("All");
            displayTags();
        }
    }

    protected void getFeeds() {
        Ion.with(getApplicationContext())
                .load(APIRoutes.GetFeedsRoute())
                .setHeader("Authorization", APIRoutes.GetAuthorizationHeader(user))
                .asJsonArray()
                .withResponse()
                .then(new FutureCallback<Response<JsonArray>>() {
                    @Override
                    public void onCompleted(Exception e, Response<JsonArray> result) {
                        if (e != null) {
                            Log.v(currentTAG, e.toString());
                        }
                        else if (result != null) {
                            Log.v(currentTAG, "result not null");
                            Log.v(currentTAG, String.valueOf(result.getHeaders().code()));
                            if (result.getHeaders().code() == 200) {
                                Log.v(currentTAG, "RESULT :");
                                Log.v(currentTAG, result.getResult().toString());
                                JsonArray feedsArray = result.getResult().getAsJsonArray();
                                ArrayList<Feed> feedList = new ArrayList<>();
                                for (int i = 0; i < feedsArray.size(); i++) {
                                    JsonObject obj = feedsArray.get(i).getAsJsonObject();
                                    Feed feed = new Feed();
                                    feed.setId(obj.get("id").getAsInt());
                                    feed.setName(obj.get("title").getAsString());
                                    feed.setTags(obj.get("tag").getAsString());
                                    feed.setUrl(obj.get("url").getAsString());
                                    feedList.add(feed);
                                }
                                Feeds feeds = new Feeds();
                                feeds.setFeeds(feedList);
                                displayFeeds("All");
                            }
                        }
                        else if (result == null) {
                            Log.v(currentTAG, "result null");
                        }
                    }
                });
    }

    protected void getTags() {
        Ion.with(getApplicationContext())
                .load(APIRoutes.GetTagsRoute())
                .setHeader("Authorization", APIRoutes.GetAuthorizationHeader(user))
                .asJsonArray()
                .withResponse()
                .then(new FutureCallback<Response<JsonArray>>() {
                    @Override
                    public void onCompleted(Exception e, Response<JsonArray> result) {
                        if (e != null) {
                            Log.v(currentTAG, e.toString());
                        }
                        else if (result != null) {
                            Log.v(currentTAG, "result not null");
                            Log.v(currentTAG, String.valueOf(result.getHeaders().code()));
                            if (result.getHeaders().code() == 200) {
                                Log.v(currentTAG, "RESULT :");
                                Log.v(currentTAG, result.getResult().toString());
                                JsonArray array = result.getResult();
                                ArrayList<String> tagList = new ArrayList<>();
                                for (int i = 0; i < array.size(); i++) {
                                    String tag = array.get(i).getAsString();
                                    tagList.add(tag);
                                }
                                Tags tags = new Tags();
                                tags.SetTagList(tagList);
                                displayTags();
                            }
                        }
                        else if (result == null) {
                            Log.v(currentTAG, "result null");
                        }
                    }
                });
    }

    protected void displayFeeds(String tagSelected) {
        Feeds feeds = new Feeds();
        ArrayList<Feed> feedList = feeds.getFeeds();

        // fill the RSS feeds
        try {
            for (int i = 0; i < feedList.size(); i++) {
                if (tagSelected.equals(feedList.get(i).getTags()) || tagSelected.equals("All")) {
                    feedNames.add(feedList.get(i).getName());
                    feedIDs.add(feedList.get(i).getId());
                }
            }

            ArrayAdapter<String> feedsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, feedNames);

            mFeedsView.setAdapter(feedsAdapter);

            mFeedsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    Intent intent = new Intent(getApplicationContext(), FeedActivity.class);
                    intent.putExtra("IDFEED", feedIDs.get(position));
                    startActivity(intent);
                }
            });
        } catch (Exception e) {
            Log.v(currentTAG, e.toString());
        }
    }

    protected void displayTags() {
        // fill the tags
        try {
            Tags t = new Tags();
            ArrayList<String> tags = t.GetTagList();

            for (int i = 0; i < tags.size(); i++) {
                tagsList.add(tags.get(i));
            }

            ArrayAdapter<String> tagsAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, tagsList);

            mTagsView.setAdapter(tagsAdapter);

            mTagsView.setSelection(0);

            mTagsView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    feedNames.clear();
                    feedIDs.clear();
                    String tagSelected = parentView.getItemAtPosition(position).toString();
                    displayFeeds(tagSelected);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
        } catch (Exception e) {
            Log.v(currentTAG, e.toString());
        }
    }

}