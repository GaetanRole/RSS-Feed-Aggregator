package olivier.sturbois.rss_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.ConvertFuture;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

public class AddFeedActivity extends AppCompatActivity {

    private static final String currentTAG = "AddFeedActivity";
    private static final String[] COUNTRIES = new String[] {
            "Belgium", "France", "Italy", "Germany", "Spain"
    };

    private EditText mNameView;
    private EditText mFeedView;
    private AutoCompleteTextView mTagsView;
    private Button mAddFeed;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_feed);

        this.setTitle("Add a RSS feed");

        mFeedView = (EditText) findViewById(R.id.url_text_edit);
        mNameView = (EditText) findViewById(R.id.name_text_edit);
        mUser = User.getUser();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, COUNTRIES);
        mTagsView = (AutoCompleteTextView) findViewById(R.id.tag_autocomplete_text_view);
        mTagsView.setThreshold(1);
        mTagsView.setAdapter(adapter);

        mAddFeed = (Button) findViewById(R.id.add_feed_button);

        mAddFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View focusView = null;
                mFeedView.setError(null);
                mNameView.setError(null);
                if (mNameView.getText().toString().matches("")) {
                    mNameView.setError(getString(R.string.error_field_required));
                    focusView = mFeedView;
                    focusView.requestFocus();
                } else if (mFeedView.getText().toString().matches("")) {
                    mFeedView.setError(getString(R.string.error_field_required));
                    focusView = mFeedView;
                    focusView.requestFocus();
                } else {
                    JsonObject json = new JsonObject();
                    json.addProperty("name", mNameView.getText().toString());
                    json.addProperty("url", mFeedView.getText().toString());
                    if (!mTagsView.getText().toString().matches("")) {
                        json.addProperty("tag", mTagsView.getText().toString());
                    }
                    Ion.with(getApplicationContext())
                            .load(APIRoutes.GetAddFeedRoute())
                            .setHeader("Authorization", APIRoutes.GetAuthorizationHeader(mUser))
                            .setJsonObjectBody(json)
                            .asJsonObject()
                            .withResponse()
                            .setCallback(new FutureCallback<Response<JsonObject>>() {
                                @Override
                                public void onCompleted(Exception e, Response<JsonObject> result) {
                                    if (result != null) {
                                        Log.v(currentTAG, String.valueOf(result.getHeaders().code()));
                                    }
                                    if (result != null && result.getHeaders().code() == 200) {
                                        JsonObject obj = result.getResult();
                                        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                }
            }
        });

    }
}
