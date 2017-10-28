package webops.shaastra.iitm.shaastra2018.mainUI;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import webops.shaastra.iitm.shaastra2018.R;

import static android.Manifest.permission.READ_CONTACTS;

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
    CONTACT = "contact", CITY = "city";
    /**
     * Keep track of the Register task to ensure we can cancel it if requested.
     */
    private UserRegisterTask mAuthTask = null;

    // UI references.
    private TextView mEmailView;
    private EditText mName, mPasswordView, mConfirmPassword, mCollege, mContact, mCity;
    private View mProgressView;
    private View mRegisterFormView, focusView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // Set up the Register form.
        mEmailView = (TextView) findViewById(R.id.email);

        mName = (EditText) findViewById(R.id.register_form_field_name);
        mPasswordView = (EditText) findViewById(R.id.password);
        /*mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.register || id == EditorInfo.IME_NULL) {
                    attemptRegister();
                    return true;
                }
                return false;
            }
        });*/

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
                confirmPassword = mEmailView.getText().toString(),
                college = mEmailView.getText().toString(),
                contact = mEmailView.getText().toString(),
                city = mEmailView.getText().toString();

        ContentValues formEntries = new ContentValues();
        formEntries.put(NAME,name);
        formEntries.put(EMAIL,email);
        formEntries.put(PASSWORD,password);
        formEntries.put(CONFIRM_PASSWORD,confirmPassword);
        formEntries.put(COLLEGE,college);
        formEntries.put(CONTACT,contact);
        formEntries.put(CITY,city);

        boolean cancel = false;
        focusView = null;


        cancel = validateFormEntries(formEntries);

        /*// Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }*/

        if (cancel) {
            // There was an error; don't attempt Register and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user Register attempt.
            showProgress(true);
            mAuthTask = new UserRegisterTask(formEntries);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean validateFormEntries(ContentValues formEntries) {
        boolean cancel = false;

        if (!TextUtils.isEmpty(formEntries.getAsString(PASSWORD)) && !isPasswordValid(formEntries.getAsString(PASSWORD))){
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(formEntries.getAsString(EMAIL))) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(formEntries.getAsString(EMAIL))) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
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

