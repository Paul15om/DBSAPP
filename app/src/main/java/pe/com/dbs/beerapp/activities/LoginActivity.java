package pe.com.dbs.beerapp.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import pe.com.dbs.beerapp.R;
import pe.com.dbs.beerapp.constants.BarApi;
import pe.com.dbs.beerapp.models.Customer;
import pe.com.dbs.beerapp.runtime.ApiCliente;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;


public class LoginActivity extends AppCompatActivity /*implements LoaderCallbacks<Cursor>*/ {

    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "Jeral@1.com:zaperoko", "bar@1.com:bar12"
    };

    public static final String BASE_URL = "http://devnextdoor.com/api/";
    private static Retrofit retrofit = null;
    private UserLoginTask mAuthTask = null;
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private TextView mSignIn;
    private View mProgressView;
    private View mLoginFormView;
    private CallbackManager mCallBackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mCallBackManager = CallbackManager.Factory.create();
        LoginButton mLoginButton = (LoginButton) findViewById(R.id.loginButtonFacebook);
        mSignIn = (TextView) findViewById(R.id.link_to_login);
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    if (!isOnline()) {
                        showLoginError(getString(R.string.error_network));
                        return false;
                    }
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });
        mLoginButton.setReadPermissions(Arrays.asList(
                "public_profile", "email", "user_friends"));
        mLoginButton.registerCallback(mCallBackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {

                                try {
                                    mEmailView.setText(object.getString("email"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "email");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), R.string.com_facebook_loginview_cancel_action, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(), R.string.com_facebook_internet_permission_error_message, Toast.LENGTH_SHORT).show();
            }
        });
        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isOnline()) {
                    showLoginError(getString(R.string.error_network));
                    return;
                }
                attemptLogin();
            }
        });
        mSignIn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

                builder.setTitle("Titulo")
                        .setMessage("El Mensaje para el usuario")
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast toast1 =
                                                Toast.makeText(getApplicationContext(),
                                                        "OK", Toast.LENGTH_SHORT);

                                        toast1.show();
                                    }
                                })
                        .setNegativeButton("CANCELAR",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast toast1 =
                                                Toast.makeText(getApplicationContext(),
                                                        "CANCELAR", Toast.LENGTH_SHORT);

                                        toast1.show();
                                    }
                                });

                builder.create();
            }
        });
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }


    private boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }
    private void showLoginError(String error) {
        Toast.makeText(LoginActivity.this, error, Toast.LENGTH_LONG).show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallBackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

    }

    private void attemptLogin() {

        mEmailView.setError(null);
        mPasswordView.setError(null);

        String email = mEmailView.getText().toString();
        final String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        /*if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }*/
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }
        if (cancel) {
            focusView.requestFocus();
        } else {
            // Retrofit setup
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(JacksonConverterFactory.create())
                    .build();

            // Service setup
            BarApi apiService = ApiCliente.getClient().create(BarApi.class);

            // Prepare the HTTP request
            Call<Customer> call = apiService.login(email,password);

            // Asynchronously execute HTTP request
            call.enqueue(new Callback<Customer>() {
                @Override
                public void onResponse(Call<Customer> call, Response<Customer> response) {
                    // http response status code + headers
                    System.out.println("Response status code: " + response.code());
                    Toast.makeText(LoginActivity.this, "Response status code: " + response.code(), Toast.LENGTH_LONG).show();
/*
                    // isSuccess is true if response code => 200 and <= 300
                    if (!response.isSuccess()) {
                        // print response body if unsuccessful
                        try {
                            System.out.println(response.errorBody().string());
                        } catch (IOException e) {
                            // do nothing
                        }
                        return;
                    }

                    // if parsing the JSON body failed, `response.body()` returns null
                    Customer decodedResponse = response.body();
                    if (decodedResponse == null) return;

                    // at this point the JSON body has been successfully parsed
                    System.out.println("Response (contains request infos):");
                    System.out.println("- url:         " + decodedResponse.url);
                    System.out.println("- ip:          " + decodedResponse.origin);
                    System.out.println("- headers:     " + decodedResponse.headers);
                    System.out.println("- args:        " + decodedResponse.args);
                    System.out.println("- form params: " + decodedResponse.form);
                    System.out.println("- json params: " + decodedResponse.json);*/
                }

                @Override
                public void onFailure(Call<Customer> call, Throwable t) {
                    System.out.println("onFailure");
                    System.out.println(t.getMessage());
                }


            });

        }
    }

    public void onBackPressed() {
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
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
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    private void showAppointmentsScreen() {
        startActivity(new Intent(LoginActivity.this, BarActivity.class));
        finish();
    }

    public class UserLoginTask extends AsyncTask<Void, Void, Integer> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Integer doInBackground(Void... params) {

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return 3;
            }

            for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mEmail)) {
                    return 2;
                }

                if (pieces[1].equals(mPassword)) {
                    return 3;
                }

            }
            return 1;
            /*1=Petición exitosa
              2=email incorrecto
              3=Password incorrecto
              4=Error del servidor*/
        }

        protected void onPostExecute(final Integer success) {
            mAuthTask = null;
            showProgress(false);

            switch (success) {
                case 1:
                   // showAppointmentsScreen();
                    break;
                case 2:
                    showLoginError(getString(R.string.error_invalid_email));
                    break;
                case 3:
                    showLoginError(getString(R.string.error_invalid_password));
                    break;
                case 4:
                    showLoginError(getString(R.string.error_server));
                    break;
            }
        }



        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }

    }
}

