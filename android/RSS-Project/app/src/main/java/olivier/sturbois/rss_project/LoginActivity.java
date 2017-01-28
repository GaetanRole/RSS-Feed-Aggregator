package olivier.sturbois.rss_project;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;

import android.os.AsyncTask;
import android.content.Intent;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.util.Log;

import com.anupcowkur.reservoir.Reservoir;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import org.json.JSONObject;

/**
 * A login screen that offers login via login/password.
 */
public class LoginActivity extends AppCompatActivity {

    private static final String currentTAG = "LoginActivity";

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;
    private checkConnectivityTask mConnectivityTask = null;

    // UI references.
    private EditText mLoginView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.setTitle("Login");

        try {
            Reservoir.init(this, 2048);
        } catch (Exception e) {

        }

        // Set up the login form.
        mLoginView = (EditText) findViewById(R.id.login);

        mPasswordView = (EditText) findViewById(R.id.password);

        Button mSignInButton = (Button) findViewById(R.id.sign_in_button);
        mSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        showProgress(true);
        mConnectivityTask = new checkConnectivityTask();
        mConnectivityTask.execute((Void) null);
    }

    /**
     * Attempts to sign in specified by the login form.
     * If there are form errors (invalid login, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mLoginView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String login = mLoginView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid login
        if (TextUtils.isEmpty(login)) {
            mLoginView.setError(getString(R.string.error_field_required));
            focusView = mLoginView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            //focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            /*
            mAuthTask = new UserLoginTask(login, password);
            mAuthTask.execute((Void) null);
            */
            User user = new User();
            user.setLogin(login);
            user.setPassword(password);
            Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
            User.setUser(user);
            startActivity(intent);
            finish();
        }
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    /**
     * Represents an asynchronous task used to check if internet is enabled.
     */

    public class checkConnectivityTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected  Boolean doInBackground(Void... params) {
            try {
                ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
                if (isConnected) {
                    return true;
                } else {
                    return false;
                }
            } catch (Exception e){
                Log.v(currentTAG, e.toString());
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mConnectivityTask = null;
            showProgress(false);

            if (success) {
                Log.v(currentTAG, "All good, connectivity ok");
            } else {
                Log.v(currentTAG, "Connectivity failed");
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                intent.putExtra("ISCONNECTED", false);
                startActivity(intent);
                finish();
            }
        }


        @Override
        protected void onCancelled() {
            mConnectivityTask = null;
            Log.v(currentTAG, "Cancelled");
            showProgress(false);
            Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
            User.setUser(null);
            startActivity(intent);
            finish();
        }
    }

    /**
     * Represents an asynchronous login task used to authenticate the user.
     */

    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mLogin;
        private final String mPassword;

        UserLoginTask(String login, String password) {
            mLogin = login;
            mPassword = password;
            Log.v(currentTAG, mLogin);
            Log.v(currentTAG, mPassword);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                JsonObject json = new JsonObject();
                json.addProperty("username", mLogin);
                json.addProperty("password", mPassword);

                /*
                Ion.with(getApplicationContext())
                        .load(APIRoutes.GetLoginRoute())
                        .setJsonObjectBody(json)
                        .asJsonObject()
                        .withResponse()
                        .then(new FutureCallback<Response<JsonObject>>() {
                            @Override
                            public void onCompleted(Exception e, Response<JsonObject> result) {
                                if (result != null && result.getHeaders().code() == 201) {
                                    Log.v(currentTAG, "RESULT :");
                                    Log.v(currentTAG, result.toString());
                                    onLoginFinished(true);
                                }
                                else {
                                    onLoginFinished(false);
                                }
                            }
                        });
                        */
                onLoginFinished(true);
            } catch (Exception e) {
                Log.v(currentTAG, e.toString());
                onLoginFinished(false);
            }
            return false;
        }

        protected void onLoginFinished(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                Log.v(currentTAG, "All good, logged in");
                User user = new User();
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_credentials));
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}