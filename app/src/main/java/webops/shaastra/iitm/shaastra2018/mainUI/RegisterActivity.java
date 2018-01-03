package webops.shaastra.iitm.shaastra2018.mainUI;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import webops.shaastra.iitm.shaastra2018.R;
import webops.shaastra.iitm.shaastra2018.activities.NavigationActivity;
import webops.shaastra.iitm.shaastra2018.imageCaching.ImageUtil;

/**
 * A Register screen that offers Register via email/password.
 */
public class RegisterActivity extends AppCompatActivity{

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    private static final String NAME = "name",
        EMAIL = "email", PASSWORD = "password", CONFIRM_PASSWORD = "confirmPassword", COLLEGE = "college",
    CONTACT = "mob_no", CITY = "city", ISATTEND="lastyearcheck",GENDER = "gender";
    /**
     * Keep track of the Register task to ensure we can cancel it if requested.
     */
    private UserRegisterTask mAuthTask = null;

    // UI references.
    private TextView mEmailView;
    private EditText mName, mPasswordView, mConfirmPassword, mCollege, mContact, mCity;
    private RadioButton isAttend;
    private View mProgressView;
    private View mRegisterFormView, focusView;
    private JsonObjectRequest userSigninRequest;
    private RequestQueue queue;
    private RadioGroup genderRg,isAttendRg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // Set up the Register form.
        mEmailView = (TextView) findViewById(R.id.email);

        mName = (EditText) findViewById(R.id.register_form_field_name);
        mPasswordView = (EditText) findViewById(R.id.password);
        isAttend = (RadioButton)findViewById(R.id.register_form_yes);


        String imageURL = getString(R.string.shaastra_logo_url);

        ImageLoader imageLoader = ImageUtil.getImageLoader(this);

        ImageView imview = (ImageView) findViewById(R.id.login_shaastra_logo);

        imageLoader.displayImage(imageURL,imview);

        queue = Volley.newRequestQueue(this);


        mConfirmPassword = (EditText) findViewById(R.id.confirmPassword);
        mCollege = (EditText) findViewById(R.id.college);
        mContact = (EditText) findViewById(R.id.register_form_field_contact);
        mCity = (EditText) findViewById(R.id.register_form_field_city);


        Button mEmailRegisterButton = (Button) findViewById(R.id.email_register_button);
        mEmailRegisterButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegister();
            }
        });

        mRegisterFormView = findViewById(R.id.register_form);
        mProgressView = findViewById(R.id.register_progress);
    }
    
    
    /**
     * Attempts to register the account specified by the Register form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual Register attempt is made.
     */
    private void attemptRegister() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mName.setError(null);
        mEmailView.setError(null);
        mPasswordView.setError(null);
        mConfirmPassword.setError(null);
        mCollege.setError(null);
        mContact.setError(null);
        mCity.setError(null);

        // Store values at the time of the Register attempt.
        String  name = mName.getText().toString(),
                email = mEmailView.getText().toString(),
                password = mPasswordView.getText().toString(),
                confirmPassword = mConfirmPassword.getText().toString(),
                contact = mContact.getText().toString(),
                college = mCollege.getText().toString(),
                city = mCity.getText().toString();

        genderRg = (RadioGroup) findViewById(R.id.rg_gender);
        isAttendRg = (RadioGroup)findViewById(R.id.rg_isAttend);

        final JSONObject formEntries = new JSONObject();
        try {

            formEntries.put(NAME,name);
            formEntries.put(EMAIL,email);
            formEntries.put(PASSWORD,password);
            formEntries.put(CONFIRM_PASSWORD,confirmPassword);
            formEntries.put(COLLEGE,college);
            formEntries.put(CONTACT,contact);
            formEntries.put(CITY,city);

            // get selected radio button from radioGroup
            int selectedId = isAttendRg.getCheckedRadioButtonId();
            final String value = ((RadioButton)findViewById(selectedId)).getText().toString();

            int selectedId1 = genderRg.getCheckedRadioButtonId();
            final String value1 = ((RadioButton)findViewById(selectedId1)).getText().toString();

            formEntries.put(GENDER,value1);
            formEntries.put(ISATTEND,value);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        boolean cancel = false;
        focusView = null;

        cancel = validateFormEntries(formEntries);

        if (cancel) {
            // There was an error; don't attempt Register and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user Register attempt.
            showProgress(true);

            formEntries.remove(CONFIRM_PASSWORD);
            userSigninRequest = new JsonObjectRequest
                    (Request.Method.POST,"http://shaastra.org:8000/api/users",formEntries, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            Log.i("userSignRequestRespons", response.toString());
                            showProgress(false);

                            Intent i = new Intent(RegisterActivity.this,LoginActivity.class);
//                            try {
//                                i.putExtra("user-token", response.getString("token"));
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
                            startActivity(i);

                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {

                            showProgress(false);

                            mPasswordView.setError(getString(R.string.error_incorrect_password));
                            mPasswordView.requestFocus();

                            String body="";

                            if(error.networkResponse.data!=null && error.networkResponse.data.length>0) {
                                try {
                                    body = new String(error.networkResponse.data,"UTF-8");
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }
                            }

                            Log.i("volleyErrorBody",body);

                            JSONObject jsBody = null;
                            try {
                                jsBody = new JSONObject(body);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            String m = "";
                            try {
                                m = jsBody.getString("message");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            if(m.contains("password")) {
                                mPasswordView.setError(m);
                                mPasswordView.requestFocus();
                            }else{
                                mEmailView.setError(m);
                                mEmailView.requestFocus();
                            }

                        }
                    });

            queue.add(userSigninRequest);


//            mAuthTask = new UserRegisterTask(formEntries);
//            mAuthTask.execute((Void) null);
        }
    }

    private boolean validateFormEntries(JSONObject formEntries) {
        boolean cancel = false;

        try {
            if (!TextUtils.isEmpty(formEntries.getString(PASSWORD)) && !isPasswordValid(formEntries.getString(PASSWORD))){
                mPasswordView.setError(getString(R.string.error_invalid_password));
                focusView = mPasswordView;
                cancel = true;
            }
            // Check for a valid email address.
            if (TextUtils.isEmpty(formEntries.getString(EMAIL))) {
                mEmailView.setError(getString(R.string.error_field_required));
                focusView = mEmailView;
                cancel = true;
            } else if (!isEmailValid(formEntries.getString(EMAIL))) {
                mEmailView.setError(getString(R.string.error_invalid_email));
                focusView = mEmailView;
                cancel = true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }



        return cancel;
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }


    public void onRadioButtonClicked(View v){

    }


    /**
     * Shows the progress UI and hides the Register form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mRegisterFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
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
            mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    /**
     * Represents an asynchronous Register task used to create
     * the user.
     */
    public class UserRegisterTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserRegisterTask(ContentValues formEntries) {
            mEmail = formEntries.getAsString(EMAIL);
            mPassword = formEntries.getAsString(PASSWORD);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mEmail)) {
                    // Account exists, return true if the password matches.
                    return pieces[1].equals(mPassword);
                }
            }

            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                finish();
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}

